package alihamuh.quotes.motivational.inspirational.quotesapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;



public class NotificationRecorder extends BroadcastReceiver {

/*
    String dQuote;

    public NotificationRecorder(String Quote){

        this.dQuote=Quote;
    }
    */

    public ArrayList<CompleteQuote> inspirationQuoteListReciver;
    public ArrayList<CompleteQuote> motivationalQuoteListReciver;

    @Override
    public void onReceive(Context context, Intent intent) {

        initialization(context);

        CompleteQuote DailyQuote= generatingRandomQuote();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor =settings.edit();

        editor.putInt("quoteCategory",value);
        editor.putInt("quoteNumber",DailyQuote.getID()-1);

        editor.apply();


        //Toast.makeText(context,"one hour passed",Toast.LENGTH_SHORT).show();
        int NOTIFICATION_ID = 234;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String CHANNEL_ID ="";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Daily Quote")
                .setContentText(DailyQuote.getQuoteText());

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());





    }

    private int value;
    public CompleteQuote generatingRandomQuote(){

        value =randInt(1,2);

        //Log.d("RANDOM","inital  "+value);

        if(value ==1){
            int inspirationalrandom = randInt(0,8998);

            //Log.d("RANDOM","Inspirational  "+inspirationalrandom);

            CompleteQuote quote =inspirationQuoteListReciver.get(inspirationalrandom);

            Log.d("Random",quote.getQuoteText());

            return quote;
        }else{

            int motivationalRandom = randInt(0,3957);

            //Log.d("RANDOM","Inspirational  "+motivationalRandom);

            CompleteQuote quote =motivationalQuoteListReciver.get(motivationalRandom);

            Log.d("Random",quote.getQuoteText());

            return quote;
        }
    }

    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        //
        // In particular, do NOT do 'Random rand = new Random()' here or you
        // will get not very good / not very random results.

        Random rand=new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


    public void initialization(Context context){
        InspirationDataBaseTask quotesIns = new InspirationDataBaseTask(context);

        quotesIns.execute();
        inspirationQuoteListReciver = new ArrayList<>();

        inspirationQuoteListReciver = quotesIns.doInBackground();

        motivationDatabaseTask quotesMot = new motivationDatabaseTask(context);

        quotesMot.execute();
        motivationalQuoteListReciver = new ArrayList<>();

        motivationalQuoteListReciver = quotesMot.doInBackground();
    }
}
