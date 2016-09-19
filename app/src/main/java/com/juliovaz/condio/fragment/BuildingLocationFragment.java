/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.juliovaz.condio.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juliovaz.condio.R;
import com.juliovaz.condio.activity.BuildingLocationDetailActivity;
import com.juliovaz.condio.adapter.BuildingLocationsAdapter;
import com.juliovaz.condio.model.BuildingLocation;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Provides UI for the view with Tile.
 */
public class BuildingLocationFragment extends Fragment {

    private ArrayList<BuildingLocation> listBuildingLocations;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);


        // Set padding for Tiles

        return recyclerView;
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
                    int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
                    recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error");
                Toast.makeText(BuildingLocationFragment.this.getActivity(), "Error", Toast.LENGTH_SHORT);
            }

        });
    }
}