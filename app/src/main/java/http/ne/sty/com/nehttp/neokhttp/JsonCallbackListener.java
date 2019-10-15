package http.ne.sty.com.nehttp.neokhttp;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;

public class JsonCallbackListener<T> implements CallBackListener {
    private Class<T> responseClass;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonCallbackListener(Class<T> responseClass, IJsonDataTransformListener listener) {
        this.responseClass = responseClass;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //将源转换为对应的bean
        String response = getContent(inputStream);
        T clazz = JSON.parseObject(response, responseClass);
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure() {

    }
}
