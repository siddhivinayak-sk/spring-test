package com.rps.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rps.model.AppUsersDTO;

@Repository
public class CustomAppUsersRepositoryImpl implements CustomAppUsersRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<AppUsersDTO> getListAll() {
		 List<AppUsersDTO> aList = jdbcTemplate.query("select * from users", new RowMapper<AppUsersDTO> () {
			
			@Override
			public AppUsersDTO mapRow(ResultSet rs, int rowNum) {
				AppUsersDTO tmp = new AppUsersDTO();
				try {
					tmp.setId(rs.getLong(1));
					tmp.setFirstName(rs.getString(2));
					tmp.setLastName(rs.getString(3));
				}
				catch(SQLException sqlEx) {}
				return tmp;
			}
		});
		 return aList;
	}
	
}
