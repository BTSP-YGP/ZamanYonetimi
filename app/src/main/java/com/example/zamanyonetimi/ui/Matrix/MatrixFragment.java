package com.example.zamanyonetimi.ui.Matrix;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zamanyonetimi.DatabaseHelper;
import com.example.zamanyonetimi.R;
import com.example.zamanyonetimi.ui.Inbox.InboxFragment;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MatrixFragment extends Fragment {
    //RecyclerView recyclerView2, recyclerView4, recyclerView5, recyclerView6;
    DatabaseHelper db;
    View root;
    private MatrixViewModel homeViewModel;
    ArrayList<String> ListItem;
    ArrayAdapter adapter;
    ListView joblist;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(MatrixViewModel.class);
        View root = inflater.inflate(R.layout.fragment_matrix, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        /*recyclerView2 = root.findViewById(R.id.recyclerView2);
        recyclerView4 = root.findViewById(R.id.recyclerView4);
        recyclerView5 = root.findViewById(R.id.recyclerView5);
        recyclerView6 = root.findViewById(R.id.recyclerView6);*/

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        /*recyclerView2.setOnClickListener(new View.OnClickListener() {
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
        });*/
        db= new DatabaseHelper(getContext());
        ListItem=new ArrayList<>();
        ViewData();
        return root;
        }
    private void ViewData() {
        Cursor cursor=db.ViewData();
        if(cursor.getCount()==0){
            Toast.makeText(getContext(),"no data to show", LENGTH_SHORT).show(); }
        else {
            while (cursor.moveToNext()){
                ListItem.add(cursor.getString(2));
            } //while
            adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,ListItem);
            joblist.setAdapter(adapter);
        }//else

    }

}
