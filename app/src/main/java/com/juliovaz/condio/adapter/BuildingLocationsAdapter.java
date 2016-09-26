package com.juliovaz.condio.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.juliovaz.condio.activity.BuildingLocationDetailActivity;
import com.juliovaz.condio.model.BuildingLocation;


import java.util.ArrayList;

/**
 * Created by juliovaz on 8/2/16.
 */
public class BuildingLocationsAdapter extends RecyclerView.Adapter<BuildingLocationsAdapter.ViewHolder> {

    private ArrayList<BuildingLocation> listBuildingLocations = new ArrayList<>();
    private Context context;
    private Drawable[] mPlacePictures;

    public BuildingLocationsAdapter(Context context, ArrayList<BuildingLocation> listBuildingLocations) {
        this.context = context;
        this.listBuildingLocations = listBuildingLocations;

        Resources resources = context.getResources();
        TypedArray a = resources.obtainTypedArray(R.array.places_picture);
        mPlacePictures = new Drawable[a.length()];
        for (int i = 0; i < mPlacePictures.length; i++) {
            mPlacePictures[i] = a.getDrawable(i);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tile, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BuildingLocation buildingLocation = listBuildingLocations.get(position);
        if (buildingLocation != null) {
            holder.picture.setImageDrawable(mPlacePictures[buildingLocation.getId() - 1]);
            holder.name.setText(buildingLocation.getName());
            holder.description = buildingLocation.getDescription();
            holder.createdAt = buildingLocation.getCreatedAt();
            holder.id = buildingLocation.getId();
        }
    }

    @Override
    public int getItemCount() {
        return listBuildingLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public ImageView picture;
        public String createdAt;
        public TextView name;
        public String description;
        public int id;

        public ViewHolder(View itemView) {
            super(itemView);

            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
            name = (TextView) itemView.findViewById(R.id.tile_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, BuildingLocationDetailActivity.class);
                    intent.putExtra("BUILDING_LOCATION_ID", id);
                    intent.putExtra("BUILDING_LOCATION_NAME", name.getText());
                    intent.putExtra("BUILDING_LOCATION_PLACE_INDEX", id - 1);
                    context.startActivity(intent);
                }
            });
        }
    }
}
