package com.example.tflat_remake;

import android.app.ActivityOptions;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.support.v7.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    a
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setUpSearchView();
    }
    public void setUpSearchView()
    {
        SearchManager searchManager = (SearchManager) getSystemService(MainActivity.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        View v=searchView.findViewById()
        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(ContextCompat.getColor(searchView.getContext(),android.R.color.transparent));
        //searchView.setBackgroundResource(R.drawable.texfield_searchview_holo_light);
        //searchView.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
        searchView.setQueryHint("Look up");

    }
}
