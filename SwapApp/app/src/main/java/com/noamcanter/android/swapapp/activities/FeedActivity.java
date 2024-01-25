package com.noamcanter.android.swapapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtutorialshub.loginregister.R;
import com.noamcanter.android.swapapp.Item;
import com.noamcanter.android.swapapp.adapters.ItemsRecyclerAdapter;
import com.noamcanter.android.swapapp.sql.DatabaseHelper2;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {

    private AppCompatActivity activity = FeedActivity.this;
    private SearchView searchView;
    private ArrayList<Item> listItems;
    private Adapter itemRecyclerAdapter;
    private RecyclerView rvItems;
    private DatabaseHelper2 databaseHelper;
    private Button newItem;
    private Button profilePage;
    private Button getSelected;
    private String ownerEmail;
    private String ownerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getSupportActionBar().setTitle("");

        initViews();
        initObjects();

        listItems = new ArrayList<Item>();
        listItems.addAll(databaseHelper.getAllItems());
        for (int i = listItems.size()-1; i >= 0; i--) {
            if (listItems.get(i).getOwnerEmail().equals(ownerEmail)) {
                listItems.remove(i);
            }
        }

        ItemsRecyclerAdapter adapter = new ItemsRecyclerAdapter(listItems);

        rvItems.setAdapter(adapter);

        rvItems.setLayoutManager(new LinearLayoutManager(this));


        newItem = (Button) (findViewById(R.id.new_item_from_profile));
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UploadItem.class);
                intent.putExtra("EMAIL", ownerEmail);
                intent.putExtra("NAME", ownerName);

                startActivity(intent);
            }
        });

        profilePage = (Button) (findViewById(R.id.profile_from_feed));
        profilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ProfilePage.class);
                intent.putExtra("EMAIL", ownerEmail);
                intent.putExtra("NAME", ownerName);

                startActivity(intent);
            }
        });

        getSelected = (Button) (findViewById(R.id.get_selected));
        getSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected() != null) {
                    Intent p = new Intent(FeedActivity.this, SpecificItem.class);
                    p.putExtra("Item", adapter.getSelected());
                    p.putExtra("EMAIL", ownerEmail);
                    startActivity(p);
                }
            }
        });
    }



    private void initViews() {
        rvItems = (RecyclerView) findViewById(R.id.recyclerViewItems);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {

        rvItems.setItemAnimator(new DefaultItemAnimator());
        rvItems.setHasFixedSize(true);

        databaseHelper = new DatabaseHelper2(activity);
        ownerEmail = getIntent().getStringExtra("EMAIL");
        ownerName = getIntent().getStringExtra("NAME");


    }
    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listItems.clear();
                listItems.addAll(databaseHelper.getAllItems());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //itemRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}

