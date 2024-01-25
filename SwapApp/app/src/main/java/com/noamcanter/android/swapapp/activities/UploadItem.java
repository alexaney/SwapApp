package com.noamcanter.android.swapapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.noamcanter.android.swapapp.sql.DatabaseHelper2;

import com.androidtutorialshub.loginregister.R;
import com.noamcanter.android.swapapp.Item;
import com.noamcanter.android.swapapp.sql.DatabaseHelper2;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;

public class UploadItem extends AppCompatActivity {
    private AppCompatActivity activity = UploadItem.this;
    private EditText itemName;
    private Button uploadImageButton;
    private EditText itemDescription;
    private Button postItemButton;
    private Button itemList;
    private Button profilePage;
    private String ownerEmail;
    private String ownerName;
    private ImageView itemImage;
    private DatabaseHelper2 db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_item);
        itemName = (EditText) (findViewById(R.id.new_item_name));
        uploadImageButton = (Button) (findViewById(R.id.upload_image_button));
        itemDescription = (EditText) (findViewById(R.id.new_item_description));
        postItemButton = (Button) (findViewById(R.id.post_new_item_button));
        itemImage = (ImageView) (findViewById(R.id.item_image));
        ownerEmail = getIntent().getStringExtra("EMAIL");
        ownerName = getIntent().getStringExtra("NAME");
        db = new DatabaseHelper2(UploadItem.this);
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);

            }
        });
        postItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable) itemImage.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] byteImage = baos.toByteArray();
                String ownerEmail = getIntent().getStringExtra("EMAIL");
                String ownerName = getIntent().getStringExtra("NAME");
                Item item = new Item(itemName.getText().toString(), ownerEmail, byteImage, itemDescription.getText().toString());
                try {
                    db.addItem(item);
                    item.setItemID(item.getItemID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent i = new Intent(UploadItem.this, ProfilePage.class);
                i.putExtra( "EMAIL", ownerEmail);
                i.putExtra( "NAME", ownerName);
                startActivity(i);
            }
        });

        itemList = (Button) (findViewById(R.id.item_list_from_new_item));
        itemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, FeedActivity.class);
                intent.putExtra("EMAIL", ownerEmail);
                intent.putExtra("NAME", ownerName);
                startActivity(intent);
            }
        });

        profilePage = (Button) (findViewById(R.id.profile_from_upload));
        profilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ProfilePage.class);
                intent.putExtra("EMAIL", ownerEmail);
                intent.putExtra("NAME", ownerName);

                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    itemImage.setImageURI(selectedImage);
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    itemImage.setImageURI(selectedImage);
                }
                break;
        }
    }
}