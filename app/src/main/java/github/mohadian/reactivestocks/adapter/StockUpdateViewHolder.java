package github.mohadian.reactivestocks.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.mohadian.reactivestocks.R;

class StockUpdateViewHolder extends RecyclerView.ViewHolder {

    private static final NumberFormat PRICE_FORMAT = new DecimalFormat("#0.00");

    @BindView(R.id.stock_item_symbol)
    TextView stockSymbol;
    @BindView(R.id.stock_item_date)
    TextView date;
    @BindView(R.id.stock_item_price)
    TextView price;

    StockUpdateViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    void setStockSymbol(String stockSymbol) {
        this.stockSymbol.setText(stockSymbol);
    }

    void setPrice(BigDecimal price) {
        this.price.setText(PRICE_FORMAT.format(price));
    }

    void setDate(Date date) {
        this.date.setText(DateFormat.format("yyyy-MM-dd hh:mm", date));
    }
}
