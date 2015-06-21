package com.casassg.projectjupiter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class DetailActivity extends ActionBarActivity {

    public static final String ID_KEY = "id_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            long id = getIntent().getLongExtra(ID_KEY,-1);

            Bundle arguments = new Bundle();
            arguments.putLong(ID_KEY, id);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.moment_detail_container, fragment)
                    .commit();
        }
    }


}
