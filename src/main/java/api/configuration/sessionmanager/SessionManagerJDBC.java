package api.configuration.sessionmanager;

import api.exceptions.SessionManagerException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionManagerJDBC implements SessionManager{

    private static final int TIME_OUT_IN_SECONDS = 10;
    private final DataSource dataSource;
    private Connection connection;

    public SessionManagerJDBC(DataSource dataSource){
        if (dataSource == null){
            throw new SessionManagerException("Datasource is null");
        }
        this.dataSource = dataSource;
    }
    @Override
    public void beginSession() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commitSession() {
        checkConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void rollbackSession() {
        checkConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void close() {
        checkConnection();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public Connection getCurrentSession() {
        checkConnection();
        return connection;
    }

    private void checkConnection(){
        try {
            if (connection == null || !connection.isValid(TIME_OUT_IN_SECONDS)){
                throw new SessionManagerException("Connection is invalid");
            }
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }
}
