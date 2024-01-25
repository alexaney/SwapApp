package com.noamcanter.android.swapapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.androidtutorialshub.loginregister.R;
import com.noamcanter.android.swapapp.Item;
import com.noamcanter.android.swapapp.sql.DatabaseHelper2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class SpecificItem extends AppCompatActivity implements Serializable {
    private Item specificItem;
    private int itemId;
    private DatabaseHelper2 db;
    private TextView tvItemName;
    private TextView tvItemDescription;
    private ImageView ivItemImage;
    private Button tradeButton;
    private String ownerEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_item);
        specificItem = (Item) getIntent().getSerializableExtra("Item");
        ownerEmail = getIntent().getStringExtra("EMAIL");
        tvItemName = (TextView) findViewById(R.id.specific_item_name);
        tvItemDescription = (TextView) findViewById(R.id.specific_item_description);
        ivItemImage = (ImageView) findViewById(R.id.specific_item_image);
        tvItemName.setText(specificItem.getItemName());
        tvItemDescription.setText(specificItem.getDescription());
        Bitmap bmp = BitmapFactory.decodeByteArray(specificItem.getItemImage(), 0, specificItem.getItemImage().length);
        ivItemImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 500, 500, false));
        tradeButton = (Button) findViewById(R.id.trade_button);
        tradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SpecificItem.this, TradeActivity.class);
                i.putExtra("EMAIL", ownerEmail);
                i.putExtra("Item", specificItem);
                startActivity(i);
            }
        });
    }
}