package alihamuh.quotes.motivational.inspirational.quotesapplication.DataBase_Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "inspirational.db";

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        onUpgrade(sqLiteDatabase,1,2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        if (i1 > i) {

            sqLiteDatabase.execSQL("ALTER TABLE inspiration ADD COLUMN liked INTEGER DEFAULT 0");
            sqLiteDatabase.execSQL("ALTER TABLE motivation ADD COLUMN liked INTEGER DEFAULT 0");
        }
    }


}
