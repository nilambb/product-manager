package com.lti.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.lti.model.Company;

public class CompanyMapper implements ResultSetMapper<Company> {
	private static final String ID = "id";
	private static final String NAME = "companyName";

	@Override
	public Company map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		Company company = new Company();
		company.setId(resultSet.getInt(ID));
		company.setCompanyName(resultSet.getString(NAME));
		return company;
	}

}
