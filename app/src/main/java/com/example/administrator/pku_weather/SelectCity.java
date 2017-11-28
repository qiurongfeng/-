package com.example.administrator.pku_weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.administrator.app.MyApplication;
import com.example.administrator.bean.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SelectCity extends Activity implements View.OnClickListener {

    private ImageView mBackBtn;
    private ListView mList;
    private SearchView mSearchView;
    private List<City> cityList;
    private List<String> filterDateList = new ArrayList<>();
    private List<String> originalList = new ArrayList<>();
    private HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载UI
        setContentView(R.layout.select_city);
        //初始化，加载城市列表
        initViews();
    }

    private void initViews() {
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mList = (ListView) findViewById(R.id.title_list);
        //下面几句获得城市列表
        MyApplication myApplication = (MyApplication) getApplication();
        cityList = myApplication.getCityList();
        for(City city : cityList) {
            //第一个用于给adpter输送数据，第二个用于保存完整的数据
            filterDateList.add(city.getCity());
            originalList.add(city.getCity());
            //map用键值对保存城市和具体的citycode之间的关系
            map.put(city.getCity(),city.getNumber());
        }
        //用了系统自带的ArrayAdapter来显示列表
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                SelectCity.this, android.R.layout.simple_list_item_1,filterDateList);
        mList.setAdapter(adapter) ;

        //为搜索框设置了查询监听器
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //文本框不为空，则可以查询到匹配的列表
                if (!TextUtils.isEmpty(newText)) {
                    //清空数组，为了避免数据的叠加
                    filterDateList.clear();
                    //过滤器
                    filterDateList.addAll(getFiltList(originalList,newText));
                    Log.d("select city", filterDateList.toString());
                    adapter.notifyDataSetChanged();
                } else {
                    filterDateList.clear();
                    filterDateList.addAll(originalList);
                    Log.d("select city", filterDateList.toString());
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //当点击Item时，首先获取名字，然后通过map找到城市对应的代码，并存在SharedPreferences
                String cityName = filterDateList.get(position);
                Log.d("select city", cityName);
                String cityCode = map.get(cityName);
            SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                //将数据存入SharedPreferences
                editor.putString("cityCode", cityCode);
                editor.apply();

            Intent i = new Intent();
                Log.d("select city", cityName);
            i.putExtra("cityCode",cityCode);
                Log.d("select city", cityCode);
                //将数据通过Intent在结果中传入
            setResult(RESULT_OK, i);
                
//                onActivityResult(1,RESULT_OK,i);
            finish();
        };
        });

    }

    //这个函数是自定义的过滤器，可以返回后的列表
    private List<String> getFiltList(List<String> list, String filtValue){
        List<String> filtList = new ArrayList<>();
        for(String cityName : list){
            if (cityName.contains(filtValue)){
                filtList.add(cityName);
            }
        }
        return filtList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                //直接点击返回时，回到原来的城市列表
                SharedPreferences pref = getSharedPreferences("config", MODE_PRIVATE);
                String cityCode = pref.getString("cityCode", "101010100");
                Intent i = new Intent();
                i.putExtra("cityCode", cityCode);
                setResult(RESULT_OK, i);
                finish();break;
            default:
                break;
        }
    }
}
