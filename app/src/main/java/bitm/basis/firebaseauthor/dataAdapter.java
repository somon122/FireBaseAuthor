package bitm.basis.firebaseauthor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

public class dataAdapter extends ArrayAdapter<DataUpload>{

    
    public dataAdapter(@NonNull Context context, int resource, @NonNull DataUpload[] objects) {
        super(context, resource, objects);
    }
}
