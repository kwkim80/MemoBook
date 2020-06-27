package ca.algonquin.kw2446.memo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.algonquin.kw2446.memo.R;
import ca.algonquin.kw2446.memo.model.Memo;
import ca.algonquin.kw2446.memo.util.Utility;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    public interface MemoItemClicked{
        void onItemClicked(int i);
    }
    private static final String TAG = "MemoAdapter";
    MemoItemClicked context;
    ArrayList<Memo> list;

    public MemoAdapter(Context context, ArrayList<Memo> list) {
        this.list = list;
        this.context=(MemoItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvDate=itemView.findViewById(R.id.tvDate);

            itemView.setOnClickListener((v)->context.onItemClicked(getAdapterPosition()));

        }

        public void setItem(Memo m){
            tvTitle.setText(m.getTitle());
            //tvDate.setText(m.getTimestamp());
             tvDate.setText(Utility.memoTitleDate(m.getTimestamp()));
        }
    }

    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder holder, int position) {
        Memo memo=list.get(position);
         holder.setItem(memo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
