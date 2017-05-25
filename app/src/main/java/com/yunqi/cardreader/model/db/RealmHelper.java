package com.yunqi.cardreader.model.db;

import android.content.Context;


import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.bean.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class RealmHelper {
    private static final String DB_NAME = "IDCardReader.realm";

    private Realm mRealm;

    public RealmHelper(Context mContext) {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder(mContext)
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());
    }

    public Realm getRealm() {
        return mRealm;
    }

    /**
     * 添加登录的用户信息
     * 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     */
    public void addUser(User user) {
        deleteUser(user.id);
        mRealm.beginTransaction();
        mRealm.copyToRealm(user);
        if (mRealm.isInTransaction()) {
            mRealm.commitTransaction();
        }
    }

    /**
     * 获取最后一次登录的用户信息
     *
     * @return
     */
    public User getLastUser() {
        User user = mRealm.where(User.class).findFirst();
        if (user == null)
            return null;
        return user;
    }

    /**
     * 获取最后一次登录的用户信息
     *
     * @return
     */
    public void deleteUser(String id) {
        User user = mRealm.where(User.class).equalTo("id",id).findFirst();
        if (user != null) {
            mRealm.beginTransaction();
            user.deleteFromRealm();
            if (mRealm.isInTransaction()) {
                mRealm.commitTransaction();
            }
        }
    }

    public void addClientInfo(ClientInfo info, String userid) {
        info.id = System.currentTimeMillis();
        info.uid = userid;
        mRealm.beginTransaction();
        mRealm.copyToRealm(info);
        if (mRealm.isInTransaction()) {
            mRealm.commitTransaction();
        }
    }

    public void deleteClientInfo(long id) {
        ClientInfo clientInfo = mRealm.where(ClientInfo.class).equalTo("id", id).findFirst();
        if (clientInfo != null) {
            mRealm.beginTransaction();
            clientInfo.deleteFromRealm();
            if (mRealm.isInTransaction()) {
                mRealm.commitTransaction();
            }
        }

    }
    public List<ClientInfo> getClientInfos(String userid) {
        RealmResults<ClientInfo> clientInfos = mRealm.where(ClientInfo.class).equalTo("uid", userid).findAll();
        List<ClientInfo> datas = mRealm.copyFromRealm(clientInfos);
        return datas;
    }

    public int getClientInfoCount(String userid){
        return this.getClientInfos(userid).size();
    }
}
