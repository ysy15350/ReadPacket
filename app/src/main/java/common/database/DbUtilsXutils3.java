package common.database;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import java.io.File;

import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/8/31.
 */

public abstract class DbUtilsXutils3<T> implements DbUtils<T> {

    private static final String TAG = "DbUtilsXutils3";

    /**
     * 数据库操作对象
     */
    protected static DbManager db;

    /**
     * 初始化
     */
    protected static void init() {

        if (db != null)
            return;

        String path = CommFunAndroid.getDiskCachePath() + "/db";//Environment.getExternalStorageDirectory().getPath() + "/aandroid_log/log_db";

        Log.d(TAG, "init() called path=" + path);

        ///storage/emulated/0/Android/data/com.ysy15350.startcarunion/cache/db

        final File dbFile = new File(path);

        //        /storage/emulated/0/aandroid_log/log_db
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("log.db")
                // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(dbFile) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                .setDbVersion(12)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        if (newVersion > oldVersion) {


                        }

                        try {
                            if (oldVersion < 12)
                                db.dropDb();

                            switch (oldVersion) {
                                case 5:
                                    break;
                                default:
                                    break;
                            }
                            // db.addColumn(...);
                            // db.dropTable(...);
                            // ...
                            // or
                            // db.dropDb();
                        } catch (Exception e) {
                            Log.d(TAG, "onUpgrade() called with: db = [" + db + "], oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }).setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Log.d(TAG, "onTableCreated() called with: db = [" + db + "], table = [" + table + "]");
                    }
                });

        db = x.getDb(daoConfig);


    }


}
