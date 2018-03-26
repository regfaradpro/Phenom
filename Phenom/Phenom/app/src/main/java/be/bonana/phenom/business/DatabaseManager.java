package be.bonana.phenom.business;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by faradre on 26-03-18.
 */

public class DatabaseManager extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "Phenom.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Challenge.class);
            TableUtils.createTable(connectionSource, User.class);
            Log.i("DATABASE", "onCreate invoked");
        } catch (Exception e) {
            Log.e("DATABASE", "Can't create the DB", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
