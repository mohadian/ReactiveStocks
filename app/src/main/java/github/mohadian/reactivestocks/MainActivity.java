package github.mohadian.reactivestocks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.mohadian.reactivestocks.adapter.StockDataManager;
import github.mohadian.reactivestocks.data.StockUpdate;
import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.hello_world_salute)
    TextView textView;
    @BindView(R.id.stock_updates_recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private StockDataManager stockDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Observable.just("Hello! Please use this app responsibly!").subscribe(s -> textView.setText(s));

        prepareRecyclerView();

        Observable.just(new StockUpdate("GOOGLE", 12.43, new Date()), new StockUpdate("APPL", 645.1, new Date()), new StockUpdate("TWTR", 1.43, new Date())).doOnNext(stockUpdate -> Log.d(TAG, stockUpdate.getStockSymbol())).subscribe(stockSymbol -> stockDataManager.add(stockSymbol));
    }

    private void prepareRecyclerView() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        stockDataManager = new StockDataManager();
        recyclerView.setAdapter(stockDataManager);
    }
}
