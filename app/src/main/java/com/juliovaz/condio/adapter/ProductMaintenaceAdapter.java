package com.juliovaz.condio.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juliovaz.condio.R;
import com.juliovaz.condio.model.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by juliovaz on 10/3/16.
 */
public class ProductMaintenaceAdapter extends RecyclerView.Adapter<ProductMaintenaceAdapter.ViewHolder> {

    private ArrayList<Product> listProductMaintenance = new ArrayList<>();
    private Context context;
    private Drawable[] mBoardPicture;
    private final Drawable[] mPlaceAvators;

    public ProductMaintenaceAdapter(Context context, ArrayList<Product> listProductMaintenance) {
        this.context = context;
        this.listProductMaintenance = listProductMaintenance;
        Resources resources = context.getResources();
        TypedArray a = resources.obtainTypedArray(R.array.place_avator);
        mPlaceAvators = new Drawable[a.length()];
        for (int i = 0; i < mPlaceAvators.length; i++) {
            mPlaceAvators[i] = a.getDrawable(i);
        }
        a.recycle();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = listProductMaintenance.get(position);
        if (product != null) {
            holder.avator.setImageDrawable(mPlaceAvators[position % mPlaceAvators.length]);
            holder.name.setText(product.getName());
            holder.description.setText("Manutenção agendada para a data " + product.getMaintenanceDateFormatted());
        }
    }

    @Override
    public int getItemCount() {
        return listProductMaintenance.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avator;
        public TextView name;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);

        }
    }
}
