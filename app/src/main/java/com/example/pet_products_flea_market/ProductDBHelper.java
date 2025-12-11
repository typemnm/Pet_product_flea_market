package com.example.pet_products_flea_market;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class ProductDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Product.db";
    private static final int DATABASE_VERSION = 4;

    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_URIS = "image_uris";
    public static final String COLUMN_SELLER_ID = "seller_id";
    public static final String COLUMN_BUYER_ID = "buyer_id";
    public static final String COLUMN_IS_SOLD = "is_sold";
    public static final String COLUMN_TRADING_ADDRESS = "trading_address";
    public static final String COLUMN_PAYMENT_METHOD = "payment_method";

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
                COLUMN_IMAGE_URIS + " TEXT, " +
                COLUMN_SELLER_ID + " TEXT, " +
                COLUMN_BUYER_ID + " TEXT, " +
                COLUMN_IS_SOLD + " INTEGER DEFAULT 0, " +
                COLUMN_TRADING_ADDRESS + " TEXT, " +
                COLUMN_PAYMENT_METHOD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // 상품 등록
    public long insertProduct(String name, String price, String description, ArrayList<String> imageUris, String sellerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_SELLER_ID, sellerId);
        values.put(COLUMN_IS_SOLD, 0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < imageUris.size(); i++) {
            sb.append(imageUris.get(i));
            if (i < imageUris.size() - 1) sb.append(",");
        }
        values.put(COLUMN_IMAGE_URIS, sb.toString());

        return db.insert(TABLE_PRODUCTS, null, values);
    }

    // 구매 처리
    public void updateProductSold(int productId, String buyerId, String address, String paymentMethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_SOLD, 1);
        values.put(COLUMN_BUYER_ID, buyerId);
        values.put(COLUMN_TRADING_ADDRESS, address);
        values.put(COLUMN_PAYMENT_METHOD, paymentMethod);

        db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(productId)});
    }

    // 상품 삭제
    public void deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(productId)});
    }

    // 조회 로직
    public ArrayList<Product> loadProducts(String selection, String[] selectionArgs) {
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, selection, selectionArgs, null, null, COLUMN_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String imagesStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URIS));
                String sellerId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SELLER_ID));
                String buyerId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUYER_ID));
                boolean isSold = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_SOLD)) == 1;
                String address = "";
                String payment = "";
                int addrIdx = cursor.getColumnIndex(COLUMN_TRADING_ADDRESS);
                int payIdx = cursor.getColumnIndex(COLUMN_PAYMENT_METHOD);

                if (addrIdx != -1) address = cursor.getString(addrIdx);
                if (payIdx != -1) payment = cursor.getString(payIdx);

                ArrayList<String> imageUris = new ArrayList<>();
                if (imagesStr != null && !imagesStr.isEmpty()) {
                    String[] uris = imagesStr.split(",");
                    for (String s : uris) {
                        if(!s.trim().isEmpty()) imageUris.add(s.trim());
                    }
                }
                productList.add(new Product(id, name, price, description, imageUris, sellerId, buyerId, isSold, address, payment));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    public ArrayList<Product> loadAvailableProducts() {
        return loadProducts(COLUMN_IS_SOLD + " = 0", null);
    }

    public ArrayList<Product> loadUserSales(String userId) {
        return loadProducts(COLUMN_SELLER_ID + " = ?", new String[]{userId});
    }

    public ArrayList<Product> loadUserPurchases(String userId) {
        return loadProducts(COLUMN_BUYER_ID + " = ?", new String[]{userId});
    }
}