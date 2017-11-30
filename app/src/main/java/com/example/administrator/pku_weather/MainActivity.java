package com.example.administrator.pku_weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bean.TodayWeather;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private static final int UPDATE_TODAY_WEATHER = 1;
    private ImageView mUpdateBtn;
    private ImageView mSelectCity;
    private int flag = 1;
    private TextView cityTv, timeTv, humidityTv, weekTv, pmDataTv, pmQualityTv, temperatureTv, climateTv, windTv, city_name_Tv,temperaturn_now_Tv,pic;
    private TextView temperatureTv_day1,weekTv_day1,climateTv_day1,temperatureTv_day2,weekTv_day2,climateTv_day2,temperatureTv_day3,weekTv_day3,climateTv_day3,temperatureTv_day4,weekTv_day4,climateTv_day4,temperatureTv_day5,weekTv_day5,climateTv_day5,temperatureTv_day6,weekTv_day6,climateTv_day6;
    private ImageView weatherImg, pmImg;
    private ImageView weather_day1,weather_day2,weather_day3,weather_day4,weather_day5,weather_day6;
    private ProgressBar updatePro;
    private ViewPagesAdapter vpAdapter;
    private ViewPager vp;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1,R.id.iv2};
    private Button btn;

    //用Handler来更新   UI
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };
    void initDots(){
        dots = new ImageView[views.size()];
        for (int i = 0;i < views.size();i++){
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }
    private  void initViews(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(layoutInflater.inflate(R.layout.page1,null));
        views.add(layoutInflater.inflate(R.layout.page2,null));
//        views.add(layoutInflater.inflate(R.layout.page3,null));
        vpAdapter = new ViewPagesAdapter(views,this);
        vp = (ViewPager)findViewById(R.id.viewPage);
        vp.setAdapter(vpAdapter);
        vp.setOnPageChangeListener(this);
//        btn = (Button)views.get(2).findViewById(R.id.btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Guide.this,MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int i) {
        for (int j = 0;j <ids.length;j++){
            if (j == i){
                dots[j].setImageResource(R.drawable.page_indicator_focused);
            }else{
                dots[j].setImageResource(R.drawable.page_indicator_unfocused);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

//初始化UI中的数据
    void initView(){
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality );
        temperaturn_now_Tv = (TextView) findViewById(R.id.temperature_now_num);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        updatePro = (ProgressBar)findViewById(R.id.title_update_progress);
        LayoutInflater layout=this.getLayoutInflater();
        View view=layout.inflate(R.layout.page1, null);
        temperatureTv_day1=(TextView)view.findViewById(R.id.temperature_day1);
//        temperatureTv_day1 = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.temperature_day1);
        climateTv_day1 = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.climate_day1);
        weather_day1 = (ImageView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.weather_img_day1);
        weekTv_day1  = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.week_day1);
        temperatureTv_day2 = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.temperature_day2);
        climateTv_day2 = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.climate_day2);
        weather_day2 = (ImageView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.weather_img_day2);
        weekTv_day2  = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.week_day2);
        temperatureTv_day3 = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.temperature_day3);
        climateTv_day3 = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.climate_day3);
        weather_day3 = (ImageView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.weather_img_day3);
        weekTv_day3  = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.page1, null).findViewById(R.id.week_day3);
        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");
//        queryWeatherCode(citycode);
    }



    @Override
    public void onClick(View view) {
        //点击home键可以用Intnt传递数据，打开SelectCity的activity的界面
        if (view.getId() == R.id.title_city_manage){
            Intent i = new Intent(this,SelectCity.class);
            startActivityForResult(i,1);
        }
        if (view.getId() == R.id.title_update_btn){
            mUpdateBtn.setVisibility(View.GONE);
            updatePro.setVisibility(View.VISIBLE);
        }
        //引用 SharedPreferences存储变量获得citycode的数据
        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        String citycode = sharedPreferences.getString("cityCode", "101010100");
        Log.d("myweather", citycode);
        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("myWeather", "网络OK");
//            Toast.makeText(MainActivity.this, "网络OK！", Toast.LENGTH_LONG).show();
            //进行一次从城市的查询

            queryWeatherCode(citycode);
            //如果网络ok则刷新成功，默认延迟1秒可增加用户体验
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mUpdateBtn.setVisibility(View.VISIBLE);
                    updatePro.setVisibility(View.GONE);
                }
            }, 1000);

        } else {
            Log.d("myWeather", "网络挂了");
            Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
        }

    }
    //在列表中选择城市后，将选择的城市的代码传入并进行查询requeCode=1，resultCode=RESULT_OK，则为正常
    protected void onActivityResult(int requeCode,int resultCode,Intent data) {
        if (requeCode == 1 && resultCode == RESULT_OK) {
            String newCityCode = data.getStringExtra("cityCode");
            Log.d("myWeather", "选择的城市代码为" + newCityCode);

            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
//                Log.d("myWeather", "网络ok");
                //查询新的citycode
                queryWeatherCode(newCityCode);
            }else{
                Log.d("myWeather","网络挂了");
                Toast.makeText(MainActivity.this,"网络挂了",Toast.LENGTH_LONG).show();
            }
        }
    }

    //@parm_citycode
    //具体的查询城市天气的代码，传入citycode
    void queryWeatherCode(String citycode) {
        //输入地址
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + citycode;
        Log.d("myweather", address);
        //开启一个查询线程，将像该地址发送请求，以GET的方式，返回一个xml文件
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                TodayWeather todayWeather = null;
                try {
                    URL url = new URL(address);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        response.append(str);
                        Log.d("myWeather", str);
                    }
                    String responseStr = response.toString();
                    Log.d("myWeather", responseStr);
                    //解析XML文件的方法
                    todayWeather = parseXML(responseStr);
                    if (todayWeather != null){
                        Log.d("myWeather",todayWeather.toString());
                        Message msg =new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj=todayWeather;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
            }
        }).start();
    }
