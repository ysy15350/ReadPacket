package api.base.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import common.string.JsonConvertor;


public class Response {

    private ResponseHead head;

    private Object body;

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }


    public String getBodyJson() {
        if (this.body != null)
            return JsonConvertor.toJson(body);
        return "";
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    /**
     * 获取指定数据类型
     *
     * @param type 数据类型
     * @return
     */
    public <T> T getData(Type type) {
        try {
            if (this.body != null) {
                String dataJson = JsonConvertor.toJson(this.body);

                //dataJson = dataJson.replace("\n", "");

                T t = JsonConvertor.fromJson(dataJson, type);

                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


    // public void setData(T data) {
    // this.data = data;
    // }

    private Type getType() {
        Type genType = this.getClass().getGenericSuperclass();

        Type type = null;

        try {
            Type[] types = ((ParameterizedType) genType).getActualTypeArguments();

            if (types.length > 0)
                type = types[0];

            return type;
        } catch (Exception exception) {

        }

        return null;
    }

}
