package br.com.hkmc.accessibleTourism.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.hkmc.accessibleTourism.R;

public class PlanActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.mnPlanId);
    }

}
