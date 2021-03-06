package se.andreasottesen.btcchart.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by andreas.ottesen on 2014-05-06.
 */
public class MarketListAdapter extends BaseAdapter {
    private List<BtcMarket> markets;
    private Context context;
    private boolean[] starStates;

    public MarketListAdapter(Context context, List<BtcMarket> markets){
        this.markets = markets;
        this.context = context;

        if (markets != null) {
            this.starStates = new boolean[markets.size()];
        }
    }

    public void setMarkets(List<BtcMarket> markets){
        this.markets = markets;
        this.starStates = new boolean[markets.size()];
    }

    public List<BtcMarket> getMarkets(){
        return this.markets;
    }

    public boolean[] getStarStates(){
        return this.starStates;
    }

    @Override
    public int getCount() {
        if (markets != null)
            return markets.size();

        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (markets != null)
            return markets.get(i);

        return null;
    }

    @Override
    public long getItemId(int i) {
        if (markets != null)
            return markets.get(i).hashCode();

        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        MarketViewHolder holder = null;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.markets_item, null);
        }

        holder = new MarketViewHolder();
        holder.star = (CheckBox)view.findViewById(R.id.btn_star);
        holder.star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ListView listView = (ListView)parent;
                final int position = listView.getPositionForView(buttonView);
                starStates[position] = isChecked;
            }
        });

        holder.market = (BtcMarket) getItem(position);
        holder.content = (TextView) view.findViewById(android.R.id.text1);
        holder.content.setText(holder.market.symbol);

        holder.currency = (TextView) view.findViewById(android.R.id.text2);
        holder.currency.setText(holder.market.currency);

        return view;
    }

}
