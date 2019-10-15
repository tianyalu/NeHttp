package http.ne.sty.com.nehttp.neokhttp;

public class JsonHttpRequest implements IHttpRequest {
    private String url;
    private byte[] data;
    private CallBackListener callBackListener;

    @Override
    public void setUrl(String url) {
        this.url =  url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallBackListener listener) {
        this.callBackListener = listener;
    }

    @Override
    public void execute() {

    }
}
