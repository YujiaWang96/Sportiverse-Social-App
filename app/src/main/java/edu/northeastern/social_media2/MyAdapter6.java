package edu.northeastern.social_media2;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
public class MyAdapter6 extends RecyclerView.Adapter<productviewholder6> {
    public String a1,a2,name2,propic,url,title,key,id2,like,id3;
    private int count;
    FirebaseAuth f1;
    DatabaseReference databaseReference,mDataBaseRef;
    private Context pcontext3;
    private List<forfriends> prolist3;
    public MyAdapter6(Context pcontext3, List<forfriends> prolist3) {
        this.pcontext3 = pcontext3;
        this.prolist3 = prolist3;
    }
    @Override
    public productviewholder6 onCreateViewHolder(@NonNull ViewGroup parent,

                                                 int viewType) {
        View mView2 =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.remove_friend,parent,false);
        return new productviewholder6(mView2);
    }
    @Override
    public void onBindViewHolder(@NonNull productviewholder6 holder, int
            position) {
        Glide.with(pcontext3).load(prolist3.get(position).getTyaimg()).into(holder.
                c1);
        holder.t1.setText(prolist3.get(position).getTyaame());
        holder.t2.setText(prolist3.get(position).getTyaid());

        id3= FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference();

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        pcontext3.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    FirebaseDatabase.getInstance().getReference().child(id3+"friends").child(holder.t2.getText().toString()).removeValue().addOnCompleteListener(new
                                                                                                      OnCompleteListener<Void>() {
                                                                                                          @Override

                                                                                                          public void onComplete(@NonNull Task<Void> task) {
                                                                                                              Toast.makeText(v.getContext(), "Friend Removed!", Toast.LENGTH_SHORT).show();
                                                                                                              Intent i = new
                                                                                                                      Intent(pcontext3,videoimageplayer.class);
                                                                                                              i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                                                                                      | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                                                              pcontext3.startActivity(i);
                                                                                                          }
                                                                                                      });
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return prolist3.size();
    }
    public void filteredlist(ArrayList<forfriends> filterlist) {
        prolist3 = filterlist;
        notifyDataSetChanged();
    }
}
class productviewholder6 extends RecyclerView.ViewHolder {
    CircleImageView c1;
    TextView t1,t2;
    Button b1;
    public String id2,prop;
    public productviewholder6( View itemView) {
        super(itemView);
        c1 = itemView.findViewById(R.id.sfriendimagere);
        t1= itemView.findViewById(R.id.sfriendnamere);
        t2=itemView.findViewById(R.id.sfriendidre);
        b1 = itemView.findViewById(R.id.sfriendsharere);
    }
}