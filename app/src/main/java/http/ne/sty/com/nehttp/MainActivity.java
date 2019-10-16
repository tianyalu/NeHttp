package http.ne.sty.com.nehttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import http.ne.sty.com.nehttp.neokhttp.HttpTask;
import http.ne.sty.com.nehttp.neokhttp.IJsonDataTransformListener;

public class MainActivity extends AppCompatActivity {
    private TextView tvText;
//    private String url = "http://v.juhe.cn/historyWeather/citys?province_id=2&key=bb52107206585ab074f5e59a8c73875b";
    private String url = "xxx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = findViewById(R.id.tv_text);

        request();
    }

    private void request() {
        /**
         * 请求失败会进行三次重试，都失败后才是真正失败，走onTaskFailure()方法
         */
        NeOkHttp.sendJsonRequest(url, null, ResponseBean.class, new IJsonDataTransformListener<ResponseBean>() {

            @Override
            public void onSuccess(ResponseBean m) {
                Log.i("sty", "request success: ==> " + m.toString());
                tvText.setText(m.toString());
            }

            @Override
            public void onFailure() {
                Log.i("sty", "request failed: ==> ");
            }
        }, new HttpTask.TaskListener() {
            @Override
            public void onTaskFailure() {
                Log.i("sty", "request task failed: finished ");
                tvText.setText("request failed");
            }
        });
    }
}
