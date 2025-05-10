package com.mandragora.orderingproducts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewQuantity;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
        }
    }

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
    }
    public void onBindViewHolder(ProductViewHolder holder, int position){
        Product currentProduct = productList.get(position);
        holder.textViewName.setText(currentProduct.getName());
        holder.textViewQuantity.setText("ilość" + currentProduct.getQuantity());
    }
    public int getItemCount(){
        return productList.size();
    }
}
