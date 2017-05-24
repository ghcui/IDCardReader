package com.yunqi.cardreader.model.bean;

import java.io.Serializable;

/**
 * @author ghcui
 * @time 2017/5/4
 */
public class Room implements Serializable{
    public int id;
    public String room_code;
    public String room_address;
    public int room_num;
    public String room_detail;
    public String create_time;
    public int police_station_id;
    public int sum;
}
