package com.example.laptrinhandroid_sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.NameViewHolder> {
    LayoutInflater inflater;
    List<Location> list = new ArrayList<>();

    public LocationAdapter(Context context, List<Location> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.line_item,parent,false);
        return new NameViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
        Location location = list.get(position);
        holder.txt_li_location.setText(location.toString());
        holder.ibt_li_update.setBackgroundResource(R.drawable.ic_baseline_edit_location_alt_24);
        holder.ibt_li_remove.setBackgroundResource(R.drawable.ic_baseline_cancel_24);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void dataChange(List<Location> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        LocationAdapter adapter;
        TextView txt_li_location;
        ImageButton ibt_li_update, ibt_li_remove;
        public NameViewHolder(@NonNull View itemView, LocationAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            txt_li_location = itemView.findViewById(R.id.txt_li_name);
            ibt_li_remove = itemView.findViewById(R.id.ibt_li_remove);
            ibt_li_update = itemView.findViewById(R.id.ibt_li_update);
        }
    }
}
