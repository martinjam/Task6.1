package com.example.task61.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.example.task61.model.Food;
import com.example.task61.util.food_Util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class food_DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "tag";

    public food_DatabaseHelper(@Nullable Context context) {
        super(context, food_Util.DATABASE_NAME, null, food_Util.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_FOOD_TABLE = "CREATE TABLE " + food_Util.TABLE_NAME + "(" + food_Util.FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + food_Util.TITLE + " VARCHAR," + food_Util.DESCRIPTION + " VARCHAR," + food_Util.DATE + " VARCHAR," + food_Util.PICK_UP_TIME + " VARCHAR," +
                food_Util.QUANTITY + " VARCHAR," + food_Util.LOCATION + " VARCHAR," + food_Util.IMAGE + " VARCHAR," + food_Util.LISTED + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_FOOD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_FOOD_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_FOOD_TABLE, new String[]{food_Util.TABLE_NAME});

        onCreate(sqLiteDatabase);

    }

    public int updateListed(Food food)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(String.valueOf(food_Util.LISTED), food.getListed());

        return db.update(food_Util.TABLE_NAME, contentValues, food_Util.TITLE + "=?", new String[]{String.valueOf(food.getTitle())});
    }

    public long insertFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(food_Util.TITLE, food.getTitle());
        contentValues.put(food_Util.DESCRIPTION, food.getDescription());
        contentValues.put(food_Util.DATE, food.getDate());
        contentValues.put(food_Util.PICK_UP_TIME, food.getPick_up_time());
        contentValues.put(food_Util.QUANTITY, food.getQuantity());
        contentValues.put(food_Util.LOCATION, food.getLocation());
        contentValues.put(String.valueOf(food_Util.IMAGE), food.getImage());
        contentValues.put(String.valueOf(food_Util.LISTED), food.getListed());
        long newRowId = db.insert(food_Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public List<Food> fetchAllFood() {
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + food_Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(cursor.getInt(0));
                food.setTitle(cursor.getString(1));
                food.setDescription(cursor.getString(2));
                food.setDate(cursor.getString(3));
                food.setPick_up_time(cursor.getString(4));
                food.setQuantity(cursor.getString(5));
                food.setLocation(cursor.getString(6));
                food.setImage(cursor.getBlob(7));
                food.setListed(cursor.getInt(8));

                foodList.add(food);
            } while (cursor.moveToNext());
        }
        return foodList;
    }
}
