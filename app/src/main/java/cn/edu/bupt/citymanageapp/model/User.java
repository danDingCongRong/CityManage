package cn.edu.bupt.citymanageapp.model;

import android.content.ContentValues;

import cn.edu.bupt.citymanageapp.util.DBConstants;

/**
 * Created by chenjun14 on 16/5/18.
 */
public class User {

    private String userName;

    private String password;

    public User() {
        this.userName = "";
        this.password = "";
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBConstants.USER_NAME, userName);
        contentValues.put(DBConstants.PASSWORD, password);

        return contentValues;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
