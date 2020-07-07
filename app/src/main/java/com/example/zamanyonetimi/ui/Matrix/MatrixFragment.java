package com.example.zamanyonetimi.ui.Matrix;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.zamanyonetimi.DatabaseHelper;
import com.example.zamanyonetimi.R;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MatrixFragment extends Fragment {
    private ArrayList<String> listitemRed = new ArrayList<>();
    private ArrayList<String> listitemYellow = new ArrayList<>();
    private ArrayList<String> listitemGreen = new ArrayList<>();
    private ArrayList<String> listitemBlue = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_matrix, container, false);
        final ListView listviewRed = root.findViewById(R.id.listview_red);
        final ListView listviewYellow = root.findViewById(R.id.listview_yellow);
        final ListView listviewBlue=root.findViewById(R.id.listview_blue);
        final ListView listviewGreen=root.findViewById(R.id.listview_green);

        ViewData();

        ArrayAdapter<String> adapterRed = new ArrayAdapter<>(container.getContext(), R.layout.matrix_item, listitemRed);
        listviewRed.setAdapter(adapterRed);
        ArrayAdapter<String> adapterYellow = new ArrayAdapter<>(container.getContext(), R.layout.matrix_item, listitemYellow);
        listviewYellow.setAdapter(adapterYellow);
        ArrayAdapter<String> adapterBlue = new ArrayAdapter<>(container.getContext(), R.layout.matrix_item, listitemBlue);
        listviewBlue.setAdapter(adapterBlue);
        ArrayAdapter<String> adapterGreen = new ArrayAdapter<>(container.getContext(), R.layout.matrix_item, listitemGreen);
        listviewGreen.setAdapter(adapterGreen);
       return root;
    }

    private void ViewData() {
        DatabaseHelper db = new DatabaseHelper(getContext());
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