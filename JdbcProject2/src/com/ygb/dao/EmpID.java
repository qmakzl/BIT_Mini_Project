package com.ygb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.ygb.model.Employee;

public class EmpID implements Case{
	
	@Override
	public void getData(Scanner scanner) {
		
		HrDao dao = HrDao.getInstance();
		List<Employee> employees = new ArrayList<Employee>();
			
		System.out.println("�����ȣ�� �Է��ϼ��� : ");
		int inputH = scanner.nextInt();
		employees = dao.getEmployeesById(inputH);
			
		if (employees.size() == 0) {
			System.out.println("���ǿ� �´� �����Ͱ� �����ϴ�.");
		}
		System.out.println("�̸�                                                        ������                                                    ������                                      ������");
		System.out.println("===============================================================================================================================");
		for (Employee employee : employees) {
			System.out.print(employee.getFirstName());
		    System.out.printf("%30s", employee.getJobTitle());
		    System.out.printf("%30s", employee.getHireDate());
		    System.out.printf("%30s", employee.getEndDate());
		    System.out.println();
		}
	}
}