package com.imobile3.groovypayments.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.model.PaymentType;
import com.imobile3.groovypayments.utils.StateListHelper;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PaymentTypeListAdapter
        extends RecyclerView.Adapter<PaymentTypeListAdapter.ViewHolder> {

    private Context mContext;
    private AdapterCallback mCallbacks;
    private List<PaymentType> mItems;

    public interface AdapterCallback {

        void onPaymentTypeClick(PaymentType paymentType);
    }

    public PaymentTypeListAdapter(
            @NonNull Context context,
            @NonNull List<PaymentType> products,
            @NonNull AdapterCallback callback) {
        mContext = context;
        mCallbacks = callback;
        mItems = products;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.payment_type_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PaymentType item = mItems.get(position);

        // Primary list item container pressed / default state.
        holder.container.setBackground(
                StateListHelper.getBgColorSelector(mContext, item.getColor().colorRes));

        // Configure the icon and background.
        holder.icon.setImageResource(item.getIcon().drawableRes);
        holder.icon.setBackground(
                ContextCompat.getDrawable(mContext, item.getColor().colorRes));

        // Configure label.
        holder.label.setText(item.getName());
        holder.label.setTextColor(
                StateListHelper.getTextColorSelector(mContext, R.color.black_space));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ViewGroup container;
        ImageView icon;
        TextView label;

        ViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            icon = itemView.findViewById(R.id.icon);
            label = itemView.findViewById(R.id.label);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == container) {
                mCallbacks.onPaymentTypeClick(mItems.get(getAdapterPosition()));
            }
        }
    }

    public List<PaymentType> getItems() {
        return mItems;
    }

    public void setItems(@Nullable List<PaymentType> items) {
        mItems = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }
}