//对所得到的XML文件进行解析，得到具体的数值
    private  TodayWeather parseXML(String xmldata) {
        TodayWeather todayWeather = null;
        int fengxiangCount = 0;
        int fengliCount = 0;
        int dateCount = 0;
        int highCount = 0;
        int lowCount = 0;
        int typeCount = 0;
        int sunriseCount = 0;
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = fac.newPullParser();
            xmlPullParser.setInput(new StringReader(xmldata));
            int eventType = xmlPullParser.getEventType();
            Log.d("myWeather", "parseXML");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
// 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:
                        break;
// 判断当前事件是否为标签元素开始事件
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("resp")){
                        todayWeather = new TodayWeather();
                    }if (todayWeather !=null) {

                        if (xmlPullParser.getName().equals("city")) {
                            eventType = xmlPullParser.next();;
                            todayWeather.setCity(xmlPullParser.getText());
                        } else if (xmlPullParser.getName().equals("updatetime")) {
                            eventType = xmlPullParser.next();
                            todayWeather.setUpdatetime(xmlPullParser.getText());
                        } else if (xmlPullParser.getName().equals("shidu")) {
                            eventType = xmlPullParser.next();
                            todayWeather.setShidu(xmlPullParser.getText());
                        } else if (xmlPullParser.getName().equals("wendu")) {
                            eventType = xmlPullParser.next();
                            todayWeather.setWendu(xmlPullParser.getText());
                        } else if (xmlPullParser.getName().equals("pm25")) {
                            eventType = xmlPullParser.next();
                            todayWeather.setPm25(xmlPullParser.getText());
                        } else if (xmlPullParser.getName().equals("quality")) {
                            eventType = xmlPullParser.next();
                            todayWeather.setQuality(xmlPullParser.getText());
                        } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == 0) {
                            eventType = xmlPullParser.next();
                            todayWeather.setFengxiang(xmlPullParser.getText());
                            fengxiangCount++;
                        } else if (xmlPullParser.getName().equals("fengli") && fengliCount == 0) {
                            eventType = xmlPullParser.next();
                            todayWeather.setFengli(xmlPullParser.getText());
                            fengliCount++;
                        } else if (xmlPullParser.getName().equals("date") && dateCount == 0) {
                            eventType = xmlPullParser.next();
                            todayWeather.setDate(xmlPullParser.getText());
                            dateCount++;
                        } else if (xmlPullParser.getName().equals("high") && highCount == 0) {
                            eventType = xmlPullParser.next();
                            todayWeather.setHigh(xmlPullParser.getText());
                            highCount++;
                        } else if (xmlPullParser.getName().equals("low") && lowCount == 0) {
                            eventType = xmlPullParser.next();
                            todayWeather.setLow(xmlPullParser.getText());
                            lowCount++;
                        } else if (xmlPullParser.getName().equals("type") && typeCount == 0) {
                            eventType = xmlPullParser.next();
                            todayWeather.setType(xmlPullParser.getText());
                            typeCount++;
                        } else if (xmlPullParser.getName().equals("sunrise_1") && sunriseCount == 0) {
                            eventType = xmlPullParser.next();
                            Log.d("myWeather", "sunrise: " + xmlPullParser.getText());
                            sunriseCount++;
                        }else if (xmlPullParser.getName().equals("date") && dateCount == 1) {
                            eventType = xmlPullParser.next();
                            todayWeather.setDate_day1(xmlPullParser.getText());
                            dateCount++;
                        } else if (xmlPullParser.getName().equals("high") && highCount == 1) {
                            eventType = xmlPullParser.next();
                            todayWeather.setHigh_day1(xmlPullParser.getText());
                            highCount++;
                        } else if (xmlPullParser.getName().equals("low") && lowCount == 1) {
                            eventType = xmlPullParser.next();
                            todayWeather.setLow_day1(xmlPullParser.getText());
                            lowCount++;
                        } else if (xmlPullParser.getName().equals("type") && typeCount == 1) {
                            eventType = xmlPullParser.next();
                            todayWeather.setType_day1(xmlPullParser.getText());
                            typeCount++;
                        }else if (xmlPullParser.getName().equals("date") && dateCount == 2) {
                            eventType = xmlPullParser.next();
                            todayWeather.setDate_day2(xmlPullParser.getText());
                            dateCount++;
                        } else if (xmlPullParser.getName().equals("high") && highCount == 2) {
                            eventType = xmlPullParser.next();
                            todayWeather.setHigh_day2(xmlPullParser.getText());
                            highCount++;
                        } else if (xmlPullParser.getName().equals("low") && lowCount == 2) {
                            eventType = xmlPullParser.next();
                            todayWeather.setLow_day2(xmlPullParser.getText());
                            lowCount++;
                        } else if (xmlPullParser.getName().equals("type") && typeCount == 2) {
                            eventType = xmlPullParser.next();
                            todayWeather.setType_day2(xmlPullParser.getText());
                            typeCount++;
                        }else if (xmlPullParser.getName().equals("date") && dateCount == 3) {
                            eventType = xmlPullParser.next();
                            todayWeather.setDate_day3(xmlPullParser.getText());
                            dateCount++;
                        } else if (xmlPullParser.getName().equals("high") && highCount == 3) {
                            eventType = xmlPullParser.next();
                            todayWeather.setHigh_day3(xmlPullParser.getText());
                            highCount++;
                        } else if (xmlPullParser.getName().equals("low") && lowCount == 3) {
                            eventType = xmlPullParser.next();
                            todayWeather.setLow_day3(xmlPullParser.getText());
                            lowCount++;
                        } else if (xmlPullParser.getName().equals("type") && typeCount == 3) {
                            eventType = xmlPullParser.next();
                            todayWeather.setType_day3(xmlPullParser.getText());
                            typeCount++;
                        }
                    }
                        break;
