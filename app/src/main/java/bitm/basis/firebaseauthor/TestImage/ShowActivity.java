package bitm.basis.firebaseauthor.TestImage;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.ListView;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

        import bitm.basis.firebaseauthor.R;

public class ShowActivity extends AppCompatActivity {

    ListView listView;
    RecyclerView recyclerView;
    List<Upload>imageList;
    ImageAdapter adapter;
    DatabaseReference mDatabaseRef;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        imageList = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar_id);
        listView = findViewById(R.id.listView_id);



        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Upload upload = snapshot.getValue(Upload.class);
                    imageList.add(upload);

                }
                progressBar.setVisibility(View.INVISIBLE);
                adapter = new ImageAdapter(ShowActivity.this,imageList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ShowActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
