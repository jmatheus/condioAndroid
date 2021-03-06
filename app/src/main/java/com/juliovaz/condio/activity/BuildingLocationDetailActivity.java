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

package com.juliovaz.condio.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.juliovaz.condio.R;
import com.juliovaz.condio.decorators.EventDecorator;
import com.juliovaz.condio.decorators.HighlightWeekendsDecorator;
import com.juliovaz.condio.decorators.UnavailableDateDecorator;
import com.juliovaz.condio.model.BuildingLocation;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class BuildingLocationDetailActivity extends AppCompatActivity implements OnDateSelectedListener {

    private MaterialCalendarView materialCalendar;
    private ArrayList<CalendarDay> reservedDates;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_location_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        materialCalendar = (MaterialCalendarView) findViewById(R.id.calendarView);
        // Set Collapsing Toolbar layout to the screen

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Resources resources = getResources();
        collapsingToolbar.setTitle(getIntent().getStringExtra("BUILDING_LOCATION_NAME"));

        TypedArray placePictures = resources.obtainTypedArray(R.array.places_picture);
        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        placePicutre.setImageDrawable(placePictures.getDrawable(getIntent().getIntExtra("BUILDING_LOCATION_PLACE_INDEX", 0)));

        materialCalendar.setOnDateChangedListener(this);
        materialCalendar.addDecorators(new HighlightWeekendsDecorator());

        placePictures.recycle();

        initComponents();

    }

    private void initComponents() {
        getReservedDates();
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        CalendarDay day = materialCalendar.getSelectedDate();
        Calendar cal = Calendar.getInstance();
        CalendarDay currentDay = CalendarDay.from(cal);

        boolean validDay = currentDay.isAfter(day);

        if (reservedDates != null) {
            boolean contains = reservedDates.contains(day);
            if (validDay || contains) {
                String message = "Essa data está indisponível, por favor selecione outra.";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent(this, NewReservationActivity.class);
                intent.putExtra("EVENT_DATE", getSelectedDate());
                intent.putExtra("EVENT_DATE_STRING", getSelectedDatesString());
                intent.putExtra("BUILDING_LOCATION_ID", getIntent().getIntExtra("BUILDING_LOCATION_ID", 0));
                intent.putExtra("BUILDING_LOCATION_NAME", getIntent().getStringExtra("BUILDING_LOCATION_NAME"));
                startActivity(intent);
            }
        }
    }


    private String getSelectedDatesString() {
        CalendarDay date = materialCalendar.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return SimpleDateFormat.getDateInstance().format(date.getDate());
    }

    private String getSelectedDate() {
        CalendarDay date = materialCalendar.getSelectedDate();
        String dateString = null;
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateString = sdf.format(date.getDate());
        }

        return dateString;
    }

    public void getBuildingLocation() {
        final int locationId = getIntent().getIntExtra("BUILDING_LOCATION_ID", 0);
        ApiService apiService = ApiMethodsManager.getMethodGetService();
        apiService.getBuildingLocation(String.valueOf(locationId), new Callback<BuildingLocation>() {

            @Override
            public void success(BuildingLocation buildingLocation, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void getReservedDates() {

        int locationId = getIntent().getIntExtra("BUILDING_LOCATION_ID", 0);

        ApiService apiService = ApiMethodsManager.getMethodGetService();
        apiService.getBuildingLocationDates(String.valueOf(locationId), new Callback<ArrayList<String>>() {
            @Override
            public void success(ArrayList<String> dates, Response response) {
                Calendar cal = Calendar.getInstance();
                reservedDates  = new ArrayList<>();
                for (String date: dates) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        cal.setTime(sdf.parse(date));
                        CalendarDay day = CalendarDay.from(cal);
                        reservedDates.add(day);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                materialCalendar.addDecorator(new EventDecorator(Color.RED, reservedDates));
            }

            @Override
            public void failure(RetrofitError error) {
                String message = "Ocorreu um pequeno erro ao recuperar as datas. Tente novamente em instantes por favor!";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

    }
}
