package bitm.basis.firebaseauthor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class dataAdapter extends ArrayAdapter<DataUpload>{
    ArrayList<DataUpload> dataList = new ArrayList<>();
    Context mContext;

    public dataAdapter(@NonNull Context context,@NonNull  ArrayList<DataUpload>dataList) {
        super(context, R.layout.custom_data_show,dataList );
        this.dataList=dataList;
        mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.custom_data_show,parent,false);

        TextView nameView = convertView.findViewById(R.id.dataName_id);
        TextView timeView = convertView.findViewById(R.id.dataTime_id);

        DataUpload dataupload = dataList.get(position);

        nameView.setText(dataupload.getRefName());
        timeView.setText(""+dataupload.getmTime());


        return convertView;
    }
}
