package com.imobile3.groovypayments.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.model.Product;
import com.imobile3.groovypayments.utils.StateListHelper;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter
        extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private Context mContext;
    private AdapterCallback mCallbacks;
    private List<Product> mItems;

    public interface AdapterCallback {

        void onProductClick(Product product);
    }

    public ProductListAdapter(
            @NonNull Context context,
            @NonNull List<Product> products,
            @NonNull AdapterCallback callback) {
        mContext = context;
        mCallbacks = callback;
        mItems = products;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO: Update the look and feel of product list items
        Product button = mItems.get(position);
        // Primary list item container pressed / default state.
        holder.container.setBackground(
                StateListHelper.getBgColorSelector(mContext, R.color.orange_california));

        // Configure the icon and background circle.
        holder.icon.setImageResource(R.drawable.ic_person);
        holder.icon.setBackground(
                ContextCompat.getDrawable(mContext, R.drawable.dashboard_icon_bg_red));

        // Configure label and description.
        holder.label.setText(button.getName());
        holder.label.setTextColor(
                StateListHelper.getTextColorSelector(mContext, R.color.black_space));
        holder.description.setText(mContext.getString(R.string.placeholder_description));
        holder.description.setTextColor(
                StateListHelper.getTextColorSelector(mContext, R.color.gray_down_pour));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ViewGroup container;
        ImageView icon;
        TextView label, description;

        ViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            icon = itemView.findViewById(R.id.icon);
            label = itemView.findViewById(R.id.label);
            description = itemView.findViewById(R.id.description);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == container) {
                mCallbacks.onProductClick(mItems.get(getAdapterPosition()));
            }
        }
    }

    public List<Product> getItems() {
        return mItems;
    }

    public void setItems(@Nullable List<Product> items) {
        mItems = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }
}
