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

public class MyAdapter3 extends RecyclerView.Adapter<productviewholder3> {
    public String a1,a2,name2,propic,url,title,key,id2,like,id3;
    private int count;
    FirebaseAuth f1;
    DatabaseReference databaseReference,mDataBaseRef;
    private Context pcontext3;
    private List<forfriends> prolist3;
    public MyAdapter3(Context pcontext3, List<forfriends> prolist3) {
        this.pcontext3 = pcontext3;
        this.prolist3 = prolist3;
    }
    @Override
    public productviewholder3 onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {
        View mView2 =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.send_friends_layout,parent,false);
        return new productviewholder3(mView2);
    }
    @Override
    public void onBindViewHolder(@NonNull productviewholder3 holder, int
            position) {
        Glide.with(pcontext3).load(prolist3.get(position).getTyaimg()).into(holder.
                c1);
        holder.t1.setText(prolist3.get(position).getTyaame());
        holder.t2.setText(prolist3.get(position).getTyaid());

        id3= FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference();
        mDataBaseRef.child(FirebaseAuth.getInstance().getUid()+"temporary").child(" 1").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                upload xx = snapshot.getValue(upload.class);
                propic = xx.getPropic();
                name2 =xx.getName2();
                title = xx.getTitle();
                id2 = xx.getId2();
                url = xx.getUrl();
                key = xx.getKey();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    });
    upload nji = new upload(propic,name2,title,id2,url,url,key);
    holder.b1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {ConnectivityManager manager= (ConnectivityManager)
                pcontext3.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activenetwork = manager.getActiveNetworkInfo();
            if(null!=activenetwork){
                FirebaseDatabase.getInstance().getReference().child("message"+holder.t2.getText().toString()).child(key)
                        .child("id2").setValue(id2);
                FirebaseDatabase.getInstance().getReference().child("message"+holder.t2.getText().toString()).child(key)
                        .child("key").setValue(key);
                FirebaseDatabase.getInstance().getReference().child("message"+holder.t2.getText().toString()).child(key)
                        .child("name2").setValue(name2);
                FirebaseDatabase.getInstance().getReference().child("message"+holder.t2.getText().toString()).child(key)
                        .child("propic").setValue(propic);
                FirebaseDatabase.getInstance().getReference().child("message"+holder.t2.getText().toString()).child(key)
                        .child("title").setValue(title);
                FirebaseDatabase.getInstance().getReference().child("message"+holder.t2.getText().toString()).child(key)
                        .child("url").setValue(url);
                Toast.makeText(v.getContext(), "Sent successfully!",
                        Toast.LENGTH_SHORT).show();
                Intent i = new
                        Intent(pcontext3,videoimageplayer.class);
                pcontext3.startActivity(i);
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
class productviewholder3 extends RecyclerView.ViewHolder {CircleImageView c1;
    TextView t1,t2;
    Button b1;
    public String id2,prop;
    public productviewholder3( View itemView) {
        super(itemView);
        c1 = itemView.findViewById(R.id.sfriendimage);
        t1= itemView.findViewById(R.id.sfriendname);
        t2=itemView.findViewById(R.id.sfriendid);
        b1 = itemView.findViewById(R.id.sfriendshare);
    }
}