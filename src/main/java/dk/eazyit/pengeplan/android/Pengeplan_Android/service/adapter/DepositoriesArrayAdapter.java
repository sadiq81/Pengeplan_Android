package dk.eazyit.pengeplan.android.Pengeplan_Android.service.adapter;

import android.content.Context;

/**
 * @author
 */
public class DepositoriesArrayAdapter extends PengeplanAdapter {

    public DepositoriesArrayAdapter(Context context) {
        super(context, null);
    }

    @Override
    protected String getSelectRawStatement() {
        return "ownedAccount, SUM(amount) AS AMOUNT, SUM(localAmount) AS AMOUNT_LOCAL, currency";
    }

    @Override
    protected String getGroupBy() {
        return "ownedAccount";
    }

    @Override
    protected String getLeftCellContent(int position) {
        String ownedAccount = res.get(position)[0];
        return ownedAccount.substring(0, ownedAccount.lastIndexOf("-"));
    }

    @Override
    protected String getRightCellContent(int position) {
        return res.get(position)[2] + " DKK";
    }
}