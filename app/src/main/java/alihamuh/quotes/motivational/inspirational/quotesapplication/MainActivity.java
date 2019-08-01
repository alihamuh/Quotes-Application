package alihamuh.quotes.motivational.inspirational.quotesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import alihamuh.quotes.motivational.inspirational.quotesapplication.DataBase_Helpers.CommonSQLiteUtilities;
import alihamuh.quotes.motivational.inspirational.quotesapplication.DataBase_Helpers.MyDataBaseHelper;

public class MainActivity extends AppCompatActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    private static String file_url = "https://github.com/alihamuh/book1-database/blob/master/quotes2.zip?raw=true";
    public static int index = 0;
    public static int index2=0;
    public static ArrayList<CompleteQuote> inspirationQuoteList = new ArrayList<>();
    public static ArrayList<CompleteQuote> motivationalQuoteList = new ArrayList<>();
    public static int quoteCategory;
    public static int quoteNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().setTitle("Motivational&Inspirational Quotes");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        index =settings.getInt("index",0);
        index2=settings.getInt("index2",0);





        ///////////////////////////////////////////////////////////////////////////////////////////////

        File f = getApplicationContext().getDatabasePath("inspirational.db");
        long filesizeinMB= (f.length())/(1024*1024);
        if(f.exists() && filesizeinMB>=2){
            Log.d("DB","db file exists");
            MyDataBaseHelper db = new MyDataBaseHelper(this);
            CommonSQLiteUtilities.logDatabaseInfo(db.getWritableDatabase());



            MyDataBaseHelper mDBHelper = new MyDataBaseHelper(this);
            mDBHelper.getReadableDatabase();
            mDBHelper.close();


            quoteCategory =settings.getInt("quoteCategory",1);
            quoteNumber   =settings.getInt("quoteNumber",49);


            if(!(inspirationQuoteList.size()>0)||!(motivationalQuoteList.size()>0)) {
                InspirationDataBaseTask quotesIns = new InspirationDataBaseTask(this);

                quotesIns.execute();
                inspirationQuoteList = new ArrayList<>();

                inspirationQuoteList = quotesIns.doInBackground();

                motivationDatabaseTask quotesMot = new motivationDatabaseTask(this);

                quotesMot.execute();
                motivationalQuoteList = new ArrayList<>();

                motivationalQuoteList = quotesMot.doInBackground();

            }




        }else {
            quoteCategory=0;
            DownloadFileFromURL abc = new DownloadFileFromURL(this);
            abc.execute(file_url);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////



        //getSupportActionBar().setElevation(0);



        // get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
   /*     // Create a new Tab named "First"
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("First"); // set the Text for the first Tab
        //firstTab.setIcon(R.drawable.ic_launcher_background); // set an icon for the
        // first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
        // Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Second"); // set the Text for the second Tab
        //secondTab.setIcon(R.drawable.ic_launcher_background); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
        // Create a new Tab named "Third"
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Third"); // set the Text for the first Tab
        //thirdTab.setIcon(R.drawable.ic_launcher_background); // set an icon for the first tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout
*/




/*
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FirstFragment();
                        break;
                    case 1:
                        fragment = new SecondFragment();
                        break;
                    case 2:
                        fragment = new ThirdFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        */


            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.simpleFrameLayout, new FirstFragment());
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();


        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
        // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FirstFragment();
                        break;
                    case 1:
                        fragment = new SecondFragment();
                        //flipCard();
                        break;
                    case 2:
                        fragment = new ThirdFragment();
                        break;
                }

                flipCard(fragment);
                //FragmentManager fm = getSupportFragmentManager();
                //FragmentTransaction ft = fm.beginTransaction();
                //ft.replace(R.id.simpleFrameLayout, fragment);
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        NotificationAlaram();

        //generatingRandomQuote();

    }


    private void flipCard(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.right_in, R.animator.right_out,
                        R.animator.left_in, R.animator.left_out)
                .replace(R.id.simpleFrameLayout, fragment)
                // Add this transaction to the back stack, allowing users to press Back
                // to get to the front of the card.
                .addToBackStack(null)
                // Commit the transaction.
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    void NotificationAlaram()
    {
        Calendar cal_alarm=Calendar.getInstance();

        //SharedPreferences getTime= PreferenceManager.getDefaultSharedPreferences(this);

        //int timeInMnutes=getTime.getInt("zikr_alaram",0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();
        //Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,6);
        cal_alarm.set(Calendar.MINUTE,00);
        cal_alarm.set(Calendar.SECOND,00);
        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
            //Toast.makeText(this,"one hour passed",Toast.LENGTH_SHORT).show();
        }


        Intent myIntent = new Intent(this,NotificationRecorder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        //manager.setRepeating(AlarmManager.RTC_WAKEUP,60000,AlarmManager.INTERVAL_DAY, pendingIntent);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
