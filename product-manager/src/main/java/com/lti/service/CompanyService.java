package com.lti.service;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lti.dao.CompanyDao;
import com.lti.model.Company;

public abstract class CompanyService {
	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private static final String DATABASE_ACCESS_ERROR = "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
	private static final String DATABASE_CONNECTION_ERROR = "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
	private static final String UNEXPECTED_DATABASE_ERROR = "Unexpected error occurred while attempting to reach the database. Details: ";
	private static final String SUCCESS = "Success";
	private static final String UNEXPECTED_DELETE_ERROR = "An unexpected error occurred while deleting employee.";
	private static final String COMPANY_NOT_FOUND = "Company id %s not found.";

	@CreateSqlObject
	abstract CompanyDao campanyDao();

	public List<Company> getCompanies() {
		return campanyDao().getCompanies();
	}

	public Company getCompany(int id) {
		Company company = campanyDao().getCompany(id);
		if (Objects.isNull(company)) {
			throw new WebApplicationException(String.format(COMPANY_NOT_FOUND, id), Status.NOT_FOUND);
		}
		return company;
	}

	public Company createCompany(Company company) {
		campanyDao().createCompany(company);
		return campanyDao().getCompany(campanyDao().lastInsertId());
	}

	public Company editCompany(Company company) {
		if (Objects.isNull(campanyDao().getCompany(company.getId()))) {
			throw new WebApplicationException(String.format(COMPANY_NOT_FOUND, company.getId()), Status.NOT_FOUND);
		}
		campanyDao().editCompany(company);
		return campanyDao().getCompany(company.getId());
	}

	public String deleteCompany(final int id) {
		int result = campanyDao().deleteCompany(id);
		logger.info("Result in EmployeeService.deleteEmployee is: {}", result);
		switch (result) {
		case 1:
			return SUCCESS;
		case 0:
			throw new WebApplicationException(String.format(COMPANY_NOT_FOUND, id), Status.NOT_FOUND);
		default:
			throw new WebApplicationException(UNEXPECTED_DELETE_ERROR, Status.INTERNAL_SERVER_ERROR);
		}
	}

	public String performHealthCheck() {
		try {
			campanyDao().getCompanies();
		} catch (UnableToObtainConnectionException ex) {
			return checkUnableToObtainConnectionException(ex);
		} catch (UnableToExecuteStatementException ex) {
			return checkUnableToExecuteStatementException(ex);
		} catch (Exception ex) {
			return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
		}
		return null;
	}

	private String checkUnableToObtainConnectionException(UnableToObtainConnectionException ex) {
		if (ex.getCause() instanceof java.sql.SQLNonTransientConnectionException) {
			return DATABASE_ACCESS_ERROR + ex.getCause().getLocalizedMessage();
		} else if (ex.getCause() instanceof java.sql.SQLException) {
			return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
		} else {
			return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
		}
	}

	private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
		if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
			return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
		} else {
			return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
		}
	}

}
