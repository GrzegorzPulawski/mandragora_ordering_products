package com.mandragora.orderingproducts;



import static com.mandragora.orderingproducts.ui.Unit.KILOGRAMY;
import static com.mandragora.orderingproducts.ui.Unit.LITRY;
import static com.mandragora.orderingproducts.ui.Unit.OPAKOWANIE;
import static com.mandragora.orderingproducts.ui.Unit.SZTUKI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mandragora.orderingproducts.ui.AppDatabase;
import com.mandragora.orderingproducts.ui.Department;
import com.mandragora.orderingproducts.ui.DepartmentFilterHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ProductDeleteDialogFragment.ProductDeletedListener{
    private ArrayList<Product> productList;
    private Department currentFilterDepartment = null;

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
        setupDepartmentFilter();
        setupAddProductButton();


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
                    currentFilterDepartment = spinnerFilter.getSelectedItemPosition() == 0
                            ? null  // "Wszystkie działy"
                            : Department.values()[spinnerFilter.getSelectedItemPosition() - 1];

                    Map<Integer, Boolean> selectionMap = new HashMap<>();
                    for (Product p : productList) {
                        selectionMap.put(p.getId(), p.isToOrder());
                    }
                    productList.clear();
                    for (Product filtered : filteredProducts) {
                        Boolean wasSelected = selectionMap.get(filtered.getId());
                        if (wasSelected != null) {
                            filtered.setToOrder(wasSelected);
                        }
                        productList.add(filtered);
                    }

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
        Department selectedDepartment = DepartmentFilterHelper.getCurrentSelectedDepartment(spinnerFilter);
        productList.clear();

        if (selectedDepartment == null) {
            productList.addAll(db.productDao().getAll());
        } else {
            productList.addAll(db.productDao().getByDepartment(currentFilterDepartment));
        }
    }

    private void insertDataIfEmpty(AppDatabase db){
        if (db.productDao().getAll().isEmpty()){
            Log.d("DB_INIT", "Liczba produktów w bazie: " + db.productDao().getAll().size());

            db.productDao().insertAll(
                    new Product("Krewetki", 1.0f, SZTUKI, Department.RYBY),
                    new Product("Dorsz", 1.0f, OPAKOWANIE,Department.RYBY ),
                    new Product("Łosoś", 1.0f, SZTUKI, Department.RYBY),
                    new Product("Truskawki", 1.0f,SZTUKI,Department.OWOCE),
                    new Product("Jabłka", 1.0f, KILOGRAMY, Department.OWOCE),
                    new Product("Banany", 1.0f, SZTUKI, Department.OWOCE),
                    new Product("Arbuz", 1.0f, SZTUKI,Department.OWOCE),
                    new Product("Borówki", 1.0f, SZTUKI, Department.OWOCE),
                    new Product("Limonki", 1.0f, SZTUKI, Department.OWOCE),
                    new Product("Pomarańcza", 1.0f, SZTUKI,Department.OWOCE),
                    new Product("Cytryny", 1.0f, SZTUKI, Department.OWOCE),
                    new Product("Winogron", 1.0f,KILOGRAMY, Department.OWOCE),
                    new Product("Sałata do burgera", 1.0f, SZTUKI, Department.WARZYWA),
                    new Product("Szpiank", 1.0f, SZTUKI,Department.WARZYWA),
                    new Product("Cebula", 1.0f, KILOGRAMY, Department.WARZYWA),
                    new Product("Pomidory",1.0f, KILOGRAMY, Department.WARZYWA),
                    new Product("Sałata lodowa", 1.0f, SZTUKI, Department.WARZYWA),
                    new Product("Pietrucha", 1.0f, SZTUKI,Department.WARZYWA),
                    new Product("Marchew", 1.0f, KILOGRAMY, Department.WARZYWA),
                    new Product("Czosnek", 1.0f, SZTUKI, Department.WARZYWA),
                    new Product("Papryka", 1.0f, SZTUKI,Department.WARZYWA),
                    new Product("Cukinia",1.0f, SZTUKI,Department.WARZYWA),
                    new Product("Czerwona cebula", 1.0f, KILOGRAMY,Department.WARZYWA),
                    new Product("Por", 1.0f, SZTUKI, Department.WARZYWA),
                    new Product("Seler", 1.0f, SZTUKI, Department.WARZYWA),
                    new Product("Rukola", 1.0f, SZTUKI, Department.WARZYWA),
                    new Product("Ziemniaki", 5.0f,KILOGRAMY,Department.WARZYWA),
                    new Product("Natka pietruszki", 1.0f, SZTUKI,Department.WARZYWA),
                    new Product("Szczypiorek", 1.0f, SZTUKI, Department.WARZYWA),
                    new Product("Ogórek kiszony", 1.0f, OPAKOWANIE, Department.WARZYWA),
                    new Product("Biała kapusta", 1.0f, SZTUKI,Department.WARZYWA),
                    new Product("Frytki z batata", 1.0f, OPAKOWANIE, Department.MROZONKI),
                    new Product("Frytki normalne", 1.0f, OPAKOWANIE, Department.MROZONKI),
                    new Product("Frytki Steak House", 1.0f, OPAKOWANIE, Department.MROZONKI),
                    new Product("Lód do drinków", 1.0f, KILOGRAMY, Department.MROZONKI),
                    new Product("Lody waniliowe", 1.0f, SZTUKI,Department.MROZONKI),
                    new Product("Mrożone bagietki", 1.0f, OPAKOWANIE,Department.MROZONKI),
                    new Product("Frużelina malinowa", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Frużelina jagodowa", 1.0f, SZTUKI,Department.SYPKIE),
                    new Product("Frużelina truskawka", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Pomidory Pelati puszka 2,5kg", 1.0f,SZTUKI, Department.SYPKIE),
                    new Product("Mleko 3,2%", 1.0f, OPAKOWANIE, Department.SYPKIE),
                    new Product("Pomidory krojone puszka 2,5kg", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Mleko owsiane", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Pikle słoik", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Śmietana Debic sprey", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Jogurt naturalny 1l", 1.0f, LITRY, Department.NABIAL),
                    new Product("Maka kukurydziana", 1.0f, KILOGRAMY, Department.SYPKIE),
                    new Product("Cukier waniliowy 1kg", 1.0f, KILOGRAMY,Department.SYPKIE),
                    new Product("Słonecznik łuskany", 1.0f, KILOGRAMY,Department.SYPKIE),
                    new Product("Oliwki zielone słoik", 1.0f, LITRY,Department.SYPKIE),
                    new Product("Makaron do rosołu 2kg", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Nutella 3kg", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Cukier puder 0,5kg", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Oliwa z oliwek", 1.0f,LITRY, Department.SYPKIE),
                    new Product("Olej rzepakowy 5l.", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Kukurydza 400g", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Majonez wiadro", 1.0f, SZTUKI,Department.SYPKIE),
                    new Product("Suszone pomidory paski", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Suszona żurawina 1kg", 1.0f, SZTUKI,Department.SYPKIE),
                    new Product("Czerwona fasola 400g", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Buraczki wiórki", 1.0f, LITRY, Department.SYPKIE),
                    new Product("Ketchup 1l", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Ocet", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Musztarda francuska 200g", 1.0f, SZTUKI,Department.SYPKIE),
                    new Product("Sriracha", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("BBQ", 1.0f, SZTUKI, Department.SYPKIE),
                    new Product("Mąka tortowa", 1.0f, KILOGRAMY,Department.SYPKIE),
                    new Product("Polewa czekoladowa", 1.0f, LITRY, Department.SYPKIE),
                    new Product("Polewa malinowa", 1.0f, LITRY, Department.SYPKIE),
                    new Product("Polewa truskawkowa", 1.0f, LITRY, Department.SYPKIE),
                    new Product("JAJKA 30szt.", 1.0f,  OPAKOWANIE, Department.SYPKIE),
                    new Product("Bułka tarta 1kg", 1.0f, KILOGRAMY, Department.PIECZYWO),
                    new Product("Buła burger", 1.0f, SZTUKI, Department.PIECZYWO),
                    new Product("Buła szwedka", 1.0f, SZTUKI,Department.PIECZYWO),
                    new Product("Karkówka na grill", 1.0f, SZTUKI, Department.MIESO),
                    new Product("Schab", 1.0f, KILOGRAMY, Department.MIESO),
                    new Product("Fiet z kurczaka", 1.0f, KILOGRAMY, Department.MIESO),
                    new Product("Rosłowe", 1.0f, SZTUKI, Department.MIESO),
                    new Product("Parówki berlinki", 1.0f, SZTUKI, Department.MIESO),
                    new Product("Szynka gotowana plastry 0,5kg", 1.0f, SZTUKI, Department.MIESO),
                    new Product("Boczek plastry 0,5kg", 1.0f, SZTUKI,Department.MIESO),
                    new Product("Łopatka wieprzowa", 1.0f, SZTUKI, Department.MIESO),
                    new Product("Łopatka WOŁOWA!", 1.0f, KILOGRAMY, Department.MIESO),
                    new Product("Goleń WOŁOWA!", 1.0f, KILOGRAMY, Department.MIESO),
                    new Product("Rozbratel WOŁOWY!", 1.0f, KILOGRAMY, Department.MIESO),
                    new Product("Rosołowe z kaczki", 1.0f, SZTUKI, Department.MIESO),
                    new Product("Camembert", 5.0f, SZTUKI, Department.NABIAL),
                    new Product("Twaróg sernikowy", 1.0f, SZTUKI, Department.NABIAL),
                    new Product("Ser cheddar plastry", 1.0f, KILOGRAMY,Department.NABIAL),
                    new Product("Feta wiadro", 1.0f, SZTUKI, Department.NABIAL),
                    new Product("Mascarpone 1kg", 1.0f,SZTUKI,Department.NABIAL),
                    new Product("Ser żółty plastry", 1.0f, KILOGRAMY, Department.NABIAL),
                    new Product("Masło", 1.0f, SZTUKI, Department.NABIAL),
                    new Product("Mozzarella kula", 5.0f, SZTUKI, Department.NABIAL),
                    new Product("Śmietana UHT", 1.0f, SZTUKI, Department.NABIAL),
                    new Product("Baza do lemoniady", 1.0f, SZTUKI, Department.BAR),
                    new Product("Syrop malionowy 5l.", 1.0f, SZTUKI, Department.BAR),
                    new Product("Syrop do lemoniady", 1.0f, LITRY, Department.BAR),
                    new Product("Herbata czarna", 1.0f, OPAKOWANIE, Department.BAR),
                    new Product("Herbata mix", 1.0f, OPAKOWANIE,Department.BAR),
                    new Product("Syrop Karmelowy", 1.0f, LITRY, Department.BAR),
                    new Product("Syrop Waniliowy", 1.0f, LITRY, Department.BAR),
                    new Product("Syrop orzechowy", 1.0f, LITRY, Department.BAR),
                    new Product("Kawa", 1.0f, KILOGRAMY, Department.BAR),
                    new Product("Prosseco półwytrawne", 1.0f, SZTUKI, Department.ALKOHOL),
                    new Product("Cabernet Sauvignon", 1.0f, SZTUKI, Department.ALKOHOL),
                    new Product("Merlot", 1.0f, SZTUKI, Department.ALKOHOL),
                    new Product("Pinot Girgio", 1.0f, SZTUKI, Department.ALKOHOL),
                    new Product("Chardonnay", 1.0f, SZTUKI, Department.ALKOHOL),
                    new Product("Aperol", 1.0f, SZTUKI,Department.ALKOHOL),
                    new Product("Lech free", 1.0f, OPAKOWANIE, Department.ALKOHOL),
                    new Product("Lech free limonka", 1.0f, OPAKOWANIE, Department.ALKOHOL),
                    new Product("Książęce ciemne łagodne", 1.0f, OPAKOWANIE, Department.ALKOHOL),
                    new Product("Książęce złote pszeniczne", 1.0f, SZTUKI, Department.ALKOHOL),
                    new Product("Pilsner Urquell", 1.0f, OPAKOWANIE, Department.ALKOHOL),
                    new Product("Lech Pils", 1.0f, OPAKOWANIE, Department.ALKOHOL),
                    new Product("Kozel jasny KEG 30l", 1.0f, SZTUKI, Department.ALKOHOL),
                    new Product("Hard made", 1.0f,OPAKOWANIE, Department.ALKOHOL ),
                    new Product("Pepsi 0,5l", 1.0f, OPAKOWANIE,Department.PEPSI),
                    new Product("Pepsi max 0,5l", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Mirinda", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("7UP", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Ice tea brzoskwinia", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Ice tea cytryna", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Ice tea zielony", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Woda gazowana 0.5l", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Woda niegazowana 0.5l", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Woda gazowana 1.5l", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Woda niegazowana 1.5l", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Sok jabłkowy", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Sok pomarańczowy", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Sok jagodowy", 1.0f, OPAKOWANIE, Department.PEPSI),
                    new Product("Papier toaletowy", 1.0f, OPAKOWANIE, Department.OPAKOWANIA),
                    new Product("Recznik papierowy", 1.0f, OPAKOWANIE, Department.OPAKOWANIA),
                    new Product("Folia aluminiowa", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Strech 30cm", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Serwetki", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Płyn do naczyń 5l", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Mydło 5l", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Płyn do podłogi 5l", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Płyn do szyb 1l", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Płyn odtłuszczający 5l", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Worki na śmieci 60l", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Worki na śmieci 90l", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Worki na śmieci 120l", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Box na wynos", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Patyczki burger", 1.0f, OPAKOWANIE, Department.OPAKOWANIA),
                    new Product("Torba papierowa", 1.0f, OPAKOWANIE, Department.OPAKOWANIA),
                    new Product("Torebki foliowe 1000szt", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Rękawiczki nitrylowe", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Rolki do drukarki fisk.", 1.0f, OPAKOWANIE, Department.OPAKOWANIA),
                    new Product("Rolki do terminala", 1.0f, OPAKOWANIE, Department.OPAKOWANIA),
                    new Product("Rolki do bonownika", 1.0f, OPAKOWANIE, Department.OPAKOWANIA),
                    new Product("Słomki do drinków", 1.0f, OPAKOWANIE, Department.OPAKOWANIA),
                    new Product("Łyżka i widelec", 1.0f, OPAKOWANIE, Department.OPAKOWANIA),
                    new Product("Ścierki uniwersalne", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Gąbki do teflonu", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Druciak metalowy", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Mleczko CIF", 1.0f, SZTUKI, Department.OPAKOWANIA),
                    new Product("Płyn do toalety", 1.0f, SZTUKI, Department.OPAKOWANIA)

            );
        }
    }
    private void setupAddProductButton() {
        findViewById(R.id.buttonAddProduct).setOnClickListener(v ->
                AddProductDialog.showAddProductDialog(this, this::refreshFilteredProductList)
        );
    }


    @Override
    public void onProductDeleted() {
        spinnerFilter.setSelection(spinnerFilter.getSelectedItemPosition());
        refreshFilteredProductList();

    }
    private void refreshFilteredProductList() {
        Department selected = DepartmentFilterHelper.getCurrentSelectedDepartment(spinnerFilter);
        List<Product> filtered = (selected == Department.All || selected == null)
                ? db.productDao().getAll()
                : db.productDao().getByDepartment(selected);

        Map<Integer, Boolean> selectionMap = new HashMap<>();
        for (Product p : productList) {
            selectionMap.put(p.getId(), p.isToOrder());
        }

        productList.clear();
        for (Product filteredItem : filtered) {
            Boolean wasSelected = selectionMap.get(filteredItem.getId());
            if (wasSelected != null) {
                filteredItem.setToOrder(wasSelected);
            }
            productList.add(filteredItem);
        }

        adapter.notifyDataSetChanged();
    }


}
