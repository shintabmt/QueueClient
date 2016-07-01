package com.example.queueclient.persistences.database;

import android.database.sqlite.SQLiteDatabase;

import com.example.queueclient.persistences.Server;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by shintabmt on 7/1/2016.
 */
public abstract class UpgradeDatabase {
    protected static boolean freshlyCreatedTables = false;
    protected DatabaseHelper databaseHelper;

    public UpgradeDatabase(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public abstract void upgrade(SQLiteDatabase db, ConnectionSource connectionSource) throws SQLException;

    public static void createAllTables(ConnectionSource connectionSource) throws SQLException {
        freshlyCreatedTables = true;
        TableUtils.createTable(connectionSource, Server.class);

    }

    public static void dropAllTables(ConnectionSource connectionSource) throws SQLException {
        TableUtils.dropTable(connectionSource, Server.class, true);
    }
}
