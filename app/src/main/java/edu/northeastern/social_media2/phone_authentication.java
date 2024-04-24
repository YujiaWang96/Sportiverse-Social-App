package edu.northeastern.social_media2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import java.util.concurrent.TimeUnit;
public class phone_authentication extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edtPhone, edtOTP;

    private Button verifyOTPBtn, generateOTPBtn;
    ProgressBar p1;
    private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);
        p1 = findViewById(R.id.progressBar);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        mAuth = FirebaseAuth.getInstance();
        edtPhone = findViewById(R.id.idEdtPhoneNumber);
        edtOTP = findViewById(R.id.idEdtOtp);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);
        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    Toast.makeText(phone_authentication.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    p1.setVisibility(View.VISIBLE);
                    String phone = "+1" + edtPhone.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    Toast.makeText(phone_authentication.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyCode(edtOTP.getText().toString());
                }
            }
        });
    }
    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task)

                    {
                        if (task.isSuccessful()) {
                            Intent i = new
                                    Intent(phone_authentication.this, videoimageplayer.class);
                            startActivity(i);
                            finish();

                            String name = edtPhone.getText().toString();
                            String

                                    photo="https://firebasestorage.googleapis.com/v0/b/social-media- 853d3.appspot.com/o/istockphoto.jpg?alt=media&token=d96a8857-6c2d-4b51- bc17-192daef26c38";
                            userinfo u = new userinfo(name,photo);
                            forfriends f = new
                                    forfriends(name,photo,FirebaseAuth.getInstance().getUid());
                            FirebaseDatabase.getInstance().getReference().child("users")
                                    .child(FirebaseAuth.getInstance().getUid()).

                                    setValue(u).addOnCompleteListener(new

                                                                              OnCompleteListener<Void>() {
                                                                                  @Override
                                                                                  public void onComplete(@NonNull
                                                                                                         Task<Void> task) {
                                                                                      if (task.isSuccessful()) {
                                                                                          Toast.makeText(phone_authentication.this, "Sign In Successful!",
                                                                                                  Toast.LENGTH_SHORT).show();
                                                                                          FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()+"friends")
                                                                                                  .child(FirebaseAuth.getInstance().getUid()).
                                                                                                  setValue(f);
                                                                                      } else {
                                                                                      }
                                                                                  }
                                                                              });
                        } else {
                            Toast.makeText(phone_authentication.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void sendVerificationCode(String number) {
// this method is used for getting
// OTP on user phone number.

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
.setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
// initializing our callbacks for on
// verification callback method.
            mCallBack = new
            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                // below method is used when
// OTP is sent from Firebase
                @Override
                public void onCodeSent(String s,
                                       PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
// when we receive the OTP it
// contains a unique id which
// we are storing in our string
// which we have already created.
                    p1.setVisibility(View.INVISIBLE);
                    verificationId = s;
                }
                // this method is called when user
// receive OTP from Firebase.
                @Override
                public void onVerificationCompleted(PhoneAuthCredential
                                                            phoneAuthCredential) {
// below line is used for getting OTP code
// which is sent in phone auth credentials.
                    final String code = phoneAuthCredential.getSmsCode();
// checking if the code
// is null or not.
                    if (code != null) {
                        // if the code is not null then
                        // we are setting that code to
                        // our OTP edittext field.
                        edtOTP.setText(code);
                        // after setting this code
                        // to OTP edittext field we
                        // are calling our verifycode method.
                        verifyCode(code);
                    }
                }
// this method is called when firebase doesn't

                // sends our OTP code due to any error or issue.
                @Override
                public void onVerificationFailed(FirebaseException e) {
// displaying error message with firebase exception.
                    Toast.makeText(phone_authentication.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            };
    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(verificationId, code);
        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
}