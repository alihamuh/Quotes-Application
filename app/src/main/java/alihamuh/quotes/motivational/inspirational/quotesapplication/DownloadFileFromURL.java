package alihamuh.quotes.motivational.inspirational.quotesapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


/**
     * Background Async Task to download file
     * */
    public class DownloadFileFromURL extends AsyncTask<String, String, String> {

    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;


    private Context mContext;

    public DownloadFileFromURL(Context context){
        this.mContext=context;
    }

    private ProgressDialog dialog;


        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(mContext);
            this.dialog.setMessage("Downloading file. Please wait...");
            this.dialog.setIndeterminate(false);
            this.dialog.setMax(100);
            this.dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.dialog.setCancelable(false);
            this.dialog.show();
            //showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);
                //////////////////////////////////////////////////////////////////
                String appDataPath = mContext.getApplicationInfo().dataDir;
                File dbFolder = new File(appDataPath + "/databases");//Make sure the /databases folder exists
                dbFolder.mkdir();//This can be called multiple times.

                File dbFilePath = new File(appDataPath + "/databases/quotes.zip");
                // Output stream
                //OutputStream output = new FileOutputStream(Environment
                        //.getExternalStorageDirectory().toString()
                        //+ "/bukhari.zip");

                OutputStream output = new FileOutputStream(dbFilePath);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            this.dialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            this.dialog.dismiss();



            String appDataPath = mContext.getApplicationInfo().dataDir;


            unPackZipFiles zipFiles =new unPackZipFiles(appDataPath + "/databases/",appDataPath + "/databases/","/quotes.zip");

            if(zipFiles.unpackZip()){

                //Toast.makeText(getApplicationContext(),"unzipped successfully",Toast.LENGTH_SHORT).show();
                Log.d("UNZIPPED","unzipped successfully");

            }else{
                Toast.makeText(mContext.getApplicationContext(),"zip failed", Toast.LENGTH_SHORT).show();
                Log.d("FAILED","unzipping failed");
            }




            File databasePath = mContext.getApplicationContext().getDatabasePath("inspirational.db");
            File zipFile = mContext.getApplicationContext().getDatabasePath("quotes.zip");
            long filesizeinMB= (zipFile.length())/(1024*1024);
            long filesizeinMBData= (databasePath.length())/(1024*1024);

            if (zipFile.exists() && filesizeinMB>=1) {
                //is = new FileInputStream(databasePath);
                Log.d("SUCCESSFUL","download was successful");
                zipFile.delete();

                if(!zipFile.exists()){
                    Log.d("DELETED","zip file deleted");
                }

                if(databasePath.exists()&&filesizeinMBData>=2){
                    Log.d("DB","db file exists");
                }
            }else{
                Log.d("UNSUCCESSFUL","download was unsuccessful");
                //new DownloadFileFromURL(mContext).execute(file_url);
            }

            //MyDataBaseHelper db = new MyDataBaseHelper(mContext.getApplicationContext());
            //CommonSQLiteUtilities.logDatabaseInfo(db.getWritableDatabase());
            Intent i = new Intent(mContext.getApplicationContext(), MainActivity.class);
            mContext.startActivity(i);


            /*
            File databasePath = getApplicationContext().getFileStreamPath("bukhari.zip");

            long filesizeinMB= (databasePath.length())/(1024*1024);

            if (databasePath.exists() && filesizeinMB>=5) {
                //is = new FileInputStream(databasePath);
                Toast.makeText(getApplicationContext(),"File already exist and size is "+filesizeinMB,Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"File does not exist or size is"+filesizeinMB,Toast.LENGTH_SHORT).show();
            }
          */
        }

    }