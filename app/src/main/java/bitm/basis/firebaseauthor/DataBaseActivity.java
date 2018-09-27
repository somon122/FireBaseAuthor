package bitm.basis.firebaseauthor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBaseActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    String refName;
    String uId;
    ListView listView;

    private DatabaseReference root_reference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);


        listView = findViewById(R.id.listView_id);
        editText = findViewById(R.id.refName_id);
        textView = findViewById(R.id.texView_id);

        refName = editText.getText().toString();
        user = FirebaseAuth.getInstance().getCurrentUser();


        root_reference = FirebaseDatabase.getInstance().getReference("UserInfo");

    }

    public void SaveTime(View view) {

        String id = root_reference.push().getKey();
        uId = user.getUid();
        long time = System.currentTimeMillis();
        String mTime = Long.toString(time);

        DataUpload dataUpload = new DataUpload(id,refName,mTime);


        root_reference.child(uId).child("Events").child(id).setValue(dataUpload);


    }

    public void GoToImageActivity(View view) {
        startActivity(new Intent(DataBaseActivity.this,ImageActivity.class));
    }
}
