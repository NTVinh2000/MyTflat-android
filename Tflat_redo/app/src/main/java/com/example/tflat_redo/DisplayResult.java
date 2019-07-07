package com.example.tflat_redo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class DisplayResult extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextView titleTextview;
    TextView titleTextview2;
    TextView contentTextview;
    TextToSpeech textToSpeech;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        getSupportActionBar().hide();
        textToSpeech= new TextToSpeech(this,this);
        titleTextview=findViewById(R.id.titleTextView);
        contentTextview=findViewById(R.id.contentTextView);
        titleTextview2=findViewById(R.id.titleTextView2);
        imageButton=findViewById(R.id.speakButton);
        titleTextview2.setPaintFlags(titleTextview2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        final Intent intent=this.getIntent();
        final String s=intent.getStringExtra("title");
        titleTextview.setText( s);
        titleTextview2.setText(s);
        contentTextview.setText(intent.getStringExtra("content"));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!s.isEmpty())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
                    } else {
                        textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else Toast.makeText(DisplayResult.this, "Text is null", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onInit(int i) {

        // TODO Auto-generated method stub
        if(i == TextToSpeech.SUCCESS){
            int result=textToSpeech.setLanguage(Locale.US);
            textToSpeech.setSpeechRate((float) 0.6);
            if(result==TextToSpeech.LANG_MISSING_DATA ||
                    result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("error", "This Language is not supported");
            }
        }
        else
            Log.e("error", "Initilization Failed!");
    }


}
