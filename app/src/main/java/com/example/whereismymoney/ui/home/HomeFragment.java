package com.example.whereismymoney.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereismymoney.CustomAdapter;
import com.example.whereismymoney.DatabaseHelper;
import com.example.whereismymoney.MainActivity;
import com.example.whereismymoney.MainMenu;
import com.example.whereismymoney.R;
import com.example.whereismymoney.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private int status;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //((MainMenu)getActivity()).checkData("home");


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    public void plusClick(View view) {
        status = 1;
        CardView cv = view.findViewById(R.id.newMoney);
        cv.setCardBackgroundColor(Color.parseColor("#E8F5E9"));
        cv.setVisibility(View.VISIBLE);
    }

    public void minusClick(View view) {
        status = 0;
        CardView cv = view.findViewById(R.id.newMoney);
        cv.setCardBackgroundColor(Color.parseColor("#FCE4EC"));
        cv.setVisibility(View.VISIBLE);
    }

    public void onAcceptClick(View view) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date_date = new Date(System.currentTimeMillis());

        String date = formatter.format(date_date);
        EditText sum, description;
        description = view.findViewById(R.id.today_description);
        sum = view.findViewById(R.id.today_sum);
        int user_id = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getInt("id", 0);

        DatabaseHelper db = new DatabaseHelper(this.getActivity());
        db.createMoney(user_id, status, Float.valueOf(sum.getText().toString().trim()), date, description.getText().toString().trim());

        CardView cv = view.findViewById(R.id.newMoney);
        cv.setVisibility(View.INVISIBLE);
    }
}