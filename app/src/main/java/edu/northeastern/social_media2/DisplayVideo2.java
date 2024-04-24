package edu.northeastern.social_media2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
public class DisplayVideo2 extends AppCompatActivity {
    String ke3,prop,a1,a2,random,x1,x2,x3,x4,x5,x6,x7;
    private int count;
    VideoView videoView;
    CircleImageView c1;
    TextView t1,t2,t3,t4,t5;
    FirebaseAuth f1;
    Button b1,b2,b3;
    DatabaseReference databaseReference,mDataBaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video2);
        c1 = findViewById(R.id.idCVAuthor3dis);
        t1=findViewById(R.id.idTVAuthorName3dis);
        t2=findViewById(R.id.idTVdesc3dis);
        t3 = findViewById(R.id.idTVLikes3dis);
        t4 = findViewById(R.id.idTVPostuserid3dis);
        t5 = findViewById(R.id.idTVPosttimekey3dis);
        b2 = findViewById(R.id.idTVLikesbtn3dis);
        b1 = findViewById(R.id.addfriend3dis);
        b3 = findViewById(R.id.sharebutton3dis);
        videoView = (VideoView) findViewById(R.id.videoViewdis);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        Bundle mbundle = getIntent().getExtras();
        if(mbundle!=null){

            t1.setText(mbundle.getString("Name"));
            t2.setText(mbundle.getString("Title"));
            t4.setText(mbundle.getString("Key"));
            t5.setText(mbundle.getString("Id"));
            Glide.with(this).load(mbundle.getString("Image2")).into(c1);
            prop = mbundle.getString("Image2");
            ke3 = mbundle.getString("Image");
            x1 = mbundle.getString("Image2");
            x2 = mbundle.getString("Name");
            x3 = mbundle.getString("Title");
            x4 = mbundle.getString("Id");
            x5 = mbundle.getString("Image");
            x7 =mbundle.getString("Key");

        }
        upload uv = new upload(x1,x2,x3,x4,x5,x5,x7);
        Uri uri = Uri.parse(ke3);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.start();
        Toast.makeText(getApplicationContext(),"Please wait!",Toast.LENGTH_SHORT).show();

        mDataBaseRef = FirebaseDatabase.getInstance().getReference();
        mDataBaseRef.child(t4.getText().toString()+"likes").child("likes").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot

                                                     snapshot) {
                        if(snapshot.exists()){
                            count=(int) snapshot.getChildrenCount();
                            t3.setText(Integer.toString(count));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        f1 = FirebaseAuth.getInstance();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager manager= (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    String id3;
                    id3 = f1.getUid();
                    FirebaseDatabase.getInstance().getReference().child(t4.getText().toString()
                                    +"likes")
                            .child("likes").child(id3).setValue("1")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()+"temporary").child("1").
                            setValue(uv).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void
                                onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                    Intent i = new Intent(DisplayVideo2.this,SendVideoPost.class);
                                    startActivity(i);
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        String id2=t5.toString();
        String id;
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        id = f1.getUid();
        forfriends u = new forfriends(t1.getText().toString(), prop,t5.getText().toString());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager= (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    FirebaseDatabase.getInstance().getReference().child("videoposts").child(t4.
                                    getText().toString()).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void
                                onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                    FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()+"videoposts").child(t4.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void
                                        onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                            Toast.makeText(DisplayVideo2.this, "Post Deleted!",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent i = new
                                                    Intent(DisplayVideo2.this,videoimageplayer.class);
                                            startActivity(i);
                                        }
                                    });
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new
                Intent(DisplayVideo2.this,videoimageplayer.class);
        startActivity(intent);

    }
}