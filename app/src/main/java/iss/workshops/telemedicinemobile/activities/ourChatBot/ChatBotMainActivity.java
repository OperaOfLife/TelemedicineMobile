package iss.workshops.telemedicinemobile.activities.ourChatBot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChatBotMainActivity extends AppCompatActivity {

    Retrofit retrofit;
    Button btnSend;
    EditText etChat;
    private RecyclerView rvChatList;
    private RecyclerView.Adapter adapter;
    AdapterChatBot adapterChatBot;
    Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot_main);
        Context context=this.getApplicationContext();

        Toast.makeText(getApplicationContext(), "Please enter a text", Toast.LENGTH_LONG).show();


        btnSend = findViewById(R.id.btnSend);
        adapterChatBot = new AdapterChatBot(context);
        etChat = findViewById(R.id.etChat);
        rvChatList=findViewById(R.id.rvChatList);

        rvChatList.setHasFixedSize(true);
        rvChatList.setLayoutManager(new LinearLayoutManager(this));
        rvChatList.setAdapter(adapterChatBot);

        setuphomebtn();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etChat == null) {
                    Toast.makeText(getApplicationContext(), "Please enter a text", Toast.LENGTH_LONG).show();

                }



                Call<ChatResponse> callback = RetrofitClient.getInstance().getChatAPI()
                        .chatWithTheBit(etChat.getText().toString());

                adapterChatBot.addChatToList(etChat.getText().toString());
                chat(callback);
                etChat.getText().clear();
            }
        });
    }

    private void setuphomebtn() {
        homeBtn = findViewById(R.id.homeBtn);

        if (homeBtn != null) {
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        finish();
                    }
            });
        }
    }


    public void chat(Call<ChatResponse> call) {
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    adapterChatBot.addChatToList(response.body().getChatBotReply());
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong here", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went wrong! Try again later", Toast.LENGTH_LONG).show();


            }
        });
    }
}

