package com.example.pet_products_flea_market;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class ProductAddActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1001;
    private EditText etProductName, etProductPrice, etProductDescription;
    private Button btnSave, btnAddImage;
    private ImageView imgPreview1, imgPreview2, imgPreview3;
    private ImageButton btnDelete1, btnDelete2, btnDelete3;
    private ProductDBHelper dbHelper;
    private ArrayList<String> selectedImageUris = new ArrayList<>();
    private List<ImageView> previewViews = new ArrayList<>();
    private List<ImageButton> deleteButtons = new ArrayList<>();
    private ActivityResultLauncher<Intent> galleryLauncher;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        userId = getIntent().getStringExtra("USER_ID");
        dbHelper = new ProductDBHelper(this);
        initViews();
        initGalleryLauncher();
    }

    private void initViews() {
        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductDescription = findViewById(R.id.etProductDescription);
        btnSave = findViewById(R.id.btnSave);
        btnAddImage = findViewById(R.id.btnAddImage);

        imgPreview1 = findViewById(R.id.imgPreview1);
        imgPreview2 = findViewById(R.id.imgPreview2);
        imgPreview3 = findViewById(R.id.imgPreview3);

        btnDelete1 = findViewById(R.id.btnDelete1);
        btnDelete2 = findViewById(R.id.btnDelete2);
        btnDelete3 = findViewById(R.id.btnDelete3);

        previewViews.add(imgPreview1);
        previewViews.add(imgPreview2);
        previewViews.add(imgPreview3);

        deleteButtons.add(btnDelete1);
        deleteButtons.add(btnDelete2);
        deleteButtons.add(btnDelete3);

        btnSave.setOnClickListener(v -> saveProduct());
        btnAddImage.setOnClickListener(v -> checkPermissionAndOpenGallery());

        btnDelete1.setOnClickListener(v -> removeImage(0));
        btnDelete2.setOnClickListener(v -> removeImage(1));
        btnDelete3.setOnClickListener(v -> removeImage(2));
    }

    private void removeImage(int index) {
        if (index < selectedImageUris.size()) {
            selectedImageUris.remove(index);
            updateImagePreviews();
        }
    }

    private void initGalleryLauncher() {
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            try {
                                getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }
                            selectedImageUris.add(selectedImageUri.toString());
                            updateImagePreviews();
                        }
                    }
                }
        );
    }

    private void checkPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                openGallery();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "갤러리 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        if (selectedImageUris.size() >= 3) {
            Toast.makeText(this, "사진은 최대 3장까지 등록할 수 있습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void updateImagePreviews() {
        for (int i = 0; i < previewViews.size(); i++) {
            ImageView imageView = previewViews.get(i);
            ImageButton deleteButton = deleteButtons.get(i);
            if (i < selectedImageUris.size()) {
                imageView.setImageURI(Uri.parse(selectedImageUris.get(i)));
                imageView.setVisibility(View.VISIBLE);
                imageView.setBackgroundResource(R.drawable.rounded_gray_border);
                deleteButton.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
            }
        }
    }

    private void saveProduct() {
        String name = etProductName.getText().toString().trim();
        String price = etProductPrice.getText().toString().trim();
        String description = etProductDescription.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedImageUris.isEmpty()) {
            Toast.makeText(this, "최소 1장의 사진을 등록해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        String seller = (userId != null) ? userId : "unknown";

        long result = dbHelper.insertProduct(name, price, description, selectedImageUris, seller);

        if (result != -1) {
            Toast.makeText(this, "상품이 등록되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}