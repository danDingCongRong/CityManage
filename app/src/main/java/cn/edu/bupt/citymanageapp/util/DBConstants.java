package cn.edu.bupt.citymanageapp.util;

/**
 * Created by chenjun14 on 16/5/18.
 */
public class DBConstants {

    public static String USER_TABLE = "user";

    public static String USER_NAME = "userName";

    public static String PASSWORD = "password";

    public static String CREATE_USER_TABLE = "create table " + USER_TABLE
            + "(_id INTEGER primary key autoincrement,"
            + USER_NAME +" TEXT not null,"
            + PASSWORD  +" TEXT not null"
            + ");";

}
