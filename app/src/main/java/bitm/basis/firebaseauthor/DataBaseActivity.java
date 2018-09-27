package bitm.basis.firebaseauthor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataBaseActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    String refName;
    String uId;
    String id;
    ListView listView;
    List<DataUpload>dataList;
    dataAdapter adapter;

    private DatabaseReference root_reference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);


        listView = findViewById(R.id.mListView_id);
        editText = findViewById(R.id.refName_id);
        textView = findViewById(R.id.texView_id);
        dataList = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        root_reference = FirebaseDatabase.getInstance().getReference("UserInfo");


        root_reference.child(uId).child("Event")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    DataUpload dataupload = snapshot.getValue(DataUpload.class);
                    dataList.add(dataupload);
                }
                adapter = new dataAdapter(getApplicationContext(),dataList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(DataBaseActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void SaveTime(View view) {

        id = root_reference.push().getKey();
        uId = user.getUid();
        long time = System.currentTimeMillis();
        String mTime = Long.toString(time);
        refName = editText.getText().toString();
        DataUpload dataUpload = new DataUpload(id,refName,mTime);
        root_reference.child(uId).child("Event").child(id).setValue(dataUpload).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DataBaseActivity.this, "success", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void GoToImageActivity(View view) {
        startActivity(new Intent(DataBaseActivity.this,ImageActivity.class));
    }
}
