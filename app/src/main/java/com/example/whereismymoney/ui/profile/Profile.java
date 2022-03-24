package com.example.whereismymoney.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whereismymoney.MainMenu;
import com.example.whereismymoney.R;
import com.example.whereismymoney.databinding.FragmentProfileBinding;

public class Profile extends Fragment {

    private FragmentProfileBinding binding;

    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String email = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getString("email", "default");
        String username = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getString("username", "default");

        TextView welcome = root.findViewById(R.id.welcome);
        EditText email_profile = root.findViewById(R.id.email_profile);
        EditText username_profile = root.findViewById(R.id.username_profile);
        email_profile.setText(email);
        username_profile.setText(username);
        welcome.setText("Welcome, " + username);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}