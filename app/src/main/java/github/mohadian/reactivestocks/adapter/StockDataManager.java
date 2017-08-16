package github.mohadian.reactivestocks.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import github.mohadian.reactivestocks.R;
import github.mohadian.reactivestocks.data.StockUpdate;

public class StockDataManager extends RecyclerView.Adapter<StockUpdateViewHolder> {

    private final List<StockUpdate> data = new ArrayList<>();

    public StockUpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_update_item, parent, false);
        return new StockUpdateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StockUpdateViewHolder holder, int position) {
        StockUpdate stockUpdate = data.get(position);
        holder.setStockSymbol(stockUpdate.getStockSymbol());
        holder.setPrice(stockUpdate.getPrice());
        holder.setDate(stockUpdate.getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(StockUpdate stockSymbol) {
        this.data.add(stockSymbol);
        notifyItemInserted(data.size() - 1);
    }
}
