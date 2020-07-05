package com.example.zamanyonetimi.ui.Matrix;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.zamanyonetimi.ui.Inbox.InboxAdapter;
import com.example.zamanyonetimi.ui.Inbox.InboxFragment;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MatrixFragment extends Fragment {
    DatabaseHelper db;
    View root;
    ArrayList<String> listitemRed = new ArrayList<String>();
    ArrayList<String> listitemYellow = new ArrayList();
    ArrayList<String> listitemGreen = new ArrayList();
    ArrayList<String> listitemBlue = new ArrayList();

    private MatrixViewModel matrixViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        matrixViewModel =
                ViewModelProviders.of(this).get(MatrixViewModel.class);
        View root = inflater.inflate(R.layout.fragment_matrix, container, false);
        final ListView listviewRed = root.findViewById(R.id.listview_red);
        final ListView listviewYellow = root.findViewById(R.id.listview_yellow);
        final ListView listviewBlue=root.findViewById(R.id.listview_blue);
        final ListView listviewGreen=root.findViewById(R.id.listview_green);

        ViewData();

        // her liste için adapter oluşturuldu
        ArrayAdapter<String> adapterRed = new ArrayAdapter<String>(container.getContext(), R.layout.matrix_item, listitemRed);
        listviewRed.setAdapter(adapterRed);
        ArrayAdapter<String> adapterYellow = new ArrayAdapter<String>(container.getContext(), R.layout.matrix_item, listitemYellow);
        listviewRed.setAdapter(adapterYellow);
        ArrayAdapter<String> adapterBlue = new ArrayAdapter<String>(container.getContext(), R.layout.matrix_item, listitemBlue);
        listviewRed.setAdapter(adapterBlue);
        ArrayAdapter<String> adapterGreen = new ArrayAdapter<String>(container.getContext(), R.layout.matrix_item, listitemGreen);
        listviewRed.setAdapter(adapterGreen);


        return root;
    }

    public void ViewData() {
        db = new DatabaseHelper(getContext());
        SQLiteDatabase dbSQL = db.getWritableDatabase();
        Cursor cursor = dbSQL.rawQuery("select * from jobs", null);
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "no data to show", LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex("complete")) != 1) {
                    if (cursor.getInt(cursor.getColumnIndex("important")) == 1 &&
                            cursor.getInt(cursor.getColumnIndex("urgent")) == 1) {
                        listitemRed.add(cursor.getString(cursor.getColumnIndex("name")));

                    } else if (cursor.getInt(cursor.getColumnIndex("important")) == 0 &&
                            cursor.getInt(cursor.getColumnIndex("urgent")) == 1) {
                        listitemYellow.add(cursor.getString(cursor.getColumnIndex("name")));

                    } else if (cursor.getInt(cursor.getColumnIndex("important")) == 1 &&
                            cursor.getInt(cursor.getColumnIndex("urgent")) == 0) {
                        listitemGreen.add(cursor.getString(cursor.getColumnIndex("name")));

                    } else {
                        listitemBlue.add(cursor.getString(cursor.getColumnIndex("name")));

                    }
                }
            }
        }
        cursor.close();
    }

}