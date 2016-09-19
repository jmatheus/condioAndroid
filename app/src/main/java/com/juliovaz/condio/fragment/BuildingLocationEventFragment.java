package com.juliovaz.condio.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juliovaz.condio.R;
import com.juliovaz.condio.model.Reservation;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class BuildingLocationEventFragment extends Fragment {

    private ArrayList<Reservation> listReservations;
    private RecyclerView recyclerView;

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

    }
}
