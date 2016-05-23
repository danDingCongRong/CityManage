package cn.edu.bupt.citymanageapp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chenjun14 on 16/5/17.
 */
public class MToast {

    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
