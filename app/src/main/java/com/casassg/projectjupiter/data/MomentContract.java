package com.casassg.projectjupiter.data;

import android.provider.BaseColumns;

/**
 * Created by casassg on 19/06/15.
 *
 * @author casassg
 */
public class MomentContract {

    public static final class MomentEntry implements BaseColumns {

        public static final String TABLE_NAME = "moment";

        //Columns

        public static final String COL_TITLE = "title";
        public static final String COL_RATING = "rating";
        public static final String COL_COORD_X = "x_coord";
        public static final String COL_COORD_Y = "y_coord";
        public static final String COL_DATE = "date";


    }
}
