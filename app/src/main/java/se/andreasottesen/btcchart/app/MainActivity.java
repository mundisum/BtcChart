package se.andreasottesen.btcchart.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;


public class MainActivity extends ActionBarActivity {
    public static final String API_URL = "http://api.bitcoincharts.com";

    private ProgressDialog pDiag;
    private Button btnStart;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btnStart);
        txtResult = (TextView)findViewById(R.id.txtResult);
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

            String temp = "";
            for (BtcMarket market : markets){
                temp += market.symbol;
            }

            txtResult.setText(temp);
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
