package com.sample.drawer.realm;

import io.realm.RealmObject;


/**
 * Created by Daniyar_Amangeldy on 08/12/15.
 */
public class Report extends RealmObject {
    private int   money;
    private Owner owner;
    private String dayId;
    private String ttCode;





    public String getDayId() { return dayId; }

    public void   setDayId(String dayId) { this.dayId = dayId; }

    public int getMoney() { return money; }

    public void   setMoney(int money) { this.money = money; }

    public Owner getOwner() { return owner; }

    public void   setOwner(Owner owner) { this.owner = owner; }

    public String getTtCode() { return ttCode; }

    public void   setTtCode(String ttCode) { this.ttCode= ttCode; }
}
