package com.yunqi.cardreader.model.bean;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author ghcui
 * @time 2017/5/4
 */
public class ClientInfo extends RealmObject implements Serializable {
    @PrimaryKey
    public long id;
    public String uid;
    @Ignore
    public String order_id;
    public String custom_name;
    public String custom_id_card;
    public String custom_sex;
    public String custom_nation;
    public String custom_residence;
    @Ignore
    public String custom_police_address;
    @Ignore
    public String custom_valid_date;
    public String custom_birth_date;
    public String room_code;
    public int retinue;
    public int room_number;
    public String card_photo_url;
    public String user_photo_url;
    public String user_from;
    public String sign_time;
}
