package com.abhishekshrinath.computershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishekshrinath.computershop.Model.Cart;
import com.abhishekshrinath.computershop.Prevalent.Prevalent;
import com.abhishekshrinath.computershop.ViewHolder.CartViewHolder;
import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import io.paperdb.Paper;
import kotlin.contracts.Returns;

public class cartActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private TextView txtmsg1;
    private ShimmerTextView  txttotal,txttl;
    private Shimmer shimmer,shimmer1;
    private Button placeorder;
    LottieAnimationView emptycart;
    String user= Paper.book().read(Prevalent.UsernameKey);
    int overTotalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide(); //hide the default actionbar
        Paper.init(this);
        setContentView(R.layout.activity_cart);
        txttotal=findViewById(R.id.tv_total);
        recyclerView=findViewById(R.id.cart_list);


        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        placeorder=findViewById(R.id.btn_placeorder);
        txtmsg1=findViewById(R.id.msg1);
        txttl=findViewById(R.id.ttltxt);
        emptycart=findViewById(R.id.empty_order_cart);

        shimmer=new Shimmer();
        shimmer1=new Shimmer();

        shimmer.start(txttotal);
        shimmer1.start(txttl);

        Paper.init(this);
        placeorder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent in=new Intent(cartActivity.this,ConfirmFinalOrderActivity.class);
                in.putExtra("totalprice",String.valueOf(overTotalPrice));
                startActivity(in);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (txttotal.equals("0 Rs"))
        {
            placeorder.setEnabled(false);
        }
        else
            {
            checkOrderstate();

            final DatabaseReference cardlistref = FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/").getReference().child("Cart View");
            FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cardlistref.child("User View")
                    .child(user).child("Products"), Cart.class).build();
            FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                    holder.txtproductQuantity.setText("Quantity =" + model.getpQuantity());
                    holder.txtproductPrice.setText("Price =₹" + model.getPprice());
                    holder.txtproductName.setText("" + model.getPname());
                    int onetimeproductTprice = ((Integer.valueOf(model.getPprice()))) * Integer.valueOf(model.getpQuantity());
                    overTotalPrice = overTotalPrice + onetimeproductTprice;
                    txttotal.setText("" + String.valueOf(overTotalPrice));
                    placeorder.setEnabled(true);
                    emptycart.setVisibility(View.GONE);

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CharSequence option1[] = new CharSequence[]
                                    {
                                            "Remove All"
                                    };
                            AlertDialog.Builder builder = new AlertDialog.Builder(cartActivity.this);
                            builder.setTitle("Cart Options:");
                            builder.setItems(option1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    if (i == 0) {
                                        cardlistref.child("User View").child(user).child("Products").removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task)
                                                    {
                                                        cardlistref.child("Admin View").child(user).child("Products").removeValue()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            txttotal.setText("0 Rs");
                                                                            emptycart.setVisibility(View.VISIBLE);
                                                                            placeorder.setEnabled(false);
                                                                            Toast.makeText(cartActivity.this, "Item Remove Successfull", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });

                                                    }
                                                });
                                    }
                                }
                            });
                            builder.show();
                        }
                    });

                }


                @NonNull
                @Override
                public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
                    CartViewHolder holder = new CartViewHolder(view);

                    return holder;

                }
            };
            recyclerView.setAdapter(adapter);

            adapter.startListening();

        }
    }
        private void checkOrderstate ()
        {
            DatabaseReference orderref = FirebaseDatabase.getInstance("https://computer-shop-26dfd-default-rtdb.firebaseio.com/").getReference().child("Orders").child(user);
            orderref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String ShippmentState = snapshot.child("State").getValue().toString();
                        String Username1 = snapshot.child("Name").getValue().toString();
                        if (ShippmentState.equals("Shipped")) {
                            txttotal.setText(" Dear, " + Username1 + "\n Order is Shipped SuccessFully.");
                            recyclerView.setVisibility(View.GONE);
                            txtmsg1.setVisibility(View.VISIBLE);
                            txttl.setVisibility(View.GONE);
                            txtmsg1.setText("Congratulation your final order has been  Shipped Successfully ,Soon You will recive Your Order at your Door Steps");
                            placeorder.setVisibility(View.GONE);
                            emptycart.setVisibility(View.GONE);

                            Toast.makeText(cartActivity.this, "You can Purchase more Products,Once You Recive Your Final Order.", Toast.LENGTH_SHORT).show();
                        }
                        if (ShippmentState.equals("Not Shipped")) {
                            txttotal.setText("Shipping State = Not Shipped");
                            recyclerView.setVisibility(View.GONE);
                            txtmsg1.setVisibility(View.VISIBLE);
                            placeorder.setVisibility(View.GONE);
                            txttl.setVisibility(View.GONE);
                            emptycart.setVisibility(View.GONE);

                            Toast.makeText(cartActivity.this, "You can Purchase more Products,Once You Recive Your Final Order.", Toast.LENGTH_SHORT).show();

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

}