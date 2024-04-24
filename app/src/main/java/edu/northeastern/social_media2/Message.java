package edu.northeastern.social_media2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class Message extends AppCompatActivity {
    RecyclerView recycle;
    List<upload> prolist;
    FirebaseUser vapar;
    DatabaseReference acc2;
    private String userId,abc,getUserId;
    public DatabaseReference acc;
    MyAdapter myadapter;
    private DatabaseReference refrence;
    private ValueEventListener eventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        recycle=findViewById(R.id.messagerecycler);
        acc = FirebaseDatabase.getInstance().getReference().child("users");
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue));

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycle.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setReverseLayout(true);
        gridLayoutManager.setStackFromEnd(true);
        vapar = FirebaseAuth.getInstance().getCurrentUser();
        userId = vapar.getUid();
        prolist = new ArrayList<>();
        myadapter = new MyAdapter(Message.this, prolist);
        recycle.setAdapter(myadapter);
        refrence =
                FirebaseDatabase.getInstance().getReference("message"+userId);

        eventListener = refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prolist.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()){
                    upload dete = itemSnapshot.getValue(upload.class);
                    prolist.add(dete);
                }
                myadapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(Message.this,videoimageplayer.class);
        startActivity(intent);
    }
}