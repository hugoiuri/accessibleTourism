package br.com.hkmc.accessibleTourism.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.hkmc.accessibleTourism.R;
import br.com.hkmc.accessibleTourism.services.TokenService;

public class ProfileActivity extends AppCompatActivity {

    private String username = "karine.cordeiro";

    TextView txtName;
    TextView txtEmail;



    public void print(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mnHomeId:
                    Intent homeIntent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                case R.id.mnProfileId:
                    return true;
                case R.id.mnPlanId:
                    Intent planIntent = new Intent(ProfileActivity.this, PlanActivity.class);
                    startActivity(planIntent );
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.txtNameId);
        txtEmail = findViewById(R.id.txtEmailId);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.mnProfileId);

        UsersService usersService = new UsersService();
        usersService.execute(username);
    }

    public class UsersService extends AsyncTask<String, Void, String> {

        private String usersUrl = "https://accessible-tourism-api.herokuapp.com/v1/users/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
                URL url = new URL(usersUrl + strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty ("Authorization", "JWT " + access_token);

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                StringBuffer buffer = new StringBuffer();
                while((line = reader.readLine()) != null) {
                    buffer.append(line);
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

        @Override
        protected void onPostExecute(String s) {
            if(s == null)
                print("Não foi possível recuperar os dados...");
            else {
                try {

                    JSONObject json = new JSONObject(s);

                    txtName.setText(json.getString("name"));
                    txtEmail.setText(json.getString("email"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
