package github.mohadian.reactivestocks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import github.mohadian.reactivestock.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.hello_world_salute)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Observable.just("Hello! Please use this app responsibly!").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                textView.setText(s);
            }
        });
    }
}
