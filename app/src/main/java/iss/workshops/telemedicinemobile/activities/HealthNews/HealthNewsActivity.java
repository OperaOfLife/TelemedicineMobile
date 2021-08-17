package iss.workshops.telemedicinemobile.activities.HealthNews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import iss.workshops.telemedicinemobile.R;

public class HealthNewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_health_news);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParseAdapter(parseItems, this);
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute();
    }


    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
          super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(HealthNewsActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(HealthNewsActivity.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String url = "https://www.financialexpress.com/lifestyle/health/";

                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("div.imgbox");
                int size = data.size();
                Log.d("-----doc", "doc: "+doc);
                Log.d("-----data", "data: "+data);
                Log.d("-----size", ""+size);
                for (int i = 1; i < size; i+=1) {
                    String imgUrl = data.select("div.imgbox")
                            .select("img")
                            .eq((2*i+1))
                            .attr("src");


                    String title = doc.select("div.content-list")
                            .select("h3")

                            .eq(i)
                            .text();

                    String detailUrl = doc.select("div.content-list")
                            .select("h3")

                            .select("a")
                            .eq(i)
                            .attr("href");

                    parseItems.add(new ParseItem(imgUrl, title, detailUrl));
                    Log.d("--------items", "img: " + imgUrl + " . title: " + title
                            +".  detailUrl"+detailUrl);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }


}