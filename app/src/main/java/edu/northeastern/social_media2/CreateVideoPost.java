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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CreateVideoPost extends AppCompatActivity {
    EditText e1;
    FirebaseAuth f1;
    public String id,nl,downloadUri;
    public String a1,a2,edit;
    Button btn;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    public StorageReference storageReference;
    ProgressBar p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_video_post);
        p1 = findViewById(R.id.progressBar4);
        f1 = FirebaseAuth.getInstance();
        btn=findViewById(R.id.button4);
        id=f1.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference().child("videoposts");
        firebaseDatabase=FirebaseDatabase.getInstance();
        nl="null";
        e1=findViewById(R.id.editTextTextPersonName2);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue));
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager= (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    p1.setVisibility(View.VISIBLE);
                    choosevideo();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void choosevideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }
    Uri videouri;
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null &&
                data.getData() != null) {
            videouri = data.getData();
            uploadvideo();
        }

    }
    private String getfiletype(Uri videouri) {
        ContentResolver r = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }
    private void uploadvideo() {
        if (videouri != null) {
            String mycurrenttime= DateFormat.getDateTimeInstance()
                    .format(Calendar.getInstance().getTime());
            final StorageReference reference =
                    storageReference.child(mycurrenttime);
            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Task<Uri> uriTask =
                            taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    downloadUri = uriTask.getResult().toString();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("videolink", downloadUri);
                    torealtime();
                    Toast.makeText(CreateVideoPost.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateVideoPost.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void torealtime(){
        edit= e1.getText().toString();
        String mycurrenttime2= DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());
        upload u = new upload(a2,a1,edit,id,downloadUri,nl,mycurrenttime2);
        FirebaseDatabase.getInstance().getReference().child("videoposts")
                .child(mycurrenttime2).
                setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateVideoPost.this, "Your Post is Online!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to upload post", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()+"videoposts")
                .child(mycurrenttime2).
                setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Intent i = new Intent(CreateVideoPost.this,videoimageplayer.class);
                            startActivity(i);
                            Toast.makeText(CreateVideoPost.this, "Your Post is Online!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to upload post", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}