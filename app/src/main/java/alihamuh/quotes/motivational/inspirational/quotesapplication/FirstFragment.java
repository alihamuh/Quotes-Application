package alihamuh.quotes.motivational.inspirational.quotesapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import static alihamuh.quotes.motivational.inspirational.quotesapplication.MainActivity.inspirationQuoteList;
import static alihamuh.quotes.motivational.inspirational.quotesapplication.MainActivity.motivationalQuoteList;
import static alihamuh.quotes.motivational.inspirational.quotesapplication.MainActivity.quoteCategory;
import static alihamuh.quotes.motivational.inspirational.quotesapplication.MainActivity.quoteNumber;

public class FirstFragment extends Fragment {

public FirstFragment() {
// Required empty public constructor
}

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState)
{
// Inflate the layout for this fragment
    View view =inflater.inflate(R.layout.fragment_first, container, false);

    CompleteQuote daily;




    TextView first =(TextView)view.findViewById(R.id.firstFragment);
    TextView firstA =(TextView)view.findViewById(R.id.firstAuthor);
    first.setMovementMethod(new ScrollingMovementMethod());
    Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/purisa.ttf");
    Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(),"fonts/purisabold.ttf");
    first.setTypeface(tf);
    firstA.setTypeface(tf2);


    if(quoteCategory==1){
        daily =inspirationQuoteList.get(quoteNumber);
        first.setText(daily.getQuoteText());
        firstA.setText(daily.getQuoteAuthor());
    }else if (quoteCategory==2){
        daily =motivationalQuoteList.get(quoteNumber);
        first.setText(daily.getQuoteText());
        firstA.setText(daily.getQuoteAuthor());
    }else if(quoteCategory ==0){

    }



    return view;
}

}