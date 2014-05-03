package dk.eazyit.pengeplan.android.Pengeplan_Android.service;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.androidplot.pie.PieChart;
import dk.eazyit.pengeplan.android.Pengeplan_Android.service.adapter.DepositoriesArrayAdapter;
import dk.eazyit.pengeplan.android.Pengeplan_Android.service.adapter.HistoryArrayAdapter;
import dk.eazyit.pengeplan.android.Pengeplan_Android.service.adapter.SecuritiesArrayAdapter;
import dk.eazyit.pengeplan.android.Pengeplan_Android.service.adapter.TransactionsArrayAdapter;

/**
 * @author
 */
public class TransactionService {

    private static SecuritiesArrayAdapter securitiesArrayAdapter;
    private static DepositoriesArrayAdapter depositoriesArrayAdapter;
    private static HistoryArrayAdapter historyArrayAdapter;
    private static TransactionsArrayAdapter transactionsArrayAdapter;

    public static SecuritiesArrayAdapter getSecuritiesAdapter(Context context, PieChart pie) {
        if (securitiesArrayAdapter == null) {
            securitiesArrayAdapter = new SecuritiesArrayAdapter(context, pie);
        }
        return securitiesArrayAdapter;
    }

    public static ArrayAdapter getDepositoriesAdapter(Context context) {
        if (depositoriesArrayAdapter == null) {
            depositoriesArrayAdapter = new DepositoriesArrayAdapter(context);
        }
        return depositoriesArrayAdapter;
    }

    public static ArrayAdapter getHistoryAdapter(Context context) {
        if (historyArrayAdapter == null) {
            historyArrayAdapter = new HistoryArrayAdapter(context);
        }
        return historyArrayAdapter;
    }

    public static ArrayAdapter getTransactionsAdapter(Context context) {
        if (transactionsArrayAdapter == null) {
            transactionsArrayAdapter = new TransactionsArrayAdapter(context);
        }
        return transactionsArrayAdapter;
    }
}
