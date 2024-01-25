package com.noamcanter.android.swapapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.androidtutorialshub.loginregister.R;
import com.noamcanter.android.swapapp.Item;
import com.noamcanter.android.swapapp.User;
import com.noamcanter.android.swapapp.adapters.ItemsRecyclerAdapter;
import com.noamcanter.android.swapapp.adapters.UsersRecyclerAdapter;
import com.noamcanter.android.swapapp.sql.DatabaseHelper;
import com.noamcanter.android.swapapp.sql.DatabaseHelper2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProfilePage extends AppCompatActivity {

    private AppCompatActivity activity = ProfilePage.this;
    private TextView textViewName;
    private TextView textViewEmail;
    private ArrayList<Item> listItems;
    private Adapter itemRecyclerAdapter;
    private RecyclerView rvItems;
    private DatabaseHelper2 databaseHelper;
    private Button newItem;
    private Button itemList;
    private String ownerEmail;
    private String ownerName;
    private Button deleteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        getSupportActionBar().setTitle("");

        initViews();
        initObjects();


        listItems = new ArrayList<Item>();

        listItems.addAll(databaseHelper.getUsersItems(ownerEmail));

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

        itemList = (Button) (findViewById(R.id.item_list_from_profile));
        itemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, FeedActivity.class);
                intent.putExtra("EMAIL", ownerEmail);
                intent.putExtra("NAME", ownerName);
                startActivity(intent);
            }
        });

        deleteItem = (Button) findViewById(R.id.delete_button);
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected() != null) {
                    databaseHelper.deleteItem(adapter.getSelected());
                    Intent intent = new Intent(activity, ProfilePage.class);
                    intent.putExtra("EMAIL", ownerEmail);
                    intent.putExtra("NAME", ownerName);
                    startActivity(intent);
                }
            }
        });

    }



    private void initViews() {
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        rvItems = (RecyclerView) findViewById(R.id.recyclerViewItems);

    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listItems = new ArrayList<Item>();
        //itemRecyclerAdapter = new ItemsRecyclerAdapter(listItems);

        rvItems.setItemAnimator(new DefaultItemAnimator());
        rvItems.setHasFixedSize(true);

        databaseHelper = new DatabaseHelper2(activity);
        ownerEmail = getIntent().getStringExtra("EMAIL");
        ownerName = getIntent().getStringExtra("NAME");
        textViewName.setText(ownerName);
        textViewEmail.setText(ownerEmail);

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

