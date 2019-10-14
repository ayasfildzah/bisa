package com.mkp.bisa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class detail extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        listView = findViewById(R.id.listItem);
        listView.setOnItemClickListener(this);
        getJSON();
    }


    private void showBrands() {
        JSONObject jsonObject;
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject ok = result.getJSONObject(i);
                String idd = ok.getString(konfigurasi.TAG_ID);
                String user = ok.getString(konfigurasi.TAG_NAMA);
                String cr = ok.getString(konfigurasi.TAG_CREAT);


                HashMap<String, String> pel = new HashMap<>();
                pel.put(konfigurasi.TAG_ID,idd);
                pel.put(konfigurasi.TAG_NAMA,user);
                pel.put(konfigurasi.TAG_CREAT,cr);

                list.add(pel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                detail.this, list, R.layout.activity_detail,
                new String[]{konfigurasi.TAG_ID, konfigurasi.TAG_NAMA,konfigurasi.TAG_CREAT},
                new int[]{R.id.id, R.id.nama, R.id.creat});

        listView.setAdapter(adapter);
    }

    private void getJSON() {


        class GetJSON extends AsyncTask<Void, Void, String> {

            private ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(detail.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }


            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showBrands();
            }


            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequest(konfigurasi.URL_GET_ALL);
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, semua.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String csId = map.get(konfigurasi.TAG_ID);
        intent.putExtra(konfigurasi.QR_ID, csId);
        startActivity(intent);
    }

    public void add(View view) {
        Intent intent = new Intent(detail.this, tampil.class);
        startActivity(intent);


    }
}
