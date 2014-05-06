package se.andreasottesen.btcchart.app;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by andreas.ottesen on 2014-05-06.
 */
public class MarketListAdapter extends ArrayAdapter<String> {
    private HashMap<String, Integer> idMap = new HashMap<String, Integer>();

    public MarketListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        for (int i=0;i<objects.size();i++){
            idMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return idMap.get(item);
    }
}
