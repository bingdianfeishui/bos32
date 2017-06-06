package com.itheima.crm.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.domain.Customer;

@Transactional
public class CustomerService implements ICustomerService {
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public String sayHello(){
		return "Hi, web service!!";
	}

	@Override
	public List<Customer> findAll() {
		String sql = "SELECT * FROM t_customer";
		List<Customer> list = jdbcTemplate.query(sql, new RowMapper<Customer>(){
			@Override
			public Customer mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Integer id = rs.getInt("id");
				String name=rs.getString("name");
				String station = rs.getString("station");
				String telephone = rs.getString("telephone");
				String location = rs.getString("location");
				Integer decidedZoneId = rs.getInt("decidedzone_id");
				return new Customer(id, name, station, telephone, location, decidedZoneId);
			}
		});
		return list;
	}

    @Override
    public List<Customer> findListNotAssociated() {
        String sql = "SELECT * FROM t_customer WHERE decidedzone_id IS null";
        List<Customer> list = jdbcTemplate.query(sql, new RowMapper<Customer>(){
            @Override
            public Customer mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Integer id = rs.getInt("id");
                String name=rs.getString("name");
                String station = rs.getString("station");
                String telephone = rs.getString("telephone");
                String location = rs.getString("location");
                Integer decidedZoneId = rs.getInt("decidedzone_id");
                return new Customer(id, name, station, telephone, location, decidedZoneId);
            }
        });
        return list;
    }

    @Override
    public List<Customer> findListAssociatedToZone(Integer decidedZoneId) {
        String sql = "SELECT * FROM t_customer WHERE decidedzone_id=?";
        List<Customer> list = jdbcTemplate.query(sql, new RowMapper<Customer>(){
            @Override
            public Customer mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                Integer id = rs.getInt("id");
                String name=rs.getString("name");
                String station = rs.getString("station");
                String telephone = rs.getString("telephone");
                String location = rs.getString("location");
                Integer decidedZoneId = rs.getInt("decidedzone_id");
                return new Customer(id, name, station, telephone, location, decidedZoneId);
            }
        }, decidedZoneId);
        return list;
    }
	
//	public static void main(String[] args){
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:cxf.xml");
//		ICustomerService cs = (ICustomerService) context.getBean("customerService");
//		List<Customer> list = cs.findAll();
//		System.out.println(list);
//	}
}
