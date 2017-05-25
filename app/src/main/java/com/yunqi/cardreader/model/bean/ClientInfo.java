package com.yunqi.cardreader.model.bean;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * @author ghcui
 * @time 2017/5/4
 */
public class ClientInfo extends RealmObject implements Serializable {
    public long id;
    public String custom_name;
    public String custom_id_card;
    public String custom_sex;
    public String custom_nation;
    public String custom_residence;
    public String custom_police_address;
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
