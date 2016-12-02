package com.juliovaz.condio.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juliovaz.condio.R;
import com.juliovaz.condio.adapter.ProductMaintenaceAdapter;
import com.juliovaz.condio.model.Product;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductMaintenanceActivityFragment extends Fragment {

    private ArrayList<Product> listMaintenance;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);

        listMaintenance = new ArrayList<>();
        initComponents();

        return recyclerView;
    }

    private void initComponents() {
        getProducts();
    }

    private void getProducts() {
        ApiService service = ApiMethodsManager.getMethodGetService();


        service.getProducts(new Callback<ArrayList<Product>>() {
            @Override
            public void success(ArrayList<Product> products, Response response) {
                if (response.getStatus() == 200) {
                    if (products== null) {
                        listMaintenance = new ArrayList<>();
                    } else {
                        listMaintenance = products;
                    }

                    ProductMaintenaceAdapter productMaintenaceAdapter = new ProductMaintenaceAdapter(recyclerView.getContext(), listMaintenance);
                    recyclerView.setAdapter(productMaintenaceAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ProductMaintenanceActivityFragment.this.getActivity(), "Erro de conexão com o servidor", Toast.LENGTH_SHORT);
            }

        });
    }
}
