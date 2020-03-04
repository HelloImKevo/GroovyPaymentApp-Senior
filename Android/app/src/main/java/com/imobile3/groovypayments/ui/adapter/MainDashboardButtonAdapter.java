package com.imobile3.groovypayments.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.utils.StateListHelper;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainDashboardButtonAdapter
        extends RecyclerView.Adapter<MainDashboardButtonAdapter.ViewHolder> {

    private Context mContext;
    private AdapterCallback mCallbacks;
    private List<MainDashboardButton> mButtons;

    public interface AdapterCallback {

        void onButtonClick(MainDashboardButton button);
    }

    public MainDashboardButtonAdapter(
            @NonNull Context context,
            @NonNull List<MainDashboardButton> buttons,
            @NonNull AdapterCallback callback) {
        mContext = context;
        mCallbacks = callback;
        mButtons = buttons;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.main_dashboard_button_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainDashboardButton button = mButtons.get(position);
        // Primary list item container pressed / default state.
        holder.container.setBackground(
                StateListHelper.getBgColorSelector(mContext, button.colorResource));

        // Configure the icon and background circle.
        holder.icon.setImageResource(button.iconResource);
        holder.icon.setBackground(
                ContextCompat.getDrawable(mContext, button.backgroundResource));

        // Configure label and description.
        holder.label.setText(mContext.getString(button.labelResource));
        holder.label.setTextColor(
                StateListHelper.getTextColorSelector(mContext, button.colorResource));
        holder.description.setText(mContext.getString(button.descriptionResource));
        holder.description.setTextColor(
                StateListHelper.getTextColorSelector(mContext, button.colorResource));
    }

    @Override
    public int getItemCount() {
        return mButtons.size();
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
                mCallbacks.onButtonClick(mButtons.get(getAdapterPosition()));
            }
        }
    }

    public List<MainDashboardButton> getButtons() {
        return mButtons;
    }
}
