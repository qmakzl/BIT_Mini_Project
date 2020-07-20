package com.ygb.main;

import java.util.Scanner;

import com.ygb.dao.Case;
import com.ygb.dao.EmpID;
import com.ygb.dao.Name;
import com.ygb.dao.Year;

public class Run {
	
	Scanner scanner = null;
	Case c = null;
	boolean check = true;
	
	private static Run run = new Run(); // main() �Լ��� ����Ǳ��� ����Ǵ� static ���๮
	
	private Run() {
		
	}
	
	public static Run getInstance() {
		return run;
	}
//=============================================================================
	public void run() {
		scanner = new Scanner(System.in);
		while(check) {
			init();
			
			String choiceMenu = scanner.next();			
			switch(choiceMenu) {
			
				case "a": c = new Name(); 			break;
				case "b": c = new Year(); 			break;
				case "c": c = new EmpID(); 			break;
				case "q": exit();					break;
				default : System.out.println("���ǿ� �´� �����Ͱ� �����ϴ�."); continue;
			}
			c.getData(scanner);
		}
	}
	
	private void init() {
		System.out.println();
		System.out.println("�޴� ����!");
		System.out.println("================");		
		System.out.println("a. ������� �˻�(�̸�) : ");
		System.out.println("b. ������� �˻�(�Ի��) : ");
		System.out.println("c. ���� �����̷� �˻�(���ID): ");
		System.out.println("q. ����");
	}
	
	private void exit() {
		System.out.println("���α׷� ����");
		if(check == true)
			check = false;
		scanner.close();
	}
}