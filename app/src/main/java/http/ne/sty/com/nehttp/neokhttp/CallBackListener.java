package http.ne.sty.com.nehttp.neokhttp;

import java.io.InputStream;

public interface CallBackListener {
    void onSuccess(InputStream inputStream);

    void onFailure();
}
