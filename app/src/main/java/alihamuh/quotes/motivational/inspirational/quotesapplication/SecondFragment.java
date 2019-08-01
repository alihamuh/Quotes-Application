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

import static alihamuh.quotes.motivational.inspirational.quotesapplication.MainActivity.index;
import static alihamuh.quotes.motivational.inspirational.quotesapplication.MainActivity.inspirationQuoteList;

public class SecondFragment extends Fragment {

    public ListView quoteListView;

public SecondFragment() {
// Required empty public constructor
}

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {

    View view =inflater.inflate(R.layout.fragment_second, container, false);


    ArrayList<CompleteQuote> singleQuoteList = new ArrayList<>();

    singleQuoteList =inspirationQuoteList;

    String[] textArray = new String[8999];
    String[] authorArray = new String[8999];
    String[] bookArray = new String[8999];
    int[] id = new int[8999];

    for(int index=0; index<8999; index++){


        textArray[index]= singleQuoteList.get(index).getQuoteText();
        authorArray[index]= singleQuoteList.get(index).getQuoteAuthor();
        bookArray[index] = singleQuoteList.get(index).getQuoteBook();
        id[index] =singleQuoteList.get(index).getID();

    }

    quoteListView =(ListView)view.findViewById(R.id.secondFragListView);
    secondFragArrayAdapter adapter = new secondFragArrayAdapter(view.getContext(), textArray,authorArray,bookArray,id,"1");
    quoteListView.setAdapter(adapter);


// Inflate the layout for this fragment
return view;
}

    @Override
    public void onResume() {
        super.onResume();
       quoteListView.setSelectionFromTop(index,0);

    }

    @Override
    public void onPause() {
        super.onPause();
        index = quoteListView.getFirstVisiblePosition();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor =settings.edit();

        editor.putInt("index",index);

        editor.apply();

    }
}