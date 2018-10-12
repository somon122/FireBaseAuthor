package bitm.basis.firebaseauthor.TestImage;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import bitm.basis.firebaseauthor.MainActivity;
import bitm.basis.firebaseauthor.Moment;
import bitm.basis.firebaseauthor.MomentAdapter;
import bitm.basis.firebaseauthor.R;
import bitm.basis.firebaseauthor.TestImageShow_Activity;

public class ImageActivity extends AppCompatActivity {


ImageView imageView;
int IMAGE_PICK_REQUEST = 100;
int GELLERY_PICK_REQUEST= 99;
Uri mUri;
private StorageReference mStoreRef;
private DatabaseReference mDatabaseRef;
private EditText nameEditText;
private ListView listView;
    String uId;
    String id;
    String rowId;

    ArrayList<Moment> momentList = new ArrayList<>();
    MomentAdapter adapter;

    private DatabaseReference root_reference;
    private FirebaseUser user;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        getCameraPermission();
        progressDialog = new ProgressDialog(this);

        mStoreRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        imageView = findViewById(R.id.image_id);
        nameEditText = findViewById(R.id.name_id);
        listView = findViewById(R.id.mShowListView_id);

        user = FirebaseAuth.getInstance().getCurrentUser();
        root_reference = FirebaseDatabase.getInstance().getReference("UserInfo");

       rowId = getIntent().getStringExtra("rowId");

        if (rowId != null){

            imageView.setVisibility(View.GONE);
            nameEditText.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            

            uId = user.getUid();
            root_reference.child(uId).child("Event").child(rowId).child("Moment")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            momentList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                Moment moment = snapshot.getValue(Moment.class);
                                momentList.add(moment);
                            }
                            adapter = new MomentAdapter(getApplicationContext(),momentList);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Toast.makeText(ImageActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(this, "LogIn First", Toast.LENGTH_SHORT).show();
        }





    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            chooseImage();
        }
    });


    }

    public void SaveImage(View view) {

        uploadfile();

    }

    private void uploadfile() {

        progressDialog .setMessage("Image Uploading....");
        progressDialog.show();
        if (mUri != null){
            StorageReference fileRef = mStoreRef.child(mUri.getLastPathSegment());
            fileRef.putFile(mUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(ImageActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                    Uri downdoadUri = taskSnapshot.getDownloadUrl();

                    Upload upload = new Upload(nameEditText.getText().toString().trim(),
                            downdoadUri.toString());

                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ImageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })/*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);

                }
            })*/;
        }else {
            Toast.makeText(this, "No file selected ", Toast.LENGTH_SHORT).show();
        }

    }
    private String getFile (Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,GELLERY_PICK_REQUEST);

    }

    @Override
    protected void onStart() {
        super.onStart();

        startActivity(new Intent(ImageActivity.this,ShowActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST && resultCode ==RESULT_OK
                && data != null && data.getData() != null){

            Bundle bundle = data.getExtras();
           Bitmap photo = (Bitmap) bundle.get("data");
           imageView.setImageBitmap(photo);

         /*  mUri = data.getData();
           imageView.setImageURI(mUri);
            //Picasso.with(this).load(mUri).into(imageView);*/

        }else if (requestCode == GELLERY_PICK_REQUEST && resultCode ==RESULT_OK
                && data != null && data.getData() != null)
        {
            mUri = data.getData();
            imageView.setImageURI(mUri);


        }

    }

    public void ShowImage(View view) {
        startActivity(new Intent(ImageActivity.this,ShowActivity.class));
    }

    public void TaggleButton(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,IMAGE_PICK_REQUEST);

    }
    public void getCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, IMAGE_PICK_REQUEST);
            }
        }
    }
}
