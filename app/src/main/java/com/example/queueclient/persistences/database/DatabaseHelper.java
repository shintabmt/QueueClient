package com.example.queueclient.persistences.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.queueclient.persistences.Server;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by shintabmt on 7/1/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "com.queue.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getName();
    private static DatabaseHelper sInstance = null;


    private Dao<Server, Long> serverDao = null;

    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        this(context, DATABASE_NAME);
    }

    private DatabaseHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(TAG, "Creating locations database");
            UpgradeDatabase.createAllTables(connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database");
    }

    public Dao<Server, Long> getServerDao() throws SQLException {
        if (serverDao == null) {
            serverDao = getDao(Server.class);
        }
        return serverDao;
    }

    @Override
    public void close() {
        super.close();
        serverDao = null;
    }
}
