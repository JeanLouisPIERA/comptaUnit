package com.dummy.myerp.consumer.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;

public class DataSourceMYERP implements javax.sql.DataSource {
	
	// ==================== Attributs ====================
    /** {@link DataSourceMYERP} */
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    
    
	// ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final DataSourceMYERP INSTANCE = new DataSourceMYERP();

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link DataSourceMYERP}
     */
    public static DataSourceMYERP getInstance() {
        return DataSourceMYERP.INSTANCE;
    }

    /**
     * Constructeur.
     */
    protected DataSourceMYERP() {
        
    }

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	
    
    
}
