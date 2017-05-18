package fr.givel.catherine.dsmap;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.Constants;
import com.mapbox.services.commons.ServicesException;
import com.mapbox.services.commons.geojson.LineString;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.directions.v5.DirectionsCriteria;
import com.mapbox.services.directions.v5.MapboxDirections;
import com.mapbox.services.directions.v5.models.DirectionsResponse;
import com.mapbox.services.directions.v5.models.DirectionsRoute;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private static final String TAG = "MapActivity";

    private MapView mapView;
    private MapboxMap map;
    private DirectionsRoute currentRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiY2F0aGd2bCIsImEiOiJjajI2Mm1iNnMwMDNrMnFvNmNtZzdlN28wIn0.eT6QBOmb44ig8jFNijKQ5w");
        setContentView(R.layout.activity_main);

        // Créer la liste des lieux (avec noms et coordonnées)
        List<Coordonnees> list = new ArrayList<>();

        list.add(new Coordonnees("Accueil", 1,1));
        list.add(new Coordonnees("Foyer", 47.1978933,-0.9982995));
        list.add(new Coordonnees("Site 6/5", 47.1980076,-0.996303));

        // Créer les objets depart et arrivee pour le trajet choisi
        final Coordonnees coorddepart = list.get(1);
        final Coordonnees coordarrivee = list.get(2);

        // Afficher le texte du trajet correspondant au trajet choisi
        TextView depart = (TextView) findViewById(R.id.depart);
        depart.setText(coorddepart.getNom());

        TextView arrivee = (TextView) findViewById(R.id.arrivee);
        arrivee.setText(coordarrivee.getNom());

        // Configurer la vue de la carte
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                map = mapboxMap;

                // Affiche les points sur la carte
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(coorddepart.getLatitude(), coorddepart.getLongitude()))
                        .title("Depart")
                        .snippet(coorddepart.getNom()));
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(coordarrivee.getLatitude(), coordarrivee.getLongitude()))
                        .title("Arrivee")
                        .snippet(coordarrivee.getNom()));

                // Récupère le trajet depuis l'API
                try {
                    getRoute(coorddepart, coordarrivee);
                } catch (ServicesException servicesException) {
                    servicesException.printStackTrace();
                }
            }
        });

    }

    //Méthodes de la carte

    private void getRoute(Coordonnees depart, Coordonnees arrivee) throws ServicesException {

        MapboxDirections client = new MapboxDirections.Builder()
                .setOrigin(Position.fromCoordinates(depart.getLatitude(),depart.getLongitude()))
                .setDestination(Position.fromCoordinates(arrivee.getLatitude(),arrivee.getLongitude()))
                .setProfile(DirectionsCriteria.PROFILE_WALKING)
                .setAccessToken("pk.eyJ1IjoiY2F0aGd2bCIsImEiOiJjajI2Mm1iNnMwMDNrMnFvNmNtZzdlN28wIn0.eT6QBOmb44ig8jFNijKQ5w")
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                // On stocke la réponse de l'API dans le log
                Log.d(TAG, "Code de réponse de l'API: " + response.code());
                if (response.body() == null) {
                    Log.e(TAG, "Pas de trajet trouvé, vérifiez le token");
                    return;
                } else if (response.body().getRoutes().size() < 1) {
                    Log.e(TAG, "Pas de trajet trouvé");
                    return;
                }

                // Afficher des informations par rapport au trajet
                currentRoute = response.body().getRoutes().get(0);
                Log.d(TAG, "Distance: " + currentRoute.getDistance());
                Toast.makeText(
                        MapActivity.this,
                        "Le trajet fait " + currentRoute.getDistance() + " mètres.",
                        Toast.LENGTH_SHORT).show();

                // Dessiner le trajet sur la carte
                drawRoute(currentRoute);
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Log.e(TAG, "Erreur: " + throwable.getMessage());
                Toast.makeText(MapActivity.this, "Erreur: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void drawRoute(DirectionsRoute route) {

        // Convertir le LineString en coordonnées dans le tableau LatLng[]
        LineString lineString = LineString.fromPolyline(route.getGeometry(), Constants.OSRM_PRECISION_V5);
        List<Position> coordinates = lineString.getCoordinates();
        LatLng[] points = new LatLng[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            points[i] = new LatLng(
                    coordinates.get(i).getLatitude(),
                    coordinates.get(i).getLongitude());
        }

        // Afficher le dessin du trajet sur la carte
        map.addPolyline(new PolylineOptions()
                .add(points)
                .color(Color.parseColor("#009688"))
                .width(5));
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


}
