package bitm.basis.firebaseauthor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText emailEditText,passwordEditText;
    Button logInButton;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email_id);
        passwordEditText = findViewById(R.id.password_id);
        logInButton = findViewById(R.id.logIn_Id);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user!= null){
            startActivity(new Intent(MainActivity.this,DataBaseActivity.class));
            finish();
        }

       /* if (user.getEmail() != null)
        {
            startActivity(new Intent(MainActivity.this,DataBaseActivity.class));
        }*/


        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

               auth.createUserWithEmailAndPassword(email,password)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()){
                                  user = auth.getCurrentUser();
                                   Toast.makeText(MainActivity.this, "Success"+user.getEmail(), Toast.LENGTH_LONG).show();

                               }
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });

            }
        });
        logInButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    user = auth.getCurrentUser();
                                    Toast.makeText(MainActivity.this, "Success"+user.getEmail(), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this,DataBaseActivity.class));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                return false;
            }
        });



    }


    public void SaveTime(View view) {
        startActivity(new Intent(MainActivity.this,DataBaseActivity.class));
    }
}