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

public class YourVideoPost extends AppCompatActivity {
    public DatabaseReference acc;
    RecyclerView recyclerView;

    List<upload> prolist5;
    FirebaseUser vapar;
    DatabaseReference acc2;
    private String userId,abc;
    MyAdapter myadapter2;
    private DatabaseReference refrence;
    private ValueEventListener eventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_video_post);
        recyclerView=findViewById(R.id.yourvideopostrecycler);
        acc = FirebaseDatabase.getInstance().getReference().child("users");
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        LinearLayoutManager gridLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(gridLayoutManager2);
        gridLayoutManager2.setReverseLayout(true);
        gridLayoutManager2.setStackFromEnd(true);
        vapar = FirebaseAuth.getInstance().getCurrentUser();
        userId = vapar.getUid();
        prolist5 = new ArrayList<>();
        MyAdapter5 myadapter2 = new MyAdapter5(getApplicationContext(), prolist5);
        recyclerView.setAdapter(myadapter2);
        refrence = FirebaseDatabase.getInstance().getReference(userId+"videoposts");
        eventListener = refrence.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prolist5.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()){upload dete2 = itemSnapshot.getValue(upload.class);
                    prolist5.add(dete2);
                }
                myadapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}