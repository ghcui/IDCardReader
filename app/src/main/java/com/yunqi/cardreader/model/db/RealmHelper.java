package com.yunqi.cardreader.model.db;

import android.content.Context;


import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.bean.User;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
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

    /**
     * 添加登录的用户信息
     * 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     */
    public void addUser(User user) {
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
        return mRealm.copyFromRealm(user);
    }

    public void addClientInfo(ClientInfo info) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(info);
        if (mRealm.isInTransaction()) {
            mRealm.commitTransaction();
        }
    }

    public void getClientInfos() {
        mRealm.where(ClientInfo.class).findAll()
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<RealmResults<ClientInfo>>() {
                    @Override
                    public void call(RealmResults<ClientInfo> clientInfos) {
                        List<ClientInfo> clientInfoList = mRealm.copyFromRealm(clientInfos);
                    }
                });

    }
}
