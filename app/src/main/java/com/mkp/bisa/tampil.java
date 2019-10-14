package com.mkp.bisa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class tampil extends AppCompatActivity  {

    private final static String CREATE_TASK_URL = "http://172.16.8.198:3000/api/v1/brands.json";
    private SharedPreferences mPreferences;
    private String mTaskTitle;
    private EditText editTextNama;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        editTextNama = findViewById(R.id.editTextnama);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        editTextNama = findViewById(R.id.editTextnama);

        final IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCameraId(0);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                intentIntegrator.initiateScan();
            }

        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data.
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    editTextNama.setText(obj.getString("textPersonName"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    String contents = data.getStringExtra("SCAN_RESULT");
                    Toast.makeText(getBaseContext(), "Hasil :" + contents, Toast.LENGTH_SHORT).show();
                    editTextNama.setText(contents);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void saveTask(View button) {
        EditText taskTitlelField = (EditText) findViewById(R.id.editTextnama);
        mTaskTitle = taskTitlelField.getText().toString();

        if (mTaskTitle.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please write something as a title for this brands",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            // everything is ok!
            CreateTaskTask createTask = new CreateTaskTask(tampil.this);
            createTask.setMessageLoading("Creating new brands...");
            createTask.execute(CREATE_TASK_URL);
        }
    }

    public void view(View view) {
        Intent intent = new Intent(tampil.this, detail.class);
        startActivity(intent);
    }

    private class CreateTaskTask extends UrlJsonAsyncTask {
        public CreateTaskTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject taskObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {
                    json.put("success", "sukses");
                    json.put("info", "Something went wrong. Retry!");
                    taskObj.put("name", mTaskTitle);
                    holder.put("brand", taskObj);
                    StringEntity se = new StringEntity(holder.toString());
                    post.setEntity(se);
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");
                    post.setHeader("Authorization", "Token token=" + mPreferences.getString("AuthToken", "sukses"));

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}