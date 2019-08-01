package alihamuh.quotes.motivational.inspirational.quotesapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import static alihamuh.quotes.motivational.inspirational.quotesapplication.MainActivity.index2;
import static alihamuh.quotes.motivational.inspirational.quotesapplication.MainActivity.motivationalQuoteList;


public class ThirdFragment extends Fragment {

    public ListView quoteListView;

public ThirdFragment() {
// Required empty public constructor
}

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {

    View view =inflater.inflate(R.layout.fragment_third, container, false);


    ArrayList<CompleteQuote> singleQuoteList = new ArrayList<>();

    singleQuoteList =motivationalQuoteList;

    String[] textArray = new String[3958];
    String[] authorArray = new String[3958];
    String[] bookArray = new String[3958];
    int[] id = new int[3958];

    for(int index=0; index<3958; index++){


        textArray[index]= singleQuoteList.get(index).getQuoteText();
        authorArray[index]= singleQuoteList.get(index).getQuoteAuthor();
        bookArray[index] = singleQuoteList.get(index).getQuoteBook();
        id[index] =singleQuoteList.get(index).getID();

    }

    quoteListView =(ListView)view.findViewById(R.id.thirdFragListView);
    secondFragArrayAdapter adapter = new secondFragArrayAdapter(view.getContext(), textArray,authorArray,bookArray,id,"2");
    quoteListView.setAdapter(adapter);


// Inflate the layout for this fragment
    return view;
}


    @Override
    public void onResume() {
        super.onResume();
        quoteListView.setSelectionFromTop(index2,0);

    }

    @Override
    public void onPause() {
        super.onPause();
        index2 = quoteListView.getFirstVisiblePosition();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor =settings.edit();

        editor.putInt("index2",index2);

        editor.apply();

    }

}