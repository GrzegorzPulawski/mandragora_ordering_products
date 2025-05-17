package com.mandragora.orderingproducts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mandragora.orderingproducts.ui.AppDatabase;

public class ProductDeleteDialogFragment extends DialogFragment {
    private static final String ARG_PRODUCT_ID = "product_id";
    private static final String ARG_PRODUCT_NAME = "product_name";

    public static ProductDeleteDialogFragment newInstance(int productId, String productName){
        ProductDeleteDialogFragment fragment = new ProductDeleteDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT_ID, productId);
        args.putString(ARG_PRODUCT_NAME, productName);
        fragment.setArguments(args);
    return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int productId = getArguments().getInt(ARG_PRODUCT_ID);
        String productName = getArguments().getString(ARG_PRODUCT_NAME);

        return new AlertDialog.Builder(requireContext())
                .setTitle("Usuń produkt")
                .setMessage("Czy na pewno chcesz usunąć produkt \"" + productName + "\"?")
                .setPositiveButton("Usuń", (dialog, which) -> {
                    AppDatabase.getInstance(requireContext())
                            .productDao()
                            .deleteById(productId);
                    if (getActivity() instanceof ProductDeletedListener) {
                        ((ProductDeletedListener) getActivity()).onProductDeleted();
                    }
                })
                .setNegativeButton("Anuluj", null)
                .create();
    }
    public interface ProductDeletedListener {
        void onProductDeleted();
    }
}
