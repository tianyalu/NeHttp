package http.ne.sty.com.nehttp.neokhttp;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

public class HttpTask<T> implements Runnable {
    private IHttpRequest request;

    public HttpTask(String url, T requestData, IHttpRequest httpRequest, CallBackListener listener ){
        this.request = httpRequest;
        httpRequest.setUrl(url);
        httpRequest.setListener(listener);
        String content = JSON.toJSONString(requestData);
        try {
            httpRequest.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //执行具体的网络请求操作
        this.request.execute();
    }
}
