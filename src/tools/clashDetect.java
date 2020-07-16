package tools;

import java.util.ArrayList;


public class clashDetect {
	public static boolean Detect(ArrayList<int[]> choosed,int[] toChoose) {
		int termToChoose=0;
		int termHasChoose=0;
		/*
		 * 要选择的课还未被初始化
		 */
		if(toChoose[ExcelReader.CLASSROOM_COL]==-1) {
			return false;
		}
		/*
		 * 一周上一节课
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
		 * 一周上两节课
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
	
	public static boolean hasChoosed(ArrayList<int[]> choosed,int[] toChoose) {
		for(int i=0;i<choosed.size();i++) {
			if(choosed.get(i)[ExcelReader.NAME_COL]==toChoose[ExcelReader.NAME_COL]) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean DetectTime(ArrayList<int[]> choosed,int time,int term) {
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
	 * 默认时间都是20以内，通过前后半学期来分辨
	 */
	public static boolean DetectClassroom(ArrayList<int[]> allCourse,int time,int terms,int classroom) {
		int termHasChoose;
		for(int i=0;i<allCourse.size();i++) {
				termHasChoose=allCourse.get(i)[ExcelReader.TERM_COL];
				if(allCourse.get(i)[ExcelReader.WEEKTIME1_COL]==time) {
					if(terms==termHasChoose||terms==2||termHasChoose==2) {
						if(allCourse.get(i)[ExcelReader.CLASSROOM_COL]==classroom) {
							return true;
						}
					}
				}
				
				if(allCourse.get(i)[ExcelReader.WEEKTIME2_COL]==time) {
					if(terms==termHasChoose||terms==2||termHasChoose==2) {
						if(allCourse.get(i)[ExcelReader.CLASSROOM_COL]==classroom) {
							return true;
						}
					}
				}
		}
		return false;
	}
	
	public static boolean DetectTeacher(ArrayList<int[]> teacher,int teacherIndex,int time,int term) {
		if(term==0) {
			if(teacher.get(teacherIndex-1)[time-1]==1) {
				return true;
			}
		}else if(term==1){
			if(teacher.get(teacherIndex-1)[time-1+20]==1) {
				return true;
			}
		}else if(term==2) {
			if(teacher.get(teacherIndex-1)[time-1+20]==1) {
				return true;
			}
			if(teacher.get(teacherIndex-1)[time]==1) {
				return true;
			}
		}
		return false;
		
	}
}
