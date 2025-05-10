package com.mandragora.orderingproducts;



import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Product> productList;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        productList = new ArrayList<>();

        // przykładowe dane na start
        productList.add(new Product(1, "Pomidory", 5, false));
        productList.add(new Product(2, "Mozzarella", 2, false));
        productList.add(new Product(3, "Salami", 1, false));

        adapter = new ProductAdapter(productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.buttonOrder).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            ArrayList<Product> selectedProducts = new ArrayList<>();
            for (Product product:productList) {
                if (product.isToOrder()) {
                    selectedProducts.add(product);
                }
            }
            intent.putExtra("ORDERED_PRODUCTS", selectedProducts);
            startActivity(intent);
        });
    }
}
