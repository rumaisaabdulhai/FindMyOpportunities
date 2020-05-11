package com.afg.helpout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    EditText emailID, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        mFirebaseAuth = FirebaseAuth.getInstance();
//        emailID = findViewById(R.id.email);
//        password = findViewById(R.id.password);
//        btnSignUp = findViewById(R.id.signUp);
//        tvSignIn = findViewById(R.id.signInLink);
//
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = emailID.getText().toString();
//                String pwd = password.getText().toString();
//                if (email.isEmpty()) {
//                    emailID.setError("Please enter your email.");
//                    emailID.requestFocus();
//                }
//                else if (pwd.isEmpty()) {
//                    password.setError("Please enter your password.");
//                    password.requestFocus();
//                }
//                else if (email.isEmpty() && pwd.isEmpty()) {
//                    Toast.makeText(ProfileActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
//                }
//                else if (!(email.isEmpty() && pwd.isEmpty())) {
//                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (!task.isSuccessful()) {
//                                Toast.makeText(ProfileActivity.this, "Sign Up Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
//                            }
//                        }
//                    });
//                }
//                else {
//                    Toast.makeText(ProfileActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        tvSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
//                startActivity(i);
//            }
//        });

        // BOTTOM NAVIGATION MENU
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_home_nav:
                        Intent i1 = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_search_nav:
                        Intent i2 = new Intent(ProfileActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_profile_nav:
                        break;
                }
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}
