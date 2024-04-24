package edu.northeastern.social_media2;

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
public class Friends extends AppCompatActivity {
    public DatabaseReference acc;
    RecyclerView recycle;
    List<forfriends> prolist3;
    FirebaseUser vapar;
    public String a1,a2,userId;
    private DatabaseReference refrence;
    private ValueEventListener eventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        recycle=(RecyclerView) findViewById(R.id.friendsrecycler);
        acc = FirebaseDatabase.getInstance().getReference().child("users");
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue));

        LinearLayoutManager gridLayoutManager = new
                LinearLayoutManager(getApplicationContext());
        recycle.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setReverseLayout(true);
        gridLayoutManager.setStackFromEnd(true);
        vapar = FirebaseAuth.getInstance().getCurrentUser();
        userId = vapar.getUid();
        prolist3 = new ArrayList<>();
        MyAdapter6 myadapter = new MyAdapter6(Friends.this, prolist3);
        recycle.setAdapter(myadapter);
        refrence =
                FirebaseDatabase.getInstance().getReference(userId+"friends");

        eventListener = refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prolist3.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()){forfriends dete3 = itemSnapshot.getValue(forfriends.class);
                    prolist3.add(dete3);
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}