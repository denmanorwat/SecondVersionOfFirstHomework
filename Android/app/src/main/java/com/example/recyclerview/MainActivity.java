package com.example.recyclerview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements AdapterFragment.ChosenOne {

    @Override
    public void InitSecondFragment(AdapterFragment.Number num) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment f = fragmentManager.findFragmentById(R.id.Fragment);
        if (f != null) {
            NumberFragment nf1 = new NumberFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Number",num);
            nf1.setArguments(bundle);
            transaction.replace(R.id.Fragment, nf1);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment f = fragmentManager.findFragmentById(R.id.Fragment);
        if (f == null) {
            transaction.replace(R.id.Fragment, new AdapterFragment());
            transaction.commit();
        }
    }

}
