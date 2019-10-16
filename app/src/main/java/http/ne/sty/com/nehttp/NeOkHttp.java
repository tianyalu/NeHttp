package http.ne.sty.com.nehttp;

import http.ne.sty.com.nehttp.neokhttp.CallBackListener;
import http.ne.sty.com.nehttp.neokhttp.HttpTask;
import http.ne.sty.com.nehttp.neokhttp.IHttpRequest;
import http.ne.sty.com.nehttp.neokhttp.IJsonDataTransformListener;
import http.ne.sty.com.nehttp.neokhttp.JsonCallbackListener;
import http.ne.sty.com.nehttp.neokhttp.JsonHttpRequest;
import http.ne.sty.com.nehttp.neokhttp.ThreadPoolManager;

public class NeOkHttp {
    public static<T,M> void sendJsonRequest(String url, T requestData, Class<M> response,
                                            IJsonDataTransformListener listener, HttpTask.TaskListener taskListener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallBackListener callBackListener = new JsonCallbackListener<>(response, listener);
        HttpTask httpTask = new HttpTask(url, requestData,  httpRequest, callBackListener, taskListener);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
