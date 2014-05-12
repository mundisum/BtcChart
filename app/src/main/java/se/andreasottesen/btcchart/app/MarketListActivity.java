package se.andreasottesen.btcchart.app;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;


public class MarketListActivity extends ListActivity {
    public static final String API_URL = "http://api.bitcoincharts.com";
    public static final String STAR_STATES = "btcchart:star_states";

    private boolean[] starStates;
    private ProgressDialog pDiag;
    private MarketListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);

        /*if (savedInstanceState != null){
            starStates = savedInstanceState.getBooleanArray(STAR_STATES);
        }else{

        }*/

        listAdapter = new MarketListAdapter(MarketListActivity.this, null);
        setListAdapter(listAdapter);

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        BtcMarket market = (BtcMarket) listAdapter.getItem(position);

        Intent intent = new Intent();
        intent.putExtra("symbol", market.symbol);
        intent.putExtra("ask", market.ask);
        intent.putExtra("bid", market.bid);
        intent.putExtra("currency", market.currency);

        intent.setClass(MarketListActivity.this, MarketActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        starStates = listAdapter.getStarStates();
        outState.putBooleanArray(STAR_STATES, starStates);
    }

    private void showMessageToast(String message){
        Toast.makeText(MarketListActivity.this, message, Toast.LENGTH_LONG);
    }

    private class GetDataAsyncTask extends AsyncTask<Object, String, List<BtcMarket>> {
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(final List<BtcMarket> markets) {
            if (pDiag.isShowing()){
                pDiag.dismiss();
            }

            //starStates = listAdapter.getStarStates();
            listAdapter.setMarkets(markets);
            listAdapter.notifyDataSetChanged();
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
