package com.example.administrator.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.example.administrator.bean.City;
import com.example.administrator.db.CityDB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MyApplication extends Application{
                private static final String TAG = "MyAPP";
                private static MyApplication myApplication;
                private CityDB mCityDB;
                private List<City> mCityList;
    @Override
    //一个activity启动调用的第一个函数就是onCreate,初始化数据库
                public void onCreate(){
                    super.onCreate();
                    Log.d(TAG,"MyApplication->Oncreate");
                    myApplication = this;
        //
                    mCityDB = openCityDB();
        //初始化数据库文件下的city列表
                    initCityList();
                }
//onCreate方法中调用的初始化方法
    private void initCityList(){
        mCityList = new ArrayList<City>();
        //创建一个子线程，用来打开数据库文件
        new Thread(new Runnable() {
            @Override
            public void run() {
// TODO Auto-generated method stub
                prepareCityList();
            }
        }).start();
    }
    private boolean prepareCityList() {
        mCityList = mCityDB.getAllCity();
        int i=0;
        //循环取出列表中的数据
        for (City city : mCityList) {
            i++;
            String cityName = city.getCity();
            String cityCode = city.getNumber();
            Log.d(TAG,cityCode+":"+cityName);
        }
        //打印数据
        Log.d(TAG,"i="+i);
        return true;
    }
    public List<City> getCityList() {
        return mCityList;
    }
    public static MyApplication getInstance(){
                    return myApplication;
                }
    //打开数据库文件的方法
    private CityDB openCityDB() {
        String path = "/data" + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "databases1"
                + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d(TAG,path);
        //数据库文件不存在，则创建
        if (!db.exists()) {
            String pathfolder = "/data"
                    + Environment.getDataDirectory().getAbsolutePath()
                    + File.separator + getPackageName()
                    + File.separator + "databases1"
                    + File.separator;
            File dirFirstFolder = new File(pathfolder);
            if(!dirFirstFolder.exists()){
                dirFirstFolder.mkdirs();
                Log.i("MyApp","mkdirs");
            }
            Log.i("MyApp","db is not exists");
            try {
                InputStream is = getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this, path);
    }

}
