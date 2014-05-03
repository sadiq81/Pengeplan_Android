package dk.eazyit.pengeplan.android.Pengeplan_Android.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Transaction;

import java.sql.SQLException;

/**
 * @author
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /**
     * *********************************************
     * Suggested Copy/Paste code. Everything from here to the done block.
     * **********************************************
     */

    private static final String DATABASE_NAME = "pengeplan.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Transaction, Long> transactionDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * *********************************************
     * Suggested Copy/Paste Done
     * **********************************************
     */

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Transaction.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, Transaction.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    public Dao<Transaction, Long> getTransactionDao() throws SQLException {
        if (transactionDao == null) {
            transactionDao = getDao(Transaction.class);
        }
        return transactionDao;
    }
}
