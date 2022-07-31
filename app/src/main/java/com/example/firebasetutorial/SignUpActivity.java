package com.example.firebasetutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText registerName, registerEmail;
    TextInputLayout registerPwd;
    Button signUpConBtn;
    FirebaseAuth mAuth;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        //HOOKS
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPwd = findViewById(R.id.registerPwd);
        signUpConBtn = findViewById(R.id.signUpConBtn);

        signUpConBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = registerName.getText().toString();
                String email = registerEmail.getText().toString();
                String pwd = registerPwd.getEditText().getText().toString();
                if(name.isEmpty() && email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Field's empty", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                            loadingBar.dismiss();
                        }
                    });
                }
            }
        });
    }
}