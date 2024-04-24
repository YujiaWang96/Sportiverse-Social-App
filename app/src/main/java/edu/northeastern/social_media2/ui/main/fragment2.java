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
import edu.northeastern.social_media2.CreateVideoPost;
import edu.northeastern.social_media2.MyAdapter;
import edu.northeastern.social_media2.MyAdapter2;
import edu.northeastern.social_media2.R;
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
import de.hdodenhof.circleimageview.CircleImageView;
public class fragment2 extends Fragment {
    public DatabaseReference acc;
    CircleImageView c1;
    String final1;
    RecyclerView recycle2;
    List<upload> prolist2;
    FirebaseUser vapar;
    DatabaseReference acc2;
    private String userId,abc;
    MyAdapter myadapter2;
    private DatabaseReference refrence;
    private ValueEventListener eventListener;
    Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout,container,false);
        recycle2=(RecyclerView) view.findViewById(R.id.recycler_view);
        acc = FirebaseDatabase.getInstance().getReference().child("users");
        btn =(Button) view.findViewById(R.id.button22);
        acc = FirebaseDatabase.getInstance().getReference().child("users");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager= (ConnectivityManager)
                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    Intent i=new Intent(getActivity(),
                            CreateVideoPost.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });


        LinearLayoutManager gridLayoutManager2 = new
                LinearLayoutManager(getContext());
        recycle2.setLayoutManager(gridLayoutManager2);
        gridLayoutManager2.setReverseLayout(true);
        gridLayoutManager2.setStackFromEnd(true);
        vapar = FirebaseAuth.getInstance().getCurrentUser();
        userId = vapar.getUid();
        prolist2 = new ArrayList<>();
        MyAdapter2 myadapter2 = new MyAdapter2(getActivity(), prolist2);
        recycle2.setAdapter(myadapter2);
        refrence =
                FirebaseDatabase.getInstance().getReference("videoposts");

        eventListener = refrence.addValueEventListener(new

            ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot){
                prolist2.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    upload dete2 = itemSnapshot.getValue(upload.class);
                    prolist2.add(dete2);
                }
                myadapter2.notifyDataSetChanged();
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
            }
        });

        return view;
    } public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}