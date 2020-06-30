package com.example.zamanyonetimi.ui.Inbox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.zamanyonetimi.DatabaseHelper;
import com.example.zamanyonetimi.EditJob;
import com.example.zamanyonetimi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment {

    DatabaseHelper myDb;
    Integer selectedPosition;
    List jobList = new ArrayList<>();
    List descriptionList = new ArrayList();
    List flagList = new ArrayList();
    FloatingActionButton fabMenu, fabDuzenle, fabSil, fabTamamla, fabDelege, fabEkle;
    Boolean isFABOpen=false;
    private ArrayList<CardView> mJobList;
    private InboxViewModel inboxViewModel;
    private InboxAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        myDb = new DatabaseHelper(container.getContext());
        SQLiteDatabase db = myDb.getWritableDatabase();
        Cursor res = db.rawQuery("select * from jobs", null);
        while (res.moveToNext()) {
            jobList.add(res.getString(res.getColumnIndex("name")));

            descriptionList.add(res.getString(res.getColumnIndex("description")));
            if (res.getInt(res.getColumnIndex("complete")) == 1) {
                flagList.add("tamam");
            }
            if (res.getInt(res.getColumnIndex("important")) == 1 &&
                    res.getInt(res.getColumnIndex("urgent")) == 1) {
                flagList.add("RED");
            } else if (res.getInt(res.getColumnIndex("important")) == 0 &&
                    res.getInt(res.getColumnIndex("urgent")) == 1) {
                flagList.add("YELLOW");
            } else if (res.getInt(res.getColumnIndex("important")) == 1 &&
                    res.getInt(res.getColumnIndex("urgent")) == 0) {
                flagList.add("GREEN");
            } else {
                flagList.add("BLUE");
            }

        }
        res.close();
        inboxViewModel =
                ViewModelProviders.of(this).get(InboxViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inbox, container, false);
        final RecyclerView recyclerViewer = root.findViewById(R.id.recyclerView);


        mAdapter = new InboxAdapter(container.getContext(), jobList, descriptionList, flagList);
        recyclerViewer.setAdapter(mAdapter);
        recyclerViewer.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mAdapter.setOnItemClickListener(new InboxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectedPosition = position;
                // AlertDialog kısmı silinecek
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(selectedPosition.toString()).setTitle("secilen");
                AlertDialog dialog = builder.create();
                dialog.show();
                // AlertDialog son
            }
        });

        fabMenu = (FloatingActionButton) root.findViewById(R.id.fab_menu);
        fabDuzenle = (FloatingActionButton) root.findViewById(R.id.fab_duzenle);
        fabEkle = (FloatingActionButton) root.findViewById(R.id.fab_ekle);
        fabSil = (FloatingActionButton) root.findViewById(R.id.fab_sil);
        fabDelege = (FloatingActionButton) root.findViewById(R.id.fab_delege);
        fabTamamla = (FloatingActionButton) root.findViewById(R.id.fab_tamamla);

        fabEkle.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mAdapter.setOnItemClickListener(new InboxAdapter.OnItemClickListener() {
                       @Override
                       public void onItemClick(int position) {
//
                       }
                   });
                   Intent startIntent = new Intent(container.getContext(), EditJob.class);
                   startActivity(startIntent);
               }

        });

        fabDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setOnItemClickListener(new InboxAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//
                    }
                });
                Intent startIntent = new Intent(container.getContext(), EditJob.class);
                startActivity(startIntent);
            }

        });

        fabSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Gorev silinsin mi?");

                builder.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDb.deleteJob(selectedPosition);

                    }
                });

                builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                refreshInbox();

            }

        });

        fabTamamla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.tamamlaJob(selectedPosition);
            }

        });

        fabDelege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });

        ViewAll();
        return root;
    }
    public void ViewAll () {
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
/*
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("id : "+ res.getString(0)+ "\n");
                    buffer.append("name : "+ res.getString(1)+ "\n");
                }
                showmessage("Data", buffer.toString());


                List jobList = new ArrayList<>();
                while (res.moveToNext()) {
                    jobList.add(res.getString(1));
                }
*/

        });

    }
    private void showFABMenu(){
        isFABOpen=true;
        fabDuzenle.animate().translationY(-165);
        fabSil.animate().translationY(-300);
        fabDelege.animate().translationY(-440);
        fabTamamla.animate().translationY(-580);
        fabEkle.animate().translationX(-150);

    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabDuzenle.animate().translationY(0);
        fabSil.animate().translationY(0);
        fabDelege.animate().translationY(0);
        fabTamamla.animate().translationY(0);
        fabEkle.animate().translationX(0);
    }
    public void showMessage (String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void refreshInbox () {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}
