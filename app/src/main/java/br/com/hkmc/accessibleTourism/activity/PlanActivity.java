package br.com.hkmc.accessibleTourism.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.hkmc.accessibleTourism.R;

public class PlanActivity extends AppCompatActivity {

    private EditText edtGoing;
    private EditText edtReturns;
    private EditText edtGoingDate;
    private EditText edtReturnsDate;
    private TextView txtCia;
    private TextView txtValor;
    private TextView txtReturnsTime;
    private TextView txtGoingTime;
    private TextView txtCurrency;

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
        txtCia = findViewById(R.id.txtCiaId);
        txtValor = findViewById(R.id.txtValorId);
        txtGoingTime = findViewById(R.id.txtDepartureHourId);
        txtReturnsTime = findViewById(R.id.txtArrivalHourId);
        txtCurrency = findViewById(R.id.txtCurrencyId);
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

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://accessible-tourism-api.herokuapp.com/v1/flights?" + strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
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

                    JSONObject json = new JSONObject(s);

                    txtCia.setText(json.getString("company"));
                    txtValor.setText(json.getString("value"));
                    txtGoingTime.setText(json.getString("goingTime"));
                    txtReturnsTime.setText(json.getString("returnsTime"));
                    txtCurrency.setText(json.getString("currency"));

                    print("Endereço recuperado com sucesso!");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class WebAPI extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
