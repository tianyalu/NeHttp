package http.ne.sty.com.nehttp.neokhttp;

public interface IHttpRequest {
    void setUrl(String url);

    void setData(byte[] data);

    void setListener(CallBackListener listener);

    void execute();
}
