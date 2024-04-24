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
public class MyAdapter5 extends RecyclerView.Adapter<productviewholder5> {
    public String a1,a2;
    private int count;
    FirebaseAuth f1;
    DatabaseReference databaseReference,mDataBaseRef;
    private Context pcontext5;
    private List<upload> prolist5;
    public MyAdapter5(Context pcontext5, List<upload> prolist5) {
        this.pcontext5 = pcontext5;
        this.prolist5 = prolist5;
    }
    @Override
    public productviewholder5 onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {
        View mView2 =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.your_videopost_layout,parent,false);
        return new productviewholder5(mView2);
    }
    @Override
    public void onBindViewHolder(@NonNull productviewholder5 holder, int
            position) {
        Glide.with(pcontext5).load(prolist5.get(position).getPropic()).into(holder.
                c1);
        Glide.with(pcontext5).load(prolist5.get(position).getUrl()).into(holder.z2)
        ;
        holder.t1.setText(prolist5.get(position).getName2());
        holder.t2.setText(prolist5.get(position).getTitle());
        holder.t4.setText(prolist5.get(position).getKey());
        holder.t5.setText(prolist5.get(position).getId2());
        String prop3;
        prop3=(prolist5.get(position).getUrl().toString());
        holder.mccard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        pcontext5.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    Intent intent = new
                            Intent(pcontext5,DisplayVideo2.class);
                    intent.putExtra("Image",prolist5.get(holder.getAdapterPosition()).getUrl());
                    intent.putExtra("Image2",prolist5.get(holder.getAdapterPosition()).getPropic());
                    intent.putExtra("Name",prolist5.get(holder.getAdapterPosition()).getName2()
                    );
                    intent.putExtra("Title",prolist5.get(holder.getAdapterPosition()).getTitle(
                    ));
                    intent.putExtra("Key",prolist5.get(holder.getAdapterPosition()).getKey());
                    intent.putExtra("Id",prolist5.get(holder.getAdapterPosition()).getId2());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    pcontext5.startActivity(intent);
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
        return prolist5.size();
    }
    public void filteredlist(ArrayList<upload> filterlist) {
        prolist5 = filterlist;
        notifyDataSetChanged();
    }
}
class productviewholder5 extends RecyclerView.ViewHolder {
    CircleImageView c1;
    TextView t1,t2,t3,t4,t5;
    Button b1,b2,b3;
    CardView mccard;
    public String id2,prop;
    ImageView z2;
    public productviewholder5( View itemView) {
        super(itemView);
        z2 = itemView.findViewById(R.id.idIVPost2yvp);
        c1 = itemView.findViewById(R.id.idCVAuthor2yvp);
        t1= itemView.findViewById(R.id.idTVAuthorName2yvp);
        t2=itemView.findViewById(R.id.idTVdesc2yvp);
        t3 = itemView.findViewById(R.id.idTVLikes2yvp);
        t4 = itemView.findViewById(R.id.idTVPostuserid2yvp);
        t5 = itemView.findViewById(R.id.idTVPosttimekey2yvp);
        b2 = itemView.findViewById(R.id.idTVLikesbtn2yvp);
        mccard = itemView.findViewById(R.id.mycardyvp);
    }
}