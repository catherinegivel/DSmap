package fr.givel.catherine.dsmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText(Arrivee.getNom());

    }

}
