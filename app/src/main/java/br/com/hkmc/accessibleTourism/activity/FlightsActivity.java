//NÃO USAR

package br.com.hkmc.accessibleTourism.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.com.hkmc.accessibleTourism.R;

public class FlightsActivity extends AppCompatActivity {

    private TextView txtDeparture;
    private TextView txtArrivalID;
    private TextView txtDepartureDate;
    private TextView txtArrivalDate;
    private TextView txtCia;
    private TextView txtValor;
    private TextView txtDepartureHour;
    private TextView txtArrivalHour;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mnHomeId:
                    Intent homeIntent = new Intent(FlightsActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                case R.id.mnProfileId:
                    Intent profileIntent = new Intent(FlightsActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    return true;
                case R.id.mnPlanId:
                    Intent planIntent = new Intent(FlightsActivity.this, PlanActivity.class);
                    startActivity(planIntent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        txtDeparture = findViewById(R.id.txtDepartureId);
        txtArrivalID = findViewById(R.id.txtArrivalID);
        txtDepartureDate = findViewById(R.id.txtDepartureDateId);
        txtArrivalDate = findViewById(R.id.txtArrivalDateId);
        txtCia = findViewById(R.id.txtCiaId);
        txtValor = findViewById(R.id.txtValorId);
        txtDepartureHour = findViewById(R.id.txtDepartureHourId);
        txtArrivalHour = findViewById(R.id.txtArrivalHourId);

    }

    // EXIBE UMA MENSAGEM TOAST PARA O USUÁRIO
    public void print(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



}
