package com.example.task61;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.task61.data.food_DatabaseHelper;
import com.example.task61.data.user_DatabaseHelper;
import com.example.task61.model.Food;
import com.example.task61.model.User;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

public class NewFoodItem extends AppCompatActivity {
    Button foodSaveButton;
    ImageView foodImage;
    EditText foodTitleEditText, descriptionEditText, pickupEditText, quantityEditText, locationEditText;
    DatePicker datePicker;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    food_DatabaseHelper db;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food_item);
        foodSaveButton = findViewById(R.id.foodSaveButton);

        foodImage = findViewById(R.id.imageView);
        foodTitleEditText = findViewById(R.id.foodTitleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        datePicker = findViewById(R.id.datePicker);
        pickupEditText = findViewById(R.id.pickupEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        locationEditText = findViewById(R.id.locationEditText);
        db = new food_DatabaseHelper(this);

        foodImage.setOnClickListener(new View.OnClickListener() {

            //permissions questioning taken from https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
            }
        });

        foodSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = foodTitleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                String day = "Day = " + datePicker.getDayOfMonth();
                String month = "Month = " + datePicker.getMonth();
                String year = "Year = " + datePicker.getYear();
                String date = day + "/" + month + "/" + year;

                String pickup = pickupEditText.getText().toString();
                String quantity = quantityEditText.getText().toString();
                String location = locationEditText.getText().toString();

                BitmapDrawable drawable = (BitmapDrawable) foodImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                byte[] bytes = getBitmapAsByteArray(bitmap);

                if (title != null && date != null && location != null) {
                    long result = db.insertFood(new Food(date, title, description, pickup, quantity, location, bytes));
                    if (result > 0) {
                        Toast.makeText(com.example.task61.NewFoodItem.this, "Food entered successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(com.example.task61.NewFoodItem.this, "Error occured", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(com.example.task61.NewFoodItem.this, "Please enter data in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(NewFoodItem.this, new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);
        boolean yes = false;

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(NewFoodItem.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                yes = true;
            } else {
                Toast.makeText(NewFoodItem.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (yes == true) {
            openGallery();
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            foodImage.setImageURI(imageUri);
        }
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }
}