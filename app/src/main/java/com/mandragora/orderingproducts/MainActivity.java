package com.mandragora.orderingproducts;



import static com.mandragora.orderingproducts.ui.Unit.KILOGRAMY;
import static com.mandragora.orderingproducts.ui.Unit.LITRY;
import static com.mandragora.orderingproducts.ui.Unit.OPAKOWANIE;
import static com.mandragora.orderingproducts.ui.Unit.SZTUKI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;

import com.mandragora.orderingproducts.ui.AddProductDialog;
import com.mandragora.orderingproducts.ui.AppDatabase;
import com.mandragora.orderingproducts.ui.Department;
import com.mandragora.orderingproducts.ui.DepartmentFilterHelper;
import com.mandragora.orderingproducts.ui.Unit;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Product> productList;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    AppDatabase db;
    Spinner spinnerFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        db = AppDatabase.getInstance(getApplicationContext());
        insertDataIfEmpty(db);

        setupProductList();
        adapter = new ProductAdapter(productList, true);
        setupRecyclerView();
        setupOrderButton();
        setupAddProductButton();
        setupDepartmentFilter();

    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(productList, true); // true = selection mode
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private void setupDepartmentFilter() {
        spinnerFilter = findViewById(R.id.spinner_filter_department);
        spinnerFilter.setBackgroundResource(R.drawable.spinner_background);

        DepartmentFilterHelper.setupDepartmentFilterSpinner(
                this,
                spinnerFilter,
                filteredProducts -> {
                    productList.clear();
                    productList.addAll(filteredProducts);
                    adapter.notifyDataSetChanged();
                }
        );
    }

    private void setupOrderButton() {
        findViewById(R.id.buttonOrder).setOnClickListener(v -> {
            ArrayList<Product> selectedProducts = getSelectedProducts();

            if (selectedProducts.isEmpty()) {
                showEmptySelectionWarning();
            } else {
                startOrderActivity(selectedProducts);
            }
        });
    }
    private ArrayList<Product> getSelectedProducts() {
        ArrayList<Product> selected = new ArrayList<>();
        for (Product product : productList) {
            if (product.isToOrder()) {
                selected.add(product);
            }
        }
        return selected;
    }
    private void showEmptySelectionWarning() {
        Toast.makeText(this, "Nie wybrano produktów do zamówienia!", Toast.LENGTH_SHORT).show();
    }
    private void startOrderActivity(ArrayList<Product> selectedProducts) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("ORDERED_PRODUCTS", selectedProducts);
        startActivity(intent);
    }
    private void setupProductList() {
        productList = new ArrayList<>();
        // Pobranie produktów z bazy
        productList.addAll(db.productDao().getAll());
    }
    private void insertDataIfEmpty(AppDatabase db){
        if (db.productDao().getAll().isEmpty()){
            Log.d("DB_INIT", "Liczba produktów w bazie: " + db.productDao().getAll().size());

            db.productDao().insertAll(
                    new Product("Mleko", 1.0f, LITRY, Department.NABIAL),
                    new Product("Mieso mielone", 1.0f, KILOGRAMY,Department.MIESO ),
                    new Product("Pepsi", 2.0f, OPAKOWANIE, Department.BAR)
            );
        }
    }
    private void setupAddProductButton() {
        findViewById(R.id.buttonAddProduct).setOnClickListener(v -> AddProductDialog.showAddProductDialog(this, () -> {
            productList.clear();
            productList.addAll(AppDatabase.getInstance(this).productDao().getAll());
            adapter.notifyDataSetChanged();
        }));
    }

}
