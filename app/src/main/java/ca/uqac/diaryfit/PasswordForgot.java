package ca.uqac.diaryfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordForgot extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmailForgot;
    private ProgressBar progressBar;
    private Button resetButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgot);

        editEmailForgot = findViewById(R.id.forgotTextEmail);
        resetButton = findViewById(R.id.buttonResetPassword);
        progressBar = findViewById(R.id.progressBarForgot);

        resetButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonResetPassword){
            resetPassword();
        }
    }

    private void resetPassword() {
        String email = editEmailForgot.getText().toString().trim();

        if(email.isEmpty()){
            editEmailForgot.setError("Email is required");
            editEmailForgot.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmailForgot.setError("Email invalid");
            editEmailForgot.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PasswordForgot.this, "Check your email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(PasswordForgot.this, "Try again !", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}