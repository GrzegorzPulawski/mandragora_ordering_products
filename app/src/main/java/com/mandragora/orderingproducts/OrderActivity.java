package com.mandragora.orderingproducts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.recyclerView);

        ArrayList<Product> orderedProductsList =
                (ArrayList<Product>) getIntent().getSerializableExtra("ORDERED_PRODUCTS");

        if (orderedProductsList == null) {
            orderedProductsList = new ArrayList<>();
        }

        adapter = new ProductAdapter(orderedProductsList, false); // tylko do podglądu
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button buttonSenSMS = findViewById(R.id.buttonSendSms);
        ArrayList<Product> finalProductList = orderedProductsList;
        buttonSenSMS.setOnClickListener(v->{
            if (finalProductList.isEmpty()){
                Toast.makeText(this, "Brak produktów do zamówienia", Toast.LENGTH_SHORT).show();
            return;
            }
            StringBuilder smsBody = new StringBuilder("Zamówienie dla Mandragora Łeba:\n");
            for (Product product : finalProductList) {
                smsBody.append("- ")
                        .append(product.getName())
                        .append(" (")
                        .append(product.getQuantity())
                        .append(" ")
                        .append(product.getUnit())
                        .append(")\n");
            }
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:")); // <-- numer telefonu
                smsIntent.putExtra("sms_body", smsBody.toString());

                if (smsIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(smsIntent);


                } else {
                    Toast.makeText(this, "Brak aplikacji SMS", Toast.LENGTH_SHORT).show();
                }


        });
    }
}