package tools;

import java.util.ArrayList;

public class clashDetect {
	public static boolean Detect(ArrayList<byte[]> choosed,byte[] toChoose) {
		int termToChoose=0;
		int termHasChoose=0;
		/*
		 * Ҫѡ��Ŀλ�δ����ʼ��
		 */
		if(toChoose[ExcelReader.CLASSROOM_COL]==-1) {
			return false;
		}
		/*
		 * һ����һ�ڿ�
		 */
		if(toChoose[ExcelReader.WEEKTIME2_COL]==-1) {
			for(int i=0;i<choosed.size();i++) {
				termToChoose=toChoose[ExcelReader.TERM_COL];
				termHasChoose=choosed.get(i)[ExcelReader.TERM_COL];
				if(choosed.get(i)[ExcelReader.WEEKTIME1_COL]==toChoose[ExcelReader.WEEKTIME1_COL]) {
					if(termToChoose==termHasChoose||termToChoose==2||termHasChoose==2) {
						return true;
					}
				}
				if(choosed.get(i)[ExcelReader.WEEKTIME2_COL]==toChoose[ExcelReader.WEEKTIME1_COL]) {
					if(termToChoose==termHasChoose||termToChoose==2||termHasChoose==2) {
						return true;
					}
				}
			}
		}
		/*
		 * һ�������ڿ�
		 */
		else {
			for(int i=0;i<choosed.size();i++) {
				termToChoose=toChoose[ExcelReader.TERM_COL];
				termHasChoose=choosed.get(i)[ExcelReader.TERM_COL];
				if(choosed.get(i)[ExcelReader.WEEKTIME1_COL]==toChoose[ExcelReader.WEEKTIME1_COL]) {
					if(termToChoose==termHasChoose||termToChoose==2||termHasChoose==2) {
						return true;
					}
				}
				if(choosed.get(i)[ExcelReader.WEEKTIME2_COL]==toChoose[ExcelReader.WEEKTIME1_COL]) {
					if(termToChoose==termHasChoose||termToChoose==2||termHasChoose==2) {
						return true;
					}
				}
				if(choosed.get(i)[ExcelReader.WEEKTIME1_COL]==toChoose[ExcelReader.WEEKTIME2_COL]) {
					if(termToChoose==termHasChoose||termToChoose==2||termHasChoose==2) {
						return true;
					}
				}
				if(choosed.get(i)[ExcelReader.WEEKTIME2_COL]==toChoose[ExcelReader.WEEKTIME2_COL]) {
					if(termToChoose==termHasChoose||termToChoose==2||termHasChoose==2) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean hasChoosed(ArrayList<byte[]> choosed,byte[] toChoose) {
		if(toChoose[ExcelReader.CLASSROOM_COL]==-1) {
			return false;
		}
		for(int i=0;i<choosed.size();i++) {
			if(choosed.get(i)[ExcelReader.NAME_COL]==toChoose[ExcelReader.NAME_COL]) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean DetectTime(ArrayList<byte[]> choosed,int time,int term) {
		int termHasChoose;
		for(int i=0;i<choosed.size();i++) {
			termHasChoose=choosed.get(i)[ExcelReader.TERM_COL];
			if(choosed.get(i)[ExcelReader.WEEKTIME1_COL]==time) {
				if(term==termHasChoose||termHasChoose==2||term==2)
				return true;
			}
			if(choosed.get(i)[ExcelReader.WEEKTIME2_COL]==time) {
				if(term==termHasChoose||termHasChoose==2||term==2)
					return true;
			}
		}
		return false;
	}
	/*
	 * Ĭ��ʱ�䶼��20���ڣ�ͨ��ǰ���ѧ�����ֱ�
	 */
	public static boolean DetectClassroom(int time,int terms,int classroom) {
		int termHasChoose;
		for(int i=0;i<ExcelReader.allCourse.size();i++) {
				termHasChoose=ExcelReader.allCourse.get(i)[ExcelReader.TERM_COL];
				if(ExcelReader.allCourse.get(i)[ExcelReader.WEEKTIME1_COL]==time) {
					if(terms==termHasChoose||terms==2||termHasChoose==2) {
						if(ExcelReader.allCourse.get(i)[ExcelReader.CLASSROOM_COL]==classroom) {
							return true;
						}
					}
				}
				
				if(ExcelReader.allCourse.get(i)[ExcelReader.WEEKTIME2_COL]==time) {
					if(terms==termHasChoose||terms==2||termHasChoose==2) {
						if(ExcelReader.allCourse.get(i)[ExcelReader.CLASSROOM_COL]==classroom) {
							return true;
						}
					}
				}
		}
		return false;
	}
}
