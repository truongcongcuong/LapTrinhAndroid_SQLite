package com.example.laptrinhandroid_sqlite;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.NameViewHolder> {
    LayoutInflater inflater;
    List<Location> list;
    Context context;
    DatabaseHandler databaseHandler;

    public LocationAdapter(Context context, List<Location> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.line_item,parent,false);
        return new NameViewHolder(view, this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
        Location location = list.get(position);
        holder.txt_li_location.setText(position+1+". "+location.getName());
        holder.ibt_li_update.setBackgroundResource(R.drawable.ic_baseline_edit_location_alt_24);
        holder.ibt_li_remove.setBackgroundResource(R.drawable.ic_baseline_cancel_24);

        holder.ibt_li_remove.setOnClickListener(v -> {

            list.remove(position);
            databaseHandler.removeLocation(location.getId());
            notifyDataSetChanged();
        });

        holder.ibt_li_update.setOnClickListener(v -> showForgotDialog(context,position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void dataChange(List<Location> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public static class NameViewHolder extends RecyclerView.ViewHolder {
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





    private void showForgotDialog(Context c, int position) {
        final EditText taskEditText = new EditText(c);
        taskEditText.setText(list.get(position).getName());
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Edit Location?")
                .setMessage("Enter new Location")
                .setView(taskEditText)
                .setPositiveButton("Update", (dialog1, which) -> {

                    databaseHandler.updateLocation(new Location(list.get(position).getId(),taskEditText.getText().toString()));
                    list = databaseHandler.getLocations();
                    notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}
