package bitm.basis.firebaseauthor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MomentAdapter extends ArrayAdapter<Moment> {
    ArrayList<Moment> momentList = new ArrayList<>();
    Context mContext;

    public MomentAdapter(@NonNull Context context, @NonNull ArrayList<Moment> momentList) {
        super(context, R.layout.custom_moment_show, momentList);
        mContext = context;
        this.momentList = momentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.custom_moment_show, parent, false);

        TextView nameView = convertView.findViewById(R.id.momentShowName_id);
        TextView timeView = convertView.findViewById(R.id.momentShowTime_id);

        Moment moment = momentList.get(position);

        nameView.setText(moment.getmName());
        timeView.setText(moment.getmTime());
        Toast.makeText(mContext, "Name : "+moment.getmName()+"\n"+moment.getmTime(), Toast.LENGTH_SHORT).show();

        return convertView;
    }

}