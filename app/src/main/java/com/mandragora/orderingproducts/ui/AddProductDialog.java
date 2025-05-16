package com.mandragora.orderingproducts.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mandragora.orderingproducts.Product;
import com.mandragora.orderingproducts.R;

import java.util.Arrays;

public class AddProductDialog {
    public interface OnProductListener{
         void onProductAdded();
    }

    public static void showAddProductDialog(Context context, Runnable onProductAdded) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Dodaj produkt");

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_product, null);
        builder.setView(dialogView);

        EditText nameInput = dialogView.findViewById(R.id.edit_text_name);
        EditText quantityInput = dialogView.findViewById(R.id.edit_text_quantity);
        Spinner unitSpinner = dialogView.findViewById(R.id.spinner_unit);
        Spinner departmentSpinner = dialogView.findViewById(R.id.spinner_department);

        unitSpinner.setAdapter(new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item,
                Unit.values()));

        departmentSpinner.setAdapter(new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                Department.values()
        ));

        builder.setPositiveButton("Dodaj", null); // ustaw null, by potem nadpisać listener
        builder.setNegativeButton("Anuluj", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(d -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                String name = nameInput.getText().toString().trim();
                String quantityString = quantityInput.getText().toString().trim();
                Unit unit = (Unit) unitSpinner.getSelectedItem();
                Department department =(Department) departmentSpinner.getSelectedItem();

                if (name.isEmpty() || quantityString.isEmpty()) {
                    Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    float quantity = Float.parseFloat(quantityString);
                    Product product = new Product(name, quantity, unit, department);
                    AppDatabase.getInstance(context).productDao().insert(product);
                    Toast.makeText(context, "Dodano produkt", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    onProductAdded.run(); // odśwież listę
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Nieprawidłowa ilość", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }


}
