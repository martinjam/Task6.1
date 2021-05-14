package com.example.task61;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<com.example.task61.model.Food> foodList;
    private Context context;
    private OnRowClickListener listener;

    public FoodAdapter(List<com.example.task61.model.Food> foodList, Context context, OnRowClickListener clickListener) {
        this.foodList = foodList;
        this.context = context;
        this.listener = clickListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.food_row, parent, false);
        return new FoodViewHolder(itemView, listener);
    }

    public interface OnRowClickListener {
        void addToListImageView(int position);
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView foodImageView;
        public TextView titleTextView;
        public TextView descriptionTextView;
        public OnRowClickListener onRowClickListener;

        public FoodViewHolder(@NonNull View itemView, OnRowClickListener onRowClickListener) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.foodImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descTextView);
            this.onRowClickListener = onRowClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRowClickListener.addToListImageView(getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        byte[] image = foodList.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length, options);
        holder.foodImageView.setImageBitmap(bitmap);
        holder.titleTextView.setText(foodList.get(position).getTitle());
        holder.descriptionTextView.setText(foodList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (foodList != null) {
            return foodList.size();
        } else {
            return 0;
        }
    }
}