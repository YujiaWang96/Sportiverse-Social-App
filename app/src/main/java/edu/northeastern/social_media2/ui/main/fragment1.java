package edu.northeastern.social_media2.ui.main;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.northeastern.social_media2.CreatePost;
import edu.northeastern.social_media2.R;
import edu.northeastern.social_media2.MyAdapter;
import edu.northeastern.social_media2.upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class fragment1 extends Fragment {
    public DatabaseReference acc;
    RecyclerView recycle;
    List<upload> prolist;
    FirebaseUser vapar;
    DatabaseReference acc2;
    private String userId,abc;

    MyAdapter myadapter;
    private DatabaseReference refrence;
    private ValueEventListener eventListener;
    Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout,container,false);
        btn =(Button) view.findViewById(R.id.button2);
        recycle=(RecyclerView) view.findViewById(R.id.idRVInstaFeeds);
        acc = FirebaseDatabase.getInstance().getReference().child("users");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager= (ConnectivityManager)
                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    Intent i=new Intent(getActivity(), CreatePost.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        recycle.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setReverseLayout(true);
        gridLayoutManager.setStackFromEnd(true);
        vapar = FirebaseAuth.getInstance().getCurrentUser();
        userId = vapar.getUid();
        prolist = new ArrayList<>();
        myadapter = new MyAdapter(getActivity(), prolist);
        recycle.setAdapter(myadapter);
        refrence = FirebaseDatabase.getInstance().getReference("posts");

        eventListener = refrence.addValueEventListener(new
            ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot){
                prolist.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {

                    upload dete = itemSnapshot.getValue(upload.class);
                    prolist.add(dete);
                }
                myadapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
            }
        });

        return view;
    }
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}