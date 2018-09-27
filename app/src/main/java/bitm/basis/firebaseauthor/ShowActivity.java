package bitm.basis.firebaseauthor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    ListView listView;
    List<Upload>imageList;
    ImageAdapter adapter;
    DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        imageList = new ArrayList<>();

        listView = findViewById(R.id.listView_id);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Upload upload = snapshot.getValue(Upload.class);
                    imageList.add(upload);
                }
                adapter = new ImageAdapter(ShowActivity.this,imageList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(ShowActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
