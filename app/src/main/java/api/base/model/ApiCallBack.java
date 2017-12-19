package api.base.model;

public abstract class ApiCallBack {


    public void onSuccess(boolean isCache, String data) {
    }

    public void onSuccess(boolean isCache, Response response) {

    }

    public void onFailed(String msg) {
    }
}
