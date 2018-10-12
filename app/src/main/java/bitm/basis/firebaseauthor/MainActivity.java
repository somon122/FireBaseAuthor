package bitm.basis.firebaseauthor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import bitm.basis.firebaseauthor.TestImage.ImageActivity;

public class MainActivity extends AppCompatActivity {
    EditText emailEditText,passwordEditText;
    Button logInButton;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView scoreTextView;
    //private int score = 0;
    private LinearLayout layoutColor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email_id);
        passwordEditText = findViewById(R.id.password_id);
        logInButton = findViewById(R.id.logIn_Id);
        scoreTextView = findViewById(R.id.scoreAdd_id);
        layoutColor = findViewById(R.id.layoutColorId);



        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (loadColor() != getResources().getColor(R.color.colorPrimary)){
            layoutColor.setBackgroundColor(loadColor());

        }


/*

        if (user != null){
            startActivity(new Intent(MainActivity.this,DataBaseActivity.class));
        }
*/

       /* SharedPreferences myScore = getSharedPreferences("LastScore",MODE_PRIVATE);
        score = myScore.getInt("Score", score);
        scoreTextView.setText(" "+score);*/




        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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



            }
        });


       /* logInButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
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

                return false;
            }
        });

*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        startActivity(new Intent(MainActivity.this,ImageActivity.class));
        finish();
    }

    public void SaveTime(View view) {
        startActivity(new Intent(MainActivity.this,DataBaseActivity.class));

     /*   score +=5;
        SharedPreferences myScore = getSharedPreferences("LastScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = myScore.edit();
        editor.putInt("Score",score);
        editor.commit();

        scoreTextView.setText(" "+ score);*/

    }

    public void Blue(View view) {
        layoutColor.setBackgroundColor(getResources().getColor(R.color.blueColor));
        saveColor(getResources().getColor(R.color.blueColor));
        startActivity(new Intent(MainActivity.this,RecyclearViewActivity.class));
    }

    public void Pink(View view) {
        layoutColor.setBackgroundColor(getResources().getColor(R.color.pinkColor));
        saveColor(getResources().getColor(R.color.pinkColor));
        startActivity(new Intent(MainActivity.this,ImageActivity.class));

    }

    public void Green(View view) {
        layoutColor.setBackgroundColor(getResources().getColor(R.color.greenColor));
        saveColor(getResources().getColor(R.color.greenColor));
        startActivity(new Intent(MainActivity.this,RecyclearViewActivity.class));

    }

    public void Red(View view) {
        layoutColor.setBackgroundColor(getResources().getColor(R.color.redColor));
        saveColor(getResources().getColor(R.color.redColor));

    }

    private void saveColor (int color){

        SharedPreferences myColor = getSharedPreferences("SaveBackgroundColor",MODE_PRIVATE);
        SharedPreferences.Editor editor = myColor.edit();
        editor.putInt("SaveColor",color);
        editor.commit();


    }
    private int loadColor (){
        SharedPreferences myColor = getSharedPreferences("SaveBackgroundColor",MODE_PRIVATE);
        int setColor = myColor.getInt("SaveColor",getResources().getColor(R.color.colorPrimary));
        return setColor;

    }
}