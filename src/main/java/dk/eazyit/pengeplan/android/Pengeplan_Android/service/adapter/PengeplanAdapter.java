package dk.eazyit.pengeplan.android.Pengeplan_Android.service.adapter;

import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.androidplot.Plot;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import dk.eazyit.pengeplan.android.Pengeplan_Android.R;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Transaction;
import dk.eazyit.pengeplan.android.Pengeplan_Android.service.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * @author
 */
public abstract class PengeplanAdapter extends ArrayAdapter<String> {


    private final Context context;
    protected List<String[]> res;
    protected Plot plot;

    protected PengeplanAdapter(Context context, Plot plot) {
        super(context, R.layout.transaction_list_cell);
        this.context = context;
        this.plot = plot;
        this.res = getData(context);
    }

    protected List<String[]> getData(Context context) {
        DatabaseHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        List<String[]> res = null;
        try {
            QueryBuilder<Transaction, Long> queryBuilder = helper.getTransactionDao().queryBuilder();

            queryBuilder.selectRaw(getSelectRawStatement());
            queryBuilder.groupBy(getGroupBy());

            GenericRawResults<String[]> results = helper.getTransactionDao().queryRaw(queryBuilder.prepareStatementString());
            res = results.getResults();
            if (res.size() == 0) res.add(new String[]{"empty","","","","","","","","","","","","","","",""});
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            return res;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.transaction_list_cell, parent, false);


        TextView name = (TextView) rowView.findViewById(R.id.transaction_list_cell_name);
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        name.setWidth(display.getWidth() / 2);
        TextView value = (TextView) rowView.findViewById(R.id.transaction_list_cell_value);
        value.setWidth(display.getWidth() / 2);

        if (res.size() == 1 && res.get(0)[0].equals("empty")){
            name.setText("Pull to refresh");
        }
        name.setText(getLeftCellContent(position));
        value.setText(getRightCellContent(position));

        return rowView;
    }

    @Override
    public int getCount() {
        return res.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        res = getData(context);
    }

    protected abstract String getSelectRawStatement();

    protected abstract String getGroupBy();

    protected abstract String getLeftCellContent(int position);

    protected abstract String getRightCellContent(int position);

}