package se.andreasottesen.btcchart.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by andreas.ottesen on 2014-05-06.
 */
public class MarketListAdapter extends BaseAdapter {
    private List<BtcMarket> markets;
    private Context context;

    public MarketListAdapter(Context context, List<BtcMarket> markets){
        this.markets = markets;
        this.context = context;
    }

    public void setMarkets(List<BtcMarket> markets){
        this.markets = markets;
    }

    public List<BtcMarket> getMarkets(){
        return this.markets;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.markets_item, null);
        }

        BtcMarket market = (BtcMarket) getItem(position);
        TextView text = (TextView) view.findViewById(android.R.id.text1);
        text.setText(market.symbol);

        text = (TextView) view.findViewById(android.R.id.text2);
        text.setText(market.currency);

        CheckBox btnStar = (CheckBox)view.findViewById(R.id.btn_star);
        btnStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            }
        });

        return view;
    }
}
