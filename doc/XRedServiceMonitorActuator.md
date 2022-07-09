# Actuator

## 1 什么是 Actuator
Actuator是Spring Boot提供的应用系统监控的开源框架，它是Spring Boot体系中非常重要的组件。它可以轻松实现应用程序的监控治理，支持通过众多REST接口、远程Shell和JMX收集应用的运行情况。

Actuator的核心是端点（Endpoint），它用来监视、提供应用程序的信息，SpringBoot提供的spring-boot-actuator组件中已经内置了非常多的Endpoint（health、info、beans、metrics、httptrace、shutdown等），每个端点都可以启用和禁用。

Actuator也允许我们扩展自己的端点。通过JMX或HTTP的形式暴露自定义端点，Actuator会将自定义端点的ID默认映射到一个带/actuator前缀的URL。比如，health端点默认映射到/actuator/health。这样就可以通过HTTP的形式获取自定义端点的数据。

Actuator同时还可以与外部应用监控系统整合，比如Prometheus、Graphite、DataDog、Influx、Wavefront、New Relic等。这些系统提供了非常好的仪表盘、图标、分析和告警等功能，使得你可以通过统一的接口轻松地监控和管理你的应用系统。这对于实施微服务的中小团队来说，无疑是一种快速高效的解决方案。

## 2 Spring Boot 集成 Actuator

在Spring Boot项目中集成Actuator非常简单，只需要在项目中添加spring-boot-starter-actuator组件，就能自动启动应用监控的功能。

- 添加依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
如上面的示例所示，我们添加了actuator和web两个组件。spring-boot-starter-actuator除了可以监控Web系统外，还可以监控后台服务等Spring Boot应用。

然后，修改配置文件，配置Actuator端点：
```yaml
server:
  port: 10041
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
```
最后，启动项目并在浏览器中输入http://localhost:10041/actuator，我们可以看到返回的是Actuator提供的各种数据接口信息。

如下所示，Actuator提供了丰富的数据接口，包括/actuator/health、/actuator/env、/actuator/metrics等。下面我们请求其中的一个地址/actuator/health，查看接口返回的详细信息。

```json
{
  "_links": {
    "self": {
      "href": "http://127.0.0.1:10041/actuator",
      "templated": false
    },
    "beans": {
      "href": "http://127.0.0.1:10041/actuator/beans",
      "templated": false
    },
    "caches": {
      "href": "http://127.0.0.1:10041/actuator/caches",
      "templated": false
    },
    "caches-cache": {
      "href": "http://127.0.0.1:10041/actuator/caches/{cache}",
      "templated": true
    },
    "health-path": {
      "href": "http://127.0.0.1:10041/actuator/health/{*path}",
      "templated": true
    },
    "health": {
      "href": "http://127.0.0.1:10041/actuator/health",
      "templated": false
    },
    "info": {
      "href": "http://127.0.0.1:10041/actuator/info",
      "templated": false
    },
    "conditions": {
      "href": "http://127.0.0.1:10041/actuator/conditions",
      "templated": false
    },
    "configprops": {
      "href": "http://127.0.0.1:10041/actuator/configprops",
      "templated": false
    },
    "configprops-prefix": {
      "href": "http://127.0.0.1:10041/actuator/configprops/{prefix}",
      "templated": true
    },
    "env-toMatch": {
      "href": "http://127.0.0.1:10041/actuator/env/{toMatch}",
      "templated": true
    },
    "env": {
      "href": "http://127.0.0.1:10041/actuator/env",
      "templated": false
    },
    "loggers-name": {
      "href": "http://127.0.0.1:10041/actuator/loggers/{name}",
      "templated": true
    },
    "loggers": {
      "href": "http://127.0.0.1:10041/actuator/loggers",
      "templated": false
    },
    "heapdump": {
      "href": "http://127.0.0.1:10041/actuator/heapdump",
      "templated": false
    },
    "threaddump": {
      "href": "http://127.0.0.1:10041/actuator/threaddump",
      "templated": false
    },
    "metrics": {
      "href": "http://127.0.0.1:10041/actuator/metrics",
      "templated": false
    },
    "metrics-requiredMetricName": {
      "href": "http://127.0.0.1:10041/actuator/metrics/{requiredMetricName}",
      "templated": true
    },
    "scheduledtasks": {
      "href": "http://127.0.0.1:10041/actuator/scheduledtasks",
      "templated": false
    },
    "mappings": {
      "href": "http://127.0.0.1:10041/actuator/mappings",
      "templated": false
    }
  }
}
```

访问 http://127.0.0.1:10041/actuator/health 接口返回了系统详细的健康状态信息，包括系统的状态（UP为正常）、磁盘使用情况等信息。

