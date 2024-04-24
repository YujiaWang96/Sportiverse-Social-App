package edu.northeastern.social_media2;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
public class ProfileActivity extends AppCompatActivity {
    public Uri imageuri;
    public static final int PICK_IMAGE =1;
    public DatabaseReference acc;
    public String name1,photo,s1,s2,imgUrl;
    Button btn,btn2;
    CircleImageView c1;
    EditText t1;
    ProgressBar p1;
    public StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn = findViewById(R.id.button12);
        btn2 = findViewById(R.id.button13);
        p1 = findViewById(R.id.progressBar2);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    AlertDialog.Builder alertDialog = new
                            AlertDialog.Builder(ProfileActivity.this);
                    alertDialog.setTitle("Exit"); // Sets title for your alertbox
                    alertDialog.setMessage("Do you really want to Delete Account?"); // Message to be displayed on alertbox
                            /* When positive (yes/ok) is clicked */
                            alertDialog.setPositiveButton("Yes", new
                                    DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int which) {
                                            Toast.makeText(ProfileActivity.this, "Account Deleted successfully!", Toast.LENGTH_SHORT).show();
                                            FirebaseAuth fAuth =
                                                    FirebaseAuth.getInstance();
                                            fAuth.signOut();

                                            Intent i5 = new Intent(getApplicationContext(),

                                                    MainActivity.class);
                                            startActivity(i5);
                                        }
                                    });

                    /* When negative (No/cancel) button is clicked*/
                    alertDialog.setNegativeButton("No", new

                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int
                                        which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        acc = FirebaseDatabase.getInstance().getReference();
        acc.child("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userinfo u = snapshot.getValue(userinfo.class);
                    photo = u.img;
                    name1 = u.name;
                    Glide.with(getApplicationContext()).load(photo).into(c1);
                    t1.setText(name1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        t1=findViewById(R.id.etextView);
        c1=findViewById(R.id.eslidephoto);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");

                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,
                        "Select Picture!"), PICK_IMAGE);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,@Nullable
    Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager= (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    p1.setVisibility(View.VISIBLE);
                    upload(imageuri);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data !=
                null && data.getData() != null){
            imageuri = data.getData();
            c1.setImageURI(imageuri);
        }
    }
    public void upload(Uri uri){
        String mycurrenttime= DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());
        StorageReference fileRef = storageReference.child(mycurrenttime);
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override

                    public void onSuccess(Uri uri) {
                        Task<Uri> uriTask =
                                taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri urlImage = uriTask.getResult();
                        imgUrl = urlImage.toString();
                        upload2();
                    }

                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
    private String getFileExtension(Uri mUri){
        ContentResolver cr =getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
    public void upload2(){
        userinfo u = new userinfo( t1.getText().toString(),imgUrl);
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i7 = new Intent(ProfileActivity.this,videoimageplayer.class);
                        startActivity(i7);
                        Toast.makeText(ProfileActivity.this,"Profile Update!",Toast.LENGTH_LONG).show();
                    }
                });
    }
}