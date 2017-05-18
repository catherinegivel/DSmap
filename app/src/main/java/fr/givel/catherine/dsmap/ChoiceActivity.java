package fr.givel.catherine.dsmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        String Départ = spinner1.getSelectedItem().toString();

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        String Arrivée = spinner2.getSelectedItem().toString();
    }

    public void GoToMapActivity (View view) {
        Intent i = new Intent(activity_main.this, MapActivity.class);
        startActivity(i);

    }
}