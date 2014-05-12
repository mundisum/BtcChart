package se.andreasottesen.btcchart.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MarketActivity extends Activity {
    private BtcMarket market;
    private TextView txtMarket;
    private TextView txtAsk;
    private TextView txtBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        txtMarket = (TextView) findViewById(R.id.txtMarket);
        txtAsk = (TextView) findViewById(R.id.txtAsk);
        txtBid = (TextView) findViewById(R.id.txtBid);

        Bundle b = getIntent().getExtras();
        if (b != null){
            txtMarket.setText(b.getString("symbol"));
            txtAsk.setText(Float.toString(b.getFloat("ask")) + " (" + b.getString("currency") + ")");
            txtBid.setText(Float.toString(b.getFloat("bid")) + " (" + b.getString("currency") + ")");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.market, menu);
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

}
