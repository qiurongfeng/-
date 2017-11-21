package com.example.administrator.pku_weather;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.app.MyApplication;
import com.example.administrator.bean.City;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.filter;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SelectCity extends ListActivity implements View.OnClickListener{
        private ImageView mBackBtn;
        private ListView mList;
        private List<City> cityList;
        private ArrayList<City> filterDateList;
        protected void onCreate(Bundle savedInstanceStatic){
            super.onCreate(savedInstanceStatic);
            setContentView(R.layout.select_city);
//            initViews();
            mBackBtn = (ImageView) findViewById(R.id.title_back);
            mBackBtn.setOnClickListener(this);
        }
        public void onClick(View v){
            switch(v.getId()){
                case R.id.title_back:
                    Intent i = new Intent();
                    i.putExtra("cityCode","101210101");
                    setResult(RESULT_OK,i);
                    finish();
                    break;
                default:
                    break;
            }
        }



//        private void initViews(){
//            mBackBtn = (ImageView)findViewById(R.id.title_back);
//            mBackBtn.setOnClickListener(this);
//            mList = (ListView) findViewById(R.id.title_list);
//            MyApplication myApplication = (MyApplication)getApplication();
//            cityList = myApplication.getCityList();
//            for (City city:cityList)
//            {
//                filterDateList.add(city);
//            }
//            myadapter = new Myadapter();
//            ArrayAdapter<String>  adapter = new ArrayAdapter<String>(SelectCity.this,,cityList);
//            mList.setAdapter();
//        }

}
