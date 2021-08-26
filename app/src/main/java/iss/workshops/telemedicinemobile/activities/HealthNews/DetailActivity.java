package iss.workshops.telemedicinemobile.activities.HealthNews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Executor;

import iss.workshops.telemedicinemobile.R;

public class DetailActivity extends AppCompatActivity {

    private static  Executor FULL_TASK_EXECUTOR ;

    private ImageView imageView;
    private TextView title,time,content;
    String titleString,timeString,imgUrl,contentString;
    String detailUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthnews_detail);

        detailUrl = getIntent().getStringExtra("detailUrl");
        imageView=findViewById(R.id.imageView);
        title=findViewById(R.id.title);
        time=findViewById(R.id.time);
        content=findViewById(R.id.content);


        this.title.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("ImgUrl")).into(imageView);
        Content con = new Content();
        con.execute();
    }

    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            time.setText(timeString);
            content.setText(contentString);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url =detailUrl;

                Document doc = Jsoup.connect(url).get();;

                timeString = doc.select("div.place-line")
                        .select("span")
                        .text();
                imgUrl = doc
                        .select("span.custom-caption")
                        .select("img.lazy-loaded")
                        .attr("src");
                contentString = doc.select("div.post-summary")
                        .select("p").text();
                Log.d("--------items", "img: " + imgUrl+titleString +timeString);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
