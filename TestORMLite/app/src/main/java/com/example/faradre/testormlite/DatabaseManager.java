package com.example.faradre.testormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by faradre on 14-01-18.
 */

public class DatabaseManager extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "Game.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Score.class);
            TableUtils.createTable(connectionSource, User.class);
            Log.i("DATABASE", "onCreate invoked");
        } catch (Exception e) {
            Log.e("DATABASE", "Can't create the DB", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Score.class,true);
            //TableUtils.dropTable(connectionSource, User.class, true);

            onCreate(database, connectionSource);
            Log.i("DATABASE", "onUpgrade invoked");
        } catch (Exception e) {
            Log.e("DATABASE", "Can't create the DB", e);
        }
    }

    public void insertScore(Score score) {
        try {
            Dao<Score, Integer> dao = getDao(Score.class);
            dao.create(score);
            Log.i("DATABASE", "insert invoked");
        } catch (SQLException e) {
            Log.e("DATABASE", "Can't insert the DB", e);
        }
    }

    public List<Score> readScores() {
        try {
            Dao<Score, Integer> dao = getDao(Score.class);
            List<Score> scores = dao.queryForAll();
            Log.i("DATABASE", "read succes");
            return scores;
        } catch (SQLException e) {
            Log.e("DATABASE", "Can't insert the DB", e);
            return null;
        }
    }
}




















