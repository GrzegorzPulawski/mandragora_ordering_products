package com.mandragora.orderingproducts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> productList;
    private final boolean isSelectionMode;  // True for MainActivity, False for OrderActivity

    public ProductAdapter(List<Product> productList, boolean isSelectionMode) {
        this.productList = productList != null ? productList: new ArrayList<>();
        this.isSelectionMode = isSelectionMode;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Always update these views
        holder.textViewName.setText(product.getName());
        holder.textViewQuantity.setText("ilość: " + product.getQuantity() +" "+ product.getUnit());
        holder.textViewDepartment.setText("dział: " + product.getDepartment().getDisplayEnum());

        if (isSelectionMode) {
            // MainActivity Mode - show interactive elements
            holder.checkBox.setChecked(product.isToOrder());
            holder.addButton.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.decreaseButton.setVisibility(View.VISIBLE);

            // Checkbox handling
            holder.checkBox.setOnCheckedChangeListener(null); // Reset listener
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                product.setToOrder(isChecked);
            });

            // Quantity increment handling
            holder.addButton.setOnClickListener(v -> {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Product currentProduct = productList.get(currentPosition);
                    currentProduct.setQuantity(currentProduct.getQuantity() + 1);
                    notifyItemChanged(currentPosition);
                }
            });
            holder.decreaseButton.setOnClickListener(v ->{
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Product currentProduct = productList.get(currentPosition);
                    currentProduct.setQuantity(currentProduct.getQuantity() - 1);
                    notifyItemChanged((currentPosition));
                }
            });
        } else {
            // OrderActivity Mode - hide interactive elements
            holder.checkBox.setVisibility(View.GONE);
            holder.addButton.setVisibility(View.GONE);
            holder.decreaseButton.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size(): 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewName;
        final TextView textViewQuantity;
        final CheckBox checkBox;
        final Button addButton;
        final Button decreaseButton;
        final TextView textViewDepartment;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewDepartment = itemView.findViewById(R.id.textViewDepartment);
            checkBox = itemView.findViewById(R.id.checkBoxSelect);
            addButton = itemView.findViewById(R.id.buttonIncrease);
            decreaseButton = itemView.findViewById(R.id.buttonDecrease);
        }
    }
}