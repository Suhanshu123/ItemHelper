package com.example.android.itemrecorder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.EditText;

public class ShowRecyclerView extends AppCompatActivity {
    private GUESTLIST_ADAPTER mAdapter;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recycler_view);
        RecyclerView waitlistRecyclerView;

        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DbHelperClass dbHelper = new DbHelperClass(this);

        mDb = dbHelper.getReadableDatabase();

        Cursor cursor = getAllGuests();

        mAdapter = new GUESTLIST_ADAPTER(this, cursor);

        waitlistRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // COMPLETED (4) Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // COMPLETED (5) Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // COMPLETED (8) Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                // COMPLETED (9) call removeGuest and pass through that id
                //remove from DB
                removeGuest(id);
                // COMPLETED (10) call swapCursor on mAdapter passing in getAllGuests() as the argument
                //update the list
                mAdapter.swapCursor(getAllGuests());
            }

            //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(waitlistRecyclerView);

    }

    /**
     * Query the mDb and get all guests from the waitlist table
     *
     * @return Cursor containing the list of guests
     */
    public Cursor getAllGuests() {
        return mDb.query(
                ContractClass.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ContractClass.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    private boolean removeGuest(long id) {
        // COMPLETED (2) Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WaitlistEntry._ID equals id
        return mDb.delete(ContractClass.WaitlistEntry.TABLE_NAME, ContractClass.WaitlistEntry._ID + "=" + id, null) > 0;
    }
}

