package github.mohadian.reactivestocks.network;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class YahooApi {

    void setup() {
        YahooService yahooService = new RetrofitYahooServiceFactory().create();
        String query = "select * from yahoo.finance.quote where symbol in ('YHOO','AAPL','GOOG','MSFT')";
        String env = "store://datatables.org/alltableswithkeys";

        yahooService.yqlQuery(query, env)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> Timber.d(
                        data.getQuery().getResults()
                                .getQuote().get(0).getSymbol())
                );
    }

}
