package bitm.basis.firebaseauthor;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {


ImageView imageView;
int IMAGE_PICK_REQUEST = 100;
Uri mUri;
private StorageReference mStoreRef;
private DatabaseReference mDatabaseRef;
private ProgressBar  mProgressBar;
private EditText nameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mStoreRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

    imageView = findViewById(R.id.image_id);
    nameEditText = findViewById(R.id.name_id);

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
        if (mUri != null){
            StorageReference fileRef = mStoreRef.child(System.currentTimeMillis()
            +"."+getFile(mUri));
            fileRef.putFile(mUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   /* Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },5000);*/
                    Toast.makeText(ImageActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                    Upload upload = new Upload(nameEditText.getText().toString().trim(),
                            taskSnapshot.getUploadSessionUri().toString());
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);
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
        startActivityForResult(intent,IMAGE_PICK_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST && resultCode ==RESULT_OK
                && data != null && data.getData() != null){

           mUri = data.getData();
            Picasso.get().load(mUri).into(imageView);

        }
    }

    public void ShowImage(View view) {
        startActivity(new Intent(ImageActivity.this,ShowActivity.class));
    }
}
