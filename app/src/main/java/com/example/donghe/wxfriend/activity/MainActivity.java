package com.example.donghe.wxfriend.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.donghe.wxfriend.R;
import com.example.donghe.wxfriend.RecycleViewDivider;
import com.example.donghe.wxfriend.adapter.MyRecycleViewAdapter;
import com.example.donghe.wxfriend.bean.GridImageModle;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecycleViewAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LoadTask().execute();
    }


    //异步加载图片
    class LoadTask extends AsyncTask<Void, Void, GridImageModle> {

        @Override
        protected GridImageModle doInBackground(Void... voids) {
            Gson gson = new Gson();
            GridImageModle itemModle = gson.fromJson(getData(), GridImageModle.class);

            return itemModle;
        }

        @Override
        protected void onPostExecute(GridImageModle itemModle) {
            super.onPostExecute(itemModle);
            recyclerView = (RecyclerView) findViewById(R.id.listview);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.addItemDecoration(new RecycleViewDivider(MainActivity.this, LinearLayoutManager.HORIZONTAL));
            recycleViewAdapter = new MyRecycleViewAdapter(MainActivity.this);
            recyclerView.setAdapter(recycleViewAdapter);
            recycleViewAdapter.setList(itemModle.getList());

        }
    }

    private String getData() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open("data.json");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            is.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
