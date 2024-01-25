package com.noamcanter.android.swapapp.adapters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtutorialshub.loginregister.R;
import com.noamcanter.android.swapapp.Item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemsRecyclerAdapter2 extends RecyclerView.Adapter<ItemsRecyclerAdapter2.ViewHolder> {
    private ArrayList<Item> mlistItems = new ArrayList<Item>();
    private int checkedPosition = -1;
    public ItemsRecyclerAdapter2(ArrayList<Item> listItems) {
        mlistItems = listItems;
    }

    public ItemsRecyclerAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_item_recycler, parent, false);
        RecyclerView.ViewHolder viewHolder =  new ItemsRecyclerAdapter2.ViewHolder(itemView);
        return (ItemsRecyclerAdapter2.ViewHolder) viewHolder;
    }
    @Override
    public void onBindViewHolder(ItemsRecyclerAdapter2.ViewHolder holder, int position) {
        holder.textViewItem.setText(mlistItems.get(position).getItemName());
        holder.textViewDescription.setText(mlistItems.get(position).getDescription());
        byte[] bitmapdata = mlistItems.get(position).getItemImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        holder.imageViewItem.setImageBitmap(bitmap);
        holder.bind(mlistItems.get(position));
    }
    @Override
    public int getItemCount() {
        Log.v(ItemsRecyclerAdapter.class.getSimpleName(),""+mlistItems.size());
        return mlistItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItem;
        public TextView textViewDescription;
        public ImageView imageViewItem;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textViewItem = (TextView) view.findViewById(R.id.textViewItem);
            textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
            imageViewItem = (ImageView) view.findViewById(R.id.imageViewzItem);
            imageView = (ImageView) view.findViewById(R.id.tick);
        }

        void bind(final Item item) {
            imageView.setVisibility(item.isChecked() ? View.VISIBLE : View.GONE);
            textViewItem.setText(item.getItemName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setChecked(!item.isChecked());
                    imageView.setVisibility(item.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
    public ArrayList<Item> getSelected() {
        ArrayList<Item> selected = new ArrayList<>();
        for (int i = 0; i < mlistItems.size(); i++) {
            if (mlistItems.get(i).isChecked()) {
                selected.add(mlistItems.get(i));
            }
        }
        return selected;
    }

}