package com.example.zamanyonetimi.ui.Inbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zamanyonetimi.R;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder> {

    private LayoutInflater inflater;
    //private InboxViewHolder.ItemClickListener mClickListener;
    String data1[], data2[];
    int images[];
    Context context;

    public InboxAdapter(Context ct, String s1[], String s2[], int img[]) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
    }

    @NonNull
    @Override
    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item, parent, false);
        return new InboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxViewHolder holder, int position) {
        holder.gorevismi_txt.setText(data1[position]);
        holder.gorevdescription_txt.setText(data2[position]);
        holder.gorev_image.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }
    public class InboxViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener {

        TextView gorevismi_txt, gorevdescription_txt;
        ImageView gorev_image;

        public InboxViewHolder(@NonNull View itemView) {
            super(itemView);
            gorevismi_txt = itemView.findViewById(R.id.gorevismi_txt);
            gorevdescription_txt = itemView.findViewById(R.id.gorevdescription_txt);
            gorev_image = itemView.findViewById(R.id.gorev_image);
        }
/*      @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
        // convenience method for getting data at click position


        String getItem(int id) {
            return mData.get(id);
        }

        // allows clicks events to be caught
        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }

 */
    }
}
