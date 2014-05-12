package se.andreasottesen.btcchart.app;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import retrofit.RestAdapter;


public class MarketListActivity extends ListActivity {
    public static final String API_URL = "http://api.bitcoincharts.com";

    private ProgressDialog pDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);

        new GetDataAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.market_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class GetDataAsyncTask extends AsyncTask<Object, String, List<BtcMarket>> {
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(final List<BtcMarket> markets) {
            if (pDiag.isShowing()){
                pDiag.dismiss();
            }

            setListAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return markets.size();
                }

                @Override
                public Object getItem(int i) {
                    return markets.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    if (view == null){
                        //view = View.inflate(MarketListActivity.this, android.R.layout.two_line_list_item, null);
                        view = View.inflate(MarketListActivity.this, R.layout.markets_item, null);
                    }

                    BtcMarket market = (BtcMarket) getItem(i);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    text.setText(market.symbol);

                    text = (TextView) view.findViewById(android.R.id.text2);
                    text.setText(market.currency);

                    return view;
                }
            });
        }

        @Override
        protected void onPreExecute() {
            pDiag = new ProgressDialog(MarketListActivity.this);
            pDiag.setMessage("Fetching data, please wait.");
            pDiag.show();
        }

        @Override
        protected List<BtcMarket> doInBackground(Object... objects) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build();
            IBtcMarketService marketService = restAdapter.create(IBtcMarketService.class);
            List<BtcMarket> markets = marketService.markets();

            return markets;
        }
    }
}
