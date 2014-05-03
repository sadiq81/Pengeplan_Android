package dk.eazyit.pengeplan.android.Pengeplan_Android.service.adapter;

import android.content.Context;

/**
 * @author
 */
public class HistoryArrayAdapter extends PengeplanAdapter {

    public HistoryArrayAdapter(Context context) {
        super(context,null);
    }

    @Override
    protected String getSelectRawStatement() {
        return "paperName, SUM(numberOfItems) AS ITEMS";
    }

    @Override
    protected String getGroupBy() {
        return "paperName";
    }

    @Override
    protected String getLeftCellContent(int position) {
        return res.get(position)[0];
    }

    @Override
    protected String getRightCellContent(int position) {
        return res.get(position)[1];
    }
}