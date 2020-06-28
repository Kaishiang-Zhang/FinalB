package com.example.finalb.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class LocationclistContract {
    public static final String AUTHORITY="com.example.finalb";

    public static final String LOCATIONS="/locations";
    public static final String FARAWAY="/faraway";
    public static final String LOCATION="/location";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + LOCATIONS);

    public static final class LocationclistEntry implements BaseColumns{
        public static final String TABLE_NAME="locationlist";
        public static final String LOCATION_ID="id";
        public static final String LOCATION_LONGITUDE="longitude";
        public static final String LOCATION_LATITUDE="latitude";
        public static final String LOCATION_NAME="name";


    }
}
