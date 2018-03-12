package com.example.android.itemrecorder;

import android.provider.BaseColumns;

/**
 * Created by Suhanshu on 11-03-2018.
 */

public class ContractClass {
        public static final class WaitlistEntry implements BaseColumns {
            public static final String TABLE_NAME = "itemrecorder";
            public static final String COLUMN_PERSON_NAME = "guestName";
            public static final String COLUMN_ITEM_BOUGHT = "itembought";
            public static final String COLUMN_ITEM_AMOUNT= "itemtaken";
            public static final String COLUMN_TIMESTAMP = "timestamp";
        }

    }

