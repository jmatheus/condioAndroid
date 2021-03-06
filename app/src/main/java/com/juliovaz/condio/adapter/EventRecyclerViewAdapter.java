package com.juliovaz.condio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juliovaz.condio.R;
import com.juliovaz.condio.activity.ReservationDetailActivity;
import com.juliovaz.condio.model.Reservation;
import com.juliovaz.condio.util.ConstantsCondio;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Reservation} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {

    private final List<Reservation> mValues;
    private final Context context;
    private boolean reservationHistory = false;

    public EventRecyclerViewAdapter(Context context, List<Reservation> items) {
        this.context = context;
        this.mValues = items;
    }

    public EventRecyclerViewAdapter(Context context, List<Reservation> items, boolean reservationHistory) {
        this.context = context;
        this.mValues = items;
        this.reservationHistory = reservationHistory;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getEventDate());
        holder.mContentView.setText(mValues.get(position).getBuildingLocation().getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ReservationDetailActivity.class);
                intent.putExtra(ConstantsCondio.RESERVATION_ID, mValues.get(position).getId());
                intent.putExtra(ConstantsCondio.BUILDING_LOCATION_ID, mValues.get(position).getBuildingLocation().getId() - 1);

                if (reservationHistory) {
                    intent.putExtra(ConstantsCondio.RESERVATION_HISTORY_FLAG, reservationHistory);
                }

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Reservation mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.txEventDate);
            mContentView = (TextView) view.findViewById(R.id.txLocationName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
