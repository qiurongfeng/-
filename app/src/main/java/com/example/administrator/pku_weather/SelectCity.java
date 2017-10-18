package com.example.administrator.pku_weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SelectCity extends Activity implements View.OnClickListener{
        private ImageView mBackBtn;
        protected void onCreate(Bundle savedInstanceStatic){
            super.onCreate(savedInstanceStatic);
            setContentView(R.layout.select_city);
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

}
