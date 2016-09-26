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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.juliovaz.condio.R;
import com.juliovaz.condio.activity.NewMessageActivity;
import com.juliovaz.condio.adapter.BuildingMessageAdapter;
import com.juliovaz.condio.model.BuildingMessage;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Provides UI for the view with Cards.
 */
public class BuildingMessageFragment extends Fragment {

    private ArrayList<BuildingMessage> listBuildingMessages;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);

        listBuildingMessages = new ArrayList<>();
        initComponents();

        return recyclerView;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity().getApplicationContext(), NewMessageActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_message_icon, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private void initComponents() {
        getAllBuildingMessages();
    }

    private void getAllBuildingMessages() {

        ApiService service = ApiMethodsManager.getMethodGetService();


        service.getAllBuildingMessages(new Callback<ArrayList<BuildingMessage>>() {
            @Override
            public void success(ArrayList<BuildingMessage> buildingMessages, Response response) {

                if (response.getStatus() == 200) {
                    if (buildingMessages == null) {
                        listBuildingMessages = new ArrayList<>();
                    } else {
                        listBuildingMessages = buildingMessages;
                    }

                    BuildingMessageAdapter messageBoardAdapter = new BuildingMessageAdapter(recyclerView.getContext(), listBuildingMessages);
                    recyclerView.setAdapter(messageBoardAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(BuildingMessageFragment.this.getActivity(), "Error", Toast.LENGTH_SHORT);
            }

        });

    }
}
