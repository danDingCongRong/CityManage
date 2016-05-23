package cn.edu.bupt.citymanageapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.edu.bupt.citymanageapp.model.User;
import cn.edu.bupt.citymanageapp.util.DBConstants;
import cn.edu.bupt.citymanageapp.util.DBHelper;
import cn.edu.bupt.citymanageapp.util.MLog;

/**
 * Created by chenjun14 on 16/5/18.
 */
public class UserController {

    private static UserController instance;

    private DBHelper dbHelper;

    private UserController(Context context) {
        dbHelper = new DBHelper(context);
    }

    public static UserController getInstance(Context context) {
        if (null == instance) {
            instance = new UserController(context);
        }

        return instance;
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = user.toContentValues();

        long insertRow = -1;
        try {
            insertRow = db.insert(DBConstants.USER_TABLE, null, contentValues);
        } catch (Exception e) {
            MLog.d(e.toString());
        } finally {
            db.close();
        }

        if (insertRow == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean queryUser(String userName, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {"userName,password"};
        String selection = "userName = ? and password = ?";
        String[] args = {userName, password};

        int queryCount = 0;
        try {
            Cursor cursor = db.query(DBConstants.USER_TABLE, columns, selection, args, null, null, null);
            queryCount = cursor.getCount();
        } catch (Exception e) {
            MLog.d(e.toString());
        } finally {
            db.close();
        }

        if (queryCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePassword(String userName, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        String[] args = {userName};

        int updateRowCount = 0;
        try {
            updateRowCount = db.update(DBConstants.USER_TABLE, contentValues, "userName=?", args);
        } catch (Exception e) {
            MLog.d(e.toString());
        } finally {
            db.close();
        }

        if (updateRowCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containsUserName(String userName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {"userName"};
        String selection = "userName=?";
        String[] args = {userName};

        int queryCount = 0;
        try {
            Cursor cursor = db.query(DBConstants.USER_TABLE, columns, selection, args, null, null, null);
            queryCount = cursor.getCount();
        } catch (Exception e) {
            MLog.d(e.toString());
        } finally {
            db.close();
        }

        if (queryCount > 0) {
            return true;
        } else {
            return false;
        }
    }

}
