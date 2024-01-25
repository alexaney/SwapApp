package com.noamcanter.android.swapapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidtutorialshub.loginregister.R;
import com.noamcanter.android.swapapp.Item;
import com.noamcanter.android.swapapp.adapters.ItemsRecyclerAdapter;
import com.noamcanter.android.swapapp.adapters.ItemsRecyclerAdapter2;
import com.noamcanter.android.swapapp.sql.DatabaseHelper;
import com.noamcanter.android.swapapp.sql.DatabaseHelper2;

import java.util.ArrayList;

public class TradeActivity extends AppCompatActivity {
    private AppCompatActivity activity = TradeActivity.this;
    private TextView textViewName;
    private TextView textViewEmail;
    private ArrayList<Item> listItemsUser;
    private ArrayList<Item> listItemsOther;
    private Item specificItem;
    private RecyclerView rvItemsUser;
    private RecyclerView rvItemsOther;
    private DatabaseHelper2 databaseHelper;
    private DatabaseHelper db1;
    private Button newItem;
    private Button itemList;
    private Button profile;
    private Button trade;
    private String ownerEmail;
    private String otherEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        databaseHelper = new DatabaseHelper2(activity);
        db1 = new DatabaseHelper(activity);
        specificItem = (Item) getIntent().getSerializableExtra("Item");
        ownerEmail = getIntent().getStringExtra("EMAIL");
        otherEmail = specificItem.getOwnerEmail();

        rvItemsOther = (RecyclerView) findViewById(R.id.recyclerViewItems1);
        rvItemsUser = (RecyclerView) findViewById(R.id.recyclerViewItems2);

        rvItemsOther.setItemAnimator(new DefaultItemAnimator());
        rvItemsOther.setHasFixedSize(true);

        listItemsOther = new ArrayList<Item>();
        listItemsOther.addAll(databaseHelper.getUsersItems(otherEmail));
        ItemsRecyclerAdapter2 adapter = new ItemsRecyclerAdapter2(listItemsOther);
        rvItemsOther.setAdapter(adapter);
        rvItemsOther.setLayoutManager(new LinearLayoutManager(this));



        rvItemsUser.setItemAnimator(new DefaultItemAnimator());
        rvItemsUser.setHasFixedSize(true);

        listItemsUser = new ArrayList<Item>();
        listItemsUser.addAll(databaseHelper.getUsersItems(ownerEmail));
        ItemsRecyclerAdapter2 adapterb = new ItemsRecyclerAdapter2(listItemsUser);
        rvItemsUser.setAdapter(adapterb);
        rvItemsUser.setLayoutManager(new LinearLayoutManager(this));


        trade = (Button) findViewById(R.id.buttonTrade);
        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                String yourItems = "";
                String othersItems = "";
                ArrayList<Item> itemsu = adapterb.getSelected();
                ArrayList<Item> itemso = adapter.getSelected();

                for (int i = 0; i <= itemsu.size()-1; i++) {
                    yourItems = yourItems + itemsu.get(i).getItemName() + ", ";
                }
                for (int i = 0; i <= itemso.size()-1; i++) {
                    othersItems = othersItems + itemso.get(i).getItemName() + ", ";
                }

                String otherName = db1.getNameFromEmail(otherEmail);
                String userName = db1.getNameFromEmail(ownerEmail);
                String body = otherName + ", you have received a trade offer from "
                        + userName + "!  They are offering: "
                        + yourItems + "in exchange for your items: "
                        + othersItems
                        + "You can respond directly to this email to be in touch with "
                        + userName + ". ";

                Uri data = Uri.parse("mailto:?subject=" + "You have received a trade offer!"+ "&body=" + body + "&to=" + otherEmail);
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));

            }
        });
    }
}