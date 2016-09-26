package com.juliovaz.condio.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.juliovaz.condio.R;
import com.juliovaz.condio.adapter.BuildingLocationsAdapter;
import com.juliovaz.condio.model.BuildingLocation;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewReservationFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<BuildingLocation> listBuildingLocations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        listBuildingLocations = new ArrayList<>();
        initComponents();

        return recyclerView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_reservation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initComponents() {

        getAllBuildingLocations();

    }

    private void getAllBuildingLocations() {
        ApiService service = ApiMethodsManager.getMethodGetService();


        service.getAllBuildingLocations(new Callback<ArrayList<BuildingLocation>>() {
            @Override
            public void success(ArrayList<BuildingLocation> buildingLocations, Response response) {

                if (response.getStatus() == 200) {
                    if (buildingLocations == null) {
                        listBuildingLocations = new ArrayList<>();
                    } else {
                        listBuildingLocations = buildingLocations;
                    }

                    BuildingLocationsAdapter buildingLocationsAdapter = new BuildingLocationsAdapter(recyclerView.getContext(), listBuildingLocations);
                    recyclerView.setAdapter(buildingLocationsAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("ERROR --------------------------------------// HÁ MALANDRAMENTE");
                Toast.makeText(NewReservationFragment.this.getActivity(), "Error", Toast.LENGTH_SHORT);
            }
        });
    }
}
