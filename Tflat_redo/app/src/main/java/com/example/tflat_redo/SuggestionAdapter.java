package com.example.tflat_redo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder> {
    List<SuggestionModel> suggestionModelList;
    LayoutInflater layoutInflater;
    Context context;

    NotificationManager notificationManager;
    Notification.Builder notification;
    NotificationChannel notificationChannel;

    public void setNoti(NotificationManager notificationManager,Notification.Builder notification) {
        this.notificationManager=notificationManager;
        this.notification = notification;
    }


    public void setSuggestionModelList(List<SuggestionModel> suggestionModelList) {
        this.suggestionModelList = suggestionModelList;
    }

    public SuggestionAdapter(List<SuggestionModel> suggestionModelList, Context context) {
        this.suggestionModelList = suggestionModelList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SuggestionAdapter.SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=layoutInflater.inflate(R.layout.activity_suggestion,parent,false);
        return new SuggestionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionAdapter.SuggestionViewHolder holder, int position) {
        holder.titleTextView.setText(suggestionModelList.get(position).getTitle());
        holder.contentTextView.setText(suggestionModelList.get(position).getContent());
        return;
    }

    @Override
    public int getItemCount() {
        return suggestionModelList.size();
    }

    public class SuggestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView contentTextView;
        public SuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView= itemView.findViewById(R.id.titleTextView);
            contentTextView=itemView.findViewById(R.id.contentTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos=getLayoutPosition();
            notification.setContentTitle(suggestionModelList.get(pos).getTitle());
            notification.setContentText(suggestionModelList.get(pos).getContent());
            notificationManager.notify(101,notification.build());
            Intent intent = new Intent(context,DisplayResult.class);
            intent.putExtra("title",suggestionModelList.get(pos).getTitle());
            intent.putExtra("content",suggestionModelList.get(pos).getContent());
            context.startActivity(intent);
        }
    }
}
