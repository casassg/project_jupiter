package com.casassg.projectjupiter;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.casassg.projectjupiter.data.MomentContract;
import com.casassg.projectjupiter.model.Moment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by casassg on 19/06/15.
 *
 * @author casassg
 */
public class Utility {


    private static final String LOG = Utility.class.getSimpleName();

    public static String formatDate(Date date) {
        DateFormat format = SimpleDateFormat.getDateTimeInstance();
        return format.format(date);
    }

    public static Moment getMoment(Cursor cursor) {
        Moment ret = new Moment();

        ret.setTitle(cursor.getString(MomentContract.MomentEntry.COL_TITLE_IND));
        ret.setDate(new Date(cursor.getLong(MomentContract.MomentEntry.COL_DATE_IND)));
        ret.setX_coord(cursor.getDouble(MomentContract.MomentEntry.COL_COORD_X_IND));
        ret.setY_coord(cursor.getDouble(MomentContract.MomentEntry.COL_COORD_Y_IND));
        ret.setRating(cursor.getDouble(MomentContract.MomentEntry.COL_RATING_IND));
        ret.setId(cursor.getLong(MomentContract.MomentEntry.COL_ID_IND));
        return ret;
    }

    public static long saveMoment(Moment moment, Context context) {
        long momentID;

        Cursor momentCursor = context.getContentResolver().query(
                MomentContract.MomentEntry.CONTENT_URI,
                new String[]{MomentContract.MomentEntry._ID},
                MomentContract.MomentEntry._ID + " = ?",
                new String[]{String.valueOf(moment.getId())},
                null
        );

        if (momentCursor.moveToFirst()) {
            int momentIdIndex = momentCursor.getColumnIndex(MomentContract.MomentEntry._ID);
            momentID = momentCursor.getLong(momentIdIndex);
        } else {
            ContentValues values = new ContentValues();

            values.put(MomentContract.MomentEntry.COL_TITLE, moment.getTitle());
            values.put(MomentContract.MomentEntry.COL_RATING, moment.getRating());
            values.put(MomentContract.MomentEntry.COL_COORD_X, moment.getX_coord());
            values.put(MomentContract.MomentEntry.COL_COORD_Y, moment.getY_coord());
            values.put(MomentContract.MomentEntry.COL_DATE, moment.getDate().getTime());

            Uri insertedUri = context.getContentResolver().insert(
                    MomentContract.MomentEntry.CONTENT_URI,
                    values
            );
            momentID = ContentUris.parseId(insertedUri);

            Log.i(LOG, "Saved new moment " + momentID);
        }
        momentCursor.close();
        return momentID;
    }

    public static int deleteMoment(Moment moment, Context context) {
        int result = context.getContentResolver().delete(
                MomentContract.MomentEntry.buildMomentUri(moment.getId()),
                null,
                null
                );


        return result;

    }


    public static void showLocationDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.location_request_title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent settings = new Intent("com.google.android.gms.location.settings.GOOGLE_LOCATION_SETTINGS");
                context.startActivity(settings);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setMessage(R.string.location_request_message);
        builder.create().show();
    }


}