```json
{
    "status": "UP",
    "components": {
        "diskSpace": {
            "status": "UP",
            "details": {
                "total": 250685575168,
                "free": 43504340992,
                "threshold": 10485760,
                "exists": true
            }
        },
        "ping": {
            "status": "UP"
        }
    }
}
```
## 3 Actuator 监控端点
### 3.1 监控端点
Actuator监控分成两类：原生端点和用户自定义端点。  
原生端点是Actuator组件内置的，在应用程序中提供了众多Web接口，通过它们了解应用程序运行时的内部情况，原生端点可以分成3类：
- 应用配置类：可以查看应用在运行期的静态信息，比如自动配置信息、加载的Spring Bean信息、YML文件配置信息、环境信息、请求映射信息。
- 度量指标类：主要是运行期的动态信息，如堆栈、请求连接、健康状态、系统性能等。
- 操作控制类：主要是指shutdown，用户可以发送一个请求将应用的监控功能关闭。

Actuator提供了非常多的原生监控端点，比如health端点提供基本的应用程序运行状况信息，具体接口及说明如下所示。

| 端点          | 说明                                                    | JMX | HTTP |
|-------------|-------------------------------------------------------|-----|------|
| auditevents | 显示应用暴露的审计事件（如认证进入、订单失败）                               | Y   | N    |
| beans       | 描述应用程序上下文中全部的Bean以及他们的关系                              | Y   | N    |
| conditions  | 就是1.0的/autoconfig，提供一份自动配置生效的条件情况，记录哪些自动配置条件通过了，哪些没通过 | Y   | N    |
| configprops | 描述配置属性（包含默认值）如何注入Bean                                 | Y   | N    |
| env         | 获取全部环境属性                                              | Y   | N    |
| env/{name}  | 根据名称获取特定的环境属性值                                        | Y   | N    |
| shutdown    | 允许优雅地关闭应用程序                                           | Y   | N    |
| metrics     | 描述程序中各种度量信息，比如内存用量、HTTP请求数                            | Y   | N    |
| health      | 报告应用程序的健康指标，这些值由HealthIndicator的实现类提供                 | Y   | Y    |
| heapdump    | dump一份应用的JVM堆信息                                       | N/A | N    |
| httptrace   | 显示HTTP足迹，最近100个HTTP请求/响应                              | Y   | N    |
| info        | 获取应用程序的定制信息，这些信息由 info 打头的属性提供                        | Y   | Y    |
| logfile     | 返回 log file 中的内容（如果logging.file或者logging.path被设置）     | N/A | N    |


Spring Boot包括许多内置的端点，并且支持JMX或者HTTP访问。HTTP默认对外公开只有health和info两个端点。

同时，Actuator支持自定义端点，用户可以根据自己的实际应用定义一些比较关心的指标，在运行期进行监控。使用@Endpoint、@JmxEndpoint、@WebEndpoint等注解实现对应的方法即可定义一个Actuator中的自定义端点。

### 3.2 配置监控端点
为了安全起见，在Spring Boot 2.x中，Actuator的HTTP接口默认只开放了/actuator/health和/actuator/info两个端点。下表显示了这两个属性的默认配置情况，公开或关闭端点受到include和exclude属性控制。

| 属性                                        | 默认值         |
|-------------------------------------------|-------------|
| management.endpoints.jmx.exposure.exclude ||
| management.endpoints.jmx.exposure.include | *           |
| management.endpoints.web.exposure.exclude ||
| management.endpoints.web.exposure.include | info,health |

- 开启端点
如果需要打开所有的监控点，可以在配置文件中设置为打开。具体设置如下：
```properties
management.endpoints.web.exposure.include=*
```
当然，也可以选择打开部分监控点：
```properties
management.endpoints.web.exposure.exclude=beans,trace
```
- 配置路径
Actuator默认所有的监控点路径都在/actuator/*，当然也可以定制这个路径：
```properties
management.endpoints.web.base-path=/manage
```
设置完重启后，再次访问地址就会变成/manage/*。
### 3.3 自定义端点
Spring Boot支持自定义端点，只需要在我们定义的类中使用@Endpoint、@JmxEndpoint、@WebEndpoint等注解，实现对应的方法即可定义一个Actuator中的自定义端点。  
从Spring Boot 2.x版本开始，Actuator支持CRUD（增删改查）模型，而不是旧的RW（读/写）模型。  
我们可以按照3种策略来自定义：
- 使用@Endpoint注解，同时支持JMX和HTTP方式。
- 使用@JmxEndpoint注解，只支持JMX技术。
- 使用@WebEndpoint注解，只支持HTTP。  

编写自定义端点类很简单，只需要在类前面使用@Endpoint注解，然后在类的方法上使用@ReadOperation、@WriteOperation或@DeleteOperation（分别对应HTTP中的GET、POST、DELETE）等注解获取、设置端点信息。

参考[自定义获取时间Endpoint](../xred-service-monitor/xred-service-monitor-admin-client/src/main/java/com/xred/service/monitor/admin/client/endpoints/SystemTimeEndpoint.java)。

配置好端点后，即可访问 http://localhost:10043/actuator/systemTime 查看配置信息。

## 参考
- [Spring Boot 参考指南（端点）](https://segmentfault.com/a/1190000015309478?utm_source=tag-newest)
