package com.afg.helpout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The SignInActivity Class
 *
 * Allows the User to Sign in with an Email and Password.
 *
 */
public class SignInActivity extends AppCompatActivity {

    // Variables
    TextInputLayout emailID, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    /**
     * This is the onCreate Method for SignInActivity.
     *
     * When called, it creates a FirebaseAuth object. It gets the references of the
     * email, password, and sign in button and sign up link to be used later.
     *
     * It implements an AuthStateListener that checks whether the user has been logged in.
     * It also has listeners for the sign in button and sign up link.
     *
     * @param savedInstanceState State of the UI Controller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.signIn);
        tvSignUp = findViewById(R.id.signUpLink);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            /**
             * Checks if the User is Logged in.
             * @param firebaseAuth The FirebaseAuth object
             */
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(SignInActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(SignInActivity.this, "Please Log In", Toast.LENGTH_SHORT).show();
                }
            }

        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the Sign In Button is clicked.
             * @param view The View object
             */
            @Override
            public void onClick(View view) {

                // Convert input email and password to Strings
                String email = emailID.getEditText().getText().toString();
                String pwd = password.getEditText().getText().toString();

                // If both attributes are empty
                if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                }

                // If the email is an empty string
                if (email.isEmpty()) {
                    emailID.setError("Please enter your email.");
                    emailID.requestFocus();
                }

                // If the password is an empty string
                else if (pwd.isEmpty()) {
                    password.setError("Please enter your password.");
                    password.requestFocus();
                }

                // If both attributes are filled in
                else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        /**
                         *
                         * TODO: Complete Documentation
                         *
                         * @param task
                         */
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignInActivity.this, "Login Error, Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }

                // If none of the statements are satisfied above, there is likely an error.
                else {
                    Toast.makeText(SignInActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            /**
             * Opens the SignUpActivity when the Sign Up Link is clicked.
             * @param view The View object
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * When initializing activity, checks to see if the user is currently signed in.
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}