package com.casassg.projectjupiter;

import android.content.Intent;
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

    public MainActivityFragment() {
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
                    Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .setData(MomentContract.MomentEntry.buildMomentUri(moment_id));
                    startActivity(intent);
                }
            }
        });
        return root;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri momentUri = MomentContract.MomentEntry.buildMomentUri();
        String sortOrder = MomentContract.MomentEntry.COL_DATE + " ASC";
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
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMomentAdapter.swapCursor(null);
    }
}
