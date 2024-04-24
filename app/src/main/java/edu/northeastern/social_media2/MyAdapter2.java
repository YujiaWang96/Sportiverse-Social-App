package edu.northeastern.social_media2;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
public class MyAdapter2 extends RecyclerView.Adapter<productviewholder2> {
    public String a1,a2;
    private int count;
    FirebaseAuth f1;
    DatabaseReference databaseReference,mDataBaseRef;
    private Context pcontext2;
    private List<upload> prolist2;
    public MyAdapter2(Context pcontext2, List<upload> prolist2) {
        this.pcontext2 = pcontext2;
        this.prolist2 = prolist2;
    }
    @Override
    public productviewholder2 onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {
        View mView2 =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.insta_feed_rv_videoitem,parent,false);
        return new productviewholder2(mView2);
    }
    @Override
    public void onBindViewHolder(@NonNull productviewholder2 holder, int
            position) {
        Glide.with(pcontext2).load(prolist2.get(position).getPropic()).into(holder.
                c1);
        Glide.with(pcontext2).load(prolist2.get(position).getUrl()).into(holder.z2)
        ;
        holder.t1.setText(prolist2.get(position).getName2());
        holder.t2.setText(prolist2.get(position).getTitle());
        holder.t4.setText(prolist2.get(position).getKey());
        holder.t5.setText(prolist2.get(position).getId2());
        String prop3;
        prop3=(prolist2.get(position).getUrl().toString());
        holder.mccard.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            ConnectivityManager manager= (ConnectivityManager)
                    pcontext2.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activenetwork = manager.getActiveNetworkInfo();
            if(null!=activenetwork){
                Intent intent = new Intent(pcontext2,DisplayVideo.class);
                intent.putExtra("Image",prolist2.get(holder.getAdapterPosition()).getUrl())
                ;
                intent.putExtra("Image2",prolist2.get(holder.getAdapterPosition()).getPropic());
                intent.putExtra("Name",prolist2.get(holder.getAdapterPosition()).getName2()
                );
                intent.putExtra("Title",prolist2.get(holder.getAdapterPosition()).getTitle(
                ));
                intent.putExtra("Key",prolist2.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Id",prolist2.get(holder.getAdapterPosition()).getId2());
                pcontext2.startActivity(intent);
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
        return prolist2.size();
    }
    public void filteredlist(ArrayList<upload> filterlist) {
        prolist2 = filterlist;
        notifyDataSetChanged();
    }
}
class productviewholder2 extends RecyclerView.ViewHolder {
    CircleImageView c1;
    TextView t1,t2,t3,t4,t5;
    Button b1,b2,b3;
    CardView mccard;
    public String id2,prop;
    ImageView z2;
    public productviewholder2( View itemView) {
        super(itemView);
        z2 = itemView.findViewById(R.id.idIVPost2);
        c1 = itemView.findViewById(R.id.idCVAuthor2);
        t1= itemView.findViewById(R.id.idTVAuthorName2);
        t2=itemView.findViewById(R.id.idTVdesc2);
        t3 = itemView.findViewById(R.id.idTVLikes2);
        t4 = itemView.findViewById(R.id.idTVPostuserid2);
        t5 = itemView.findViewById(R.id.idTVPosttimekey2);
        b2 = itemView.findViewById(R.id.idTVLikesbtn2);
        mccard = itemView.findViewById(R.id.mycard);
    }
}