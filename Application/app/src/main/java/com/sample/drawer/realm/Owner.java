package com.sample.drawer.realm;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by Daniyar_Amangeldy on 01/12/15.
 */
@RealmClass

public class Owner extends RealmObject {
    //Owner DataBase
    @PrimaryKey
    private String    tt_code;
    @Index
    private String    supervisor;
    @Ignore
    private int       sessionId;
    @Required
    private String    region;
    private String    agent;
    private String      agent_code;
    private String      supervisor_code;
    private String    code_sbyta;
    private boolean   tt_type;
    private String    location;
    private String    timetable;
    private String    owner;
    private String    tt;
    private double    latitude;
    private double    longitude;

    //Getter and Setter
    public String getTt_code() { return tt_code; }

    public void   setTt_code(String tt_code) { this.tt_code = tt_code; }

    public String getSupervisor() { return supervisor; }

    public void   setSupervisor(String supervisor) { this.supervisor = supervisor; }

    public int getSessionId() { return sessionId; }

    public void   setSessionId(int sessionId) { this.sessionId = sessionId; }

    public String getRegion() { return region; }

    public void   setRegion(String region) { this.region = region; }

    public String getAgent() { return agent; }

    public void   setAgent(String agent) { this.agent = agent; }

    public String getAgent_code() { return agent_code; }

    public void   setAgent_code(String agent_code) { this.agent_code = agent_code; }

    public String getSupervisor_code() { return supervisor_code; }

    public void   setSupervisor_code(String supervisor_code) { this.supervisor_code = supervisor_code; }

    public String getCode_sbyta() { return code_sbyta; }

    public void   setCode_sbyta(String code_sbyta) { this.code_sbyta = code_sbyta; }

    public boolean getTt_type() { return tt_type; }

    public void   setTt_type(boolean tt_type) { this.tt_type = tt_type; }

    public String getLocation() { return location; }

    public void   setLocation(String location) { this.location = location; }

    public String getTimetable() { return timetable; }

    public void   setTimetable(String timetable) { this.timetable = timetable; }

    public String getOwner() { return owner; }

    public void   setOwner(String owner) { this.owner = owner; }

    public String getTt() { return tt; }

    public void  setTt(String tt) { this.tt = tt; }

    public double getLatitude() { return latitude; }

    public void   setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void   setLongitude(double longitude) { this.longitude = longitude; }



}
