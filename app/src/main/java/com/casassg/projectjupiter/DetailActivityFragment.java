package com.casassg.projectjupiter;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.casassg.projectjupiter.data.MomentContract;
import com.casassg.projectjupiter.model.Moment;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DETAIL_LOADER = 0;


    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private View root;
    private Moment moment;
    private long moment_id;
    private boolean mEmpty;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        updateEmpty(arguments);

        if(mEmpty) {
            root = inflater.inflate(R.layout.empty_message, container, false);
        } else {
            root = inflater.inflate(R.layout.fragment_detail, container, false);
        }

        setHasOptionsMenu(true);
        return root;
    }

    private void updateEmpty(Bundle arguments) {
        if (arguments != null) {
            moment_id = arguments.getLong(DetailActivity.ID_KEY);
            if(moment_id<0) {
                mEmpty = true;
            } else {
                mEmpty = false;
            }
        } else {
            mEmpty = true;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(!mEmpty)
            inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_map:
                openMomentLocationInMap();
                return true;
            case R.id.share:
                startActivity(createShareMomentIntent());
                return true;
            case R.id.delete:
                Utility.deleteMoment(moment,getActivity());
                Toast.makeText(getActivity(),R.string.successfully_deleted,Toast.LENGTH_SHORT).show();
                if(getActivity().findViewById(R.id.myFAB)==null)
                    getActivity().finish();
                else
                    ((MainActivityFragment.Callback)getActivity()).onItemSelected(-1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        updateEmpty(arguments);
        if (!mEmpty) {
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);

        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "In onCreateLoader");
        if(mEmpty )
            return null;

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                MomentContract.MomentEntry.buildMomentUri(moment_id),
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

        moment = Utility.getMoment(data);
        loadMomentToUI();
    }

    private void loadMomentToUI() {
        RatingBar rating = (RatingBar) root.findViewById(R.id.ratingBar);
        TextView date = (TextView) root.findViewById(R.id.date);
        TextView title = (TextView) root.findViewById(R.id.title);

        rating.setRating((float) moment.getRating());
        date.setText(Utility.formatDate(moment.getDate()));
        title.setText(moment.getTitle());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void openMomentLocationInMap() {
        String location =  moment.getX_coord() + "," + moment.getY_coord() + " (" + moment.getTitle()+ ")";

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivity(intent);
        } else {
            Log.e(LOG_TAG, "Couldn't call " + location + ", no receiving apps installed!");
        }
    }

    private Intent createShareMomentIntent() {
        String share = getShareString();
        String hashtag = "#" +getResources().getString(R.string.app_name);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, share +" " +hashtag);
        return Intent.createChooser(shareIntent, getResources().getText(R.string.abc_shareactionprovider_share_with));
    }

    public String getShareString() {
        return getResources().getString(R.string.share_string) + moment.getTitle() + " (" + Utility.formatDate(moment.getDate()) + ")";
    }
}
