package com.lti.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.lti.mapper.CompanyMapper;
import com.lti.model.Company;

@RegisterMapper(CompanyMapper.class)
public interface CompanyDao {
	
	@SqlQuery("select * from company;")
	public List<Company> getCompanies();
	
	@SqlQuery("select * from company where id = :id")
	public Company getCompany(@Bind("id") int id);

	@SqlQuery("select last_insert_id();")
	public int lastInsertId();

	@SqlUpdate("insert into company (companyName) values (:companyName)")
	public void createCompany(@BindBean Company company);

	 @SqlUpdate("update company set companyName = coalesce(:companyName, companyName) " +
	          " where id = :id")
	public void editCompany(@BindBean Company company);

	 @SqlUpdate("delete from company where id = :id")
	public int deleteCompany(@Bind("id") int id);

}
