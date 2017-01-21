package com.example.anders.devfest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Room> rooms;
    private Fragment fragment;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageViewStatus;
        TextView textViewStatus;
        TextView textViewName;
        TextView textViewDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.card);
            this.imageViewStatus = (ImageView) itemView.findViewById(R.id.imageView);
            this.textViewStatus = (TextView) itemView.findViewById(R.id.textView1);
            this.textViewName = (TextView) itemView.findViewById(R.id.textView2);
            this.textViewDescription = (TextView) itemView.findViewById(R.id.textView3);
        }
    }

    public CustomAdapter(ArrayList<Room> rooms, Fragment fragment) {
        this.rooms = rooms;
        this.fragment = fragment;
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Room room = rooms.get(position);
        if(room.getOwner().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.imageViewStatus.setImageResource(R.drawable.ic_star_black_24dp);
        }
        holder.textViewStatus.setText(Integer.toString(room.getSize()) + " / " + Integer.toString(room.getCapacity()));
        holder.textViewName.setText(room.getName());
        holder.textViewDescription.setText(room.getDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeFragment)fragment).onClickHandler();
            }
        });
    }

}
