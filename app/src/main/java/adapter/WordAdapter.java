package adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.miwoklanguagelearning.R;
import com.example.miwoklanguagelearning.Word;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    // Color resource value for each activity text bg color.

    int mcolorResourceId;


    public WordAdapter(@NonNull Context context, @NonNull ArrayList<Word> words, int colorResource) {
        super(context, 0, words);
        mcolorResourceId = colorResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word currentWord = getItem(position);


        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokTextView = listItemView.findViewById(R.id.miwok_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        miwokTextView.setText(currentWord.getmMiwokTranslation());


        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = listItemView.findViewById(R.id.default_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        defaultTextView.setText(currentWord.getmDefaultTranslation());


        ImageView itemImage = listItemView.findViewById(R.id.item_image);

        if (currentWord.hasImage()) {
            itemImage.setImageResource(currentWord.getmImageResource());
            itemImage.setVisibility(View.VISIBLE);
        } else {
            itemImage.setVisibility(View.GONE);
        }

        // Set the theme color for list item text view.

        View textContainer = listItemView.findViewById(R.id.text_item_bg);
        int color = ContextCompat.getColor(getContext(), mcolorResourceId);

        textContainer.setBackgroundColor(color);


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}

