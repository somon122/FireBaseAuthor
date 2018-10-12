package bitm.basis.firebaseauthor;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public ViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void  setDetails (Context context,String name,String image){

        TextView textView = mView.findViewById(R.id.custom_name_id);
        ImageView imageView = mView.findViewById(R.id.custom_image_id);

        Uri uri = Uri.parse(image);
        Toast.makeText(context, ""+uri, Toast.LENGTH_SHORT).show();

        textView.setText(name);
        Picasso.with(context).load(uri).into(imageView);
    }
}
