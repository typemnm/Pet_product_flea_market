package com.example.pet_products_flea_market;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 로그인 + 회원가입을 한 화면에서 처리하는 Activity
 * SharedPreferences를 이용해 간단한 계정 생성/로그인 기능을 수행함
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUserId;
    private EditText etUserPw;
    private Button btnLogin;
    private Button btnSignup;

    private SharedPreferences prefs;

    private static final String PREF_USER_DB = "UserDB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initPrefs();
        initListeners();
    }

    /**
     * UI 컴포넌트 초기화
     */
    private void initViews() {
        etUserId = findViewById(R.id.edtId);
        etUserPw = findViewById(R.id.edtPw);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
    }

    /**
     * SharedPreferences 초기화
     */
    private void initPrefs() {
        prefs = getSharedPreferences(PREF_USER_DB, Context.MODE_PRIVATE);
    }

    /**
     * 버튼 리스너 설정
     */
    private void initListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
        btnSignup.setOnClickListener(v -> handleSignup());
    }

    /**
     * 로그인 처리
     */
    private void handleLogin() {
        String id = etUserId.getText().toString().trim();
        String pw = etUserPw.getText().toString().trim();

        if (!validateInput(id, pw)) {
            showToast("아이디와 비밀번호를 입력하세요");
            return;
        }

        String savedPw = prefs.getString(id, null);

        if (savedPw == null) {
            showToast("존재하지 않는 계정입니다");
            return;
        }

        if (!savedPw.equals(pw)) {
            showToast("비밀번호가 일치하지 않습니다");
            return;
        }

        showToast("로그인 성공!");

        moveToProductList(id);
    }

    /**
     * 회원가입 처리
     */
    private void handleSignup() {
        String id = etUserId.getText().toString().trim();
        String pw = etUserPw.getText().toString().trim();

        if (!validateInput(id, pw)) {
            showToast("아이디와 비밀번호를 모두 입력하세요");
            return;
        }

        if (prefs.contains(id)) {
            showToast("이미 등록된 아이디입니다");
            return;
        }

        prefs.edit().putString(id, pw).apply();
        showToast("회원가입 완료! 로그인해주세요");
    }

    /**
     * 입력값 검증
     */
    private boolean validateInput(String id, String pw) {
        return !(TextUtils.isEmpty(id) || TextUtils.isEmpty(pw));
    }

    /**
     * 로그인 성공 시 회원정보(ID)를 가지고 상품 목록 화면으로 이동
     */
    private void moveToProductList(String userId) {
        Intent intent = new Intent(LoginActivity.this, ProductListActivity.class);
        intent.putExtra("USER_ID", userId);   // 🔥 회원정보 전달
        startActivity(intent);
        finish();
    }
    /**
     * 토스트 출력
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

