package com.casassg.projectjupiter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.casassg.projectjupiter.model.Moment;

/**
 * Created by casassg on 20/06/15.
 *
 * @author casassg
 */
public class MomentAdapter extends CursorAdapter {
    public MomentAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_moment, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = (TextView) view;
        Moment m = Utility.getMoment(cursor);
        tv.setText(m.toString());
    }
}
