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
public class MyAdapter extends RecyclerView.Adapter<productviewholder> {
    public String a1,a2,id3,x1,x2,x3,x4,x5,x6,x7;
    private int count;
    FirebaseAuth f1;
    DatabaseReference databaseReference,mDataBaseRef;
    private Context pcontext;
    private List<upload> prolist;
    public MyAdapter(Context pcontext, List<upload> prolist) {
        this.pcontext = pcontext;
        this.prolist = prolist;
    }
    @Override
    public productviewholder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        View mView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.insta_feed_rv_item,parent,false);
        return new productviewholder(mView);
    }
    @Override
    public void onBindViewHolder(@NonNull productviewholder holder, int position) {
        Glide.with(pcontext).load(prolist.get(position).getPropic()).into(holder.c1);
        Glide.with(pcontext).load(prolist.get(position).getUrl()).into(holder.z1);
        holder.t1.setText(prolist.get(position).getName2());
        holder.t2.setText(prolist.get(position).getTitle());
        holder.t4.setText(prolist.get(position).getKey());
        holder.t5.setText(prolist.get(position).getId2());
        x1 = (prolist.get(position).getPropic());
        x2 = (prolist.get(position).getName2());
        x3 = (prolist.get(position).getTitle());
        x4 = (prolist.get(position).getId2());
        x5 = (prolist.get(position).getUrl());
        x7 = (prolist.get(position).getKey());
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
                        pcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
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
                } }
        });
        holder.b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        pcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()+"temporary").child("1").
                            setValue(u).addOnCompleteListener(new
                                                                      OnCompleteListener<Void>() {
                                                                          @Override

                                                                          public void onComplete(@NonNull Task<Void>

                                                                                                         task) {
                                                                              Intent i = new
                                                                                      Intent(pcontext,Send_Post.class);
                                                                              pcontext.startActivity(i);
                                                                          }
                                                                      });
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.prop=(prolist.get(position).getPropic().toString());
        holder.id2=holder.t5.toString();
        String id;
        f1 = FirebaseAuth.getInstance();
        databaseReference =
                FirebaseDatabase.getInstance().getReference("users");
        id = f1.getUid();
        forfriends u2 = new forfriends(holder.t1.getText().toString(),
                holder.prop,holder.t5.getText().toString());
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager= (ConnectivityManager)
                        pcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    FirebaseDatabase.getInstance().getReference()
                            .child(id + "friends")
                            .child(holder.t5.getText().toString())
                            .setValue(u2).addOnCompleteListener(new
                                                                        OnCompleteListener<Void>() {
                                                                            @Override

                                                                            public void onComplete(@NonNull Task<Void>

                                                                                                           task) {
                                                                                if (task.isSuccessful()) {
                                                                                    databaseReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void
                                                                                        onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                            userinfo obj =
                                                                                                    snapshot.getValue(userinfo.class);
                                                                                            a1 = obj.name;
                                                                                            a2 = obj.img;
                                                                                        }
                                                                                        @Override
                                                                                        public void

                                                                                        onCancelled(@NonNull DatabaseError error) {
                                                                                        }
                                                                                    });

                                                                                    forfriends u1 = new forfriends(a1,

                                                                                            a2, id);
                                                                                    FirebaseDatabase.getInstance().getReference().child(holder.t5.getText().toString() + "friends").child(id).setValue(u1);
                                                                                    Toast.makeText(view.getContext(),
                                                                                            "Friend Added!", Toast.LENGTH_SHORT).show();
                                                                                } else {
                                                                                }
                                                                            }
                                                                        });
                }
                else
                {
                    Toast.makeText(view.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return prolist.size();
    }
    public void filteredlist(ArrayList<upload> filterlist) {
        prolist = filterlist;
        notifyDataSetChanged();
    }
}
class productviewholder extends RecyclerView.ViewHolder {
    CircleImageView c1;
    ImageView z1;
    TextView t1,t2,t3,t4,t5;
    Button b1,b2,b3;
    LinearLayout mccard;
    public String id2,prop;
    public productviewholder( View itemView) {
        super(itemView);
        c1 = itemView.findViewById(R.id.idCVAuthor);
        z1 = itemView.findViewById(R.id.idIVPost);
        t1= itemView.findViewById(R.id.idTVAuthorName);
        t2=itemView.findViewById(R.id.idTVdesc);
        t3 = itemView.findViewById(R.id.idTVLikes);
        t4 = itemView.findViewById(R.id.idTVPostuserid);
        t5 = itemView.findViewById(R.id.idTVPosttimekey);
        b1 = itemView.findViewById(R.id.addfriend);
        b2 = itemView.findViewById(R.id.idTVLikesbtn);
        b3 = itemView.findViewById(R.id.sharebutton);
        mccard = itemView.findViewById(R.id.idLLTopBar);
    }
}