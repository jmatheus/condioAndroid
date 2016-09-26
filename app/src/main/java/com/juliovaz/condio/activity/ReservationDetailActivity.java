package com.juliovaz.condio.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.juliovaz.condio.R;
import com.juliovaz.condio.fragment.ReservationDetailFragment;
import com.juliovaz.condio.model.Reservation;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;
import com.juliovaz.condio.util.ConstantsCondio;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * An activity representing a single Reservation detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items.
 */
public class ReservationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_cancel_reservation);


        boolean reservationHistory = getIntent().getBooleanExtra(ConstantsCondio.RESERVATION_HISTORY_FLAG, false);


        if (reservationHistory) {
            fab.setVisibility(View.INVISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AppTheme))
                        .setTitle("Cancelar reserva")
                        .setMessage("Você tem certeza que deseja cancelar essa reserva?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ApiService service = ApiMethodsManager.getMethodGetService();

                                JsonObject reservationChild = new JsonObject();
                                JsonObject reservation = new JsonObject();

                                reservationChild.addProperty(ConstantsCondio.TAG_RESERVATION_STATUS, 2);
                                reservation.add(ConstantsCondio.TAG_RESERVATION, reservationChild);

                                String reservationId = Integer.toString(getIntent().getIntExtra(ConstantsCondio.RESERVATION_ID, 0));

                                service.cancelReservation(reservationId, reservation, new Callback<Reservation>() {
                                    @Override
                                    public void success(Reservation reservation, Response response) {
                                        if (response.getStatus() == 200) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            Toast.makeText(getApplicationContext(), "Reserva cancelada com sucesso", Toast.LENGTH_LONG).show();
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        String message = "Não foi possível cancelar sua reserva. Tente mais tarde por favor!";
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(ConstantsCondio.RESERVATION_ID,
                    getIntent().getIntExtra(ConstantsCondio.RESERVATION_ID, 0));

            arguments.putInt(ConstantsCondio.BUILDING_LOCATION_ID,
                    getIntent().getIntExtra(ConstantsCondio.BUILDING_LOCATION_ID, 0));

            ReservationDetailFragment fragment = new ReservationDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.reservation_detail_container, fragment)
                    .commit();
        }
    }
}
