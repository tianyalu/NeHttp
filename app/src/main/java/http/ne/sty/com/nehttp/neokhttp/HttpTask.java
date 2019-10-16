package http.ne.sty.com.nehttp.neokhttp;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {
    private IHttpRequest request;
    private TaskListener taskListener;

    public HttpTask(String url, T requestData, IHttpRequest httpRequest, CallBackListener listener, TaskListener taskListener ){
        this.request = httpRequest;
        this.taskListener = taskListener;
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
        try {
            //执行具体的网络请求操作
            this.request.execute();
        }catch (Exception e) {
            //将失败的任务添加到重试队列中
            ThreadPoolManager.getInstance().addDelay(this);
        }
    }

    public interface TaskListener{
        void onTaskFailure();
    }

    public TaskListener getTaskListener() {
        return taskListener;
    }

    private long delayTime;
    private int retryCount;

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        //设置延迟时间 3000
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public long getDelay(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed delayed) {
        return 0;
    }
}
