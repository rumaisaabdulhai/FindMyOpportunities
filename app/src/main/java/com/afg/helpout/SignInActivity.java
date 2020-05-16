package com.afg.helpout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
 * Initiates the WalkThroughActivity if the User has opened the
 * app for the very first time after a fresh install.
 *
 * Allows the User to Sign in with an Email and Password.
 *
 */
public class SignInActivity extends AppCompatActivity {

    // TAG for logging
    private static final String TAG = "SignInActivity";

    // Initializing variables
    TextInputLayout mEmailSignIn, mPasswordSignIn;
    Button mButtonSignIn;
    TextView mLinkSignUp;
    FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener firebaseAuthListener;

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

        // RUN ACTIVITY ONCE AFTER FRESH INSTALL
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
            Intent intent = new Intent(this, WalkThroughActivity.class);
            startActivity(intent);
        }

        // Get References
        mEmailSignIn = findViewById(R.id.email);
        mPasswordSignIn = findViewById(R.id.password);
        mButtonSignIn = findViewById(R.id.signIn);
        mLinkSignUp = findViewById(R.id.signUpLink);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            /**
             * Checks if the User is Logged in.
             * @param firebaseAuth The FirebaseAuth object
             */
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    finish();
                    Toast.makeText(SignInActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(i);
                    return;
                }
            }
        };

        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the Sign In Button is clicked.
             * @param view The View object
             */
            @Override
            public void onClick(View view) {
                // Converting input from user to String
                String email = mEmailSignIn.getEditText().getText().toString();
                String password = mPasswordSignIn.getEditText().getText().toString();

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }

                // If only the email field is empty
                else if (email.isEmpty()) {
                    mEmailSignIn.setError("Please enter your email.");
                    mEmailSignIn.requestFocus();
                }

                // If only the password field is empty
                else if (password.isEmpty()) {
                    mPasswordSignIn.setError("Please enter your email.");
                    mPasswordSignIn.requestFocus();
                }

                // If both fields are empty
                else if (!(email.isEmpty() && password.isEmpty())) {

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        /**
                         * Called when Attempting to Sign in With Firebase.
                         *
                         * @param task The Task<AuthResult>
                         */
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.d(TAG, "Sign in unsuccessful");
                                Toast.makeText(SignInActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

        mLinkSignUp.setOnClickListener(new View.OnClickListener() {
            /**
             * Opens the SignUpActivity when the Sign Up Link is clicked.
             * @param view The View object
             */
            @Override
            public void onClick(View view) {
                finish();
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
        mAuth.addAuthStateListener(firebaseAuthListener);

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}