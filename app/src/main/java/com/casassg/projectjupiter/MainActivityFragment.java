package com.casassg.projectjupiter;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.casassg.projectjupiter.data.MomentContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOMENT_LOADER = 0;

    private ListView listView;
    private MomentAdapter mMomentAdapter;
    private int mPosition;
    private static final String SELECTED_KEY = "selected_position";

    public MainActivityFragment() {
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(long id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mMomentAdapter = new MomentAdapter(getActivity(), null, 0);

        listView = (ListView) root.findViewById(R.id.momentList);

        listView.setAdapter(mMomentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mMomentAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    long moment_id = cursor.getLong(MomentContract.MomentEntry.COL_ID_IND);
                    ((Callback)getActivity())
                            .onItemSelected(moment_id);
                }
                mPosition = position;
            }
        });
        return root;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri momentUri = MomentContract.MomentEntry.buildMomentUri();
        String sortOrder = MomentContract.MomentEntry.COL_DATE + " DESC";
        return new CursorLoader(getActivity(),
                momentUri,
                MomentContract.MomentEntry.MOMENT_COLUMNS,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOMENT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMomentAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            listView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMomentAdapter.swapCursor(null);
    }
}
