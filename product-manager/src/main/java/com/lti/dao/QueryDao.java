package com.lti.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDao {
	
	public ResultSet executeQuery(Connection connection, String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}
}
