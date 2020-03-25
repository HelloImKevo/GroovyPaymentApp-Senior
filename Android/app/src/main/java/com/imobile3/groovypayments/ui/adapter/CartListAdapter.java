package com.imobile3.groovypayments.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.rules.CartRules;
import com.imobile3.groovypayments.utils.StateListHelper;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartListAdapter
        extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private Context mContext;
    private AdapterCallback mCallbacks;
    private List<Cart> mItems;

    public interface AdapterCallback {

        void onCartClick(Cart cart);
    }

    public CartListAdapter(
            @NonNull Context context,
            @NonNull List<Cart> carts,
            @NonNull AdapterCallback callback) {
        mContext = context;
        mCallbacks = callback;
        mItems = carts;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.cart_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cart item = mItems.get(position);
        CartRules rules = new CartRules(item);

        // Configure the icon and background circle.
        holder.icon.setImageResource(GroovyIcon.Bookmarklet.drawableRes);
        holder.icon.setBackground(
                ContextCompat.getDrawable(mContext, GroovyColor.Orange.colorRes));

        holder.description.setText(rules.getOrderHistoryDescription());
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
        TextView labelTotal, labelDate, description;

        ViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            icon = itemView.findViewById(R.id.icon);
            labelTotal = itemView.findViewById(R.id.label_total);
            labelDate = itemView.findViewById(R.id.label_date);
            description = itemView.findViewById(R.id.description);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == container) {
                mCallbacks.onCartClick(mItems.get(getAdapterPosition()));
            }
        }
    }

    public List<Cart> getItems() {
        return mItems;
    }

    public void setItems(@Nullable List<Cart> items) {
        mItems = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }
}
