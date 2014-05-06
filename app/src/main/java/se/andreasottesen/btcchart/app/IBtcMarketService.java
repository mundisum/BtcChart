package se.andreasottesen.btcchart.app;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by andreas.ottesen on 2014-05-06.
 */
public interface IBtcMarketService {
    @GET("/v1/markets.json")
    List<BtcMarket> markets();
}
