package de.iteratec.schnitzel.client.frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.iteratec.schnitzel.client.R;
import de.iteratec.schnitzel.common.model.Puzzle;

/**
 * Created by mathias on 26.11.15.
 */
public class PuzzleAdapter extends ArrayAdapter<Puzzle> {
    private final Context context;
    private final Puzzle[] values;

    public PuzzleAdapter(Context context, int resource, Puzzle[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.listviewelement, parent, false);
        }else{
            rowView = convertView;
        }
        TextView textView = (TextView) rowView.findViewById(R.id.listViewElementtext);
        textView.setText(values[position].getName());
        return rowView;
    }
}
