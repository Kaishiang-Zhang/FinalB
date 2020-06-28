package com.example.finalb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalb.data.LoactionlistDBHelper;
import com.example.finalb.data.LocationclistContract;
import com.example.finalb.data.LocationlistDBProvider;

public class MainActivity extends AppCompatActivity {
    private LocationlistAdapter mAdapter;
    private EditText mlongitudeEditText;
    private EditText mlatitudeEditText;
    private EditText mnameEditText;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView locationlist_ReV;
        locationlist_ReV =(RecyclerView)findViewById(R.id.location_rev);
        mlongitudeEditText =(EditText)findViewById(R.id.location_longitude);
        mlatitudeEditText =(EditText)findViewById(R.id.location_latitude);
        mnameEditText =(EditText)findViewById(R.id.location_name);
        locationlist_ReV.setLayoutManager(new LinearLayoutManager(this));

        LoactionlistDBHelper dbHelper = new LoactionlistDBHelper(this);
        mDb = dbHelper.getWritableDatabase();

//        ContentValues values = new ContentValues();
//        values.put(LocationclistContract.LocationclistEntry.LOCATION_ID, 1);
//        values.put(LocationclistContract.LocationclistEntry.LOCATION_LONGITUDE, 18.6);
//        values.put(LocationclistContract.LocationclistEntry.LOCATION_LATITUDE, 130);
//        values.put(LocationclistContract.LocationclistEntry.LOCATION_NAME,"NTUST");
//        mDb.insert(LocationclistContract.LocationclistEntry.TABLE_NAME, null, values);

        Cursor cursor =getLocation();
        mAdapter =new LocationlistAdapter(this,cursor);
        locationlist_ReV.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                Log.d("delete",String.valueOf(id));
                removeLocation(id);
                mAdapter.swapCursor(getLocation());
            }
        }).attachToRecyclerView(locationlist_ReV);

    }

    public void addtoLocationlist(View view){
        if(mnameEditText.getText().length()==0||mlongitudeEditText.getText().length()==0||mlatitudeEditText.getText().length()==0){return;}
        Double longitude =0.0;
        Double latitude =0.0;
        longitude =Double.parseDouble(mlongitudeEditText.getText().toString());
        latitude =Double.parseDouble(mlatitudeEditText.getText().toString());
        String name =mnameEditText.getText().toString();
        addNewLocation(longitude,latitude,name);
        mAdapter.swapCursor(getLocation());

        mlongitudeEditText.getText().clear();
        mlatitudeEditText.getText().clear();
        mnameEditText.getText().clear();
    }
    private void addNewLocation(double lgt,double ltd,String name){
        ContentValues cv = new ContentValues();
        cv.put(LocationclistContract.LocationclistEntry.LOCATION_LONGITUDE,lgt);
        cv.put(LocationclistContract.LocationclistEntry.LOCATION_LATITUDE,ltd);
        cv.put(LocationclistContract.LocationclistEntry.LOCATION_NAME,name);

        Uri uri=Uri.parse("content://"+ LocationclistContract.AUTHORITY+LocationclistContract.LOCATION);
        Uri muri =getContentResolver().insert(uri,cv);
    }

    private Cursor getLocation(){
//        return mDb.query(LocationclistContract.LocationclistEntry.TABLE_NAME,null,null,null,null,null,null);
        Uri CONTENT_URI= LocationclistContract.CONTENT_URI;
        Cursor cursor= getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null);
        if(cursor!=null){
            Log.d("test","cursor!");
            return cursor;
        }
        return null;
    }
    private int removeLocation(long id){
        Uri uri =Uri.parse("content://"+LocationclistContract.AUTHORITY+LocationclistContract.LOCATION+"/"+String.valueOf(id));
        int delete=getContentResolver().delete(uri,null,null);
        return delete;
    }
}
