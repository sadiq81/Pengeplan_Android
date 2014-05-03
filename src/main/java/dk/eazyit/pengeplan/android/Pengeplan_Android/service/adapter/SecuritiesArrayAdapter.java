package dk.eazyit.pengeplan.android.Pengeplan_Android.service.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * @author
 */
public class SecuritiesArrayAdapter extends PengeplanAdapter {

    EmbossMaskFilter emf = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

    public SecuritiesArrayAdapter(Context context, PieChart pie) {
        super(context, pie);
    }

    public void init(Object... params) {

    }

    @Override
    protected String getSelectRawStatement() {
        return "paperName, SUM(amount) AS AMOUNT, SUM(localAmount) AS AMOUNT_LOCAL, currency";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected String getGroupBy() {
        return "paperName";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected String getLeftCellContent(int position) {


        return res.get(position)[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected String getRightCellContent(int position) {
        return res.get(position)[1] + " " + res.get(position)[3];
    }

    @Override
    protected List<String[]> getData(Context context) {
        res = super.getData(context);

        if (plot != null) {
            plot.clear();

            for (int position = 0; position < res.size(); position++) {

                //No data in system
                if (res.get(position).length == 1){
                    return res;
                }

                Segment segment = new Segment(res.get(position)[0], new BigDecimal(res.get(position)[2]));
                SegmentFormatter segmentFormatter = new SegmentFormatter(Color.argb(255, new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                segmentFormatter.getFillPaint().setMaskFilter(emf);
                ((PieChart) plot).addSegment(segment, segmentFormatter);

            }
        }

        return res;
    }
}