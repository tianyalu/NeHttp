package http.ne.sty.com.nehttp.neokhttp;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallbackListener<T> implements CallBackListener {
    private Class<T> responseClass;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private IJsonDataTransformListener mIJsonDataTransformListener;

    public JsonCallbackListener(Class<T> responseClass, IJsonDataTransformListener listener) {
        this.responseClass = responseClass;
        this.mIJsonDataTransformListener = listener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //将源转换为对应的bean
        String response = getContent(inputStream);
        final T clazz = JSON.parseObject(response, responseClass);
        if(clazz == null) {
            mIJsonDataTransformListener.onFailure();
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mIJsonDataTransformListener != null) {
                    mIJsonDataTransformListener.onSuccess(clazz);
                }
            }
        });
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                }catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            content = sb.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return content;
    }

    @Override
    public void onFailure() {

    }
}
