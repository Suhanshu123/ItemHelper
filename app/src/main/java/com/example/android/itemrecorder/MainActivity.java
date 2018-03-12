package com.example.android.itemrecorder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private GUESTLIST_ADAPTER mAdapter;
    private SQLiteDatabase mDb;
    private EditText mNewGuestNameEditText;
    private EditText mItemDescription;
    private EditText mAmountSpent;
    private TextView mAmountToBePaid;
    private Cursor mCursor;
    float amountdue;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewGuestNameEditText = (EditText) this.findViewById(R.id.person_name_edit_text);
        mItemDescription = (EditText) this.findViewById(R.id.description_edit_text);
        mAmountSpent = (EditText) this.findViewById(R.id.amount_edit_text);

        mAmountToBePaid=findViewById(R.id.amount_to_paid);

        // Create a DB helper (this will create the DB if run for the first time)
        DbHelperClass dbHelper = new DbHelperClass(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding restaurant customers
        mDb = dbHelper.getWritableDatabase();





    }

    public void amountToBePaid(){
        mCursor=this.getAllGuests();
        amountdue=0;
        if(mCursor.moveToFirst()) {
            do {
                float amount = mCursor.getFloat(mCursor.getColumnIndex(ContractClass.WaitlistEntry.COLUMN_ITEM_AMOUNT));
                amountdue=amountdue+amount;
            }while (mCursor.moveToNext());

            mAmountToBePaid.setText("Amount Due "+"₹ : "+String.valueOf(amountdue));

        }
        else {
            mAmountToBePaid.setText("Amount Due "+": ₹ "+"0");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        amountToBePaid();

    }

    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {
        if (mNewGuestNameEditText.getText().length() == 0 ||
                mAmountSpent.getText().length() == 0||mItemDescription.getText().length()==0) {
            return;
        }
        //default party size to 1
        float partySize = 1;
        try {
            //mNewPartyCountEditText inputType="number", so this should always work
            partySize = Float.parseFloat(mAmountSpent.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
        }

        // Add guest info to mDb
        addNewGuest(mNewGuestNameEditText.getText().toString(), partySize,mItemDescription.getText().toString());

        amountToBePaid();

        mNewGuestNameEditText.getText().clear();
        mAmountSpent.getText().clear();
        mItemDescription.getText().clear();
        Toast.makeText(this,"List Added",Toast.LENGTH_LONG).show();

    }


    /**
     * Adds a new guest to the mDb including the party count and the current timestamp
     *
     * @param name  Guest's name
     * @param amount Number in party
     * @return id of new record added
     */
    private long addNewGuest(String name, float amount,String description) {
        ContentValues cv = new ContentValues();
        cv.put(ContractClass.WaitlistEntry.COLUMN_PERSON_NAME, name);
        cv.put(ContractClass.WaitlistEntry.COLUMN_ITEM_BOUGHT, description);
        cv.put(ContractClass.WaitlistEntry.COLUMN_ITEM_AMOUNT, amount);
        return mDb.insert(ContractClass.WaitlistEntry.TABLE_NAME, null, cv);
    }


    public void openRecView(View view) {
        Intent intent=new Intent(this,ShowRecyclerView.class);
        startActivity(intent);
    }

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

}

