package alihamuh.quotes.motivational.inspirational.quotesapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

import alihamuh.quotes.motivational.inspirational.quotesapplication.DataBase_Helpers.MyDataBaseHelper;


public class InspirationDataBaseTask extends AsyncTask<String, Void, ArrayList<CompleteQuote>> {

    private Context mContext;
    //private Cursor result;

    public InspirationDataBaseTask(Context context){
        this.mContext=context;
    }

    private AlertDialog dialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Please Wait...");
        builder.setMessage("Loading...");

        this.dialog =builder.create();
        this.dialog.setCancelable(true);
        this.dialog.show();
*/
    }


    @Override
    protected ArrayList<CompleteQuote> doInBackground(String...strings) {
        ArrayList<CompleteQuote> singleQuote = new ArrayList<>();

        MyDataBaseHelper mDBHlpr =new MyDataBaseHelper(mContext);
        //CommonSQLiteUtilities.logDatabaseInfo(mDBHlpr.getWritableDatabase());



       Cursor result = mDBHlpr.getReadableDatabase().query("inspiration",
                new String[]{"Quote","Author","Book","id"}
                ,null,null,null,null,null);

        result.moveToFirst();

        if(result.moveToFirst()){
            do{

                CompleteQuote row = new CompleteQuote();


                row.setQuoteAuthor(result.getString(result.getColumnIndex("Author")));

                row.setQuoteBook(result.getString(result.getColumnIndex("Book")));

                row.setQuoteText(result.getString(result.getColumnIndex("Quote")));

                row.setID(result.getInt(result.getColumnIndex("id")));

                singleQuote.add(row);

            }while(result.moveToNext());

        }



        result.close();
        mDBHlpr.close();

        return singleQuote;




    }


    @Override
    protected void onPostExecute(ArrayList<CompleteQuote> completeQuotes) {
        super.onPostExecute(completeQuotes);

        //this.dialog.hide();
    }
}
