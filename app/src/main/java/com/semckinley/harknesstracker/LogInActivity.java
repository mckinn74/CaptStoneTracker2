package com.semckinley.harknesstracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
        private FirebaseAuth mAuth;
        private EditText mEmail;
        private EditText mPassword;
        private Button mCreateAccount;
        private Button mLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
         mAuth = FirebaseAuth.getInstance();
         mCreateAccount = (Button) findViewById(R.id.bt_create);
         mLogIn = (Button) findViewById(R.id.bt_log_in);
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

    }

    public void onClick(View v){
        int buttonClicked = v.getId();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        final Intent intent = new Intent(LogInActivity.this, AddActivity.class);

        if (buttonClicked == R.id.bt_create){

           //Create the account and verify authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                         FirebaseUser user = mAuth.getCurrentUser();
                        String userName = user.getUid();
                        intent.putExtra("ID", userName);
                        startActivity(intent);
                    } else {


                        Toast.makeText(LogInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }


                }
            });


      }
        else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                 FirebaseUser user = mAuth.getCurrentUser();
                                 String userName = user.getUid();
                                 intent.putExtra("ID", userName);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                 Toast.makeText(LogInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

    }

});}
    }
}
