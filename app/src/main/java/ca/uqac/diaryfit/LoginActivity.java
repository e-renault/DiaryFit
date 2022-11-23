package ca.uqac.diaryfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView signUpText, forgotText;
    private Button buttonLogin;
    private EditText emailLogin, passwordLogin;

    private FirebaseAuth mAuth;
    private ProgressBar loginBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        signUpText = findViewById(R.id.textViewSignUp);
        signUpText.setOnClickListener(this);
        forgotText = findViewById(R.id.textViewForgot);
        forgotText.setOnClickListener(this);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        loginBar = findViewById(R.id.progressBarLogin);
        buttonLogin.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewSignUp:
                startActivity(new Intent(LoginActivity.this, SignUp.class));
                break;
            case R.id.buttonLogin:
                userLogin();
                break;
            case R.id.textViewForgot:
                startActivity(new Intent(LoginActivity.this, PasswordForgot.class));
                break;
        }
    }

    private void userLogin() {
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if(email.isEmpty()){
            emailLogin.setError("Email is required");
            emailLogin.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLogin.setError("Email invalid");
            emailLogin.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordLogin.setError("Password is required");
            passwordLogin.requestFocus();
            return;
        }
        if(password.length() < 8){
            passwordLogin.setError("Password must have 8 characters minimum");
            passwordLogin.requestFocus();
            return;
        }
        loginBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    assert user != null;
                    if(user.isEmailVerified()){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                        loginBar.setVisibility(View.GONE);
                    }
                }
                else{
                    loginBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Impossible to login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}