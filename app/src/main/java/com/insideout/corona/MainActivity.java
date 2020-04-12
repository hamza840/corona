package com.insideout.corona;

import android.icu.lang.UProperty;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.insideout.corona.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute("https://covid-19-data.p.rapidapi.com/totals?format=undefined");


    }


    public class OkHttpHandler extends AsyncTask {

        OkHttpClient client = new OkHttpClient();


        @Override
        protected void onPostExecute(Object o) {

            super.onPostExecute(o);

            try {
//                JSONObject jsonObject=new JSONObject(o.toString());
                JSONArray jsonArray=new JSONArray(o.toString());
                String s=jsonArray.getJSONObject(0).get("confirmed").toString();
                System.out.println(s);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Request request = new Request.Builder()
                    .url("https://covid-19-data.p.rapidapi.com/totals?format=undefined")
                    .get()
                    .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "a06ca0e990msh2b00cb23fe6c85bp125f4ajsne6ce5fdae9c7")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
//    private class UpdateTask extends AsyncTask<String, String,String> {
//        protected String doInBackground(String... urls) {
//
//            OkHttpClient client = new OkHttpClient();
//
//            Request request = new Request.Builder()
//                    .url("https://covid-19-data.p.rapidapi.com/totals?format=undefined")
//                    .get()
//                    .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
//                    .addHeader("x-rapidapi-key", "a06ca0e990msh2b00cb23fe6c85bp125f4ajsne6ce5fdae9c7")
//                    .build();
//
//            try {
//                Response response = client.newCall(request).execute();
//                response = client.newCall(request).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return "";
//        }
//
//    }
}