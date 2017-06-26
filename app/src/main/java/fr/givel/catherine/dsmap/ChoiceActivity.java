package fr.givel.catherine.dsmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        String Départ = spinner1.getSelectedItem().toString();

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        String Arrivée = spinner2.getSelectedItem().toString();
    }

    public void GoToMapActivity (View view) {
        Intent i = new Intent(ChoiceActivity.this, MapActivity.class);
        startActivity(i);

    }
}
