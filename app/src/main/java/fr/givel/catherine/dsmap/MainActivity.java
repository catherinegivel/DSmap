package fr.givel.catherine.dsmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiY2F0aGd2bCIsImEiOiJjajI2Mm1iNnMwMDNrMnFvNmNtZzdlN28wIn0.eT6QBOmb44ig8jFNijKQ5w");
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        // Créer la liste
        List<Coordonnees> list = new ArrayList<>();

        list.add(new Coordonnees("Accueil", 1,1));
        list.add(new Coordonnees("Foyer", 2,1));
        list.add(new Coordonnees("Site 6/5", 3,4));

        // Créer les objets Depart et Arrivee

        Coordonnees Depart = list.get(1);
        Coordonnees Arrivee = list.get(2);

        // Afficher le texte sur mon interface

        TextView depart = (TextView) findViewById(R.id.depart);
        depart.setText(Depart.getNom());

        TextView arrivee = (TextView) findViewById(R.id.arrivee);
        arrivee.setText(Arrivee.getNom());
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
