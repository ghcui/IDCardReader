package com.yunqi.cardreader.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yunqi.cardreader.greendao.gen.DaoMaster;
import com.yunqi.cardreader.greendao.gen.DaoSession;
import com.yunqi.cardreader.model.bean.User;

import java.util.List;


/**
 * @author ghcui
 * @time 2017/5/4
 */
public class GreenDaoHelper {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public GreenDaoHelper(Context context) {
        mHelper = new DaoMaster.DevOpenHelper(context, "IDCard-Reader-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    /**
     * 添加用户
     * @return
     */
    public void addUser(User user) {
        mDaoSession.getUserDao().insert(user);
    }
    /**
     * 根据id获取用户
     * @return
     */
    public User getUserById(long id) {
        return mDaoSession.getUserDao().load(id);
    }

    /**
     * 获取最后一次登录的用户
     * @return
     */
    public User getLastUser() {
        List<User> users = mDaoSession.getUserDao().loadAll();
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(users.size() - 1);
    }
}
