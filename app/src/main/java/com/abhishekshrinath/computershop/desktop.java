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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class desktop extends AppCompatActivity
{
    private static String json_url ="https://run.mocky.io/v3/272e6b64-1406-46df-8ce5-c7ce9a16fc0d";
    RecyclerView recyclerView_desk;
    List<product> product_list;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_desktop);
        getSupportActionBar().hide(); //hide the default actionbar

        bar=findViewById(R.id.progressbar_1);
        product_list=new ArrayList<>();
        recyclerView_desk = findViewById(R.id.recycleviewid_desktop);
        Getdata1 getdata1=new Getdata1();
        getdata1.execute();
    }
    public class Getdata1 extends AsyncTask<String, String, String>
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
        recyclerView_desk.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_desk.setAdapter(recycleViewAdapter);
    }
}