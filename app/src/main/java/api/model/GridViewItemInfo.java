package api.model;

public class GridViewItemInfo {

    // {"code":200,"result":[
    // {"id":"1","title":"\u6b27\u7f8e","icon":"\/Public\/home\/images\/no_icon.jpg"}
    // ,{"id":"2","title":"\u65e5\u97e9","icon":"\/Public\/home\/images\/no_icon.jpg"},
    // {"id":"3","title":"\u56fd\u4ea7","icon":"\/Public\/home\/images\/no_icon.jpg"},
    // {"id":"4","title":"\u5fae\u578b\u8f66","icon":"\/Public\/home\/images\/no_icon.jpg"}]}


    private int id;
    private String title;
    private String icon;
    private int value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
