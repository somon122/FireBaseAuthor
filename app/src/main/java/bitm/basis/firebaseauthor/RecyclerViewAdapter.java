package bitm.basis.firebaseauthor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<RViewData>dataList;
    ClickListener listener;

    public RecyclerViewAdapter(Context mContext, ArrayList<RViewData> dataList, ClickListener listener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_data_show,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RViewData viewData = dataList.get(position);
        holder.mNameTV.setText(viewData.getmName());
        holder.mTimeTV.setText(viewData.getmTime());


    }

    @Override
    public int getItemCount() {
        return dataList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mNameTV;
        TextView mTimeTV;

        public MyViewHolder(View itemView) {
            super(itemView);

            mNameTV = itemView.findViewById(R.id.dataName_id);
            mTimeTV = itemView.findViewById(R.id.dataTime_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(dataList.get(getAdapterPosition()));
                }
            });


        }
    }

    public void DataUpdate (ArrayList<RViewData>dataList){

        this.dataList = dataList;
        notifyDataSetChanged();

    }

       public  interface ClickListener{

        public void onClick (RViewData viewData);
    }

}
