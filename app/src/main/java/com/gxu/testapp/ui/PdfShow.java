package com.gxu.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.gxu.testapp.API.Getpdf;
import com.gxu.testapp.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PdfShow extends AppCompatActivity {

    PDFView pdfView1;
    Toolbar toolbar;
    public static final String BASE_URL = "http://139.224.28.1:8080/";

    public void writeResponseBodyToDisk(ResponseBody body,String filename) {

        //DownloadListener downloadListener;
        File pdfFile = new File("/storage/emulated/0/com.gxu.testapp/" + filename+".pdf");
        if (!pdfFile.exists()) {
            Log.d("upload", "下载不存在，开始下载");
            try {
                InputStream is = body.byteStream();
                //获取文件总长度
                long totalLength = is.available();
                FileOutputStream fos = new FileOutputStream(pdfFile);
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = bis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);

                    //此处进行更新操作
                    //len即可理解为已下载的字节数
                    //onLoading(len, totalLength);
                }
                Log.d("upload", "下载文件成功" + pdfFile.getAbsolutePath());
                fos.flush();
                fos.close();
                bis.close();
                is.close();
                //此处就代表更新结束
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.d("upload", "文件存在" );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_show);
        pdfView1=findViewById(R.id.pdfshow_view);


        toolbar = (Toolbar) findViewById(R.id.pdfshow_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        int pdfid=bundle.getInt("PdfFileid");

        String uploadTime=bundle.getString("uploadTime");
        String tmp_pdfname=bundle.getString("PdfFilename");
        final String pdfname=tmp_pdfname+"_"+uploadTime;
        Log.d("pdfname", pdfname );


        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        Getpdf getpdf=retrofit2.create(Getpdf.class);
        Call<ResponseBody>call=getpdf.downloadFile(pdfid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String results=response.body().toString();
                writeResponseBodyToDisk(response.body(),pdfname);
                Log.d("upload", "下载文件处理完毕");
                pdfView1.fromFile(new File("/storage/emulated/0/com.gxu.testapp/" + pdfname+".pdf"))
                        .load();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("cylog", "Error" + t.toString());
            }



        });

       //pdfView1.fromFile(new File("/storage/emulated/0/com.gxu.testapp/" + pdfname)).load();


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




}
















