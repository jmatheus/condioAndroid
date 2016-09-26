package com.juliovaz.condio.fragment;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juliovaz.condio.R;
import com.juliovaz.condio.activity.ReservationDetailActivity;
import com.juliovaz.condio.model.Reservation;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;
import com.juliovaz.condio.util.ConstantsCondio;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a single Reservation detail screen.
 * This fragment is either contained in a {@link ReservationListActivity}
 * in two-pane mode (on tablets) or a {@link ReservationDetailActivity}
 * on handsets.
 */
public class ReservationDetailFragment extends Fragment {

    /**
     * The dummy content this fragment is presenting.
     */
    private Reservation reservation;

    private CollapsingToolbarLayout appBarLayout;

    private View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReservationDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ConstantsCondio.RESERVATION_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            loadReservation(Integer.toString(getArguments().getInt(ConstantsCondio.RESERVATION_ID)));

            Activity activity = this.getActivity();
            appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            TypedArray placePictures = getResources().obtainTypedArray(R.array.places_picture);
            ImageView placePicutre = (ImageView) activity.findViewById(R.id.image_detail);
            placePicutre.setImageDrawable(placePictures.getDrawable(getArguments().getInt(ConstantsCondio.BUILDING_LOCATION_ID, 0)));

            if (appBarLayout != null) {
                appBarLayout.setTitle("Detalhes");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reservation_detail, container, false);

        return view;
    }

    public void loadReservation(final String reservationId) {

        ApiService apiService = ApiMethodsManager.getMethodGetService();
        apiService.getReservation(reservationId, new Callback<Reservation>() {

            @Override
            public void success(Reservation reservationResponse, Response response) {
                if (response.getStatus() == 200) {

                    // Show the dummy content as text in a TextView.
                    if (reservationResponse != null) {
                        reservation = reservationResponse;

                        String info = reservation.getReservationName() + " foi reservado(a) pelo apartamento " + reservation.getUser().getApartment();

                        ((TextView) view.findViewById(R.id.tx_event_detail)).setText(info);
                        ((TextView) view.findViewById(R.id.tx_event_date)).setText(reservation.getEventDate());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Erro ao fazer load de Reservation", Toast.LENGTH_SHORT).show();;
            }
        });
    }
}
