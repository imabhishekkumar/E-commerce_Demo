package com.shoppingapp.abhis.demo_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private Context context;
    private List<Model> mItemDataArray;
    private LayoutInflater mLayoutInflator;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    public ItemAdapter(Context context,List<Model> data){
        mLayoutInflator= LayoutInflater.from(context);
        mItemDataArray=data;

    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= mLayoutInflator.from(viewGroup.getContext())
                .inflate(R.layout.item_row,
                        viewGroup,
                        false);


        return new ViewHolder(view,context);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemAdapter.ViewHolder viewHolder, int i) {

        final int pos= i;
        final Model model= mItemDataArray.get(i);
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("Products");
        final String position=String.valueOf(pos+1);

   mRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String product_name= dataSnapshot.child(String.valueOf(position)).child("name").getValue().toString();
        String product_price= dataSnapshot.child(String.valueOf(position)).child("price").getValue().toString();
        model.setItemName(product_name);
        model.setItemPrice(product_price);
        viewHolder.mItemTitle.setText(model.getItemName());
        viewHolder.mItemPrice.setText(model.getItemPrice());
        Picasso.get()
                .load(dataSnapshot.child(String.valueOf(position)).child("image").getValue().toString())
                .into(viewHolder.mImageView);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }




    @Override
    public int getItemCount() {
        return mItemDataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView mItemTitle;
       ImageView mImageView;
       TextView mItemPrice;
       Button removeCart, addCart;



        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            mItemTitle=itemView.findViewById(R.id.itemTitle);
            mImageView=itemView.findViewById(R.id.itemImage);
            mItemPrice=itemView.findViewById(R.id.itemPrice);
            removeCart=itemView.findViewById(R.id.buttonMinus);
            addCart=itemView.findViewById(R.id.buttonPlus);
            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addCart.setVisibility(View.GONE);
                    removeCart.setVisibility(View.VISIBLE);

                }
            });
            removeCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeCart.setVisibility(View.GONE);
                    addCart.setVisibility(View.VISIBLE);
                }
            });


        }
    }
}