package github.mohadian.reactivestocks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.mohadian.reactivestocks.adapter.StockDataManager;
import github.mohadian.reactivestocks.data.StockUpdate;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

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
        prepareRecyclerView();

        Observable.just("Hello! Please use this app responsibly!")
                .subscribeOn(Schedulers.io())
                .doOnDispose(() -> log("doOnDispose"))
                .doOnComplete(() -> log("doOnComplete"))
                .doOnNext(e -> log("doOnNext", e))
                .doOnEach(e -> log("doOnEach"))
                .doOnSubscribe((e) -> log("doOnSubscribe"))
                .doOnTerminate(() -> log("doOnTerminate"))
                .doFinally(() -> log("doFinally"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    log("subscribe", data);
                    Log.d(TAG, "subscribe " + Thread.currentThread() + " data: " + data);
                    textView.setText(data);
                });

        Observable.just(new StockUpdate("GOOGLE", 12.43, new Date()), new StockUpdate("APPL", 645.1, new Date()), new StockUpdate("TWTR", 1.43, new Date()))
                .subscribe(stockSymbol -> stockDataManager.add(stockSymbol));

        flowableExample();

        completableExample();

        singleExample();

        maybeExample();
    }

    /**
     * Finally, the Maybe type is very similar to the Single type, but the item might not be returned to the subscriber in the end.
     */
    private void maybeExample() {
        Maybe.just("Item")
                .subscribe(
                        s -> log("success: " + s),
                        throwable -> log("error"),
                        () -> log("onComplete")
                );
    }

    /**
     * Single provides a way to represent an Observable that will return just a single item (thus the name).
     */
    private void singleExample() {
        Single.just("One item")
                .subscribe((item) -> {
                    log(item);
                }, (throwable) -> {
                    log(throwable);
                });
    }

    /**
     * Completable: This represents an action without a result that will be completed in the future
     */
    private void completableExample() {
        Completable completable = Completable.fromAction(() -> {
            log("Let's do something");
        });

        completable.subscribe(() -> {
            log("Finished");
        }, throwable -> {
            log(throwable);
        });
    }

    /**
     * Flowable can be regarded as a special type of Observable (but internally it isn't). It has
     * almost the same method signature such as the Observable as well.
     * <p>
     * The difference is that Flowable allows you to process items that emitted faster from the
     * source than some of the following steps can handle.
     */
    private void flowableExample() {
        PublishSubject<Integer> observable = PublishSubject.create();
        observable.observeOn(Schedulers.computation())
                .subscribe(v -> log("s", v.toString()), this::log);
        for (int i = 0; i < 1000000; i++) {
            observable.onNext(i);
        }

        observable.toFlowable(BackpressureStrategy.MISSING)
                .subscribe(v -> log("s", v.toString()), this::log);

        if(1==1) return; //ignore the rest of method

        //Dropping items: Dropping means that if the downstream processing steps cannot keep up with
        // the pace of the source Observable, they will just drop the data that cannot be handled.
        // This can only be used in cases when losing data is okay, and you care more about the
        // values that were emitted in the beginning.
        observable.toFlowable(BackpressureStrategy.DROP);
        //or
        observable.toFlowable(BackpressureStrategy.MISSING)
                .onBackpressureDrop();

        //Preserve latest: Preserving the last items means that if the downstream cannot cope with
        // the items that are being sent to them, the app will stop emitting values and wait until
        // they become available.
        observable.toFlowable(BackpressureStrategy.LATEST);
        //or
        observable.toFlowable(BackpressureStrategy.MISSING)
                .onBackpressureLatest();
        //a method, .debounce(), can periodically take the last value at specific intervals:
        observable.toFlowable(BackpressureStrategy.MISSING)
                .debounce(10, TimeUnit.MILLISECONDS);

        //Buffering
        //if there is just a temporal slowdown in one of the consumers. In this case, the items
        // emitted will be stored until later processing and when the slowdown is over, the
        // consumers will catch up. If the consumers cannot catch up, at some point the buffer
        // will run out of memory

        observable.toFlowable(BackpressureStrategy.BUFFER);
        //or
        observable.toFlowable(BackpressureStrategy.MISSING)
                .onBackpressureBuffer();

        observable.toFlowable(BackpressureStrategy.MISSING)
                .buffer(10);

    }

    private void log(Throwable throwable) {
        Log.e("APP", "Error", throwable);
    }

    private void log(String stage, String item) {
        Log.d(TAG, stage + ":" + Thread.currentThread().getName() + ":" +
                item);
    }

    private void log(String stage) {
        Log.d(TAG, stage + ":" + Thread.currentThread().getName());
    }


    private void prepareRecyclerView() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        stockDataManager = new StockDataManager();
        recyclerView.setAdapter(stockDataManager);
    }
}
