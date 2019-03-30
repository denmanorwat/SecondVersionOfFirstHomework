package com.example.recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class NumberFragment extends Fragment {

    AdapterFragment.Number number;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("Number",number);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) number = (AdapterFragment.Number) savedInstanceState.getSerializable("Number");
        number = (AdapterFragment.Number) getArguments().getSerializable("Number");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.big_numba, container, false);
        TextView tv = v.findViewById(R.id.textView);
        tv.setText(String.valueOf(number.number));
        tv.setTextColor(number.color);
        return v;
    }

}
