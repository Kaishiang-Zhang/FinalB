package com.example.finalb.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LocationlistDBProvider extends ContentProvider {
    private  LoactionlistDBHelper dbHelper;
    private  static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(LocationclistContract.AUTHORITY,LocationclistContract.LOCATIONS,1);
        mUriMatcher.addURI(LocationclistContract.AUTHORITY,LocationclistContract.FARAWAY,2);
        mUriMatcher.addURI(LocationclistContract.AUTHORITY,LocationclistContract.LOCATION,3);
        mUriMatcher.addURI(LocationclistContract.AUTHORITY,LocationclistContract.LOCATION+"/#",4);
    }

    @Override
    public boolean onCreate() {
        this.dbHelper = new LoactionlistDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor mCursor = null;

        switch (mUriMatcher.match(uri)){
            case 1:
                mCursor = db.query(
                        LocationclistContract.LocationclistEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                mCursor.moveToFirst();
                break;
            case 2:
                mCursor = db.query(LocationclistContract.LocationclistEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);

//                找出遠的座標
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
        mCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return mCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {return null;}

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        if(mUriMatcher.match(uri)!=3){
            throw new IllegalArgumentException("Unknown URI:"+uri);
        }
        long rowID = db.insert(LocationclistContract.LocationclistEntry.TABLE_NAME,null,values);
        if(rowID>0){
            Uri uriLocation = ContentUris.withAppendedId(uri,rowID);
            getContext().getContentResolver().notifyChange(uriLocation,null);
            return uriLocation;
        }
        throw new IllegalArgumentException("Unknown URI:"+uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count =0;
        switch (mUriMatcher.match(uri)){
            case 4:
                String rowId = uri.getPathSegments().get(1);

                count=db.delete(LocationclistContract.LocationclistEntry.TABLE_NAME,LocationclistContract.LocationclistEntry.LOCATION_ID+"="+rowId+(!TextUtils.isEmpty(selection)?" AND ("+selection+")":""),selectionArgs);
                getContext().getContentResolver().notifyChange(uri,null);
                return count;
            default:
                throw new IllegalArgumentException("UnknownURI:"+uri);
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
