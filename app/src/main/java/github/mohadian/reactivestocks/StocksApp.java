package github.mohadian.reactivestocks;

import android.app.Application;

import github.mohadian.reactivestocks.util.CrashReportingTree;
import timber.log.Timber;


public class StocksApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }
}
