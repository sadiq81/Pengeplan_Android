package dk.eazyit.pengeplan.android.Pengeplan_Android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import dk.eazyit.pengeplan.android.Pengeplan_Android.R;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Constants;
import dk.eazyit.pengeplan.android.Pengeplan_Android.service.TransactionService;

/**
 * @author
 */
public class Transactions extends PengeplanActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transactions);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.CHOSEN_STOCK);
        setTitle(message);

        final ListView listViewSecurities = (ListView) findViewById(R.id.transactionsListView);
        listViewSecurities.setAdapter(TransactionService.getTransactionsAdapter(this));

    }

}
