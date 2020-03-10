package com.example.simpledateokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtResult = (TextView)findViewById(R.id.txtResult);
        final getHttp http = new getHttp();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String respond = http.run("https://chaidan2.000webhostapp.com/first.php");
                  //  String respond = http.run("https://chaidan2.000webhostapp.com/userget.php/?username=admin&password=admin");
                    Document cleanTxt = Jsoup.parse(respond);
                    final String bodyTxt = cleanTxt.body().text();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtResult.setText(bodyTxt);
                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public class getHttp {
        OkHttpClient client = new OkHttpClient();
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return  response.body().string();
        }
    }
}
