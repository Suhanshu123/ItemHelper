package com.example.android.itemrecorder;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Suhanshu on 11-03-2018.
 */

public class GUESTLIST_ADAPTER extends RecyclerView.Adapter<GUESTLIST_ADAPTER.GuestViewHolder> {
    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor using the context and the db cursor
     * @param context the calling context/activity
     * @param cursor the db cursor with waitlist data to display
     */
    public GUESTLIST_ADAPTER(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String name = mCursor.getString(mCursor.getColumnIndex(ContractClass.WaitlistEntry.COLUMN_PERSON_NAME));
        String itemName = mCursor.getString(mCursor.getColumnIndex(ContractClass.WaitlistEntry.COLUMN_ITEM_BOUGHT));
        float amount = mCursor.getFloat(mCursor.getColumnIndex(ContractClass.WaitlistEntry.COLUMN_ITEM_AMOUNT));
        Long idforlist=mCursor.getLong(mCursor.getColumnIndex(ContractClass.WaitlistEntry._ID));
        String time = mCursor.getString(mCursor.getColumnIndex(ContractClass.WaitlistEntry.COLUMN_TIMESTAMP));


        // Display the guest name
        holder.personName.setText("NAME : "+name);
        // Display the party count
        holder.amountSpent.setText("AMOUNT : "+"₹"+String.valueOf(amount));

        holder.timeofBought.setText("TIME OF BOUGHT : "+time);
        // Display the party count
        holder.itemdescription.setText("ITEM DESCRIPTION : "+itemName);


        // COMPLETED (7) Set the tag of the itemview in the holder to the id
        holder.itemView.setTag(idforlist);
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView personName;
        // Will display the party size number
        TextView amountSpent;

        // Will display the guest name
        TextView timeofBought;
        // Will display the party size number
        TextView itemdescription;



        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link GUESTLIST_ADAPTER#onCreateViewHolder(ViewGroup, int)}
         */
        public GuestViewHolder(View itemView) {
            super(itemView);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            amountSpent = (TextView) itemView.findViewById(R.id.amount_bought);
            timeofBought = (TextView) itemView.findViewById(R.id.time_ofbought);
            itemdescription = (TextView) itemView.findViewById(R.id.item_description);
        }

    }
}
