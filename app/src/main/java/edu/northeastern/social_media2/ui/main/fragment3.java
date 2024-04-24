package edu.northeastern.social_media2.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import edu.northeastern.social_media2.Friends;
import edu.northeastern.social_media2.MainActivity;
import edu.northeastern.social_media2.Message;
import edu.northeastern.social_media2.ProfileActivity;
import edu.northeastern.social_media2.R;
import edu.northeastern.social_media2.VideoMessage;
import edu.northeastern.social_media2.YourPost;
import edu.northeastern.social_media2.YourVideoPost;
import edu.northeastern.social_media2.upload;
import edu.northeastern.social_media2.userinfo;
import edu.northeastern.social_media2.Friends;
import edu.northeastern.social_media2.MainActivity;
import edu.northeastern.social_media2.Message;
import edu.northeastern.social_media2.ProfileActivity;
import edu.northeastern.social_media2.VideoMessage;
import edu.northeastern.social_media2.YourPost;
import edu.northeastern.social_media2.YourVideoPost;
import edu.northeastern.social_media2.userinfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import de.hdodenhof.circleimageview.CircleImageView;
public class fragment3 extends Fragment {
    public DatabaseReference acc;
    public String name1,photo;
    Button btn,btn2,btn3,btn4,btn5,btn6,btn7;
    CircleImageView c1;
    TextView t1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment3_layout,container,false);
        c1 =(CircleImageView) view.findViewById(R.id.slidephoto);

        t1 =(TextView) view.findViewById(R.id.textView);
        acc = FirebaseDatabase.getInstance().getReference();
        acc.child("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userinfo u = snapshot.getValue(userinfo.class);
                    photo = u.img;
                    name1 = u.name;
                    Glide.with(getContext()).load(photo).into(c1);
                    t1.setText(name1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btn =(Button) view.findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    Intent i = new Intent(getActivity(), YourPost.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn2 =(Button) view.findViewById(R.id.button6);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    Intent i2 = new Intent(getActivity(),
                            YourVideoPost.class);
                    startActivity(i2);
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn3 =(Button) view.findViewById(R.id.button7);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    Intent i3 = new Intent(getActivity(), Friends.class);
                    startActivity(i3);
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn4 =(Button) view.findViewById(R.id.button8);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    Intent i3 = new Intent(getActivity(), Message.class);
                    startActivity(i3);
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn5 =(Button) view.findViewById(R.id.button9);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    Intent i4 = new Intent(getActivity(),
                            VideoMessage.class);
                    startActivity(i4);
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn6 =(Button) view.findViewById(R.id.button10);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)
                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    AlertDialog.Builder alertDialog = new
                            AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Exit"); // Sets title for your alertbox
                    alertDialog.setMessage("Do you really want to Logout ?"); // Message to be displayed on alertbox
                            /* When positive (yes/ok) is clicked */
                            alertDialog.setPositiveButton("Yes", new
                                    DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int
                                                which) {
                                            Toast.makeText(getContext(), "Signed Out successfully!", Toast.LENGTH_SHORT).show();
                                            FirebaseAuth fAuth =
                                                    FirebaseAuth.getInstance();
                                            fAuth.signOut();

                                            Intent i5 = new Intent(getActivity(),

                                                    MainActivity.class);
                                            startActivity(i5);
                                        }
                                    });
                    /* When negative (No/cancel) button is clicked*/
                    alertDialog.setNegativeButton("No", new

                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int
                                        which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();

                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn7 =(Button) view.findViewById(R.id.button11);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager= (ConnectivityManager)

                        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork = manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    Intent i6 = new Intent(getActivity(),
                            ProfileActivity.class);
                    startActivity(i6);
                }
                else
                {
                    Toast.makeText(v.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

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