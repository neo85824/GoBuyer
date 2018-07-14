package com.ncu.neo.GoBuyer;

import android.app.Application;

/**
 * Created by Neo on 2017/1/6.
 */

public class GlobalVariable extends Application {
    public String cur_user;
    public String type;

    public GlobalVariable() {
        cur_user = "";
        type = "";
    }
}
