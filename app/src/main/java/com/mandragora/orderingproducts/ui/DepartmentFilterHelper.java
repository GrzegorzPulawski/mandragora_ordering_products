package com.mandragora.orderingproducts.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mandragora.orderingproducts.Product;
import com.mandragora.orderingproducts.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class DepartmentFilterHelper {

    public static void setupDepartmentFilterSpinner(Context context, Spinner spinner, Consumer<List<Product>> onFiltered) {
        ArrayAdapter<Department> adapter = new ArrayAdapter<Department>(
                context,
                R.layout.custom_spinner_item,
                Arrays.asList(Department.values())
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(getItem(position).getDisplayEnum());
                return view;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(getItem(position).getDisplayEnum());
                return view;
            }
        };



        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Department selected = (Department) parent.getItemAtPosition(position);
                List<Product> filtered = selected == Department.All
                        ? AppDatabase.getInstance(context).productDao().getAll()
                        : AppDatabase.getInstance(context).productDao().getByDepartment(selected);
                onFiltered.accept(filtered);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nie rób nic
            }
        });
    }
}
