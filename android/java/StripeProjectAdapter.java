package com.drassapps.archdeal;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StripeProjectAdapter extends RecyclerView.Adapter<StripeProjectAdapter.ViewHolder> {

    private ArrayList<String> contactData;
    private Context context;

    public StripeProjectAdapter(Context context, ArrayList<String> contactData) {
        this.context = context;
        this.contactData = contactData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, cp;
        private ImageView brand;

        private ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.stripelist_brand);
            cp = view.findViewById(R.id.stripelist_cp);
            brand = view.findViewById(R.id.stripelist_image);
        }
    }

    @Override
    public StripeProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stripeproject_list, parent, false);

        return new StripeProjectAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StripeProjectAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(contactData.get(0));
        viewHolder.cp.setText(contactData.get(1));
        int idVisa = context.getResources().
                getIdentifier("visa", "drawable", context.getPackageName());
        int idMaster = context.getResources().
                getIdentifier("mastercard", "drawable", context.getPackageName());

        if (contactData.get(2).equals("visa")){ viewHolder.brand.setBackgroundResource(idVisa); }
        else { viewHolder.brand.setBackgroundResource(idMaster); }
    }

    @Override
    public int getItemCount() {
        return contactData.size();
    }
}
