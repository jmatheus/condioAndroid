package com.juliovaz.condio.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.juliovaz.condio.R;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;
import com.juliovaz.condio.util.ConstantsCondio;

import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewReservationActivity extends AppCompatActivity {

    private int userId, buildingLocationId;
    private String eventDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_new_reservation);
        setSupportActionBar(toolbar);

        EditText startDateEt = (EditText) findViewById(R.id.input_event_date);
        startDateEt.setText(getIntent().getStringExtra("EVENT_DATE"));

        String locationName = getIntent().getStringExtra("BUILDING_LOCATION_NAME");
        String eventDate = getIntent().getStringExtra("EVENT_DATE_STRING");
        String text = getResources().getString(R.string.reservation_text, locationName, eventDate);
        TextView txReservationText = (TextView) findViewById(R.id.tx_new_reservation);
        txReservationText.setText(text);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_new_reservation) {
            SharedPreferences prefs = getSharedPreferences("br.juliovaz.condio.prefs", MODE_PRIVATE);
            String loggedUser = prefs.getString("USER_ID", null);
            loggedUser = "1";
            if (loggedUser != null) {
                userId = Integer.parseInt(loggedUser);
                buildingLocationId = getIntent().getIntExtra("BUILDING_LOCATION_ID", 0);
                eventDate = getIntent().getStringExtra("EVENT_DATE");
                createNewEvent();
            }
        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_reservation, menu);
        return true;
    }

    private void createNewEvent() {

        ApiService service = ApiMethodsManager.getMethodGetService();

        JsonObject reservationChild = new JsonObject();
        JsonObject reservation = new JsonObject();

        reservationChild.addProperty(ConstantsCondio.TAG_RESERVATION_USER, this.userId);
        reservationChild.addProperty(ConstantsCondio.TAG_RESERVATION_LOCATION, this.buildingLocationId);
        reservationChild.addProperty(ConstantsCondio.TAG_RESERVATION_EVENT_DATE, this.eventDate);
        reservation.add(ConstantsCondio.TAG_RESERVATION, reservationChild);


        service.createReservation(reservation, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                if (response.getStatus() == 201) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Toast.makeText(getApplicationContext(), "Reserva efetuada com sucesso", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String message = "Não foi possível realizar a reserva. Tente novamente em instantes.";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
