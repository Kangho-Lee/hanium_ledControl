package com.example.yj.bluetoothapplication.UserType;

import java.io.Serializable;

public class User implements Serializable{
    private String userId = "";
    private String userPw = "";
    private String userName = "";
    private int manager = 0;
    private int userTeamNum = 0;
    private String userPhoneNum = "";
    private Boolean userSelected = false; //체크박스용

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public void setUserTeamNum(int userTeamNum){
        this.userTeamNum = userTeamNum;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public void setUserSelected(Boolean userSelected) {
        this.userSelected = userSelected;
    }

    public int getUserTeamNum(){
        return userTeamNum;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public int getManager() {
        return manager;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public Boolean getUserSelected() {
        return userSelected;
    }
}
