package com.example.zamanyonetimi.ui.Inbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zamanyonetimi.EditJob;
import com.example.zamanyonetimi.MainActivity;
import com.example.zamanyonetimi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InboxFragment extends Fragment {

    String s1[], s2[];
    int images[] = {R.drawable.ic_home_black_24dp, R.drawable.ic_home_black_24dp,
            R.drawable.ic_home_black_24dp, R.drawable.ic_notifications_black_24dp};
    FloatingActionButton btnEkle;

    private InboxViewModel inboxViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        inboxViewModel =
                ViewModelProviders.of(this).get(InboxViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inbox, container, false);
/*        final TextView textView = root.findViewById(R.id.text_dashboard);
        inboxViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

 */
        final RecyclerView recyclerViewer = root.findViewById(R.id.recyclerView);
        s1 = getResources().getStringArray(R.array.gorevler);
        s2 = getResources().getStringArray(R.array.description);
        InboxAdapter inboxAdapter = new InboxAdapter(container.getContext(), s1, s2, images);
        recyclerViewer.setAdapter(inboxAdapter);
        recyclerViewer.setLayoutManager(new LinearLayoutManager(container.getContext()));

        btnEkle = ((FloatingActionButton) root.findViewById(R.id.btnEkle));
        btnEkle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(container.getContext(), EditJob.class);
                startActivity(startIntent);
            }

        });
        return root;
    }
}
