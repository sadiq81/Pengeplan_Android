package dk.eazyit.pengeplan.android.Pengeplan_Android.service;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Transaction;

/**
 * @author
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[]{
            Transaction.class};

    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt", classes);
    }
}
