package com.gxu.testapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gxu.testapp.R;
import com.gxu.testapp.entity.SinoseismEventEntity;
import com.gxu.testapp.event.SinoseismEvent;
import com.yalantis.phoenix.PullToRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gxu.testapp.ui.PdfShow.BASE_URL;

public class SinoseimEventList extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    Button button;
    public ListViewAdapter adapter;
    JSONArray GetSinoseismEventList;
    PullToRefreshView mPullToRefreshView;
    public  ArrayList<SinoseismEventEntity> all_SinoseismEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_sinoseim_event_list);
        listView=findViewById(R.id.sinoseim_listview);
        button=findViewById(R.id.Sinoseim_Refresh_List);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.SinoseimList_pull_to_refresh);
        toolbar = (Toolbar) findViewById(R.id.sinosiemEvenList_toolbar);
        new Thread() {
            @Override
            public void run() {
                //这里写入子线程需要做的工作
                initViews();
            }
        }.start();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        initViews();
                        Toast.makeText(getApplicationContext(),"微震事件列表已刷新",Toast.LENGTH_SHORT).show();
                        Log.d("refreash", "刷新成功");
                    }
                },500);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Intent intent=new Intent();
                    intent.setClass(SinoseimEventList.this, SinoseismInfoActivity.class);
                    Bundle bundle = new Bundle();
                    String data1=GetSinoseismEventList.get(i).toString();
                    bundle.putString("data",data1);
                    intent.putExtras(bundle);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initViews();
                Toast.makeText(getApplicationContext(),"微震事件列表已刷新",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSinoseismEventArrived(SinoseismEvent event){
       initViews();
       Toast.makeText(getApplicationContext(),"有新的微震事件,列表已刷新",Toast.LENGTH_SHORT).show();
    }


    public void initViews() {
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        final com.gxu.testapp.API.GetSinoseismEventList getSinoseismEventList = retrofit2.create(com.gxu.testapp.API.GetSinoseismEventList.class);
        Call<ResponseBody> call =getSinoseismEventList.downloadEvenList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        all_SinoseismEvent = new ArrayList<SinoseismEventEntity>();
                        String result = response.body().string();
                        JSONObject rootObject = new JSONObject(result);
                        JSONObject payload=rootObject.getJSONObject("payload");
                        GetSinoseismEventList=payload.getJSONArray("data");
                        for (int i = 0; i < GetSinoseismEventList.length(); i++) {
                            JSONObject tmp_data = GetSinoseismEventList.getJSONObject(i);
                            int tmp_id=tmp_data.getInt("projectId");
                            String tmp_name=tmp_data.getString("eventName");
                            String tmp_time=tmp_data.getString("eventTime");
                            SinoseismEventEntity tmp_entity=new SinoseismEventEntity();
                            tmp_entity.setProjectId(tmp_id);
                            tmp_entity.setEventName(tmp_name);
                            tmp_entity.setEventTime(tmp_time);
                            all_SinoseismEvent.add(tmp_entity);
                        }
                        adapter=new ListViewAdapter(all_SinoseismEvent);
                        listView.setAdapter(adapter);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("cylog", "Error" + t.toString());
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class ListViewAdapter extends BaseAdapter {
        ArrayList<View> itemViews;

        public ListViewAdapter(ArrayList<SinoseismEventEntity> all_SinoseismEvent) {
            itemViews = new ArrayList<View>(all_SinoseismEvent.size());
            for (int i = 0; i < all_SinoseismEvent.size(); ++i) {
                itemViews.add(makeItemView(all_SinoseismEvent.get(i).getProjectId(),
                        all_SinoseismEvent.get(i).getEventName(),all_SinoseismEvent.get(i).getEventTime() ));
            }
        }

        public int getCount() {
            return itemViews.size();
        }

        public View getItem(int position) {
            return itemViews.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            return itemViews.get(position);
        }


        public View makeItemView(int  strid, String strname, String strtime) {
            LayoutInflater inflater = (LayoutInflater) SinoseimEventList.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.sinoseism_detail, null);

            TextView sinoseismevent_id= (TextView) itemView.findViewById(R.id.sinoseismevent_id);
            sinoseismevent_id.setText(String.valueOf(strid));

            TextView sinoseismevent_name= (TextView) itemView.findViewById(R.id.sinoseismevent_name);
            sinoseismevent_name.setText(strname);

            TextView sinoseismevent_time= (TextView) itemView.findViewById(R.id.sinoseismevent_time);
            sinoseismevent_time.setText(strtime);
            return itemView;
        }

    }

}