package dk.eazyit.pengeplan.android.Pengeplan_Android.service.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Constants;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Transaction;
import dk.eazyit.pengeplan.android.Pengeplan_Android.service.DatabaseHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
public class TransactionsArrayAdapter extends PengeplanAdapter {


    public TransactionsArrayAdapter(Context context) {
        super(context, null);
    }

    @Override
    protected List<String[]> getData(Context context) {
        Intent intent = ((Activity) context).getIntent();
        String stock = intent.getStringExtra(Constants.CHOSEN_STOCK);
        DatabaseHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        List<String[]> res = null;
        try {
            QueryBuilder<Transaction, Long> queryBuilder = helper.getTransactionDao().queryBuilder();

            queryBuilder.where().eq(Transaction.PAPER_NAME, stock);

            GenericRawResults<String[]> results = helper.getTransactionDao().queryRaw(queryBuilder.prepareStatementString());
            res = results.getResults();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            return res;
        }
    }

    @Override
    protected String getSelectRawStatement() {
        return null;
    }

    @Override
    protected String getGroupBy() {
        return null;
    }

    @Override
    protected String getLeftCellContent(int position) {
        SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat after = new SimpleDateFormat("dd-MM-yy");
        String dateShort = res.get(position)[2];

        try {
            Date date = before.parse(res.get(position)[2].substring(0,10));
            dateShort = after.format(date);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return dateShort;
    }

    @Override
    protected String getRightCellContent(int position) {
        BigDecimal numberOfUnites = new BigDecimal(res.get(position)[6]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal amount = new BigDecimal(res.get(position)[0]).setScale(2, RoundingMode.HALF_UP);

        return res.get(position)[10] + " " + numberOfUnites.toString() + " for " + amount.toString() + " " + res.get(position)[9] ;
    }
}