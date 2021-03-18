package com.abhishekshrinath.computershop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class laptop extends AppCompatActivity
{
    private static String json_url ="https://run.mocky.io/v3/962d45e3-219e-4d1f-b575-eeb51d4db15c";
    RecyclerView recyclerView;
    List<product> product_list;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        getSupportActionBar().hide(); //hide the default actionbar

        setContentView(R.layout.activity_laptop);
        bar=findViewById(R.id.progressbar);
        product_list=new ArrayList<>();
        recyclerView = findViewById(R.id.recycleviewid);


        Getdata getdata=new Getdata();
        getdata.execute();
    }
        public class Getdata extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            String current="";
            try
            {
                URL url;
                HttpURLConnection urlConnection=null;
                try
                {
                 url=new URL(json_url);
                 urlConnection=(HttpURLConnection) url.openConnection();

                 InputStream is=urlConnection.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);
                    int data=isr.read();
                    while (data!=-1)
                    {
                        current+=(char)data;
                        data=isr.read();
                    }
                    return current;
                }catch (Exception e)
                {
                    e.printStackTrace();

                }

                finally {
                    if(urlConnection!=null)
                    {
                        urlConnection.disconnect();
                    }
                }

        }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return current;

        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("user");
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    product pro=new product();
                    pro.setL_info(jsonObject1.getString("info"));
                    pro.setL_name(jsonObject1.getString("laptopname"));
                    pro.setL_price(jsonObject1.getString("price"));
                    pro.setL_rating(jsonObject1.getString("Rating"));
                    pro.setL_imgurl(jsonObject1.getString("image"));
                    product_list.add(pro);


                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            putproductdataintoRecycleview(product_list);
        }
    }
        private  void putproductdataintoRecycleview(List<product>product_list)
        {
            bar.setVisibility(View.GONE);
            RecycleViewAdapter recycleViewAdapter=new RecycleViewAdapter(this,product_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(recycleViewAdapter);
        }
}