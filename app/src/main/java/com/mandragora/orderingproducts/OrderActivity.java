package com.mandragora.orderingproducts;

import android.os.Bundle;

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
    }
}