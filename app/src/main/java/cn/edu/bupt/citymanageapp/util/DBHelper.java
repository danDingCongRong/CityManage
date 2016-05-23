package cn.edu.bupt.citymanageapp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenjun14 on 16/5/18.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "cityManagerApp.db";

    private static int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MLog.d("DBHelper onCreate");

        try {
            db.execSQL(DBConstants.CREATE_USER_TABLE);
        } catch (Exception e) {
            MLog.d(e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MLog.d("DBHelper onUpgrade: oldVersion=" + oldVersion + ",newVersion=" + newVersion);

    }
}
