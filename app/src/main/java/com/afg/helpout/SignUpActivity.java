package com.afg.helpout;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The SignUpActivity Class
 *
 * Allows the User to Sign Up with an email and password.
 *
 */
public class SignUpActivity extends AppCompatActivity {

    // TAG for logging
    private static final String TAG = "SignUpActivity";

    // Initializing variables
    TextInputLayout mEmailRegistration, mPasswordRegistration, mNameRegistration, mUsernameRegistration;
    Button mButtonSignUp;
    TextView mSignInLink;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;

    /**
     * This is the onCreate Method for SignUpActivity.
     *
     * When called, it creates a FirebaseAuth object. It gets the references of the
     * email, password, and sign up button and sign in link to be used later.
     *
     * It implements an AuthStateListener that checks whether the user has signed up and takes
     * the user to the Home Page (HomeActivity).
     *
     * It also has listeners for the sign up button and sign in link.
     *
     * @param savedInstanceState State of the UI Controller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // getting input from activity
        mNameRegistration = findViewById(R.id.name);
        mUsernameRegistration = findViewById(R.id.userName);
        mEmailRegistration = findViewById(R.id.email);
        mPasswordRegistration = findViewById(R.id.password);

        // Sign Up Button and Sign In Link
        mButtonSignUp = findViewById(R.id.signUp);
        mSignInLink = findViewById(R.id.signInLink);

        // Firebase Listener
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            /**
             * Checks if the User is Signed Up.
             * @param firebaseAuth The FirebaseAuth object
             */
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    finish();
                    Log.d(TAG, "Sign up successful");
                    Toast.makeText(SignUpActivity.this, "You are logged in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        };

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the Sign Up Button is clicked.
             * @param view The View object
             */
            @Override
            public void onClick(View view) {

                // Converting input from user to String
                final String name = mNameRegistration.getEditText().getText().toString();
                final String username = mUsernameRegistration.getEditText().getText().toString();
                final String email = mEmailRegistration.getEditText().getText().toString();
                final String password = mPasswordRegistration.getEditText().getText().toString();

                // Finding if a user exists that has the same username that was input by user
                Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Username").equalTo(username);
                usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    /**
                     * Checks if input username already exists.
                     * If it does not exist, creates a new user.
                     *
                     * @param dataSnapshot The DataSnapshot
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // If there is at least one user with the same username
                        if (dataSnapshot.getChildrenCount() > 0) {
                            Toast.makeText(SignUpActivity.this, "Username already taken" , Toast.LENGTH_SHORT).show();
                            mUsernameRegistration.setError("Username already taken");
                            mUsernameRegistration.requestFocus();
                        }

                        // If username does not exist, proceed
                        else {

                            boolean allEmpty = name.isEmpty() && username.isEmpty() && email.isEmpty() && password.isEmpty();

                            // If everything is empty
                            if (allEmpty) {
                                Toast.makeText(SignUpActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                            }

                            // If only the name field is empty
                            else if (name.isEmpty()) {
                                mNameRegistration.setError("Please enter your name.");
                                mNameRegistration.requestFocus();
                            }

                            // If only the username field is empty
                            else if (username.isEmpty()) {
                                mUsernameRegistration.setError("Please enter your username.");
                                mUsernameRegistration.requestFocus();
                            }

                            // If only the email field is empty
                            else if (email.isEmpty()) {
                                mEmailRegistration.setError("Please enter your email.");
                                mEmailRegistration.requestFocus();
                            }

                            // If only the password field is empty
                            else if (password.isEmpty()) {
                                mPasswordRegistration.setError("Please enter your email.");
                                mPasswordRegistration.requestFocus();
                            }

                            // If all are not empty
                            else if (!(allEmpty)) {

                                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    /**
                                     * Called when attempting to sign up with Firebase.
                                     *
                                     * @param task The Task<AuthResult>
                                     */
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Report Error
                                        if (!task.isSuccessful()) {
                                            Log.d(TAG, "Sign up unsuccessful");
                                            Toast.makeText(SignUpActivity.this, "Sign up error.", Toast.LENGTH_SHORT).show();
                                        }

                                        // Creating User was successful- Begin to store data
                                        else {

                                            final String user_id = mAuth.getCurrentUser().getUid();
                                            final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                                            Map newPost = new HashMap();
                                            newPost.put("Name", name);
                                            newPost.put("Username", username);
                                            current_user_db.setValue(newPost);
                                        }
                                    }
                                });

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
        });

        mSignInLink.setOnClickListener(new View.OnClickListener() {
            /**
             * Opens the SignInActivity when the Sign In Link is clicked.
             * @param view The View object
             */
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
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

        // If User is Logged in, go to Home Page
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
        }
    }

    /**
     * Removes listener
     */
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

}