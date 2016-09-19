package com.juliovaz.condio.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.juliovaz.condio.adapter.EventRecyclerViewAdapter;
import com.juliovaz.condio.R;
import com.juliovaz.condio.model.Reservation;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class EventFragment extends Fragment {

    private ArrayList<Reservation> listReservations;
    private RecyclerView recyclerView;
    private EventRecyclerViewAdapter eventRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_event_list, container, false);

        listReservations = new ArrayList<>();

        initComponents();
        return recyclerView;
    }

    private void initComponents() {
        getAllReservations();
    }

    private void getAllReservations() {
        ApiService apiService = ApiMethodsManager.getMethodGetService();
        apiService.getAllReservations(new Callback<ArrayList<Reservation>>() {
            @Override
            public void success(ArrayList<Reservation> reservations, Response response) {
                if (response.getStatus() == 200) {
                    if (reservations == null) {
                        listReservations = new ArrayList<>();
                    } else {
                        listReservations = reservations;
                    }

                    eventRecyclerViewAdapter = new EventRecyclerViewAdapter(recyclerView.getContext(), listReservations);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(eventRecyclerViewAdapter);

                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("---------------------//");
                System.out.println(error.getResponse().getStatus());
                Toast.makeText(EventFragment.this.getActivity(), "Error", Toast.LENGTH_SHORT);

            }
        });
    }
}
