package GA;

import java.util.ArrayList;

import tools.ExcelReader;

public class WholeSolution {
	public ArrayList<ClassSolution> wholeSolution=new ArrayList<ClassSolution>();
	/*
	 * �����еĿγ̶���ʼ������֤�����еĿγ̶�����ʼ����
	 */
	public void init() {
		for(int i=0;i<ExcelReader.classNum;i++) {
			ClassSolution cs=new ClassSolution();
			cs.init();
			wholeSolution.add(cs);
		}
	}
	
	public void select1() {
		
	}
}
