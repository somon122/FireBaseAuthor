package bitm.basis.firebaseauthor;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import bitm.basis.firebaseauthor.TestImage.ImageActivity;

public class DataBaseActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    String refName;
    String uId;
    String id;
    String rowId;
    ListView listView;
    ArrayList<DataUpload>dataList = new ArrayList<>();
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

        user = FirebaseAuth.getInstance().getCurrentUser();
        root_reference = FirebaseDatabase.getInstance().getReference("UserInfo");


        if (user != null){

           uId = user.getUid();
            root_reference.child(uId).child("Event")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            dataList.clear();

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
        }else {
            Toast.makeText(this, "LogIn First", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DataUpload upload = dataList.get(position);
                rowId = upload.getuId();

                AlertDialog.Builder builder = new AlertDialog.Builder(DataBaseActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.custom_moment,null);
                final EditText mName = view1.findViewById(R.id.momentNameId);
                final EditText time = view1.findViewById(R.id.momentTimeId);
                Button saveButton = view1.findViewById(R.id.momentSaveId);
                Button showButton = view1.findViewById(R.id.showMomentId);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      String mEmail = mName.getText().toString();
                      String mTime = time.getText().toString();
                      String mId = root_reference.push().getKey();
                      Moment moment = new Moment(mId,mEmail,mTime);

                        root_reference.child(uId).child("Event").child(rowId).child("Moment")
                                .child(mId).setValue(moment).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(DataBaseActivity.this, "success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
                showButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(DataBaseActivity.this,ImageActivity.class);
                        intent.putExtra("rowId",rowId);
                        startActivity(intent);

                    }
                });



                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
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
