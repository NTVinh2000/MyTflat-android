package com.example.tflat_redo;

import android.content.Context;
import android.content.IntentFilter;
import android.icu.text.RelativeDateTimeFormatter;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder> {
    Context context;
    List<MainMenuModel> mainMenuModelList;
    LayoutInflater layoutInflater;
    public MainMenuAdapter(Context context,List<MainMenuModel> mainMenuModelList) {
        this.context = context;
        this.mainMenuModelList=mainMenuModelList;
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MainMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=layoutInflater.inflate(R.layout.activity_cardview,viewGroup,false );
        return new MainMenuViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MainMenuViewHolder mainMenuViewHolder, int i) {
        mainMenuViewHolder.textView.setText(mainMenuModelList.get(i).getDescription());
        mainMenuViewHolder.imageView.setImageResource(mainMenuModelList.get(i).getImageId());
        return;
    }
    @Override
    public int getItemCount() {
        return mainMenuModelList.size();
    }
    public class MainMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;
        public MainMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.TextViewDescription);
            imageView=itemView.findViewById(R.id.CardViewImage);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int pos=getLayoutPosition();
            Toast.makeText(context, Integer.toString(pos), Toast.LENGTH_SHORT).show();
        }
    }
}
