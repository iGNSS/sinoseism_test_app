package com.gxu.testapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

import com.gxu.testapp.API.getpdflist;
import com.gxu.testapp.R;
import com.gxu.testapp.entity.PDF_diary;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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

public class PdfDiary extends AppCompatActivity  {


    ListView listView;
    Button refresh_diary;
    Toolbar toolbar;
    PullToRefreshView mPullToRefreshView;
    public ListViewAdapter adapter;
    public  ArrayList<PDF_diary> all_pdf_diary;
    //String pdfUrl = "https://arxiv.org/pdf/1604.03540.pdf";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfdiary);

        listView=findViewById(R.id.test_listview);
        refresh_diary=findViewById(R.id.Refresh_diary);


        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        getPDFInfo();
                        Toast.makeText(getApplicationContext(),"日报列表刷新完毕",Toast.LENGTH_SHORT).show();
                        Log.d("refreash", "刷新成功");
                    }
                },500);
            }
        });


        toolbar = (Toolbar) findViewById(R.id.pdfdiary_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String pdfSavedPath = sdCardPath.concat(File.separator).concat(getPackageName());
        File pdfSavedFolder = new File(pdfSavedPath);
        if (!pdfSavedFolder.exists()){
            if(pdfSavedFolder.mkdir()){
                Log.d("cylog", "PDF根目录创建成功");
            }else {
                Log.d("cylog", "PDF根目录创建失败");
            }
        }
        //pdfView.fromAsset("OHEM.pdf").load();
        final File[] pdfFiles = pdfSavedFolder.listFiles();
        if (pdfFiles != null && pdfFiles.length > 0){
            for (File f: pdfFiles){
                //pdfView.fromFile(f.getAbsoluteFile()).load();
                Log.d("pdfpath", "PDF根目录下的PDF文件：" + f.getAbsolutePath());
                Log.d("cylog", "PDF根目录下的PDF文件：" + f.getName());
            }
        }else {
            Log.d("cylog", "PDF根目录为空");
        }
       // pdfView.fromFile(new File("/storage/emulated/0/com.gxu.testapp/test.pdf")).load();
           // pdfView.fromFile(new File("/storage/emulated/0/com.gxu.testapp/那板水库除险加固工程1#灌溉发电放水隧洞微震监测预警日报20210531.pdf")).load();

        refresh_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPDFInfo();
                Toast.makeText(getApplicationContext(),"日报列表刷新完毕",Toast.LENGTH_SHORT).show();
            }
        });
        getPDFInfo();
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

    public  void getPDFInfo()
    {
        all_pdf_diary=new ArrayList<PDF_diary>();
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        getpdflist apiService = retrofit2.create(getpdflist.class);
        Call<ResponseBody> call = apiService.downloadFile();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        JSONObject rootObject = new JSONObject(result);
                        JSONObject payload=rootObject.getJSONObject("payload");
                        JSONArray fileList=payload.getJSONArray("fileList");

                        for (int i = 0; i < fileList.length(); i++) {
                            JSONObject data1 = fileList.getJSONObject(i);
                            String original_name=data1.getString("fileOriginalName");
                            String fileShortName = data1.getString("fileShortName");
                            if (original_name.length()>4) {
                                String ispdf = original_name.substring(original_name.length() - 4);
                                if (ispdf.equals(".pdf")) {
                                    String uploaderNickname = data1.getString("uploaderNickname");
                                    String uploadTime = data1.getString("uploadTime");
                                    int fid = data1.getInt("fid");
                                    PDF_diary pdf3 = new PDF_diary();
                                    pdf3.setPdfId(fid);
                                    pdf3.setPdfName(fileShortName);
                                    pdf3.setuploadTime(uploadTime);
                                    pdf3.setUploader(uploaderNickname);
                                    all_pdf_diary.add(pdf3);
                                    Log.d("ispdf", fileShortName + "是pdf");
                                } else
                                    Log.d("ispdf", fileShortName + "不是pdf");
                            }
                        }

                        adapter=new ListViewAdapter(all_pdf_diary);
                        listView.setAdapter(adapter);


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //File file =new File(all_pdf_diary.get(i).getPdfPath()+all_pdf_diary.get(i).getPdfName());
                        //pdfView.fromFile(file).load();
                        // pdfView.fromFile(new File(all_pdf_diary.get(i).getPdfPath()+all_pdf_diary.get(i).getPdfName())).load();
                        Intent intent=new Intent();
                        intent.setClass(PdfDiary.this,PdfShow.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("PdfFilename",all_pdf_diary.get(i).getPdfName().substring(0,all_pdf_diary.get(i).getPdfName().length()-4));
                        String uploadtime=all_pdf_diary.get(i).getuploadTime().substring(0,4)+
                                "."+all_pdf_diary.get(i).getuploadTime().substring(5,7)+
                                "."+all_pdf_diary.get(i).getuploadTime().substring(8,10)+
                                "."+all_pdf_diary.get(i).getuploadTime().substring(11,13)+
                                "."+all_pdf_diary.get(i).getuploadTime().substring(14,16)+
                                "."+all_pdf_diary.get(i).getuploadTime().substring(17,19);
                        Log.d("uploadtime",uploadtime);
                        bundle.putString("uploadTime",uploadtime);
                        bundle.putInt("PdfFileid",all_pdf_diary.get(i).getPdfId());

                        intent.putExtras(bundle);
                        startActivity(intent);
                        Log.d("listview","点击了"+i+"项");
                    }
                });



                //Log.d("cylog",pdfBean.getPdfName());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("cylog", "Error" + t.toString());
            }
        });
    }





    public class ListViewAdapter extends BaseAdapter {
        ArrayList<View> itemViews;

        public ListViewAdapter(ArrayList<PDF_diary> all_pdf_diary) {
            itemViews = new ArrayList<View>(all_pdf_diary.size());
            for (int i = 0; i < all_pdf_diary.size(); ++i) {
                itemViews.add(makeItemView(all_pdf_diary.get(i).getPdfId(),
                        all_pdf_diary.get(i).getPdfName(),all_pdf_diary.get(i).getuploadTime(),all_pdf_diary.get(i).getUploader()
                        ));
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


       public View makeItemView(int  strid, String strname, String strtime,String struploader) {
            LayoutInflater inflater = (LayoutInflater) PdfDiary.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.pdf_diary_detail, null);

            TextView pdfid= (TextView) itemView.findViewById(R.id.pdf_id);
            pdfid.setText(String.valueOf(strid));

            TextView pdfname= (TextView) itemView.findViewById(R.id.pdf_name);
            pdfname.setText(strname);

            TextView pdfpath= (TextView) itemView.findViewById(R.id.pdf_path);
            pdfpath.setText(strtime);

           TextView uploader= (TextView) itemView.findViewById(R.id.uploader);
           uploader.setText(struploader);


            return itemView;
        }

    }



}


