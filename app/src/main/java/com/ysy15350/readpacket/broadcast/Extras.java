package com.ysy15350.readpacket.broadcast;

/**
 * Created by yangshiyou on 2017/10/25.
 */

public class Extras {
    private int type;
    private String login;

    private String title;

    private String print_content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrint_content() {
        return print_content;
    }

    public void setPrint_content(String print_content) {
        this.print_content = print_content;
    }
}
