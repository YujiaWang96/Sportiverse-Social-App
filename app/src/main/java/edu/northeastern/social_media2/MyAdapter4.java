package edu.northeastern.social_media2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
public class MyAdapter4 extends RecyclerView.Adapter<productviewholder4> {
    public String a1,a2,id3,x1,x2,x3,x4,x5,x6,x7;
    private int count;
    FirebaseAuth f1;
    DatabaseReference databaseReference,mDataBaseRef;
    private Context pcontext4;
    private List<upload> prolist4;
    public MyAdapter4(Context pcontext4, List<upload> prolist4) {
        this.pcontext4 = pcontext4;
        this.prolist4 = prolist4;
    }
    @Override
    public productviewholder4 onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {
        View mView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.your_post_layout,
                        parent,false);
        return new productviewholder4(mView);
    }
    @Override
    public void onBindViewHolder(@NonNull productviewholder4 holder, int
            position) {
        Glide.with(pcontext4).load(prolist4.get(position).getPropic()).into(holder.
                c1);
        Glide.with(pcontext4).load(prolist4.get(position).getUrl()).into(holder.z1)
        ;
        holder.t1.setText(prolist4.get(position).getName2());
        holder.t2.setText(prolist4.get(position).getTitle());
        holder.t4.setText(prolist4.get(position).getKey());
        holder.t5.setText(prolist4.get(position).getId2());
        x1 = (prolist4.get(position).getPropic());
        x2 = (prolist4.get(position).getName2());
        x3 = (prolist4.get(position).getTitle());
        x4 = (prolist4.get(position).getId2());
        x5 = (prolist4.get(position).getUrl());
        x7 = (prolist4.get(position).getKey());
        upload u = new upload(x1,x2,x3,x4,x5,x5,x7);
        mDataBaseRef = FirebaseDatabase.getInstance().getReference();
        mDataBaseRef.child(holder.t4.getText().toString()+"likes").child("likes").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot
                                                     snapshot) {
                        if(snapshot.exists()){
                            count=(int) snapshot.getChildrenCount();
                            holder.t3.setText(Integer.toString(count));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager= (ConnectivityManager)
                        pcontext4.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    id3 = f1.getUid();
                    FirebaseDatabase.getInstance().getReference().child(holder.t4.getText().toString()+"likes")
                            .child("likes").child(id3).setValue("1")
                            .addOnCompleteListener(new
                                                           OnCompleteListener<Void>() {
                                                               @Override

                                                               public void onComplete(@NonNull Task<Void>

                                                                                              task) {
                                                               }
                                                           });
                }
                else
                {
                    Toast.makeText(view.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        pcontext4.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()+"temporary").child("1").
                            setValue(u).addOnCompleteListener(new
                                                                      OnCompleteListener<Void>() {
                                                                          @Override

                                                                          public void onComplete(@NonNull Task<Void>

                                                                                                         task) {
                                                                              Intent intent2 = new
                                                                                      Intent(pcontext4,Send_Post.class);
                                                                              intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                                                      |
                                                                                      Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                              pcontext4.startActivity(intent2);}
                                                                      });
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager= (ConnectivityManager)
                        pcontext4.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    FirebaseDatabase.getInstance().getReference()
                            .child("posts")
                            .child(holder.t4.getText().toString()).removeValue().addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                        @Override

                                        public void onComplete(@NonNull Task<Void>

                                                                       task) {
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child(FirebaseAuth.getInstance().getUid()+"posts").child(holder.t4.getText
                                                            ().toString())
                                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void

                                                        onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(view.getContext(), "Post Deleted!",
                                                                    Toast.LENGTH_SHORT).show();
                                                            Intent i = new
                                                                    Intent(pcontext4,videoimageplayer.class);
                                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                                    |
                                                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            pcontext4.startActivity(i);
                                                        }
                                                    });
                                        }
                                    });
                }
                else
                {
                    Toast.makeText(view.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }}
        });

    }
    @Override
    public int getItemCount() {
        return prolist4.size();
    }
    public void filteredlist(ArrayList<upload> filterlist) {
        prolist4 = filterlist;
        notifyDataSetChanged();
    }
}
class productviewholder4 extends RecyclerView.ViewHolder {
    CircleImageView c1;
    ImageView z1;
    TextView t1,t2,t3,t4,t5;
    Button b1,b2,b3;
    LinearLayout mccard;
    public String id2,prop;
    public productviewholder4( View itemView) {
        super(itemView);
        c1 = itemView.findViewById(R.id.idCVAuthoryp);
        z1 = itemView.findViewById(R.id.idIVPostyp);
        t1= itemView.findViewById(R.id.idTVAuthorNameyp);
        t2=itemView.findViewById(R.id.idTVdescyp);
        t3 = itemView.findViewById(R.id.idTVLikesyp);
        t4 = itemView.findViewById(R.id.idTVPostuseridyp);
        t5 = itemView.findViewById(R.id.idTVPosttimekeyyp);
        b1 = itemView.findViewById(R.id.addfriendyp);
        b2 = itemView.findViewById(R.id.idTVLikesbtnyp);
        b3 = itemView.findViewById(R.id.sharebuttonyp);
        mccard = itemView.findViewById(R.id.idLLTopBaryp);
    }
}
