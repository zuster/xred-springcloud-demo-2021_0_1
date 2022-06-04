EnvironmentRepository 接口用于获取配置。
```java
public interface EnvironmentRepository {
    
	Environment findOne(String application, String profile, String label);

	default Environment findOne(String application, String profile, String label, boolean includeOrigin) {
		return findOne(application, profile, label);
	}

}
```
从接口可以看到，获取 Environment 有3个纬度，application、profile和label。  
这里得到的 Environment 并不是 Spring Core 里的 Environment 接口，而是 Spring Cloud 封装的一个类，包括如下属性：
```java 
    // String 配置文件，对应 application 这个纬度
    private String name;
    // 生效的 active profile，对应 profile 这个纬度
    private String[] profiles;
    // 对应 label 这个纬度
    private String label;
    // 加载的数据源集合
    private List<PropertySource> propertySources;
    // 版本号
    private String version;
    // 状态
    private String state;
```
目前 EnvironmentRepository 具体的实现有：
![](https://tva1.sinaimg.cn/large/e6c9d24egy1h2u7qptdl5j28fi0u04c9.jpg)
- JGitEnvironmentRepository
- SvnKitEnvironmentRepository
- JdbcEnvironmentRepository
- NativeEnvironmentRepository
- RedisEnvironmentRepository
- ......

Spring Cloud Config Server 内部自带一个对外暴露 HTTP API 的 EnvironmentController ,该 controller 提供对外暴露的各个方法会获取配置信息，在获取过程中调用 EnvironmentRepository 的 findOne 方法。

```java
package org.springframework.cloud.config.server.environment;

...

@RestController
@RequestMapping(method = RequestMethod.GET, path = "${spring.cloud.config.server.prefix:}")
public class EnvironmentController {

    ...

	@GetMapping(path = "/{name}/{profiles:[^-]+}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Environment defaultLabel(@PathVariable String name, @PathVariable String profiles) {
		return getEnvironment(name, profiles, null, false);
	}

	@GetMapping(path = "/{name}/{profiles:[^-]+}", produces = EnvironmentMediaType.V2_JSON)
	public Environment defaultLabelIncludeOrigin(@PathVariable String name, @PathVariable String profiles) {
		return getEnvironment(name, profiles, null, true);
	}

	@GetMapping(path = "/{name}/{profiles}/{label:.*}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Environment labelled(@PathVariable String name, @PathVariable String profiles, @PathVariable String label) {
		return getEnvironment(name, profiles, label, false);
	}

	@GetMapping(path = "/{name}/{profiles}/{label:.*}", produces = EnvironmentMediaType.V2_JSON)
	public Environment labelledIncludeOrigin(@PathVariable String name, @PathVariable String profiles,
			@PathVariable String label) {
		return getEnvironment(name, profiles, label, true);
	}

	public Environment getEnvironment(String name, String profiles, String label, boolean includeOrigin) {
		try {
			name = normalize(name);
			label = normalize(label);
			Environment environment = this.repository.findOne(name, profiles, label, includeOrigin);
			if (!this.acceptEmpty && (environment == null || environment.getPropertySources().isEmpty())) {
				throw new EnvironmentNotFoundException("Profile Not found");
			}
			return environment;
		}
		catch (Exception e) {
			LOG.warn(String.format("Error getting the Environment with name=%s profiles=%s label=%s includeOrigin=%b",
					name, profiles, label, includeOrigin), e);
			throw e;
		}
	}

    ...
}
```
## NativeEnvironmentRepository
NativeEnvironmentRepository 生效的前提是配置文件里 active profile 需要一个 native 的 profile。
```java
// org.springframework.cloud.config.server.config.EnvironmentRepositoryConfiguration
@Configuration(proxyBeanMethods = false)
@Profile("native")
class NativeRepositoryConfiguration {

	@Bean
	public NativeEnvironmentRepository nativeEnvironmentRepository(NativeEnvironmentRepositoryFactory factory,
			NativeEnvironmentProperties environmentProperties) {
		return factory.build(environmentProperties);
	}

}
```
NativeEnvironmentRepository 获取配置过程：
```java
// org.springframework.cloud.config.server.environment.NativeEnvironmentRepository
@Override
public Environment findOne(String config, String profile, String label, boolean includeOrigin) {
    try {
        ConfigurableEnvironment environment = getEnvironment(config, profile, label);
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Map<org.springframework.core.env.PropertySource<?>, PropertySourceConfigData> propertySourceToConfigData = new HashMap<>();
        ConfigDataEnvironmentPostProcessor.applyTo(environment, resourceLoader, null,
        StringUtils.commaDelimitedListToSet(profile), new ConfigDataEnvironmentUpdateListener() {
            @Override
            public void onPropertySourceAdded(org.springframework.core.env.PropertySource<?> propertySource,
                    ConfigDataLocation location, ConfigDataResource resource) {
                    propertySourceToConfigData.put(propertySource,
                    new PropertySourceConfigData(location, resource));
            }
        });

        environment.getPropertySources().remove("config-data-setup");
        return clean(new PassthruEnvironmentRepository(environment).findOne(config, profile, label, includeOrigin),
        propertySourceToConfigData);
    }
    catch (Exception e) {
        String msg = String.format("Could not construct context for config=%s profile=%s label=%s includeOrigin=%b",
        config, profile, label, includeOrigin);
        String completeMessage = NestedExceptionUtils.buildMessage(msg,
        NestedExceptionUtils.getMostSpecificCause(e));
        throw new FailedToConstructEnvironmentException(completeMessage, e);
    }
}
```
