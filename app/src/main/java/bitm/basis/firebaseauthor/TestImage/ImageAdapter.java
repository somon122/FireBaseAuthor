package bitm.basis.firebaseauthor.TestImage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bitm.basis.firebaseauthor.R;

public class ImageAdapter extends ArrayAdapter<Upload> {
    List<Upload>imageList = new ArrayList<>();
    Context mContext;

    public ImageAdapter(@NonNull Context context,@NonNull  List<Upload>imageList) {
        super(context, R.layout.custom_image,imageList );
        this.imageList=imageList;
        mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.custom_image,parent,false);

        TextView nameEditText = convertView.findViewById(R.id.custom_name_id);
        ImageView imageView = convertView.findViewById(R.id.custom_image_id);
        final Upload upload = imageList.get(position);

        nameEditText.setText(upload.getmName());
        Picasso.with(mContext).load(upload.getmImageUri()).placeholder(R.drawable.ic_linked_camera).into(imageView);


        return convertView;
    }
}
