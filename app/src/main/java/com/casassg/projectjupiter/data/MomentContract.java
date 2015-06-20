package com.casassg.projectjupiter.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by casassg on 19/06/15.
 *
 * @author casassg
 */
public class MomentContract {

    public static final String CONTENT_AUTHORITY = "com.moment.capture";
    public static final String PATH_MOMENT = "moment";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MomentEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOMENT).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOMENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOMENT;
        public static final String TABLE_NAME = "moment";
        public static final String COL_TITLE = "title";
        public static final String COL_RATING = "rating";

        //Columns
        public static final String COL_COORD_X = "x_coord";
        public static final String COL_COORD_Y = "y_coord";
        public static final String COL_DATE = "date";
        public static final String[] MOMENT_COLUMNS = {
                _ID,
                COL_TITLE,
                COL_RATING,
                COL_COORD_X,
                COL_COORD_Y,
                COL_DATE
        };
        public static final int COL_ID_IND = 0;
        public static final int COL_TITLE_IND = 1;
        public static final int COL_RATING_IND = 2;
        public static final int COL_COORD_X_IND = 3;
        public static final int COL_COORD_Y_IND = 4;
        public static final int COL_DATE_IND = 5;

        public static Uri buildMomentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMomentUri() {
            return CONTENT_URI;
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}
