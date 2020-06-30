package com.example.zamanyonetimi.ui.Matrix;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zamanyonetimi.R;
import com.example.zamanyonetimi.ui.Inbox.InboxFragment;

public class MatrixFragment extends Fragment {
    RecyclerView recyclerView2, recyclerView4, recyclerView5, recyclerView6;
    View root;
    private MatrixViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(MatrixViewModel.class);
        View root = inflater.inflate(R.layout.fragment_matrix, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                tanimla();
                recyclerView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), InboxFragment.class);
                        startActivity(intent);
                    }

                });
                recyclerView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), InboxFragment.class);
                        startActivity(intent);
                    }
                });
                recyclerView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getContext(), InboxFragment.class);
                        startActivity(intent);
                    }
                });
                recyclerView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getContext(), InboxFragment.class);
                        startActivity(intent);
                    }
                });
            }
        });

        return root;
    }

    public void tanimla() {
        recyclerView2 = root.findViewById(R.id.recyclerView2);
        recyclerView4 = root.findViewById(R.id.recyclerView4);
        recyclerView5 = root.findViewById(R.id.recyclerView5);
        recyclerView6 = root.findViewById(R.id.recyclerView6);

    }


}
