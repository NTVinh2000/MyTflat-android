package com.example.tflat_redo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Long currentTime;
    SearchView searchView;
    RecyclerView recyclerView;
    List<MainMenuModel> mainMenuModelList;
    List<SuggestionModel> suggestionModelList;
    SuggestionAdapter suggestionAdapter;
    LinearLayoutManager linearLayoutManager ;
    MainMenuAdapter mainMenuAdapter;
    EditText editTextOfSearchView;
    RequestQueue requestQueue;
    MyBroadCast broadcastReceiver;

    NotificationManager notificationManager;
    NotificationChannel notificationChannel;
    Notification.Builder notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        suggestionModelList= new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            setupNotificationChannel();
        }

        currentTime=System.currentTimeMillis();
        //register broadcast
        broadcastReceiver= new MyBroadCast();
        IntentFilter intentFilter= new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver,intentFilter);

        anhxa();
        setUpSearchView();
        setRecyclerView();

        //get text of searchview
        editTextOfSearchView=(EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editTextOfSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty())
                {
                    recyclerView.setAdapter(mainMenuAdapter);
                }
                if(System.currentTimeMillis()-currentTime>50)
                {
                    requestSuggestion(charSequence.toString());
                    currentTime=System.currentTimeMillis();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty())
                {
                    recyclerView.setAdapter(mainMenuAdapter);
                }
            }
        });

    }
    void setRecyclerView()
    {
        mainMenuModelList=new ArrayList<>();

        mainMenuModelList.add(new MainMenuModel("Quick search",R.drawable.quick_search));
        mainMenuModelList.add(new MainMenuModel("Viet-Eng",R.drawable.viet_eng));
        mainMenuModelList.add(new MainMenuModel("VIP package words ielts",R.drawable.tflat_gift));
        mainMenuModelList.add(new MainMenuModel("VIP package words in book",R.drawable.tflat_gift));
        mainMenuModelList.add(new MainMenuModel("My words",R.drawable.tflat_mywords));
        mainMenuModelList.add(new MainMenuModel("History",R.drawable.tflat_history));
        mainMenuModelList.add(new MainMenuModel("Important words",R.drawable.tflat_important_words));
        mainMenuModelList.add(new MainMenuModel("Other apps recommended",R.drawable.tflat_another_app));
        mainMenuModelList.add(new MainMenuModel("Setting",R.drawable.tflat_setting));
        linearLayoutManager= new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        mainMenuAdapter=new MainMenuAdapter(this,mainMenuModelList);
        recyclerView.setAdapter(mainMenuAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    void anhxa()
    {
        searchView=findViewById(R.id.searchView);
        recyclerView=findViewById(R.id.recycleView);
    }
    void setUpSearchView()
    {
        getSupportActionBar().hide();
        searchView.setQueryHint("Look up");
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);

        v.setBackgroundColor(ContextCompat.getColor(searchView.getContext(),android.R.color.transparent));
        v.setBackgroundResource(R.drawable.textfield_searchview_holo_light);
    }
    void requestSuggestion(String query)
    {
        String url="https://en.wikipedia.org/w/api.php?action=opensearch&search="+query+"&limit=17&format=json";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                suggestionModelList.clear();
                try {
                    JSONArray titleArray=  response.getJSONArray(1);
                    JSONArray contentArray= response.getJSONArray(2);
                    for(int i=0;i<titleArray.length();++i)
                    {
                        suggestionModelList.add(new SuggestionModel(titleArray.getString(i),contentArray.getString(i)));
                    }
                    suggestionAdapter= new SuggestionAdapter(suggestionModelList,MainActivity.this);
                    suggestionAdapter.setNoti(notificationManager,notification);
                    recyclerView.setAdapter(suggestionAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", "onErrorResponse: "+error.toString() );
            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void setupNotificationChannel()
    {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final String channelID="my_channel_id"; // Id cua channel
        String name="Vinh";// name cua channel,xuat hien khi long press vao notification
        notificationChannel = new NotificationChannel(channelID, name, NotificationManager.IMPORTANCE_HIGH); //set cac thong so cho channel
        notification = new Notification.Builder(MainActivity.this, channelID) // set cac thong so cho notification
                .setSound(null)
                .setAutoCancel(true)
                .setContentTitle("Content title")
                .setContentText("Content text")
                .setSubText("Your search::")
                .setSmallIcon(R.drawable.tflat_translate);
        notificationManager.createNotificationChannel(notificationChannel);
    }
    @Override
    protected void onStop() {
        super.onStop();

    }
}
