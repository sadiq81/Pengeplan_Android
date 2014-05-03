package dk.eazyit.pengeplan.android.Pengeplan_Android.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import dk.eazyit.pengeplan.android.Pengeplan_Android.R;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Constants;
import dk.eazyit.pengeplan.android.Pengeplan_Android.service.TransactionService;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
public class Overview extends PengeplanActivity implements OnRefreshListener {

    private List<PullToRefreshLayout> mPullToRefreshLayouts = new ArrayList<PullToRefreshLayout>(3);
    private List<ArrayAdapter> arrayAdapters = new ArrayList<ArrayAdapter>(3);

    private PieChart pie;
    private Segment s1;
    private Segment s2;
    private Segment s3;
    private Segment s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.overview);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        TabHost.TabSpec securities = host.newTabSpec("secTab");
        securities.setIndicator(getResources().getString(R.string.securities));
        securities.setContent(R.id.securities);
        host.addTab(securities);

        TabHost.TabSpec depositories = host.newTabSpec("depTab");
        depositories.setIndicator(getResources().getString(R.string.depositories));
        depositories.setContent(R.id.depositories);
        host.addTab(depositories);

        TabHost.TabSpec history = host.newTabSpec("hisTab");
        history.setIndicator(getResources().getString(R.string.history));
        history.setContent(R.id.history);
        host.addTab(history);

        mPullToRefreshLayouts.add((PullToRefreshLayout) findViewById(R.id.ptr_layout_securities));
        ActionBarPullToRefresh.from(this).allChildrenArePullable().listener(this).setup(mPullToRefreshLayouts.get(0));

        mPullToRefreshLayouts.add((PullToRefreshLayout) findViewById(R.id.ptr_layout_depositories));
        ActionBarPullToRefresh.from(this).allChildrenArePullable().listener(this).setup(mPullToRefreshLayouts.get(1));

        mPullToRefreshLayouts.add((PullToRefreshLayout) findViewById(R.id.ptr_layout_history));
        ActionBarPullToRefresh.from(this).allChildrenArePullable().listener(this).setup(mPullToRefreshLayouts.get(2));


        pie = (PieChart) findViewById(R.id.securities_pie_chart);
        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        final ListView listViewSecurities = (ListView) findViewById(R.id.listView_securities);
        arrayAdapters.add(TransactionService.getSecuritiesAdapter(this, pie));
        listViewSecurities.setAdapter(arrayAdapters.get(0));

        final ListView listViewDepositories = (ListView) findViewById(R.id.listView_depositories);
        arrayAdapters.add(TransactionService.getDepositoriesAdapter(this));
        listViewDepositories.setAdapter(arrayAdapters.get(1));

        final ListView listViewHistory = (ListView) findViewById(R.id.listView_history);
        arrayAdapters.add(TransactionService.getHistoryAdapter(this));
        listViewHistory.setAdapter(arrayAdapters.get(2));

        listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stock = String.valueOf(((TextView) ((LinearLayout) view).getChildAt(0)).getText());
                Intent intent = new Intent(Overview.this, Transactions.class);
                intent.putExtra(Constants.CHOSEN_STOCK, stock);
                startActivity(intent);

                int i = 0;
            }
        });
    }

    public void onRefreshStarted(View view) {
        Intent intent = new Intent();
        intent.setAction(Constants.UPDATE_TRANSACTIONS);
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mMessageReceiver, new IntentFilter(Constants.UPDATED_TRANSACTIONS_COMPLETED));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (Constants.UPDATED_TRANSACTIONS_COMPLETED.equals(action)) {
                for (PullToRefreshLayout pullToRefreshLayout : mPullToRefreshLayouts) {
                    pullToRefreshLayout.setRefreshComplete();
                }
                for (ArrayAdapter arrayAdapter : arrayAdapters) {
                    arrayAdapter.notifyDataSetChanged();
                }
                pie.redraw();

            }
        }
    };

}
