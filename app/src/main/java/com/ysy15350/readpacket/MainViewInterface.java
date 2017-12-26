package com.ysy15350.readpacket;

import api.base.model.Response;

public interface MainViewInterface {

    public void updateVersion(String title, String versionName, String content, String fileSize,
                              String url);

    public void loginoutCallback(boolean isCache, Response response);

}
