### NeHttp 类OkHttp网络访问框架-线程池，请求队列，失败重试机制...
思路：
首先创建`httpTask`管理类，其中包含了`requestData， JsonHttpRequest， CallBackListener`等，然后将该任务加入
队列中，线程池管理类`ThreadPoolManager`执行死循环，从队列中取出任务执行(失败任务会放在延时队列中，延时请求)。  
参考：  
![image](https://github.com/tianyalu/NeHttp/blob/master/show/analyse.png)