# 熔断器 - Circuit Breaker
> 来源：《深入理解 Spring Cloud 与实战》
## 概述
熔断器（也叫断路器）不但在软件设计中会遇到，在现实生活中也会经常遇到。例如，有装修经验的朋友一定知道强电箱，强电箱上会安装断路器，用于安全用电。当发生一些特殊情况，比如水电工师傅在厨房水槽下面安装开关面板时，在安装过程中，如果不小心碰到了打开的水龙头，当水和电接触时，强电箱上的总开关就会自动跳闸，断掉所有的电来保护水电工师傅。

再如，群租房的朋友可能遇到过这种场景，一套房里隔断的房间太多，每个房间都安装了空调。夏天到来的时候，每个房间都开着空调，导致用电负载过大，也会自动跳闸。

同样，在软件系统中也会遇到类似的情况。比如，应用依赖的外部 API 不可用，该应用一直在超时并重试，从而引发应用整体无法提供服务，如果这时能有一个类似断路器的组件（模式）让客户端在发生多次调用失败的情况下不再重试，继续调用，则可以让服务器能够健康地运行。这个组件（模式）就是熔断器模式（Circuit Breaker Pattern）。如下图所示，微软的云计算设计模式文档对断路器模式的实现进行了说明。

![](https://tva1.sinaimg.cn/large/e6c9d24egy1h3zqtk81m8j20ot0kh75w.jpg)

从图中可以看到，熔断器有3种状态：Closed（关闭）、Half-Open（半开启）和Open（开启）。

- Closed: 默认状态。Circuit Breaker 内部维护着最近失败的次数（failure count）。操作每失败一次，就会增加一次失败次数。当错误数达到一定阈值时，就会从 Closed 状态进入 Open 状态。
- Open: 操作不会执行，而是立即失败，Circuit Breaker 内部维护着一个计时器，当时间达到一定阈值时，就会从 Open 状态进入 Half-Open 状态。
- Half-Open: 只要操作失败1次，立即进入 Open 状态；否则增加操作成功的次数，当成功次数达到一定阈值时，进入 Closed 状态并重置失败次数。

## 实现
### 自定义

### Alibaba Sentinel

### Netflix Hystrix

| 配置项                                                 | 作用                                                     | 默认值                                                  |
|-----------------------------------------------------|--------------------------------------------------------|------------------------------------------------------|
| execution.isolation.strategy                        | 隔离策略：线程池隔离（THREAD）；信号量隔离（SEMAPHORE）                    | THREAD (线程池隔离)                                       |
| execution.isolation.thread.timeoutInMilliseconds    | 执行超时时间，超时后进入 fallback 逻辑                               | 1000 (ms)                                            |
| execution.timeout.enabled                           | 执行超时时间是否生效。开启后超时时间配置才会生效                               | true                                                 |
| execution.isolation.thread.interruptOnTimeout       | 执行超时后是否可以被打断                                           | true                                                 |
| execution.isolation.thread.interruptOnCancel        | 执行被取消时是否可以被打断                                          | true                                                 |
| execution.isolation.semaphore.maxConcurrentRequests | 使用信号量隔离策略后最大QPS数                                       | 10 (QPS)                                             |
| fallback.isolation.semaphore.maxConcurrentRequests  | getCallback()方法最大请求数                                   | 10                                                   |
| fallback.enabled                                    | getCallback()方法是否生效                                    | true                                                 |
| circuitBreaker.enabled                              | 熔断功能是否开启                                               | true                                                 |
| circuitBreaker.requestVolumeThreshold               | 时间窗口内最小能触发熔断的请求数（如果时间窗口内没有达到最小请求数，即使异常比率达标，也不会触发熔断效果）  | 20                                                   |
| circuitBreaker.sleepWindowInMilliseconds            | 熔断从 Open 状态进入 Half-Open 状态的时间                          | 5000 (ms)                                            |
| circuitBreaker.errorThresholdPercentage             | 异常比率。时间窗口内最小能触发熔断的请求数中错误请求数占比                          | 50 (百分比)                                             |
| circuitBreaker.forceOpen                            |||
| circuitBreaker.forceClosed                          |||
| metrics.rollingStats.timeInMilliseconds             |||
| metrics.rollingStats.numBuckets                     |||
| metrics.rollingPercentile.enabled                   |||
| metrics.rollingPercentile.timeInMilliseconds        |||
| metrics.rollingPercentile.numBuckets                |||
| metrics.rollingPercentile.bucketSize                |||
| metrics.healthSnapshot.intervalInMilliseconds       |||
| requestCache.enabled                                || true                                                   |
| requestLog.enabled                                  | 每次 HystrixCommand 执行都会被日志记录                            | true                                                 |
| coreSize                                            | 使用线程池隔离策略时核心线程数                                        | 10                                                   |
| maximumSize                                         | 线程池最大线程数                                               | 10                                                   |
| maxQueueSize                                        | 线程池队列长度                                                | -1 (使用 SynchronousQueue 队列，否则使用 LinkedBlockingQueue) |
| queueSizeRejectionThreshold                         | 队列拒绝阈值，如果设置该值，线程池内的队列长度无效 (maxQueueSize 参数等于 -1，该配置无效) | 5 (队列长度)                                             |
| keepAliveTimeMinutes                                | 核心线程数小于最大线程数时，那些多出来的线程的存活时间                            | 1 min                                                |
| allowMaximumSizeToDivergeFromCoreSize               | 设置为 true 时，当最大线程池比核心线程池数小时，最大线程数会使用核心线程数               | false                                                |

### Resilience4j

## 应用
### 基于Sentinel进行应用保护及防止服务雪崩