package github.mohadian.reactivestocks.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StockUpdate implements Serializable {

    private final String stockSymbol;
    private final BigDecimal price;
    private final Date date;

    public StockUpdate(String stockSymbol, BigDecimal price, Date date) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.date = date;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public static StockUpdate create(YahooStockQuote r) {
        String stock;
        BigDecimal price;
        Date date;
        return new StockUpdate(r.getName(), r.getLastTradePriceOnly(), new Date());
    }
}
