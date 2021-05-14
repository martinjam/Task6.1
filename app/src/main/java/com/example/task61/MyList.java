package com.example.task61;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.task61.data.food_DatabaseHelper;
import com.example.task61.model.Food;

import java.util.ArrayList;
import java.util.List;

public class MyList extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, FoodAdapter.OnRowClickListener {
    ImageView addFoodImageView, popupImageView;
    food_DatabaseHelper db_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db_food = new food_DatabaseHelper(this);
        List<Food> foodList = db_food.fetchAllFood();
        List<Food> foodList_refined = new ArrayList<Food>();;

        RecyclerView foodRecylerView = findViewById(R.id.foodRecyclerView);
        if (foodList != null) {
            for (int i = 0; i < foodList.size(); i++) {

                if (foodList.get(i).getListed() == 1) {
                    foodList_refined.add(foodList.get(i));
                }
            }
        }

        FoodAdapter adapter = new FoodAdapter(foodList_refined, MyList.this, this);
        foodRecylerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        foodRecylerView.setLayoutManager(layoutManager);

        popupImageView = findViewById(R.id.popupImageView);
        popupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MyList.this, v);
                popup.setOnMenuItemClickListener(MyList.this);
                popup.inflate(R.menu.menu);
                popup.show();
            }
        });

        addFoodImageView = findViewById(R.id.addFoodImageView);

        addFoodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIntent = new Intent(com.example.task61.MyList.this, com.example.task61.NewFoodItem.class);
                startActivity(createIntent);
            }
        });


    }

    public void addToListImageView(int position) {
        db_food = new food_DatabaseHelper(this);
        List<Food> foodList = db_food.fetchAllFood();

        if (db_food.updateListed(foodList.get(position)) == 1) {
            Toast.makeText(this, "Food saved to My List", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Food was not saved.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent createIntent = new Intent(com.example.task61.MyList.this, com.example.task61.Home.class);
                startActivity(createIntent);
                return true;
            case R.id.account:
                Intent createIntent2 = new Intent(com.example.task61.MyList.this, com.example.task61.MainActivity.class);
                startActivity(createIntent2);
                return true;
            case R.id.myList:
                Intent createIntent3 = new Intent(com.example.task61.MyList.this, com.example.task61.MyList.class);
                startActivity(createIntent3);
                return true;
            default:
                return false;
        }
    }
}