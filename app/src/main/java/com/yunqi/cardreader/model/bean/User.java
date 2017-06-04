package com.yunqi.cardreader.model.bean;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author ghcui
 * @time 2017/5/4
 */
public class User extends RealmObject implements Serializable {
    @PrimaryKey
    public String id;
    public String account;
    public String password;
    public String phone;
    public int sex;
    public String nick_name;
    public String avatar;
    public String create_time;
    public String update_time;
    public int type;
    public String region;
    public String police_station;
}
