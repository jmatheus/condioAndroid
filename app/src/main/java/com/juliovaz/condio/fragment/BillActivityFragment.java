package com.juliovaz.condio.fragment;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.juliovaz.condio.Manifest;
import com.juliovaz.condio.R;
import com.juliovaz.condio.adapter.BillRecyclerViewAdapter;
import com.juliovaz.condio.model.Bill;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class BillActivityFragment extends Fragment {

    private ArrayList<Bill> listBills;
    private RecyclerView recyclerView;
    private BillRecyclerViewAdapter billRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.bill_list, container, false);

        listBills = new ArrayList<>();

        initComponents();

        return recyclerView;
    }

    private void initComponents() {
        getBills();
    }

    public void getBills() {
        SharedPreferences prefs = getActivity().getSharedPreferences("br.juliovaz.condio.prefs", getActivity().MODE_PRIVATE);
        String loggedUser = prefs.getString("USER_ID", null);
        loggedUser = "1";
        ApiService apiService = ApiMethodsManager.getMethodGetService();
        apiService.getBills(loggedUser, new Callback<ArrayList<Bill>>() {
            @Override
            public void success(ArrayList<Bill> bills, Response response) {
                if (response.getStatus() == 200) {
                    if (bills == null) {
                        listBills = new ArrayList<>();
                    } else {
                        listBills = bills;
                    }

                    billRecyclerViewAdapter = new BillRecyclerViewAdapter(recyclerView.getContext(), listBills);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(billRecyclerViewAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(BillActivityFragment.this.getActivity(), "Não foi possível obter as reservas.", Toast.LENGTH_SHORT);
            }
        });
    }
}
