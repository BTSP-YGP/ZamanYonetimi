package com.example.zamanyonetimi.ui.Inbox;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.zamanyonetimi.R;
import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder> {
    private OnItemClickListener moListener;
    private CardView cardView, cardOld;
    private List data1, data2, data3;
    private View oldView;

    InboxAdapter(Context ct, List s1, List s2, List s3) {
        data1 = s1;
        data2 = s2;
        data3 = s3;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        moListener = listener;
    }

    @NonNull
    @Override
    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item, parent, false);
        return new InboxViewHolder(view, moListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final InboxViewHolder holder, int position) {
        holder.gorevismi_txt.setText(data1.get(position).toString());
        holder.gorevdescription_txt.setText(data2.get(position).toString());
        if (data3.get(position) == "tamam" ) {
            holder.gorev_image.setImageResource(R.drawable.ic_check_black_24dp);
            holder.gorevismi_txt.setTextColor(Color.GRAY);
            holder.gorevismi_txt.setTypeface(null, Typeface.ITALIC);
        } else if (data3.get(position) == "RED" ) {
            holder.gorev_image.setColorFilter(Color.RED);
        } else if (data3.get(position) == "YELLOW" ) {
            holder.gorev_image.setColorFilter(Color.YELLOW);
        } else if (data3.get(position) == "GREEN" ) {
            holder.gorev_image.setColorFilter(Color.GREEN);
        } else {
            holder.gorev_image.setColorFilter(Color.BLUE);
        }
    }

    void add(Integer position, String s1, String s2, String s3) {
        data1.add(s1);
        data2.add(s2);
        data3.add(s3);
        this.notifyItemInserted(position);
    }

    void remove(int position)
    {
        data1.remove(position);
        data2.remove(position);
        data3.remove(position);
        this.notifyDataSetChanged();
    }

    void update(Integer position, String s1, String s2, String s3) {
        data1.set(position, s1);
        data2.set(position, s2);
        data3.set(position, s3);
        this.notifyItemChanged(position);
    }

    void tamamUpdate(int position, String durum)
    {
        data3.set(position, durum);
        this.notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    class InboxViewHolder extends RecyclerView.ViewHolder {

        TextView gorevismi_txt, gorevdescription_txt;
        ImageView gorev_image;

        InboxViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            gorevismi_txt = itemView.findViewById(R.id.gorevismi_txt);
            gorevdescription_txt = itemView.findViewById(R.id.gorevdescription_txt);
            gorev_image = itemView.findViewById(R.id.gorev_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (oldView == null) {
                    oldView = v;
                } else {
                    cardOld = oldView.findViewById(R.id.cardJob);
                    cardOld.setCardBackgroundColor(Color.WHITE);
                    oldView = v;
                }
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                    cardView = v.findViewById(R.id.cardJob);
                    if (cardView.getCardBackgroundColor().getDefaultColor() == Color.BLUE) {
                        cardView.setCardBackgroundColor(Color.WHITE);
                    }
                    else {
                        cardView.setCardBackgroundColor(Color.BLUE);
                    }
                }
                }
            });
        }

    }
}
