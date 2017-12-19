package common.database;

import java.util.Collection;
import java.util.List;

/**
 * Created by yangshiyou on 2017/8/31.
 */

public interface DbUtils<T> {

    int saveOrUpdate(T t) throws Exception;

    int insert(T t) throws Exception;

    int update(T t) throws Exception;

    int delete() throws Exception;

    int delete(int id) throws Exception;

    int delete(Collection<T> list) throws Exception;

    T getEntity(int id) throws Exception;

    List<T> getList() throws Exception;

}
