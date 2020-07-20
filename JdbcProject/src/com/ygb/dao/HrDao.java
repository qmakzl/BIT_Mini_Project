package com.ygb.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ygb.model.Employee;

import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;


public class HrDao {
	private static HrDao dao = new HrDao();
	private Connection conn;
	private ResultSet rSet;
	private PreparedStatement pStmt;
	
	private CallableStatement cstmt = null;
    private OracleCallableStatement ocstmt = null;
	
	private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String username = "c##hr"; 	
	private final String password = "1234";
	
	private HrDao() {
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public static HrDao getInstance() {
		return dao;
	}

	private void getConnection() { 
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void closeConnection() {
		try {
			if (rSet != null)
				rSet.close();
			if (pStmt != null)
				pStmt.close();
			if (conn != null)
				conn.close();
			if (cstmt != null)
				cstmt.close();
			if (ocstmt != null)
				ocstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<Employee> getEmployeesByName(String name) {
		List<Employee> employees = new ArrayList<Employee>();
		getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE ");
		sb.append("FROM EMPLOYEES ");
		sb.append("WHERE FIRST_NAME LIKE ? OR LAST_NAME LIKE ?");
		
		String sql = sb.toString();
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, "%" + name + "%");
			pStmt.setString(2, "%" + name + "%");
			rSet = pStmt.executeQuery();
			Employee employee;
			while (rSet.next()) {
				employee = new Employee();
				employee.setFirstName(rSet.getString(1));
				employee.setLastName(rSet.getString(2));
				employee.setEmail(rSet.getString(3));
				employee.setPhoneNumber(rSet.getString(4));
				employee.setHireDate(rSet.getDate(5).toString());
				employees.add(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();					//�޼ҵ� �ϼ�
		}
		return employees;
	}
	
	public List<Employee> getEmployeesByYear(int year) {
		List<Employee> employees = new ArrayList<Employee>();
		getConnection();
		
		StringBuffer sa = new StringBuffer();
		sa.append("SELECT E.EMPLOYEE_ID, E.FIRST_NAME, E.LAST_NAME, NVL((D.DEPARTMENT_NAME),'<Not Assigend>') ");
		sa.append("FROM EMPLOYEES E LEFT JOIN DEPARTMENTS D ");
		sa.append("ON E.DEPARTMENT_ID = D.DEPARTMENT_ID ");
		sa.append("WHERE TO_CHAR(E.HIRE_DATE,'YYYY') = ? ");
		sa.append("ORDER BY E.EMPLOYEE_ID");
		String sql = sa.toString();
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, year);
			
			rSet = pStmt.executeQuery();
			Employee employee;
			while (rSet.next()) {
				employee = new Employee();
				
				employee.setEmployeeID(rSet.getLong(1));
				employee.setFirstName(rSet.getString(2));
				employee.setLastName(rSet.getString(3));
				employee.setDepartmentName(rSet.getString(4));
				
				employees.add(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return employees;
	}		 

public List<Employee> prepareCall(int id) {
	List<Employee> employees = new ArrayList<Employee>();
	getConnection();
	    try {     
	        //Stored Procedure �� ȣ���ϱ� ���� JDBC Callable Statement�� ��� �մϴ�
	        cstmt =  conn.prepareCall("BEGIN cursor_pkg.sp_job_history(?,?); END;");
		            
	        //���ν����� In Parameter�� SELECT������ �ѱ�ϴ�.
	        cstmt.setInt(1, id);
		            
	        //CallableStatement�� ���� REF CURSOR OUTPUT PARAMETER��
	        //OracleTypes.CURSOR�� ����մϴ�.
	        cstmt.registerOutParameter (2, OracleTypes.CURSOR);
		            
	        //CallableStatement�� �����մϴ�.
	        cstmt.execute();
	            
	        //getCursor() method�� ����ϱ� ���� CallableStatement�� 
	        //OracleCallableStatement object�� �ٲߴϴ�.
	        ocstmt = (OracleCallableStatement)cstmt;
		            
	        //OracleCallableStatement �� getCursor() method�� ����ؼ� REF CURSOR�� 
	        //JDBC ResultSet variable �� �����մϴ�.
	        rSet =  ocstmt.getCursor (2);
		       
	        Employee employee;
	        while(rSet.next()) {
	    	    employee = new Employee();
	    	    employee.setFirstName(rSet.getString(1));
	    	    employee.setJobTitle(rSet.getString(2));
	     	    employee.setHireDate(rSet.getString(3));
	    	    employee.setEndDate(rSet.getString(4));	   
	    	    employees.add(employee);
	        }
        } 
	    catch(Exception e){ } 
	    finally{ closeConnection(); }
	    return employees;    
	}
}








