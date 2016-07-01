package com.example.queueclient.persistences;

import android.content.Context;
import android.util.Log;

import com.example.queueclient.persistences.database.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by shintabmt on 7/1/2016.
 */
public class ServersRepository {
    private static final String TAG = ServersRepository.class.getSimpleName();
    public static DatabaseHelper databaseHelper;
    private static Dao<Server, Long> serverDao;

    public static void createServer(Context context, Server server) {
        try {
            getServerDao(context).createOrUpdate(server);
        } catch (SQLException e) {
            Log.e(TAG, "Could not persist favorite.", e);
        }
    }

    public static void updateServer(Context context, Server server) {
        try {
            getServerDao(context).update(server);
        } catch (SQLException e) {
            Log.e(TAG, "Could not update favorite.", e);
        }
    }

    public static List<Server> getServerList(Context context) {
        try {
            return getServerDao(context).queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Could not update favorite.", e);
            return null;
        }
    }

    private static Dao<Server, Long> getServerDao(Context context) throws SQLException {
        if (serverDao == null) {
            serverDao = getDatabaseHelper(context).getServerDao();
        }
        return serverDao;
    }

    private static DatabaseHelper getDatabaseHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = DatabaseHelper.getInstance(context);
        }
        return databaseHelper;
    }
}
