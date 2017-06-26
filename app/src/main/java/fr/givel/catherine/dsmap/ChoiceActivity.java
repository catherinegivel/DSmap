package fr.givel.catherine.dsmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        // Créer la liste des lieux (avec noms et coordonnées)
        List<Coordonnees> list = new ArrayList<>();

        list.add(new Coordonnees("Accueil", 1,1));
        list.add(new Coordonnees("Foyer", 47.1978933,-0.9982995));
        list.add(new Coordonnees("Site 6/5", 47.1980076,-0.996303));

        ArrayAdapter<Coordonnees> adapter = new ArrayAdapter<Coordonnees>(this,
                R.layout.support_simple_spinner_dropdown_item,
                list);

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoToMapActivity((Coordonnees) spinner1.getSelectedItem(), (Coordonnees) spinner2.getSelectedItem());
            }
        });
    }

    public void GoToMapActivity (Coordonnees depart, Coordonnees arrivee) {
        Intent i = new Intent(ChoiceActivity.this, MapActivity.class);
        i.putExtra("Départ", depart);
        i.putExtra("Arrivée", arrivee);
        startActivity(i);
    }


}
