package com.example.whereismymoney;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.whereismymoney.databinding.ActivityMainMenuBinding;
import com.example.whereismymoney.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainMenu extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private ActivityMainMenuBinding binding;
    public RecyclerView recyclerView;
    public CustomAdapter customAdapter;
    NavigationBarView navView;
    NavController navController;

    public DatabaseHelper db;
    public ArrayList<String> money_id, money_description, money_sum, money_status;
    public int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_total, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //navView.setOnItemSelectedListener(this);


        navController.addOnDestinationChangedListener(this);

        displayData("home");
    }


    public void onOKclick(View view) {
        EditText date_date = findViewById(R.id.total_date);
        String date = date_date.getText().toString().trim();
        if (date.length() > 0) {
            displayData("total");
        }
        Button plus = findViewById(R.id.plus_button);
        Button minus = findViewById(R.id.minus_button);
        plus.setVisibility(View.VISIBLE);
        minus.setVisibility(View.VISIBLE);
    }


    @SuppressLint("Range")
    public void displayData(String check) {
        money_id = new ArrayList<>();
        money_sum = new ArrayList<>();
        money_description = new ArrayList<>();
        money_status = new ArrayList<>();
        db = new DatabaseHelper(MainMenu.this);
        String date;
        if (check.equals("home")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date_date = new Date(System.currentTimeMillis());
            date = formatter.format(date_date);
        } else {
            EditText date_date = findViewById(R.id.total_date);
            date = date_date.getText().toString().trim();
        }

        int user_id = PreferenceManager.getDefaultSharedPreferences(MainMenu.this).getInt("id", 0);
        Cursor cursor = db.getMoney(date, user_id);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                money_id.add(cursor.getString(cursor.getColumnIndex("_id")));
                money_description.add(cursor.getString(cursor.getColumnIndex("description")));
                money_sum.add(cursor.getString(cursor.getColumnIndex("sum")));
                money_status.add(cursor.getString(cursor.getColumnIndex("status")));
            }
        } else {

        }

        if (check.equals("home")) {
            recyclerView = findViewById(R.id.recyclerView);
        } else {
            recyclerView = findViewById(R.id.recyclerView_total);
        }

        customAdapter = new CustomAdapter(MainMenu.this, money_id, money_description, money_sum, money_status);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainMenu.this));
    }

    public void plusClick(View view) {
        status = 1;
        CardView cv = findViewById(R.id.newMoney);
        cv.setCardBackgroundColor(Color.parseColor("#E8F5E9"));
        cv.setVisibility(View.VISIBLE);
        displayData("home");
    }

    public void minusClick(View view) {
        status = 0;
        CardView cv = findViewById(R.id.newMoney);
        cv.setCardBackgroundColor(Color.parseColor("#FCE4EC"));
        cv.setVisibility(View.VISIBLE);
    }

    public void onAcceptClick(View view) {
        String date;
        EditText sum, description;
        if (view.getTag().toString().equals("home")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date_date = new Date(System.currentTimeMillis());
            date = formatter.format(date_date);
            description = findViewById(R.id.today_description);
            sum = findViewById(R.id.today_sum);
        } else {
            EditText date_date = findViewById(R.id.total_date);
            date = date_date.getText().toString().trim();
            description = findViewById(R.id.total_description);
            sum = findViewById(R.id.total_sum);
        }

        int user_id = PreferenceManager.getDefaultSharedPreferences(MainMenu.this).getInt("id", 0);

        DatabaseHelper db = new DatabaseHelper(MainMenu.this);
        db.createMoney(user_id, status, Float.valueOf(sum.getText().toString().trim()), date, description.getText().toString().trim());

        CardView cv = findViewById(R.id.newMoney);
        cv.setVisibility(View.INVISIBLE);

        displayData(view.getTag().toString());
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
//        int id = item.getItemId();
//        RecyclerView rv = findViewById(R.id.recyclerView);
//
//        if (handled) {
//            switch (id) {
//                case R.id.navigation_home:
//                    displayData("home");
//                    rv.setVisibility(View.VISIBLE);
//                    return true;
//                case R.id.navigation_total:
//                    rv.setVisibility(View.INVISIBLE);
//                    return true;
//                case R.id.navigation_profile:
//                    rv.setVisibility(View.INVISIBLE);
//                    return true;
//            }
//        }
//        return handled;
//
//    }
//}

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        RecyclerView rv = findViewById(R.id.recyclerView);
        if(destination.getId() == R.id.navigation_home) {
            displayData("home");
            rv.setVisibility(View.VISIBLE);
        } else {
            rv.setVisibility(View.INVISIBLE);
        }
    }
}