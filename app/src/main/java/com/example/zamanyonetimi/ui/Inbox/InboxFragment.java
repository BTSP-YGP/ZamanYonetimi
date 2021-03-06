package com.example.zamanyonetimi.ui.Inbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.zamanyonetimi.DatabaseHelper;
import com.example.zamanyonetimi.EditJob;
import com.example.zamanyonetimi.MailSending;
import com.example.zamanyonetimi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment {
    private DatabaseHelper myDb;
    private Integer selectedPosition;
    private List jobList = new ArrayList();
    private List descriptionList = new ArrayList();
    private List flagList = new ArrayList();
    private FloatingActionButton fabMenu, fabDuzenle, fabSil, fabTamamla, fabDelege, fabEkle;
    private Boolean isFABOpen=false;
    private int EDITJOB_ACTIVITY = 1, MAILSENDING_ACTIVITY = 2;
    private InboxAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        fetchData();

        View root = inflater.inflate(R.layout.fragment_inbox, container, false);
        final RecyclerView recyclerViewer = root.findViewById(R.id.recyclerView);

        mAdapter = new InboxAdapter(container.getContext(), jobList, descriptionList, flagList);
        recyclerViewer.setAdapter(mAdapter);
        recyclerViewer.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mAdapter.setOnItemClickListener(new InboxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectedPosition = position;
            }
        });

        fabMenu = root.findViewById(R.id.fab_menu);
        fabDuzenle = root.findViewById(R.id.fab_duzenle);
        fabEkle = root.findViewById(R.id.fab_ekle);
        fabSil = root.findViewById(R.id.fab_sil);
        fabDelege = root.findViewById(R.id.fab_delege);
        fabTamamla = root.findViewById(R.id.fab_tamamla);

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
                   startIntent.putExtra("editJobName", "xeklex");
                   startActivity(startIntent);
               }

        });

        fabDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != null) {
                    mAdapter.setOnItemClickListener(new InboxAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
//
                        }
                    });
                    Intent startIntent = new Intent(container.getContext(), EditJob.class);
                    startIntent.putExtra("editJobName", jobList.get(selectedPosition).toString());
                    startActivityForResult(startIntent, EDITJOB_ACTIVITY);
                } else {
                    Toast.makeText(getContext(), "Bir görev seçiniz!", Toast.LENGTH_LONG).show();
                }
            }

        });

        fabSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Gorev silinsin mi?");
                builder.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            if (myDb.deleteJob(jobList.get(selectedPosition).toString())) {
                                jobList.remove(selectedPosition);
                                descriptionList.remove(selectedPosition);
                                flagList.remove(selectedPosition);
                                mAdapter.remove(selectedPosition);
                            }
                    }
                });
                builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        fabTamamla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagList.get(selectedPosition).toString().equals("tamam")) {
                    myDb.tamamlaJob(jobList.get(selectedPosition).toString(), 0);
                    flagList.set(selectedPosition, "");
                    mAdapter.tamamUpdate(selectedPosition, "");
                } else {
                    myDb.tamamlaJob(jobList.get(selectedPosition).toString(), 1);
                    flagList.set(selectedPosition, "tamam");
                    mAdapter.tamamUpdate(selectedPosition, "tamam");
                }

            }

        });

        fabDelege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != null) {
                    mAdapter.setOnItemClickListener(new InboxAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
//
                        }
                    });
                    Intent startIntent = new Intent(container.getContext(), MailSending.class);
                    startIntent.putExtra("editJobName", jobList.get(selectedPosition).toString());
                    startActivityForResult(startIntent, MAILSENDING_ACTIVITY);
                } else {
                    Toast.makeText(getContext(), "Bir görev seçiniz!", Toast.LENGTH_LONG).show();
                }
            }

            Intent intent = new Intent(container.getContext(), MailSending.class);

        });

        ViewAll();
        return root;
    }

    private void fetchData() {
        myDb = new DatabaseHelper(getContext());
        SQLiteDatabase db = myDb.getWritableDatabase();
        Cursor res = db.rawQuery("select * from jobs", null);
        while (res.moveToNext()) {
            jobList.add(res.getString(res.getColumnIndex("name")));
            descriptionList.add(res.getString(res.getColumnIndex("description")));
            if (res.getInt(res.getColumnIndex("complete")) == 1) {
                flagList.add("tamam");
            } else {
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
        }
        res.close();
    }

    private void ViewAll() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == EDITJOB_ACTIVITY) {
          if (resultCode == Activity.RESULT_OK) {
              String resStatus =data.getStringExtra("status");
              jobList.clear();
              descriptionList.clear();
              flagList.clear();
              fetchData();
              assert resStatus != null;
              if (resStatus.equals("ekleme")) {
                 mAdapter.add(jobList.size()-1, jobList.get(jobList.size()-1).toString(),
                         descriptionList.get(descriptionList.size()-1).toString(),
                         flagList.get(flagList.size()-1).toString());
                 Toast.makeText(getContext(),"Eklendi" , Toast.LENGTH_LONG).show();
              } else {
                 mAdapter.update(selectedPosition, jobList.get(selectedPosition).toString(),
                         descriptionList.get(selectedPosition).toString(),
                         flagList.get(selectedPosition).toString());
                 Toast.makeText(getContext(),"Guncellendi" , Toast.LENGTH_LONG).show();


             }

          }
      }
        if (requestCode == MAILSENDING_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                SQLiteDatabase dbs = myDb.getWritableDatabase();
                if (myDb.deleteJob(jobList.get(selectedPosition).toString())) {
                    jobList.remove(selectedPosition);
                    descriptionList.remove(selectedPosition);
                    flagList.remove(selectedPosition);
                    mAdapter.remove(selectedPosition);
                    Toast.makeText(getContext(), "Görev delege edildi", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
