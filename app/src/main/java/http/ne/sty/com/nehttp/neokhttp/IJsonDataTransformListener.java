package http.ne.sty.com.nehttp.neokhttp;

public interface IJsonDataTransformListener<T> {
    void onSuccess(T m);

    void onFailure();
}
