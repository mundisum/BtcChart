package se.andreasottesen.btcchart.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;


public class MainActivity extends ActionBarActivity {
    public static final String API_URL = "http://api.bitcoincharts.com";

    private ProgressDialog pDiag;
    private Button btnStart;
    private TextView txtResult;
    private ListView listMarkets;
    private MarketListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btnStart);
        txtResult = (TextView)findViewById(R.id.txtResult);
        listMarkets = (ListView)findViewById(R.id.listMarkets);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void btnStartOnClick(View view){
        new GetDataAsyncTask().execute();
    }

    public class GetDataAsyncTask extends AsyncTask<Object, String, List<BtcMarket>>{
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<BtcMarket> markets) {
            if (pDiag.isShowing()){
                pDiag.dismiss();
            }
            final ArrayList<String> list = new ArrayList<String>();
            for (BtcMarket market : markets){
                list.add(market.symbol);
            }

            listAdapter = new MarketListAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
            listMarkets.setAdapter(listAdapter);

            listMarkets.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                    final String item = (String)adapterView.getItemAtPosition(i);
                    view.animate().setDuration(2000).alpha(0)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    list.remove(item);
                                    listAdapter.notifyDataSetChanged();
                                    view.setAlpha(1);
                                }
                            });
                }
            });
        }

        @Override
        protected void onPreExecute() {
            pDiag = new ProgressDialog(MainActivity.this);
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
