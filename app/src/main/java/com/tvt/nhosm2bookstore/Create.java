package com.tvt.nhosm2bookstore;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tvt.nhosm2bookstore.databinding.ActivityCreateBinding;

public class Create extends AppCompatActivity {

    ActivityCreateBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceframe(new CreateFragment());



        binding.bottomNavigationView.setOnItemSelectedListener(item ->{

            switch(item.getItemId()){
                case R.id.home:
                    replaceframe(new HomeFragment() );
                    break;
                case R.id.lib:
                    replaceframe(new LibFragment() );
                    break;
                case R.id.sear:
                    replaceframe(new SearchFragment() );
                    break;
                case R.id.cre:
                    replaceframe(new CreateFragment() );
                    break;
                case R.id.not:
                    replaceframe(new NotiFragment() );
                    break;
            }

            return true;
        } );

    }

    private  void replaceframe(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }



}
