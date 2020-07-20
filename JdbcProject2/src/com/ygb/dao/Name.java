package com.ygb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.ygb.model.Employee;
import com.ygb.dao.HrDao;

public class Name implements Case {
	private HrDao dao = HrDao.getInstance();
	private Connection conn = null;
	private PreparedStatement pStmt = null;
	private ResultSet rSet = null;
	@Override
	public void getData(Scanner scanner) {
		dao.getConnection();
		conn = dao.getConn();
		pStmt = dao.getpStmt();
		rSet = dao.getrSet();
		List<Employee> employees = new ArrayList<Employee>();
		System.out.println("����� First name �Ǵ� Last name�� �Է��ϼ���: ");
		String name = scanner.next();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE ");
		sb.append("FROM EMPLOYEES ");
		sb.append("WHERE FIRST_NAME LIKE ? OR LAST_NAME LIKE ?");
		String sql = sb.toString();
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, "%" + name + "%");// �̸� ���̳� �߰� �ڳ� ��𿡳� �ִ� �� �ش�Ǵ� ���� ã������!!!
			pStmt.setString(2, "%" + name + "%");// �ΰ��� �ִ� ������ FIRST NAME �� LAST NAME���� ã�� ������
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
			
			print(employees);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeConnection(); // �޼ҵ� �ϼ�
		}
	}
	private void print(List<Employee> employees) {
		System.out.println("        �̸�                      ��                  �̸���                        ��ȭ                               �Ի��� ");
		System.out.println("=============================================================================");
		
	if (employees.size() == 0) {
		System.out.println("���ǿ� �´� �����Ͱ� �����ϴ�.");
	}
		
	for (Employee employee : employees) { // ÷��(i)�� �ʿ�����ϱ� ���ο� for���� ������!
			// for(int i=0; i<array.size(); i++)
			// ���̽㿡�� for i in range()
			System.out.printf("%10s", employee.getFirstName());
			System.out.printf("%10s", employee.getLastName());
			System.out.printf("%15s", employee.getEmail());
			System.out.printf("%20s", employee.getPhoneNumber());
			System.out.printf("%20s", employee.getHireDate());
			System.out.println();
		}
	}
}
