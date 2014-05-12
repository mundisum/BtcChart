package se.andreasottesen.btcchart.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andreas.ottesen on 2014-05-06.
 */
public class BtcMarket{
    String symbol;
    String currency;
    float high;
    float low;
    float ask;
    float bid;
    float close;
    int n_traders;
    float currency_volume;
    float volume;
}
