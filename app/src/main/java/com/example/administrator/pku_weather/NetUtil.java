package com.example.administrator.pku_weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/10/11.
 */
//判断网络状况
    public class NetUtil {
        public static final int NETWORN_NONE = 0;
        public static final int NETWORN_WIFI = 1;
        public static final int NETWORN_MOBILE = 2;

        public static int getNetworkState(Context context) {
            ConnectivityManager connManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            //如果没有网络信息，则返回null
            if (networkInfo == null) {
                return NETWORN_NONE;
            }
            int nType = networkInfo.getType();
            //若有，则返回具体的网络状态
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                return NETWORN_MOBILE;
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                return NETWORN_WIFI;
            }
            return NETWORN_NONE;
        }
    }

