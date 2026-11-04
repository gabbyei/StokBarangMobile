package com.gabriela.stokbarangmobile.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gabriela.stokbarangmobile.R;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_product, parent, false);
        }

        Product product = getItem(position);

        TextView tvProductName =
                convertView.findViewById(R.id.tvProductName);

        TextView tvProductCode =
                convertView.findViewById(R.id.tvProductCode);

        TextView tvProductStock =
                convertView.findViewById(R.id.tvProductStock);

        TextView tvProductCategory =
                convertView.findViewById(R.id.tvProductCategory);

        if (product != null) {
            tvProductName.setText(product.getName());
            tvProductCode.setText("Kode: " + product.getCode());
            tvProductStock.setText(
                    "Stok: " + product.getStock() + " " + product.getUnit());
            tvProductCategory.setText(
                    "Kategori: " + product.getCategory());
        }

        return convertView;
    }
}
