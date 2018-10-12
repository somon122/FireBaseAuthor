package bitm.basis.firebaseauthor;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import bitm.basis.firebaseauthor.TestImage.Upload;

public class TestImageShow_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_show_);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Image List ");

        recyclerView = findViewById(R.id.testRecyclerView_Id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("uploads");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Upload,ViewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<Upload, ViewHolder>(
                        Upload.class,
                        R.layout.custom_image,
                        ViewHolder.class,
                        mRef
                )

                {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Upload upload, int position) {

                viewHolder.setDetails(getApplicationContext(),upload.getmName(),upload.getmImageUri());

                Toast.makeText(TestImageShow_Activity.this, upload.getmImageUri(), Toast.LENGTH_SHORT).show();
            }
        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }
}
