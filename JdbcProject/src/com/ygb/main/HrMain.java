package com.ygb.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ygb.dao.HrDao;
import com.ygb.model.Employee;

public class HrMain { 
	public static void main(String[] args) {
		boolean run = true;
	    Scanner sc = new Scanner(System.in);
		
		while(run) {
			main_info();
			String name = sc.next();
			
			switch(name) {
				case "a" :
					getEmployeesByName();
					break;				
				case "b" :
					getEmployeesByYear();
					break;
				case "c" :
					prepareCall();
					break;
				case "q" :
					System.out.println("����!");
					run = false;
					break;					
				default : System.out.println("�߸� �����ϼ̽��ϴ�. �ٽ� �����ϼ���!!");
				} 
			} 
		sc.close();
		}
	
	private static void main_info() {
		System.out.println("�޴� ����!");
		System.out.println("=========================");
		System.out.println("a. ������� �˻�(�̸�) : ");
		System.out.println("b. ������� �˻�(�Ի��) : ");
		System.out.println("c. ���� �����̷� �˻�(���ID): ");
		System.out.println("q. ����");
	}

	private static void getEmployeesByName() {
		Scanner sc = new Scanner(System.in);
		HrDao dao = HrDao.getInstance();
		List<Employee> employees = new ArrayList<Employee>();
		
		System.out.println("        �̸�                      ��                  �̸���                        ��ȭ                               �Ի��� ");
		System.out.println("=============================================================================");
		System.out.print("����� First name �Ǵ� Last name�� �Է��ϼ���  : ");
		String inputS = sc.next();
		
		employees = dao.getEmployeesByName(inputS);
		System.out.println("        �̸�                      ��                  �̸���                        ��ȭ                               �Ի��� ");
		System.out.println("=============================================================================");
		if (employees.size() == 0) System.out.println("��ȸ ����� �����ϴ�.\n");
		
		for(Employee employee : employees) {
			System.out.printf("%10s", employee.getFirstName());
			System.out.printf("%10s", employee.getLastName());
			System.out.printf("%15s", employee.getEmail());
			System.out.printf("%20s", employee.getPhoneNumber());
			System.out.printf("%20s", employee.getHireDate());
			System.out.println();
		}
		//sc.close();		
	}
	private static void getEmployeesByYear() {
		Scanner sc = new Scanner(System.in);
		HrDao dao = HrDao.getInstance();
		List<Employee> employees = new ArrayList<Employee>();
	
			System.out.print("�Ի�⵵�� �Է��ϼ��� : ");
			int inputY = sc.nextInt();
			employees = dao.getEmployeesByYear(inputY);
			
			System.out.println("�����ȣ             �̸�                               ��                                    �μ�");
			System.out.println("==============================================================");
			if (employees.size() == 0) System.out.println("��ȸ ����� �����ϴ�.\n");
			
			for(Employee employee : employees) {
				System.out.print(employee.getEmployeeID());
				System.out.printf("%15s", employee.getFirstName());
				System.out.printf("%20s", employee.getLastName());
				System.out.printf("%20s", employee.getDepartmentName());
				System.out.println();
			}
			//sc.close();
		}
	private static void prepareCall() {
		Scanner sc = new Scanner(System.in);
		HrDao dao = HrDao.getInstance();    
		List<Employee> employees = new ArrayList<Employee>();
	
		System.out.println("�����ȣ�� �Է��ϼ��� : ");
		int inputH = sc.nextInt();
		employees = dao.prepareCall(inputH);
	
		System.out.println("�̸�                                                        ������                                                    ������                                      ������");
		System.out.println("===============================================================================================================================");
		if (employees.size() == 0) System.out.println("��ȸ ����� �����ϴ�.\n");

		for (Employee employee : employees) {
		 	System.out.print(employee.getFirstName());
         	System.out.printf("%30s", employee.getJobTitle());
         	System.out.printf("%30s", employee.getHireDate());
         	System.out.printf("%30s", employee.getEndDate());
         	System.out.println();
    	}
		//sc.close();
	}
}