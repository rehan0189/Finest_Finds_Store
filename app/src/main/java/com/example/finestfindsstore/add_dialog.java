package com.example.finestfindsstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add_dialog extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editProductName, editProductPrice;
    private Button btnSelectImage, btnAddProduct;
    private Uri imageUri; // To store the selected image URI

    public add_dialog(MainActivity mainActivity) {
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_dialogue);

        editProductName = findViewById(R.id.editProductName);
        editProductPrice = findViewById(R.id.editProductPrice);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the image gallery to select an image
                openImageGallery();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Upload the image to Firebase Storage and save product details to Firebase Realtime Database
                uploadImageAndSaveProduct();
            }
        });
    }

    private void openImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            imageUri = data.getData();
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageAndSaveProduct() {
        if (imageUri != null) {
            // Get references to Firebase Storage and Realtime Database
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("product_images").child(System.currentTimeMillis() + ".jpg");

            // Upload image to Firebase Storage
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Image uploaded successfully
                            // Get the download URL of the uploaded image
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    // Save product details to Firebase Realtime Database
                                    saveProductToDatabase(downloadUri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the failure
                            Toast.makeText(add_dialog.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProductToDatabase(String imageUrl) {
        // Get references to Firebase Realtime Database
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("products");

        // Get product details from EditText fields
        String productName = editProductName.getText().toString().trim();
        String productPrice = editProductPrice.getText().toString().trim();

        // Validate inputs
        if (productName.isEmpty() || productPrice.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save product details to Firebase Realtime Database
        Product newProduct = new Product(productName, productPrice, imageUrl);
        productsRef.push().setValue(newProduct)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Product details saved successfully
                            Toast.makeText(add_dialog.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity
                        } else {
                            // Handle the failure
                            Toast.makeText(add_dialog.this, "Product add failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void show() {
    }
}
