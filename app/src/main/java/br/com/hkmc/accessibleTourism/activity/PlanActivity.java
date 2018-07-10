package br.com.hkmc.accessibleTourism.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.hkmc.accessibleTourism.R;
import br.com.hkmc.accessibleTourism.adapter.FlightAdapter;
import br.com.hkmc.accessibleTourism.models.Flight;
import br.com.hkmc.accessibleTourism.services.TokenService;

public class PlanActivity extends AppCompatActivity {

    private List<Flight> flightList = new ArrayList<>();

    private EditText edtGoing;
    private EditText edtReturns;
    private EditText edtGoingDate;
    private EditText edtReturnsDate;
    private RecyclerView recyclerView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mnHomeId:
                    Intent homeIntent = new Intent(PlanActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                case R.id.mnProfileId:
                    Intent profileIntent = new Intent(PlanActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    return true;
                case R.id.mnPlanId:
                    return true;
            }
            return false;
        }
    };

    public void print(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.mnPlanId);

        edtGoing = findViewById(R.id.edtGoingId);
        edtReturns = findViewById(R.id.edtReturnsId);
        edtGoingDate = findViewById(R.id.edtGoingDateId);
        edtReturnsDate = findViewById(R.id.edtReturnsDateId);

        recyclerView = findViewById(R.id.recyclerViewId);
    }
    // PROCEDIMENTO PARA EXECUTAR O ONCLICK DO BOTÃO
    public void onClickFind(View view){

        String Going = edtGoing.getText().toString();
        String Returns = edtReturns.getText().toString();
        String GoingDate = edtGoingDate.getText().toString();
        String ReturnsDate = edtReturnsDate.getText().toString();

        // parei aqui montando o json

        if(Going == null || Going.equals("") || Returns == null || Returns.equals("")
                || GoingDate == null || GoingDate.equals("") || ReturnsDate == null
                || ReturnsDate.equals("")){
            print("Obrigatório informar todos os campos");
        }else {
            String jsonScriptFind = "origin=" + Going + "&destination=" +  Returns + "&goingDate=" + GoingDate + "&returnsDate=" + ReturnsDate;
            WebServiceEndereco webServiceEndereco = new WebServiceEndereco();
            webServiceEndereco.execute(jsonScriptFind);
        }
    }

    // CLASSE PARA EXECUTA AsyncTask
    public class WebServiceEndereco extends AsyncTask<String, Void, String> {

        // MÉTODO QUE FAZ A REQUISIÇÃO HTTP
        @Override
        protected String doInBackground(String... strings) {
            String access_token = "";
            try {
                TokenService tokenService = new TokenService();
                access_token = tokenService.getToken();
            } catch (Exception e){
                print("Erro ao buscar o token");
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://accessible-tourism-api.herokuapp.com/v1/flights?" + strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty ("Authorization", "JWT " + access_token);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String linha;
                StringBuffer buffer = new StringBuffer();
                while((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                    buffer.append("\n");
                }

                return buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            return null;
        }

        // MÉTODO QUE CONFIGURA A RESPOSTA DO MÉTODO HTTP

        @Override
        protected void onPostExecute(String s) {
            if(s == null)
                print("Não existem vôos disponíveis para as datas informadas...");
            else {
                try {
                    String data = "{\"flights\": " + s + "}";
                    JSONObject json = new JSONObject(data);
                    JSONArray jsonArray = json.getJSONArray("flights");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Flight flight = new Flight();

                        flight.setOrigin(edtGoing.getText().toString());
                        flight.setDestination(edtReturns.getText().toString());
                        flight.setGoingDate(edtGoingDate.getText().toString());
                        flight.setReturnsDate(edtReturnsDate.getText().toString());

                        flight.setCompany(obj.getString("company"));
                        flight.setValue(obj.getString("value"));
                        flight.setGoingTime(obj.getString("goingTime"));
                        flight.setReturnsTime(obj.getString("returnsTime"));
                        flight.setCurrency(obj.getString("currency"));
                        flightList.add(flight);
                    }

                    FlightAdapter flightAdapter = new FlightAdapter(flightList);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                            LinearLayout.VERTICAL));
                    recyclerView.setAdapter(flightAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
