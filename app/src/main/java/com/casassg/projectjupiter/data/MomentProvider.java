package com.casassg.projectjupiter.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MomentProvider extends ContentProvider {


    static final int MOMENT = 100;
    static final int MOMENTS = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static final String SELECTION_ID = "_id = ?";
    private MomentDbHelper mDBHelper;

    public MomentProvider() {
    }

    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MomentContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MomentContract.PATH_MOMENT, MOMENTS);
        matcher.addURI(authority, MomentContract.PATH_MOMENT + "/#", MOMENT);

        return matcher;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case MOMENT:
                rowsDeleted = db.delete(
                        MomentContract.MomentEntry.TABLE_NAME,
                        SELECTION_ID,
                        getSelectionIdArgsFromUri(uri));
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case MOMENT:
                return MomentContract.MomentEntry.CONTENT_ITEM_TYPE;
            case MOMENTS:
                return MomentContract.MomentEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        if (match == MOMENTS) {
            long _id = db.insert(MomentContract.MomentEntry.TABLE_NAME, null, values);
            if (_id > 0)
                returnUri = MomentContract.MomentEntry.buildMomentUri(_id);
            else
                throw new android.database.SQLException("Failed to insert row into " + uri);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new MomentDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "location"
            case MOMENTS: {
                retCursor = mDBHelper.getReadableDatabase().query(
                        MomentContract.MomentEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MOMENT: {
                retCursor = mDBHelper.getReadableDatabase().query(
                        MomentContract.MomentEntry.TABLE_NAME,
                        projection,
                        SELECTION_ID,
                        getSelectionIdArgsFromUri(uri),
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private String[] getSelectionIdArgsFromUri(Uri uri) {
        return new String[]{MomentContract.MomentEntry.getIDFromUri(uri)};
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
