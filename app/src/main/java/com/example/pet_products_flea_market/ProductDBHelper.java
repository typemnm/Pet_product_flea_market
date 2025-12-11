package com.example.pet_products_flea_market;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class ProductDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Product.db";
    // 스키마가 변경되었으므로 버전을 올립니다. 앱 재설치 필요할 수 있음.
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    // 컬럼명 변경 (명확성을 위해)
    public static final String COLUMN_IMAGE_URIS = "image_uris";

    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                // 컬럼명 변경 반영
                COLUMN_IMAGE_URIS + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 버전이 변경되면 기존 테이블 삭제 후 재생성
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public long insertProduct(String name, String price, String description, ArrayList<String> imageUris) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESCRIPTION, description);

        // URI 문자열 리스트를 쉼표로 구분된 문자열로 변환하여 저장
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < imageUris.size(); i++) {
            sb.append(imageUris.get(i));
            if (i < imageUris.size() - 1) sb.append(",");
        }
        values.put(COLUMN_IMAGE_URIS, sb.toString());

        return db.insert(TABLE_PRODUCTS, null, values);
    }

    public ArrayList<Product> loadAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String imagesStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URIS));

                ArrayList<String> imageUris = new ArrayList<>();
                if (imagesStr != null && !imagesStr.isEmpty()) {
                    String[] uris = imagesStr.split(",");
                    for (String s : uris) {
                        // 빈 문자열이 아닌 경우에만 추가
                        if(!s.trim().isEmpty()) {
                            imageUris.add(s.trim());
                        }
                    }
                }

                productList.add(new Product(id, name, price, description, imageUris));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }
}