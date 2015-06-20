package com.casassg.projectjupiter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.casassg.projectjupiter.data.MomentContract;
import com.casassg.projectjupiter.model.Moment;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DETAIL_LOADER = 0;


    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private View root;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "In onCreateLoader");
        Intent intent = getActivity().getIntent();
        if (intent == null) {
            return null;
        }

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                intent.getData(),
                MomentContract.MomentEntry.MOMENT_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "In onLoadFinished");
        if (!data.moveToFirst()) {
            return;
        }

        Moment moment = Utility.getMoment(data);
        loadMomentToUI(moment);
        getActivity().setTitle(moment.getTitle());
    }

    private void loadMomentToUI(Moment moment) {
        RatingBar rating = (RatingBar) root.findViewById(R.id.showRating);
        TextView date = (TextView) root.findViewById(R.id.date);

        rating.setRating((float) moment.getRating());
        date.setText(Utility.formatDate(moment.getDate()));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
