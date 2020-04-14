package com.insideout.corona.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chargemap_beta.android.tableView.library.Cell;
import com.chargemap_beta.android.tableView.library.TableView;
import com.insideout.corona.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorldFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorldFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TableView mTableView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textConfirm;
    TextView textRecover;
    TextView textCritical;
    TextView textDeath;
    ViewGroup viewGroup;

    public WorldFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorldFragment newInstance(String param1, String param2) {
        WorldFragment fragment = new WorldFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_world, container, false);
        viewGroup=container;
        textConfirm=(TextView) layout.findViewById(R.id.text_total_cases);
        textDeath=(TextView) layout.findViewById(R.id.text_deaths);
        textRecover=(TextView) layout.findViewById(R.id.text_recovered);
        textCritical=(TextView) layout.findViewById(R.id.text_critical);


        TableView tableView=(TableView) layout.findViewById(R.id.tableview);
        List<Cell> column1 = new ArrayList<>(); // Add headers as the first column
        column1.add(new Cell("Header 1"));
        column1.add(new Cell("Header 1"));
        column1.add(new Cell("Header 1"));
        column1.add(new Cell("Header 1"));
        column1.add(new Cell("Header 1"));
        column1.add(new Cell("Header 1"));

        List<Cell> column2 = new ArrayList<>(); // Add data after headers
        column2.add(new Cell("Data11"));
        column2.add(new Cell("Data12"));
        column2.add(new Cell("Data13"));
        column2.add(new Cell("Data14"));
        column2.add(new Cell("Data15"));
        column2.add(new Cell("Data16"));

        List<List<Cell>> columns = new ArrayList<>();
        columns.add(column1);
        columns.add(column2);

        tableView.setItems(columns);


        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute("https://covid-19-data.p.rapidapi.com/totals?format=undefined");
        return inflater.inflate(R.layout.fragment_world, container, false);
    }


    public class OkHttpHandler extends AsyncTask {

        OkHttpClient client = new OkHttpClient();


        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Object o) {

            super.onPostExecute(o);

            try {
//                JSONObject jsonObject=new JSONObject(o.toString());
                JSONArray jsonArray=new JSONArray(o.toString());
                String s=jsonArray.getJSONObject(0).get("confirmed").toString();
                BigDecimal bigDecimal=new BigDecimal(s);
                NumberFormat df = DecimalFormat.getInstance();
                if(textConfirm!=null) {

                    textConfirm.setText(df.format(bigDecimal));

                    s=jsonArray.getJSONObject(0).get("recovered").toString();
                    bigDecimal=new BigDecimal(s);
                    textRecover.setText(df.format(bigDecimal));

                    s=jsonArray.getJSONObject(0).get("deaths").toString();
                    bigDecimal=new BigDecimal(s);
                    textDeath.setText(df.format(bigDecimal));

                    s=jsonArray.getJSONObject(0).get("critical").toString();
                    bigDecimal=new BigDecimal(s);
                    textCritical.setText(df.format(bigDecimal));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            textConfirm=(TextView) viewGroup.findViewById(R.id.text_total_cases);
            textDeath=(TextView) viewGroup.findViewById(R.id.text_deaths);
            textRecover=(TextView) viewGroup.findViewById(R.id.text_recovered);
            textCritical=(TextView) viewGroup.findViewById(R.id.text_critical);
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


}
