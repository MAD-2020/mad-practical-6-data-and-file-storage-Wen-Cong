package com.example.whack_a_mole30;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String password;
    private int[] scoreList;

    public User(){
        this.scoreList = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public User(String name, String pass){
        this.username = name;
        this.password = pass;
        this.scoreList = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int[] getScoreList() {
        return scoreList;
    }

    public void setScoreList(int[] scoreList) {
        this.scoreList = scoreList;
    }
}
