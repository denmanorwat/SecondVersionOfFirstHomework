package com.example.recyclerview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class AdapterFragment extends Fragment {
    ChosenOne co;
    private int quantity;

    private GridLayoutManager getGridLayoutManager(View v) {
        GridLayoutManager layout;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layout = new GridLayoutManager(v.getContext(), 3);
        } else {
            layout = new GridLayoutManager(v.getContext(), 4);
        }
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            co = (ChosenOne) context;
        } catch (ClassCastException e) {
            Log.w("ClassCastException", "Activity must implement ChosenOne interface");
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Quantity", quantity);
        editor.apply();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        quantity = sp == null ? 100 : sp.getInt("Quantity", 100);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final CustomAdapter ca;
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        RecyclerView rv = v.findViewById(R.id.myRecyclerView);
        GridLayoutManager layout = getGridLayoutManager(v);
        ca = new CustomAdapter(quantity);
        rv.setAdapter(ca);
        rv.setLayoutManager(layout);
        Button bt = v.findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                ca.quantity = quantity;
                ca.notifyDataSetChanged();
            }
        });
        return v;
    }


    //---------------------------------------IMPLEMENT ME section---------------------------------------------------------------------

    @FunctionalInterface
    interface ChosenOne {
        void InitSecondFragment(Number num);
    }

    //---------------------------------------Private class section,utility------------------------------------------------------------

    public class Number implements Serializable {
        int number;
        int color;

        Number(int number) {
            this.number = number;
            this.color = this.colorDecider();
        }

        private int colorDecider() {
            if (this.number % 2 == 0) return getResources().getColor(R.color.colorRed);
            else return getResources().getColor(R.color.colorBlue);
        }
    }

    //--------------------------------------Private class section,Recycler------------------------------------------------------------

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView v;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView.findViewById(R.id.First);
        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
        private int quantity;

        CustomAdapter(int quantity) {
            this.quantity = quantity;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v = inflater.inflate(R.layout.w_reclayout, viewGroup, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder cvh, int index) {
            final Number num = new Number(index);
            cvh.v.setText(String.valueOf(num.number));
            cvh.v.setTextColor(num.color);
            cvh.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    co.InitSecondFragment(num);
                }
            });
        }

        @Override
        public int getItemCount() {
            return quantity;
        }
    }
}
