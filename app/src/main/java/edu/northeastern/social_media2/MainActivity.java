package edu.northeastern.social_media2;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    // Initialize variables
    SignInButton btSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    Button Btn;
    ProgressBar p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btSignIn=findViewById(R.id.bt_sign_in);
        p1 = findViewById(R.id.progressBar1);
        Btn = findViewById(R.id.button);
        Log.d("MainActivity", "onCreate: Started.");
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fp=new Intent(MainActivity.this,phone_authentication.class);
                startActivity(fp);
                Log.d("MainActivity", "Attempting to sign in with Google.");
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("377962728653-shiq3rmiune7mimdrqpf891hbju25okp.apps.googleusercontent.com")
                        .requestEmail()
                        .build();
        googleSignInClient= GoogleSignIn.getClient(MainActivity.this,googleSignInOptions);
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=googleSignInClient.getSignInIntent();
                startActivityForResult(intent,100);
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            startActivity(new
                    Intent(MainActivity.this,videoimageplayer.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        // Check if the request code matches the one we used for Google Sign-In
        if (requestCode == 100) {
            Log.d("MainActivity", "Google Sign-In Intent Result received.");
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            // Check if Google Sign-In was successful
            if (signInAccountTask.isSuccessful()) {
                Log.d("MainActivity", "Google Sign-In was successful.");
                displayToast("Sign in successful");

                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);

                    // Proceed if the Google Sign-In account is not null
                    if (googleSignInAccount != null) {
                        Log.d("MainActivity", "GoogleSignInAccount not null, proceeding with Firebase Auth.");
                        p1.setVisibility(View.VISIBLE);

                        // Authenticate with Firebase using the Google Sign-In account
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, task -> {
                                    if (task.isSuccessful()) {
                                        Log.d("MainActivity", "Firebase Authentication with Google successful.");
                                        startActivity(new Intent(MainActivity.this, videoimageplayer.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                        displayToast("");

                                        // Code for updating user info in Firebase Database
                                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        String name = firebaseUser != null ? firebaseUser.getDisplayName() : "N/A";
                                        String photoUrl = "https://firebasestorage.googleapis.com/v0/b/social-media-2-eaf03.appspot.com/o/butterflylogo%20(6).png?alt=media&token=ddfcc319-11f8-4ac4-a7a8-12f19cf8ee9e";
                                        userinfo u = new userinfo(name, photoUrl);
                                        forfriends f = new forfriends(name, photoUrl, FirebaseAuth.getInstance().getUid());

                                        FirebaseDatabase.getInstance().getReference().child("users")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .setValue(u).addOnCompleteListener(task1 -> {
                                                    if (task1.isSuccessful()) {
                                                        Toast.makeText(MainActivity.this, "Sign In Successful!", Toast.LENGTH_SHORT).show();
                                                        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid() + "friends")
                                                                .child(FirebaseAuth.getInstance().getUid())
                                                                .setValue(f);
                                                    } else {
                                                        Log.e("MainActivity", "Updating user info failed: " + task1.getException().getMessage());
                                                    }
                                                });
                                    } else {
                                        Log.e("MainActivity", "Firebase Authentication with Google failed: " + task.getException().getMessage());
                                        displayToast("Authentication Failed :" + task.getException().getMessage());
                                    }
                                });
                    }
                } catch (ApiException e) {
                    Log.e("MainActivity", "Google Sign-In failed. Status Code: " + e.getStatusCode() + " Message: " + e.getMessage(), e);
                }
            } else {
                Log.e("MainActivity", "Google Sign-In Intent was not successful.");
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}