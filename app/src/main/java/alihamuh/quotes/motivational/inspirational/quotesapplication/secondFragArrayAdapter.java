package alihamuh.quotes.motivational.inspirational.quotesapplication;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.PopupMenu;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import alihamuh.quotes.motivational.inspirational.quotesapplication.DataBase_Helpers.MyDataBaseHelper;

public class secondFragArrayAdapter extends BaseAdapter{
    private final Context context;
    private final String[] engText;
    private final String[] engAuthor;
    private final String[] Book;
    private final int[] Id;
    private final String typeOf;
    private String quoteToCopy;;
    LayoutInflater inflater;


    public secondFragArrayAdapter(Context context, String[] eng, String[] aut, String[] bk, int[] id,String type) {
        this.context = context;
        this.engText = eng;
        this.engAuthor =aut;
        this.Book=bk;
        this.Id=id;
        this.typeOf=type;
        inflater =(LayoutInflater.from(context));

    }


    @Override
    public int getCount() {
        return engText.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

   static class ViewHolder{

        private TextView eng;
        private TextView author;
        private Button likeBtn;
        private Button shareBtn;
        private Button saveBtn;
        private Button copyBtn;
        private LinearLayout snap;
   }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder mViewHolder = new ViewHolder();



        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)convertView = vi.inflate(R.layout.custom_button,parent,false);

        mViewHolder.eng= (TextView)convertView.findViewById(R.id.SecondFragText);
        mViewHolder.author = (TextView) convertView.findViewById(R.id.SecondFragAuthor);


        mViewHolder.eng.setText(engText[position]);
        mViewHolder.author.setText(engAuthor[position]);

        mViewHolder.likeBtn  = (Button)convertView.findViewById(R.id.like);
        mViewHolder.shareBtn = (Button)convertView.findViewById(R.id.share);
        mViewHolder.saveBtn  = (Button)convertView.findViewById(R.id.save);
        mViewHolder.copyBtn  = (Button)convertView.findViewById(R.id.copy);

        mViewHolder.snap     = (LinearLayout)convertView.findViewById(R.id.snapshot);



        mViewHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 insertingLike(Id[position],typeOf);
                 Log.d("LIKED","POSITION IS"+Id[position]);
            }
        });

        mViewHolder.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label",quoteToCopy);



                clipboard.setPrimaryClip(clip);
                Toast.makeText(context,"Copied",Toast.LENGTH_SHORT).show();
            }
        });

        mViewHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(completeQuote(engText[position],engAuthor[position]));
            }
        });

        mViewHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Saved",Toast.LENGTH_SHORT).show();

                final Bitmap bp=loadBitmapFromView(mViewHolder.snap,mViewHolder.snap.getWidth(),mViewHolder.snap.getHeight());
                Permissions.check(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        // do your task.
                        saveImageInGallery(bp, "snap_" + position, "Quote");
                    }
                });


            }
        });


        return convertView;
    }


    public void insertingLike(int id,String type){

        ContentValues cv = new ContentValues();
        cv.put("liked",1); //These Fields should be your String values of actual column names
        MyDataBaseHelper DBHelper = new MyDataBaseHelper(context);

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        if(type.equals("1")) {


            if (checkingLike(DBHelper, id, type)) {


                db.update("inspiration", cv, "id=" + id, null);
                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(context, "Already Liked", Toast.LENGTH_SHORT).show();
            }

        }else{

            if (checkingLike(DBHelper, id, type)) {


                db.update("motivation", cv, "id=" + id, null);
                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(context, "Already Liked", Toast.LENGTH_SHORT).show();
            }

        }


        DBHelper.close();
    }

    public Boolean checkingLike(MyDataBaseHelper mDBHlpr,int id,String type){

        Cursor result=null;
        if(type.equals("1")) {
            result = mDBHlpr.getReadableDatabase().query("inspiration",
                    new String[]{"Quote", "Author", "Book", "id", "liked"}
                    , "id=" + id, null, null, null, null);
        }

        if(type.equals("2")){
            result = mDBHlpr.getReadableDatabase().query("motivation",
                    new String[]{"Quote", "Author", "Book", "id", "liked"}
                    , "id=" + id, null, null, null, null);

        }

        int liked =0;
        result.moveToFirst();
        Log.d("LIKED",""+id);

        if(result.moveToFirst()){
            do{


              liked =result.getInt(result.getColumnIndex("liked"));
                Log.d("LIKED",""+liked);



            }while(result.moveToNext());

        }

        if(liked==1){

            return false;
        }

        return true;

    }



    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        //v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    public void saveImageInGallery(Bitmap yourBitmap,String yourTitle, String yourDescription){

        MediaStore.Images.Media.insertImage(context.getContentResolver(), yourBitmap, yourTitle , yourDescription);
    }


    public String completeQuote(String text,String author){

        return text+"\n\n"+author;
    }

/*
    private View.OnClickListener onShowPopupMenu() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.setOnMenuItemClickListener(onPopupMenuClickListener());
                popupMenu.inflate(R.menu.quotes_share_menu);
                popupMenu.show();
            }
        };
    }

    private PopupMenu.OnMenuItemClickListener onPopupMenuClickListener() {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.image_share:

                        //final Bitmap bp =loadBitmapFromView(mViewHolder.snap,mViewHolder.snap.getWidth(),mViewHolder.snap.getHeight());

                         //share(bp);
                        return true;
                    case R.id.text_share:

                         share(quoteToCopy);

                        return true;
                    default:
                        return false;
                }


            }
        };
    }



    public void share(Bitmap quote){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        Bitmap shareBody = quote;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Quote");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
*/

    public void share(String quote){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = quote;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Quote");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}

