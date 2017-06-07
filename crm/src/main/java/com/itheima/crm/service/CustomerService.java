package com.itheima.crm.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.itheima.crm.domain.Customer;

@Transactional
public class CustomerService implements ICustomerService {
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public String sayHello(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		System.out.println("请求IP："+request.getHeader("x-forwarded-for"));
		System.out.println();
		
		return "Hi, web service!! " + request.getHeader("x-forwarded-for");
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
				String address = rs.getString("address");
				Integer decidedZoneId = rs.getInt("decidedzone_id");
				return new Customer(id, name, station, telephone, address, decidedZoneId);
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
                String address = rs.getString("address");
                Integer decidedZoneId = rs.getInt("decidedzone_id");
                return new Customer(id, name, station, telephone, address, decidedZoneId);
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
                String address = rs.getString("address");
                Integer decidedZoneId = rs.getInt("decidedzone_id");
                return new Customer(id, name, station, telephone, address, decidedZoneId);
            }
        }, decidedZoneId);
        return list;
    }

	@Override
	public void associateCustomersToDecidedZone(Integer decidedZoneId,
			Integer[] customersIds) {
//		System.out.println(decidedZoneId);
//		System.out.println(customersIds);
		String sql1 = "UPDATE t_customer SET decidedzone_id=NULL where decidedzone_id=?";
		jdbcTemplate.update(sql1, decidedZoneId);
		
		if(customersIds!=null){
			String sql2 = "UPDATE t_customer SET decidedzone_id=? where id=?";
			for (Integer id : customersIds) {
				jdbcTemplate.update(sql2, decidedZoneId, id);
			}
		}
	}
	
//	public static void main(String[] args){
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:cxf.xml");
//		ICustomerService cs = (ICustomerService) context.getBean("customerService");
//		List<Customer> list = cs.findAll();
//		System.out.println(list);
//	}
}
