package http.ne.sty.com.nehttp.neokhttp;

public class NeOkHttp {
    public static<T,M> void sendJsonRequest(String url, T requestData, Class<M> response,
                                              IJsonDataTransformListener listener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallBackListener callBackListener = new JsonCallbackListener<>(response, listener);
        HttpTask httpTask = new HttpTask(url, requestData,  httpRequest, callBackListener);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
