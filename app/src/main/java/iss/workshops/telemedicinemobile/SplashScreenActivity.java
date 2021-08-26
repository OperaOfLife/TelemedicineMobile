package iss.workshops.telemedicinemobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import iss.workshops.telemedicinemobile.activities.UserActivity;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(1000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally
                {
                    Intent Intent= new Intent(SplashScreenActivity.this, UserActivity.class);
                    startActivity(Intent);
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