// 判断当前事件是否为标签元素结束事件
                    case XmlPullParser.END_TAG:
                        break;
                }
// 进入下一个元素并触发相应事件
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todayWeather;
    }
//该方法用于更新UI
    void updateTodayWeather(TodayWeather todayWeather){
        String high = todayWeather.getHigh().substring(2);
        String low = todayWeather.getLow().substring(2);
        String high_day1 = todayWeather.getHigh_day1().substring(2);
        String low_day1 = todayWeather.getLow_day1().substring(2);
        String high_day2 = todayWeather.getHigh_day2().substring(2);
        String low_day2 = todayWeather.getLow_day2().substring(2);
        String high_day3 = todayWeather.getHigh_day3().substring(2);
        String low_day3 = todayWeather.getLow_day3().substring(2);
        city_name_Tv.setText(todayWeather.getCity()+"天气");
        cityTv.setText(todayWeather.getCity());
        timeTv.setText(todayWeather.getUpdatetime()+ "发布");
        humidityTv.setText("湿度："+todayWeather.getShidu());
        pmDataTv.setText(todayWeather.getPm25());
        pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText(todayWeather.getDate());
        temperatureTv.setText(low+"~"+high);
        climateTv.setText(todayWeather.getType());
        windTv.setText("风力:"+todayWeather.getFengli());
        temperaturn_now_Tv.setText(todayWeather.getWendu() + "℃");
        Intent intent = new Intent(MainActivity.this, Page1.class);
        intent.putExtra("climateTv_day1", todayWeather.getType_day1());
            startActivity(intent);



//        climateTv_day1.setText("1321313");
//        weekTv_day1.setText(todayWeather.getDate_day1());
//        temperatureTv_day1.setText(low_day1+"~"+high_day1);
//        climateTv_day2.setText(todayWeather.getType_day2());
//        weekTv_day2.setText(todayWeather.getDate_day2());
//        temperatureTv_day2.setText(low_day2+"~"+high_day2);
//        climateTv_day3.setText(todayWeather.getType_day3());
//        weekTv_day3.setText(todayWeather.getDate_day3());
//        temperatureTv_day3.setText(low_day3+"~"+high_day3);
        //定义一个map用于存放键值对，将一个天气状况与一个天气的图片一一对应
        Map<String,Integer>imgWeather = new HashMap<String, Integer>(){
            {
                put("暴雪",R.mipmap.biz_plugin_weather_baoxue);
                put("暴雨",R.mipmap.biz_plugin_weather_baoyu);
                put("大暴雨",R.mipmap.biz_plugin_weather_dabaoyu);
                put("特大暴雨",R.mipmap.biz_plugin_weather_tedabaoyu);
                put("大雪",R.mipmap.biz_plugin_weather_daxue);
                put("大雨",R.mipmap.biz_plugin_weather_dayu);
                put("多云",R.mipmap.biz_plugin_weather_duoyun);
                put("雷阵雨",R.mipmap.biz_plugin_weather_leizhenyu);
                put("雷阵冰雹",R.mipmap.biz_plugin_weather_leizhenyubingbao);
                put("沙尘暴",R.mipmap.biz_plugin_weather_shachenbao);
                put("小雪",R.mipmap.biz_plugin_weather_xiaoxue);
                put("小雨",R.mipmap.biz_plugin_weather_xiaoyu);
                put("雾",R.mipmap.biz_plugin_weather_wu);
                put("阴",R.mipmap.biz_plugin_weather_yin);
                put("雨夹雪",R.mipmap.biz_plugin_weather_yujiaxue);
                put("阵雪",R.mipmap.biz_plugin_weather_zhenxue);
                put("阵雨",R.mipmap.biz_plugin_weather_zhenxue);
                put("中雪",R.mipmap.biz_plugin_weather_zhongxue);
                put("中雨",R.mipmap.biz_plugin_weather_zhongyu);
            }
        };
        int main_weather = R.mipmap.biz_plugin_weather_qing;
        int main_weather_day1 = R.mipmap.biz_plugin_weather_qing;
        int main_weather_day2 = R.mipmap.biz_plugin_weather_qing;
        int main_weather_day3 = R.mipmap.biz_plugin_weather_qing;
        try{
            main_weather = imgWeather.get(todayWeather.getType());
            main_weather_day1 = imgWeather.get(todayWeather.getType_day1());
            main_weather_day2 = imgWeather.get(todayWeather.getType_day2());
            main_weather_day3 = imgWeather.get(todayWeather.getType_day3());

        }catch(NullPointerException e ) {
            Log.d("myweather", "没有这样的天气类型");
        }
        weatherImg.setImageDrawable(getResources().getDrawable(main_weather));
        weather_day1.setImageDrawable(getResources().getDrawable(main_weather_day1));
        weather_day2.setImageDrawable(getResources().getDrawable(main_weather_day2));
        weather_day3.setImageDrawable(getResources().getDrawable(main_weather_day3));
//        weatherImg.setImageDrawable(getResources().getDrawable(main_weather_day2));
//        weatherImg.setImageDrawable(getResources().getDrawable(main_weather_day3));
        String pmNum = todayWeather.getPm25();
        int i = 0;
        //若pm2.5无数据则不显示数值，若有，则显示数值
        if (pmNum !=null){
            i = Integer.parseInt(pmNum);
        }
        //判断不同的pm2.5数值下，显示什么图片
        if (i < 51){
            pmImg.setImageDrawable(getResources().getDrawable(R.drawable.biz_plugin_weather_0_50));
        }else if (i >= 51 && i < 101){
            pmImg.setImageDrawable(getResources().getDrawable(R.drawable.biz_plugin_weather_51_100));
        }else if (i >= 101 && i < 151){
            pmImg.setImageDrawable(getResources().getDrawable(R.drawable.biz_plugin_weather_101_150));
        }else if (i >= 151 && i < 201){
            pmImg.setImageDrawable(getResources().getDrawable(R.drawable.biz_plugin_weather_151_200));
        }else if (i >= 201 ){
            pmImg.setImageDrawable(getResources().getDrawable(R.drawable.biz_plugin_weather_201_300));
        }else{
            pmImg.setImageDrawable(getResources().getDrawable(R.drawable.biz_plugin_weather_0_50));
        }

        Toast.makeText(MainActivity.this,"更新成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);
        //引用 SharedPreferences存储变量获得citycode的数据
        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        String citycode = sharedPreferences.getString("cityCode", "101010100");
        initView();

        //判断是否为第一次打开该应用，若是，则引用一次查询
        if (flag == 1){
            queryWeatherCode(citycode);
            flag = 0;
        }
        initViews();
        initDots();
        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);
        mSelectCity = (ImageView)findViewById(R.id.title_city_manage);
        mSelectCity.setOnClickListener(this);

    }

}



