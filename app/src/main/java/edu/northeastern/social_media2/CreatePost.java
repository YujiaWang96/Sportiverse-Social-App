package edu.northeastern.social_media2;

import android.content.ContentResolver;
import android.content.Context;
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
import androidx.appcompat.app.AppCompatActivity;

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
public class CreatePost extends AppCompatActivity {
    CircleImageView i1;
    EditText e1;
    FirebaseAuth f1;
    public String id,nl,imgUrl;
    public String a1,a2,edit;
    public Uri imageuri;
    public static final int PICK_IMAGE =1;
    Button btn;
    ProgressBar p1;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    public StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        f1 = FirebaseAuth.getInstance();
        btn=findViewById(R.id.button3);
        p1 = findViewById(R.id.progressBar3);
        id=f1.getUid();
        databaseReference =
                FirebaseDatabase.getInstance().getReference("users");
        storageReference =
                FirebaseStorage.getInstance().getReference().child("posts");
        firebaseDatabase=FirebaseDatabase.getInstance();
        nl="null";
        i1=findViewById(R.id.imageView);
        e1=findViewById(R.id.editTextTextPersonName);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,
                        "Select Picture!"), PICK_IMAGE);
            }
        });
        databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userinfo obj = snapshot.getValue(userinfo.class);
                a1 = obj.name;
                a2 = obj.img;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }@Override
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
            i1.setImageURI(imageuri);
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

                        uploaddata();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot
                                                                                                                              snapshot) {
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
    private void uploaddata(){
        edit= e1.getText().toString();
        String mycurrenttime2= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        upload u = new upload(a2,a1,edit,id,imgUrl,nl,mycurrenttime2);
        FirebaseDatabase.getInstance().getReference().child("posts")
                .child(mycurrenttime2).
                setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Post uploaded successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to upload post", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()+"posts")
                .child(mycurrenttime2).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreatePost.this,"Your Post is now online!",Toast.LENGTH_LONG).show();
                            finish();
                            Intent i = new Intent(CreatePost.this,videoimageplayer.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to upload post", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}