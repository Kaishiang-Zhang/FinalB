package com.example.finalb;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalb.data.LocationclistContract;

public class LocationlistAdapter extends RecyclerView.Adapter<LocationlistAdapter.LocationViewHolder> {
    private Cursor mCursor;
    private Context mContext;


    public LocationlistAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }
    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.location_list, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LocationViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position))
            return;
        long id =mCursor.getLong(mCursor.getColumnIndex(LocationclistContract.LocationclistEntry.LOCATION_ID));
        double longitude =mCursor.getDouble(mCursor.getColumnIndex(LocationclistContract.LocationclistEntry.LOCATION_LONGITUDE));
        double latitude =mCursor.getDouble(mCursor.getColumnIndex(LocationclistContract.LocationclistEntry.LOCATION_LATITUDE));
        String name =mCursor.getString(mCursor.getColumnIndex(LocationclistContract.LocationclistEntry.LOCATION_NAME));

        holder.IDTextView.setText(String.valueOf(id));
        holder.LongitudeTextView.setText(String.valueOf(longitude));
        holder.LatitudeTextView.setText(String.valueOf(latitude));
        holder.NameTextView.setText(name);
        holder.itemView.setTag(id);


    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }
    @Override
    public int getItemCount() {return mCursor.getCount();}

    class LocationViewHolder extends RecyclerView.ViewHolder{
        TextView IDTextView;
        TextView LongitudeTextView;
        TextView LatitudeTextView;
        TextView NameTextView;
        public  LocationViewHolder(View itemView){
            super(itemView);
            IDTextView = (TextView) itemView.findViewById(R.id.location_id);
            LongitudeTextView = (TextView) itemView.findViewById(R.id.location_longitude);
            LatitudeTextView = (TextView) itemView.findViewById(R.id.location_latitude);
            NameTextView = (TextView) itemView.findViewById(R.id.location_name);
        }
    }
}
