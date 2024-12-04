package com.example.project;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.view.MenuItem;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private DBHelper dbHelper; // DBHelper 인스턴스 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerLayout 초기화
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ActionBar와 Drawer 연결
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // DBHelper 초기화
        dbHelper = new DBHelper(this);

        // 캐시된 데이터 로드
        loadCachedTrades();

        // 초기 화면 설정
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new BTCPriceFragment()) // 초기 프래그먼트
                    .commit();
            navigationView.setCheckedItem(R.id.nav_btc_price); // 기본 선택 메뉴
        }

        // 최신 데이터 가져오기
        fetchAllTrades();
    }

    // 데이터베이스에서 캐시된 데이터를 로드하는 함수
    private void loadCachedTrades() {
        String cachedData = dbHelper.getTrades();
        if (cachedData != null) {
            Type type = new TypeToken<List<Trade>>() {}.getType();
            List<Trade> cachedTrades = new Gson().fromJson(cachedData, type);

            // TradeManager에 캐시된 데이터 설정
            TradeManager.getInstance().setAllTrades(cachedTrades);
            Log.d("CACHE_TRADES", "Loaded cached trades: " + cachedTrades.size());
        }
    }

    // 서버에서 데이터를 가져와 데이터베이스에 저장하는 함수
    private void fetchAllTrades() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Trade>> call = apiService.getTrades();

        call.enqueue(new Callback<List<Trade>>() {
            @Override
            public void onResponse(@NonNull Call<List<Trade>> call, @NonNull Response<List<Trade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // TradeManager에 데이터 저장
                    List<Trade> trades = response.body();
                    TradeManager.getInstance().setAllTrades(trades);

                    // 데이터를 JSON으로 변환하여 데이터베이스에 저장
                    String jsonData = new Gson().toJson(trades);
                    dbHelper.saveTrades(jsonData);

                    Log.d("FETCH_TRADES", "Fetched and cached trades: " + trades.size());
                } else {
                    Log.e("FETCH_TRADES", "Failed response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Trade>> call, @NonNull Throwable t) {
                Log.e("FETCH_TRADES", "Network error: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        // 메뉴 항목에 따른 프래그먼트 선택
        if (item.getItemId() == R.id.nav_btc_price) {
            selectedFragment = new BTCPriceFragment();
        } else if (item.getItemId() == R.id.nav_trade_decision) {
            selectedFragment = new TradeDecisionFragment();
        } else if (item.getItemId() == R.id.nav_btc_balance) {
            selectedFragment = new BTCBalanceFragment();
        } else if (item.getItemId() == R.id.nav_krw_balance) {
            selectedFragment = new KRWBalanceFragment();
        } else if (item.getItemId() == R.id.nav_filtered_trade) {
            selectedFragment = new FilteredTradeFragment();
        }

        // 선택된 프래그먼트를 container에 삽입
        if (selectedFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }

        // Navigation Drawer 닫기
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START); // Navigation Drawer 닫기
        } else {
            super.onBackPressed(); // 기본 뒤로가기 동작
        }
    }
}
