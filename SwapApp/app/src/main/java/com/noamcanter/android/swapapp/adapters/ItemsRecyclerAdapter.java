package com.noamcanter.android.swapapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtutorialshub.loginregister.R;
import com.noamcanter.android.swapapp.Item;
import com.noamcanter.android.swapapp.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder> {
    private ArrayList<Item> listItems = new ArrayList<Item>();
    private int checkedPosition = -1;
    public ItemsRecyclerAdapter(ArrayList<Item> listItems) {
        this.listItems = listItems;
    }


    public ItemsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_item_recycler, parent, false);
        RecyclerView.ViewHolder viewHolder =  new ViewHolder(itemView);
        return (ViewHolder) viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewItem.setText(listItems.get(position).getItemName());
        holder.textViewDescription.setText(listItems.get(position).getDescription());
        byte[] bitmapdata = listItems.get(position).getItemImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        holder.imageViewItem.setImageBitmap(bitmap);
        holder.bind(listItems.get(position));
    }
    @Override
    public int getItemCount() {
        Log.v(ItemsRecyclerAdapter.class.getSimpleName(),""+listItems.size());
        return listItems.size();
    }
    /**
     * ViewHolder class
     */
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
            if (checkedPosition == -1) {
                imageView.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
            }
            textViewItem.setText(item.getItemName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }
    public Item getSelected() {
        if (checkedPosition != -1) {
            return listItems.get(checkedPosition);
        }
        return null;
    }
}
