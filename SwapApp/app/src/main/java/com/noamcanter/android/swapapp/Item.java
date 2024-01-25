package com.noamcanter.android.swapapp;

import android.os.AsyncTask;
import java.io.Serializable;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.noamcanter.android.swapapp.activities.ProfilePage;
import com.noamcanter.android.swapapp.sql.DatabaseHelper2;
import com.noamcanter.android.swapapp.adapters.ItemsRecyclerAdapter;

public class Item implements Serializable {
    private int itemID;
    private String itemName;
    private String ownerEmail;
    private byte[] itemImage;
    private String description;
    private boolean isChecked = false;
    private List<Item> listItems = new ArrayList<Item>();



    public Item(){ }
    public Item(String itemName, String ownerEmail, byte[] itemImage, String description){
        this.ownerEmail = ownerEmail;
        //new AsyncTask<Void, Void, Void>() {
        //    @Override
        //    protected Void doInBackground(Void... params) {
        //        if (!listItems.isEmpty())
        //            listItems.clear();
        //            listItems.addAll(databaseHelper2.getAllItems());
        //            return null;
        //        }
        //    @Override
        //    protected void onPostExecute(Void aVoid) {
        //        super.onPostExecute(aVoid);
        //        itemsRecyclerAdapter.notifyDataSetChanged();
        //    }
        //}.execute();
        //int count = 1;
        //for (int i = 0; i < listItems.size(); i++) {
        //    if (listItems.get(i).getOwnerEmail() == this.ownerEmail) {
        //        count++;
        //    }
        //}
        //itemID = 1;

        this.itemName = itemName;
        this.itemImage = itemImage;
        this.description = description;
    }

    public int getItemID() {
        Item i = new Item(itemName,ownerEmail,itemImage,description);
        //itemID = db.getID(i);
        return itemID;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
