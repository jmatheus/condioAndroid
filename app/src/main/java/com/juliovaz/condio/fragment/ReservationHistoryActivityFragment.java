package com.juliovaz.condio.fragment;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.juliovaz.condio.R;
import com.juliovaz.condio.activity.ReservationHistoryActivity;
import com.juliovaz.condio.adapter.EventRecyclerViewAdapter;
import com.juliovaz.condio.model.Reservation;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReservationHistoryActivityFragment extends Fragment {


    private ArrayList<Reservation> listReservations;
    private RecyclerView recyclerView;
    private EventRecyclerViewAdapter eventRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_reservation_history, container, false);
        listReservations = new ArrayList<>();
        initComponents();
        return recyclerView;
    }

    private void initComponents() {
        getReservations();
    }


    private void getReservations() {
        SharedPreferences prefs = getActivity().getSharedPreferences("br.juliovaz.condio.prefs", getActivity().MODE_PRIVATE);
        String loggedUser = prefs.getString("USER_ID", null);
        loggedUser = "2";
        ApiService apiService = ApiMethodsManager.getMethodGetService();
        apiService.getReservationHistory(loggedUser, new Callback<ArrayList<Reservation>>() {
            @Override
            public void success(ArrayList<Reservation> reservations, Response response) {
                if (response.getStatus() == 200) {
                    if (reservations == null) {
                        listReservations = new ArrayList<>();
                    } else {
                        listReservations = reservations;
                    }

                    eventRecyclerViewAdapter = new EventRecyclerViewAdapter(recyclerView.getContext(), listReservations, true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(eventRecyclerViewAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ReservationHistoryActivityFragment.this.getActivity(), "Não foi possível obter as reservas.", Toast.LENGTH_SHORT);

            }
        });
    }
}
