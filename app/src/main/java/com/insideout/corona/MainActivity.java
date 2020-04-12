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


    }

    private class UpdateTask extends AsyncTask<String, String,String> {
        protected String doInBackground(String... urls) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://covid-19-data.p.rapidapi.com/totals?format=undefined")
                    .get()
                    .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "a06ca0e990msh2b00cb23fe6c85bp125f4ajsne6ce5fdae9c7")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

    }
}