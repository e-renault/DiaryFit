package ca.uqac.diaryfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.uqac.diaryfit.datas.Session;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private EditText editEmail, editPassword, editPasswordConfirm;
    private Button signUpButton;
    private ProgressBar signUpBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.emailSignUp);
        editPassword = findViewById(R.id.passwordSignUp);
        editPasswordConfirm = findViewById(R.id.passwordConfirmSignUp);

        signUpButton = findViewById(R.id.buttonSignUp);
        signUpBar = findViewById(R.id.progressBarSignUp);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignUp:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editPasswordConfirm.getText().toString().trim();

        if(email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Email invalid");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        if(password.length() < 8){
            editPassword.setError("Password must have 8 characters minimum");
            editPassword.requestFocus();
            return;
        }
        if(confirmPassword.isEmpty()){
            editPasswordConfirm.setError("Please confirm your password");
            editPasswordConfirm.requestFocus();
            return;
        }
        if(!confirmPassword.equals(password)){
            editPassword.setError("You need to confirm the same password");
            editPasswordConfirm.setError("You need to confirm the same password");
            editPassword.requestFocus();
            return;
        }

        signUpBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Auth done", Toast.LENGTH_LONG).show();

                            User user = new User(email, new HashMap<String, List<Session>>(), new ArrayList<String>());

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUp.this, "You have been registered successfully", Toast.LENGTH_LONG).show();
                                                signUpBar.setVisibility(View.GONE);
                                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                            }
                                            else{
                                                Toast.makeText(SignUp.this, "Failed to register", Toast.LENGTH_LONG).show();
                                                signUpBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(SignUp.this, "Failed to register", Toast.LENGTH_LONG).show();
                            signUpBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}