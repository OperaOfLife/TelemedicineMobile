package iss.workshops.telemedicinemobile.activities.ourChatBot;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import iss.workshops.telemedicinemobile.R;

public class AdapterChatBot extends RecyclerView.Adapter<AdapterChatBot.ViewHolder>{
    private ArrayList<String> list = new ArrayList<>();
    Context context;

    public AdapterChatBot(Context context) {

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_chat, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String pos = this.list.get(position);

        holder.bind((String) pos);
    }

    public final void addChatToList(@NotNull String chat) {
        this.list.add(chat);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public final void bind(@NotNull String chat) {
            View itemView = this.itemView;
            TextView txtChat = (TextView) itemView.findViewById(R.id.txtChat);
            txtChat.setText((CharSequence) chat);
        }

    }

    }

