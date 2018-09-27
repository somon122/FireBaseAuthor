package bitm.basis.firebaseauthor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBaseActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    String refName;
    String uid;

    private DatabaseReference root_reference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);


        editText = findViewById(R.id.refName_id);
        textView = findViewById(R.id.texView_id);

        refName = editText.getText().toString();
        user = FirebaseAuth.getInstance().getCurrentUser();


        root_reference = FirebaseDatabase.getInstance().getReference("UserInfo");

    }

    public void SaveTime(View view) {

        String id = root_reference.push().getKey();
        uid = user.getUid();

        long time = System.currentTimeMillis();
        root_reference.child(uid).child("Event").child(id).setValue(time);


    }

    public void GoToImageActivity(View view) {
        startActivity(new Intent(DataBaseActivity.this,ImageActivity.class));
    }
}
