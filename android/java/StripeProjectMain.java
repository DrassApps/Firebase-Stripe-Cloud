package com.drassapps.archdeal;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stripe.android.PaymentResultListener;
import com.stripe.android.Stripe;
import com.stripe.android.view.CardInputWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;

public class StripeProjectMain extends Activity {

    // MARK - CLASS
    private ArchDealService archDealService;

    // MARK - UI
    private RecyclerView cards;
    private TextView userText1, userText2;
    private EditText userNameCharge, userAmuntCharge;
    private RelativeLayout addCharge, change, mainLay;
    private ArrayList<String> userData = new ArrayList<>();

    // MARK - MAIN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.stripeprojectmain);

        String email = getIntent().getStringExtra("email");
        String uid = getIntent().getStringExtra("uid");

        TextView userEmail = findViewById(R.id.stripemain_usertext);
        TextView userUid = findViewById(R.id.stripemain_useruid);
        mainLay = findViewById(R.id.stripemain_mainLay);
        userText1 = findViewById(R.id.stripemain_text);
        userText2 = findViewById(R.id.stripemain_text2);
        userNameCharge = findViewById(R.id.stripemain_newname);
        userAmuntCharge = findViewById(R.id.stripemain_amount);
        change = findViewById(R.id.stripemap_change);
        addCharge = findViewById(R.id.stripemain_addcharge);
        cards = findViewById(R.id.stripemain_recycler);

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mDatabaseRefUsuario = mDatabaseRef.child("/stripe_customers/"
                + uid + "/sources/" + "card_1CMyWCDh0ZSD87vDwWJ5Yu24");

        mDatabaseRefUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Obtenemos un objeto de nuestra BD
                StripeProjectCardObjec usu = dataSnapshot.getValue(StripeProjectCardObjec.class);

                if (usu != null) {
                    setUpCard(usu.brand, usu.address_zip, usu.last4);

                } else {
                    // Error no hay datos
                    Snackbar.make(mainLay,"Error al recoger los datos",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Snackbar.make(mainLay,"Error al recoger los datos",
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        userEmail.setText("Usuario: " + email);
        userUid.setText("UID: " + uid);

        addCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = userAmuntCharge.getText().toString();

                archDealService = CommonUtils.getAPIService();

                Call<ResponseBody> call = archDealService.createNewStripeCharge(Integer.parseInt(amount));

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Snackbar.make(mainLay,"Cargo realizado",Snackbar.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) { }
                });
            }
        });
    }

    // MARK - METHODS
    public void setUpCard(String brand, long cp, long lastfour) {

        if (brand.contains("Visa")) {
            String data = "****" + String.valueOf(lastfour);
            userData.add(data);
            userData.add(String.valueOf(cp));
            userData.add("visa");
        }

        StripeProjectAdapter sensorAdapter = new StripeProjectAdapter(this, userData);
        cards.setAdapter(sensorAdapter);
        cards.setLayoutManager(new GridLayoutManager(this, 1));
    }

    // MARK - ACTIONS
    public void changeView(View v) {
        change.setVisibility(View.GONE);
        cards.setVisibility(View.GONE);
        userText1.setVisibility(View.GONE);

        userText2.setVisibility(View.VISIBLE);
        addCharge.setVisibility(View.VISIBLE);
        userNameCharge.setVisibility(View.VISIBLE);
        userAmuntCharge.setVisibility(View.VISIBLE);
    }
}