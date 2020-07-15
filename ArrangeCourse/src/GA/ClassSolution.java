package GA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


import tools.ExcelReader;
import tools.GetUsable;
import tools.clashDetect;


/*
 * �����࣬��ʾ����һ���༶��ѡ�η���
 */
public class ClassSolution implements Serializable{
	public ArrayList<Integer> freeTeacher;
	public ArrayList<int[]> usableTeacher;
	public ArrayList<int[]> allCourse;
	public ArrayList<int[]> solution=new ArrayList<int[]>();
	HashMap<String,Float> need=new HashMap<String,Float>(); 
	ArrayList<Integer> usableTime=GetUsable.getUsableTime(solution);
	public ClassSolution next;
	public double point;
	/*
	 * �������������ȡѡ�η����е�Ҫ���������һ�ſα�
	 */
	public ClassSolution(ArrayList<int[]> allCourse,ArrayList<int[]> usableTeacher,ArrayList<Integer> freeTeacher){
		this.allCourse=allCourse;
		this.usableTeacher=usableTeacher;
		this.freeTeacher=freeTeacher;

	}

	public String init() {
		/*
		 * ��ȡָ���Խ�ѧ�ƻ���Ҫ��
		 */
        for(Iterator<String> it = ExcelReader.courseArrangeHashMap.keySet().iterator() ; it.hasNext();){
        	String key = it.next().toString();
            need.put(key, ExcelReader.courseArrangeHashMap.get(key));
        }
        /*
         *	 ��ȡδ����Ҫ��Ĵ��࣬���Ӹ��ִ�����ѡ�����ѡ��һ�ţ����ҿγ���������ͬ��ע�����need��ϣ
         *	�������ʱ�䣬���ܳ�ͻ��������Ž��ң����ܳ�ͻ
         */
        for(Iterator<String> it =need.keySet().iterator() ; it.hasNext();){
        	
            String key = it.next().toString();
            float value=need.get(key);
            while(value>0) {
            	System.out.println(key+"����Ҫ"+value+"ѧ��");
            	Random r=new Random();
            	int courseType=ExcelReader.typeCourseHashMap.get(key);
            	
            	ArrayList<int[]> toChoose=new ArrayList<int[]>();
            	
            	for(int i=0;i<allCourse.size();i++) {
            		
            		int[] a=allCourse.get(i);
            		/*
            		 * �ҵ�������ͬ,ʱ�䲻��ͻ��û��ѡ����ģ�����������Ŀγ̣����������������
            		 */
            		if(a[ExcelReader.TYPE_COL]==courseType) {
            			if(a[ExcelReader.VOLUME_COL]>=ExcelReader.studentsNum&&!clashDetect.Detect(solution, a)&&!clashDetect.hasChoosed(solution, a)) {
            				toChoose.add(a);
            			}
            		}
            	}
            	/*
            	 * ����û�пγ̿ɹ�ѡ����˵���÷����γ���Ʋ����㣬��ʾ��һ�ſγ����ó���������
            	 */
            	
            	if(toChoose.size()==0) {
            		System.out.println(key+"��γ��ܿ��������ù���");
            		return "ʧ��";
            	}else {
            		usableTime=GetUsable.getUsableTime(solution);
            		/*
            		for(int i=0;i<toChoose.size();i++) {
            			for(int j=0;j<toChoose.get(i).length;j++) {
            				System.out.print(toChoose.get(i)[j]+"  ");
            			}
            			System.out.println();
            		}*/
            		
            		/*
            		 * �õ�����ʱ��
            		 */
            		
            		
            		/*for(int i=0;i<usableTime.size();i++) {
            				System.out.print(usableTime.get(i)+"  ");
            		}*/
            		
            		int temp=r.nextInt(toChoose.size());
                	int[] tempCourse=toChoose.get(temp);
                	if(tempCourse[ExcelReader.CLASSROOM_COL]!=-1) {}
                	System.out.println("������Ŀγ�����Ϊ"+tempCourse[ExcelReader.NAME_COL]);
                	/*
                	 * ���¸ÿγ̵Ŀ�����
                	 */
                	System.out.println("�ÿγ̿�����Ϊ"+tempCourse[ExcelReader.VOLUME_COL]);
                	tempCourse[ExcelReader.VOLUME_COL]=tempCourse[ExcelReader.VOLUME_COL]-60;
                	
                	int a=tempCourse[ExcelReader.NAME_COL];
                	
                	String name=ExcelReader.courseNameHashMap.get(a);
                	System.out.println("������Ŀγ���Ϊ"+name);
                	float newKey=ExcelReader.coursePointHashMap.get(name);
                	System.out.println("��ѡ��Ŀγ�ѧ��Ϊ"+newKey);
                	value=value-newKey;
                	need.put(key, value);
                	value=need.get(key);
                	/*
                	 * ����ѡ�μƻ�����Ҫ��ѧ��
                	 */
                	
                	System.out.println("����֮��"+key+"��Ҫ "+value+"ѧ��");
                	if(tempCourse[ExcelReader.CLASSROOM_COL]!=-1) {
             
                		solution.add(tempCourse);
                		continue;
                	}
                	int usableNum=usableTime.size();
                	if(usableNum==0) {
                		return "ʧ��";
                	}
                	/*
                	 * �õ�ǰ����ֽܷ���
                	 */
                	byte half=0;
                	for(;half<usableNum;half++) {
                		if(usableTime.get(half)>20)
                			break;
                	}
                	System.out.println("ǰ����ܵķֽ���Ϊ"+half);
                	/*
                	 * �õ���������
                	 */
            		String type;
            		
            		if(tempCourse[ExcelReader.CLASSROOMTYPE_COL]==1) {
            			type="����";
            		}else {
            			type="��ͨ";
            		}
            		/*
            		 * ����ѧʱ����ʱ��,��Ϊ12��24��48����
            		 */
                	int learntime=tempCourse[ExcelReader.LEARNTIME_COL];
            		System.out.println("�ÿγ�ѧʱΪ"+learntime);
            		switch(learntime) {
                	case 12:
                		ArrayList<Integer> usableClassroom12=new ArrayList<Integer>();
                		int weektime;
                		do {
                		int weektimeIndex=r.nextInt(usableNum);
                		System.out.println("����ʱ����"+usableNum+"��");
                		weektime=usableTime.get(weektimeIndex);
                		System.out.println("�������ʱ��Ϊ"+weektime);
                		byte terms;
                		if(weektime>20) {
                			tempCourse[ExcelReader.WEEKTIME1_COL]=(weektime-20);
                			terms=1;
                		}else {
                			tempCourse[ExcelReader.WEEKTIME1_COL]=(weektime);
                    		terms=0;
                		}
                		tempCourse[ExcelReader.TERM_COL]=terms;
                		
                		
                		/*
                		 * ������ң��쳣���Ϊ��ʱ��û�п��õĽ���
                		 */
                		usableClassroom12=GetUsable.getUsableClassroom(allCourse,weektime, terms, type);
                		System.out.println(weektime+" "+terms+"ѧ�ڿ��õ�"+type+"�Ľ�����");
                		for(int i=0;i<usableClassroom12.size();i++) {
                			System.out.print(usableClassroom12.get(i)+" ");
                		}
                		System.out.println();
                		if(usableClassroom12.size()<1) {
                			continue;
                		}
                		int classroomNum=r.nextInt(usableClassroom12.size());
                		
                		int num12=usableClassroom12.get(classroomNum);
                		tempCourse[ExcelReader.CLASSROOM_COL]=num12;
                		System.out.println("ѡ��Ľ���Ϊ"+num12);
                		}while(usableClassroom12.size()==0);
                 		/*
                		 * �����ʦ
                		 */
            			int ok12=0;
                		if(freeTeacher.size()!=0) {
                			for(int i=0;i<freeTeacher.size();i++) {
                				int freeTeacherIndex=freeTeacher.get(i); 
                				//System.out.println("�ý�ʦרҵΪ"+ExcelReader.teacherProHashMap.get(freeTeacherIndex));
                				if(ExcelReader.teacherProHashMap.get(freeTeacherIndex).equals(key)) {
                					System.out.println("12��ʱ��freeTeacherIndex"+freeTeacherIndex);
                					tempCourse[ExcelReader.TEACHER_COL]=freeTeacherIndex;
                        			usableTeacher.get(freeTeacherIndex-1)[weektime-1]=1;
                        			freeTeacher.remove(i);
                        			ok12=1;
                        			//System.out.println("��δ����ʼ���Ľ�ʦ");
                        			break;
                				}
                			}         			
                		}	
                		if(ok12==0) {
                			ArrayList<Integer> correctPro=new ArrayList<Integer>();
                			for(Iterator<Integer> proIter = ExcelReader.teacherProHashMap.keySet().iterator() ; proIter.hasNext();){
                				int teacherNum  = proIter.next();
                				if(ExcelReader.teacherProHashMap.get(teacherNum).equals(key)) {
                					correctPro.add(teacherNum);
                				}
                			}
                			while(ok12==0){
                				System.out.println("��רҵû��δ����ʼ���Ľ�ʦ");
                				int i=r.nextInt(correctPro.size());
                				int teacherIndex=correctPro.get(i);
                				if(usableTeacher.get(teacherIndex-1)[weektime-1]==0) {
                					System.out.println("12��ʱ��TeacherIndex"+teacherIndex);
                					usableTeacher.get(teacherIndex-1)[weektime-1]=1;
                					tempCourse[ExcelReader.TEACHER_COL]=teacherIndex;
                					ok12=1;
                				}
                			}
                		}
                		break;
                	case 24:
                		/*
                		 * ok��ʾ�ܵķ����Ƿ����
                		 * noAnswer��ʾ�޽�
                		 * judge��ʾǰ�����
                		 */
                		int ok=0;
                		int noAnswer=0;
                  		int judge=r.nextInt(2);
                  		int weektime1 = 0;
                  		int weektime2 = 0;
                  		/*
            			 * ����������ܻ����ǰ���ܻ��ߺ���ܶ�ûʱ����
            			 * @����
            			 */
                		do {
                      		System.out.println("�����"+judge+"����");
                			if(judge==0) {
                				if(half==0||half==1) {
                					judge=1;
                					if(noAnswer==1) {
                						return "ʧ��";
                					}
                					noAnswer=1;
                					continue;
                				}
                				ArrayList<Integer> usableClassroomtemp=new ArrayList<Integer>();
                			/*
                			 * �ȷ���ʱ�䣬���Ҹ�ʱ��ο��õĽ��ң����û�У������·���
                			 */
                				do {
                					int weektimeIndex1=r.nextInt(half);
                					int weektimeIndex2=r.nextInt(half);
                					weektime1=usableTime.get(weektimeIndex1);
                					weektime2=usableTime.get(weektimeIndex2);
                					//System.out.println("ǰ�������ڿε�ʱ�����������"+weektimeIndex1+weektimeIndex2);
                					tempCourse[ExcelReader.WEEKTIME1_COL]=weektime1;
                					tempCourse[ExcelReader.WEEKTIME2_COL]=weektime2;
                					tempCourse[ExcelReader.TERM_COL]=0;
                					System.out.println("ǰ�������ڿε�ʱ��ֱ�Ϊ"+weektime1+weektime2);
                					ArrayList<Integer> usableClassroom24=GetUsable.getUsableClassroom(allCourse,weektime1, judge, type);
                					ArrayList<Integer> usableClassroom241=GetUsable.getUsableClassroom(allCourse,weektime2, judge, type);
                					System.out.println(weektime1+" "+judge+"ѧ�ڿ��õ�"+type+"�Ľ�����");
                            		for(int i=0;i<usableClassroom24.size();i++) {
                            			System.out.print(usableClassroom24.get(i)+" ");
                            		}
                            		System.out.println();
                            		System.out.println(weektime2+" "+judge+"ѧ�ڿ��õ�"+type+"�Ľ�����");
                            		for(int i=0;i<usableClassroom241.size();i++) {
                            			System.out.print(usableClassroom241.get(i)+" ");
                            		}
                            		System.out.println();
                					/*
                					 * �ҵ���Щ��������ʱ��ζ������Ͽ�
                					 */
                					for(int i=0;i<usableClassroom24.size();i++) {
                						for(int j=0;j<usableClassroom241.size();j++) {
                							if(usableClassroom24.get(i)==usableClassroom241.get(j)) {
                								usableClassroomtemp.add(usableClassroom24.get(i));
                								break;
                							}
                						}
                					}
                				}while(usableClassroomtemp.size()==0||weektime1==weektime2);
                				
                				int classroomNum24=r.nextInt(usableClassroomtemp.size());
                				int num24=usableClassroomtemp.get(classroomNum24);
                				tempCourse[ExcelReader.CLASSROOM_COL]=num24;
                				System.out.println("ѡ��Ľ���Ϊ"+num24);
                				ok=1;
                			}else {
                				if(usableNum-half<2) {
                					judge=0;
                					if(noAnswer==1) {
                						return "ʧ��";
                					}
                					noAnswer=1;
                					continue;
                				}
                				
                				ArrayList<Integer> usableClassroomtemp=new ArrayList<Integer>();
                				do {
                					System.out.println(usableNum+" "+half);
                					int weektimeIndex1=r.nextInt(usableNum-half)+half;
                					int weektimeIndex2=r.nextInt(usableNum-half)+half;
                					weektime1=usableTime.get(weektimeIndex1)-20;
                					weektime2=usableTime.get(weektimeIndex2)-20;
                					tempCourse[ExcelReader.WEEKTIME1_COL]=weektime1;
                					tempCourse[ExcelReader.WEEKTIME2_COL]=weektime2;
                					tempCourse[ExcelReader.TERM_COL]=1;
                					System.out.println("��������ڿε�ʱ��ֱ�Ϊ"+weektime1+" "+weektime2);
                    		
                					ArrayList<Integer> usableClassroom24=GetUsable.getUsableClassroom(allCourse,weektime1, judge, type);
                					ArrayList<Integer> usableClassroom241=GetUsable.getUsableClassroom(allCourse,weektime2, judge, type);
                					
                					System.out.println(weektime1+" "+judge+"ѧ�ڿ��õ�"+type+"�Ľ�����");
                            		for(int i=0;i<usableClassroom24.size();i++) {
                            			System.out.print(usableClassroom24.get(i)+" ");
                            		}
                            		System.out.println();
                            		System.out.println(weektime2+" "+judge+"ѧ�ڿ��õ�"+type+"�Ľ�����");
                            		for(int i=0;i<usableClassroom241.size();i++) {
                            			System.out.print(usableClassroom241.get(i)+" ");
                            		}
                            		System.out.println();
                					for(int i=0;i<usableClassroom24.size();i++) {
                						for(int j=0;j<usableClassroom241.size();j++) {
                							if(usableClassroom24.get(i)==usableClassroom241.get(j)) {
                								usableClassroomtemp.add(usableClassroom24.get(i));
                								break;
                							}
                						}
                					}
                				}while(usableClassroomtemp.size()==0||weektime1==weektime2);
                			
                			
                				int classroomNum24=r.nextInt(usableClassroomtemp.size());
                				int num24=usableClassroomtemp.get(classroomNum24);
                				tempCourse[ExcelReader.CLASSROOM_COL]=num24;
                				System.out.println("ѡ��Ľ���Ϊ"+num24);
                				ok=1;
                			}
                		}while(ok==0);
                		int k=0;
        				if(freeTeacher.size()!=0) {
                			for(int i=0;i<freeTeacher.size();i++) {
                				int freeTeacherIndex=freeTeacher.get(i); 
                				//System.out.println("�ý�ʦרҵΪ"+ExcelReader.teacherProHashMap.get(freeTeacherIndex));
                				if(ExcelReader.teacherProHashMap.get(freeTeacherIndex).equals(key)) {
                					tempCourse[ExcelReader.TEACHER_COL]=freeTeacherIndex;
                					System.out.println("24��ʱ��teacherIndex"+freeTeacherIndex);
                        			usableTeacher.get(freeTeacherIndex-1)[weektime1-1+judge*20]=1;
                        			usableTeacher.get(freeTeacherIndex-1)[weektime2-1+judge*20]=1;
                        			freeTeacher.remove(i);
                        			k=1;
                        			System.out.println("��δ����ʼ���Ľ�ʦ");
                        			break;
                				}
                			}         			
                		}
                		
                		if(k==0) {
                			ArrayList<Integer> correctPro=new ArrayList<Integer>();
                			for(Iterator<Integer> proIter = ExcelReader.teacherProHashMap.keySet().iterator() ; proIter.hasNext();){
                				int teacherNum  = proIter.next();
                				//System.out.println("��ʦ���Ϊ"+teacherNum);
                				if(ExcelReader.teacherProHashMap.get(teacherNum).equals(key)) {
                					correctPro.add(teacherNum);
                				}
                			}
                				while(k==0){
                        			int i=r.nextInt(correctPro.size());
                        			int teacherIndex=correctPro.get(i);
                        			System.out.println("24��ʱ��eacherIndex"+teacherIndex);
                        			if(usableTeacher.get(teacherIndex-1)[weektime1-1+judge*20]==0&&usableTeacher.get(teacherIndex-1)[weektime2-1+judge*20]==0) {
                        				usableTeacher.get(teacherIndex-1)[weektime1-1+judge*20]=1;
                        				usableTeacher.get(teacherIndex-1)[weektime2-1+judge*20]=1;
                        				tempCourse[ExcelReader.TEACHER_COL]=teacherIndex;
                        				k=1;
                        			}
                       			}
             		   }
                	
                		
        			break;
                	case 48:
                		/*
                		 * �Կ���ʱ���ٴι��ˣ��ҵ���Щʱ��ǰ����ܶ�����
                		 */
                		ArrayList<Integer> usable2=new ArrayList<Integer>(); 
                		for(int i=0;i<half;i++) {
                			//System.out.println(usableTime.get(i));
                			if(usableTime.indexOf((usableTime.get(i))+20)!=-1) {
                				usable2.add(usableTime.get(i));
                			}
                		}
                		//System.out.println("48����ʱ��Ϊ"+usable2.size());
                		if(usable2.size()<2) {
                			return "ʧ��";
                		}else {
                			int weektime3;
                			int weektime4;
                			ArrayList<Integer> usableClassroomtemp=new ArrayList<Integer>();
                			do {	
                			int weektimeIndex3=r.nextInt(usable2.size());
                			int weektimeIndex4=r.nextInt(usable2.size());
                			weektime3=usable2.get(weektimeIndex3);
                			weektime4=usable2.get(weektimeIndex4);
                			System.out.println("48��ʱ���ѡ��"+weektime3+" "+weektime4);
                			tempCourse[ExcelReader.WEEKTIME1_COL]=weektime3;
                    		tempCourse[ExcelReader.WEEKTIME2_COL]=weektime4;
                    		tempCourse[ExcelReader.TERM_COL]=2;
                    		
                    		ArrayList<Integer> usableClassroom24=GetUsable.getUsableClassroom(allCourse,weektime3, 2, type);
                    		ArrayList<Integer> usableClassroom241=GetUsable.getUsableClassroom(allCourse,weektime4, 2, type);
                    		
        					System.out.println(weektime3+" "+2+"ѧ�ڿ��õ�"+type+"�Ľ�����");
                    		for(int i=0;i<usableClassroom24.size();i++) {
                    			System.out.print(usableClassroom24.get(i)+" ");
                    		}
                    		System.out.println();
                    		System.out.println(weektime4+" "+2+"ѧ�ڿ��õ�"+type+"�Ľ�����");
                    		for(int i=0;i<usableClassroom241.size();i++) {
                    			System.out.print(usableClassroom241.get(i)+" ");
                    		}
                    		System.out.println();
                    		
                    		
                    		for(int i=0;i<usableClassroom24.size();i++) {
                    			for(int j=0;j<usableClassroom241.size();j++) {
                    				if(usableClassroom24.get(i)==usableClassroom241.get(j)) {
                    					usableClassroomtemp.add(usableClassroom24.get(i));
                    					break;
                    				}
                    			}
                    		}
                			}while(usableClassroomtemp.size()==0||weektime3==weektime4);
                    		int classroomNum24=r.nextInt(usableClassroomtemp.size());
                    		int num24=usableClassroomtemp.get(classroomNum24);
                    		tempCourse[ExcelReader.CLASSROOM_COL]=num24;
                    		System.out.println("ѡ��Ľ���Ϊ"+num24);
                    		int ok48=0;
                    		if(freeTeacher.size()!=0) {
                    			for(int i=0;i<freeTeacher.size();i++) {
                    				int freeTeacherIndex=freeTeacher.get(i); 
                    				//System.out.println("�ý�ʦרҵΪ"+ExcelReader.teacherProHashMap.get(freeTeacherIndex));
                    				if(ExcelReader.teacherProHashMap.get(freeTeacherIndex).equals(key)) {
                    					System.out.println("48��ʱ��freeTeacherIndex"+freeTeacherIndex);
                    					tempCourse[ExcelReader.TEACHER_COL]=freeTeacherIndex;
                            			usableTeacher.get(freeTeacherIndex-1)[weektime3-1]=1;
                            			usableTeacher.get(freeTeacherIndex-1)[weektime4-1]=1;
                            			usableTeacher.get(freeTeacherIndex-1)[weektime3+19]=1;
                            			usableTeacher.get(freeTeacherIndex-1)[weektime4+19]=1;
                            			freeTeacher.remove(i);
                            			ok48=1;
                            			//System.out.println("��רҵ��δ����ʼ���Ľ�ʦ");
                            			break;
                    				}
                    			}         			
                    		}
                    		if(ok48==0) {
                    			ArrayList<Integer> correctPro=new ArrayList<Integer>();
                    			for(Iterator<Integer> proIter = ExcelReader.teacherProHashMap.keySet().iterator() ; proIter.hasNext();){
                    				int teacherNum  = proIter.next();
                    				if(ExcelReader.teacherProHashMap.get(teacherNum).equals(key)) {
                    					correctPro.add(teacherNum);
                  	        		}
                    			}
                    			while(ok48==0){
                    				System.out.println("��רҵû��δ����ʼ���Ľ�ʦ");
                    				int i=r.nextInt(correctPro.size());
                    				int teacherIndex=correctPro.get(i);
                    				if(usableTeacher.get(teacherIndex-1)[weektime3-1]==0&&usableTeacher.get(teacherIndex-1)[weektime4-1]==0) {
                    					if(usableTeacher.get(teacherIndex-1)[weektime3+19]==0&&usableTeacher.get(teacherIndex-1)[weektime4+19]==0) {
                    						System.out.println("48��ʱ��teacherIndex"+teacherIndex);
                    						usableTeacher.get(teacherIndex-1)[weektime3-1]=1;
                        					usableTeacher.get(teacherIndex-1)[weektime4-1]=1;
                        					usableTeacher.get(teacherIndex-1)[weektime3+19]=1;
                        					usableTeacher.get(teacherIndex-1)[weektime4+19]=1;
                        					tempCourse[ExcelReader.TEACHER_COL]=teacherIndex;
                        					ok48=1;
                    					}
                    				}
                    			}
                    		}
                		}
                		
                		default:
                		break;
            		}	
            		/*System.out.println("��ʼ���Ŀγ���ϢΪ");
            		for(int i=0;i<tempCourse.length;i++) {
            			System.out.print(tempCourse[i]+" ");
            		}
            		System.out.println();
            		*/
            		solution.add(tempCourse);
            		usableTime=GetUsable.getUsableTime(solution);
            }
            }
        }
        System.out.println("ѡ��ȫ������");
		return null;
	}
	/*
	 * �Ե�ǰ�����������֣�������Ҫ����ѧϰЧ��,ǰ����ܿγ��Ƿ����;
	 * Ч�ʿ�����0.5-0.9
	 * 
	 */
	public double getPoint() {
		double effectivity=0;
		for(int i=0;i<solution.size();i++) {
			int time1=0;
			int time2=0;
			int[] temp=solution.get(i);
			time1=temp[ExcelReader.WEEKTIME1_COL];
			int courseNum=temp[ExcelReader.NAME_COL];
			String courseName=ExcelReader.courseNameHashMap.get(courseNum);
			float coursePoint=ExcelReader.coursePointHashMap.get(courseName);
			
			if(temp[ExcelReader.TERM_COL]==0||temp[ExcelReader.TERM_COL]==1) {
				double a=Math.abs((time1%4)-2.5);
				
				switch((int)a) {
					case 1:
						effectivity+=(double)1.2f*coursePoint;
						break;
					case 0:
						effectivity+=(double)0.4f*coursePoint;
						break;
					default:
						break;
				}
				
					if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
						time2=temp[ExcelReader.WEEKTIME2_COL];
					
						a=Math.abs((time2)%4-2.5);
					
						switch((int)a) {
							case 1:
								effectivity+=(double)1.2f*coursePoint;
								break;
							case 0:
								effectivity+=(double)0.4f*coursePoint;
								break;
							default:
								break;
						}
					}
				
			}else {
				double a=Math.abs((time1%4)-2.5);
				
				switch((int)a) {
				case 1:
					effectivity+=(double)1.2f*coursePoint*2;
					break;
				case 0:
					effectivity+=(double)0.4f*coursePoint*2;
					break;
				default:
					break;
				}
				
				time2=temp[ExcelReader.WEEKTIME2_COL];
				
				a=Math.abs((time2%4)-2.5);
				
				switch((int)a) {
				case 1:
					effectivity+=(double)1.2f*coursePoint*2;
					break;
				case 0:
					effectivity+=(double)0.4f*coursePoint*2;
					break;
				default:
					break;
				}
				
			}
		}
		
		double choosedNum=40-usableTime.size();
		effectivity/=choosedNum;
		if(effectivity-1<=0)
			return 0;
		else
		return effectivity-1;
	}
	/*
	 * ǰ����ܵ��ڣ���ֵ���ʣ�����������һ�����֮��,����Ϊ������Ϊ��
	 */
	public double getHalfTerm() {
		int lastTerm=0;
		int nextTerm=0;
		for(int i=0;i<solution.size();i++) {
			if(solution.get(i)[ExcelReader.TERM_COL]==2) {
				lastTerm+=2;
				nextTerm+=2;
			}else if(solution.get(i)[ExcelReader.TERM_COL]==1){
				nextTerm++;
				if(solution.get(i)[ExcelReader.WEEKTIME2_COL]!=1) {
					nextTerm++;
				}
			}else if(solution.get(i)[ExcelReader.TERM_COL]==0) {
				lastTerm++;
				if(solution.get(i)[ExcelReader.WEEKTIME2_COL]!=1) {
					lastTerm++;
				}
			}
		}
		
		double different=Math.abs(nextTerm-lastTerm);
		usableTime=GetUsable.getUsableTime(solution);
		double choosedNum=40-usableTime.size();
		double diffRate=different/choosedNum;
		return diffRate;
	}
	/*
	  * ÿ���Ͽν������Ƿ�ƽ�������ñ�׼��
	 */
	public double getDayAverage() {
		byte[] a= {4,4,4,4,4,4,4,4,4,4};
		for(int i=0;i<usableTime.size();i++) {
			
			switch(usableTime.get(i)/4) {
			case 0:
				a[0]-=1;
			case 1:
				a[1]-=1;
			case 3:
				a[3]-=1;
			case 4:
				a[4]-=1;
			case 5:
				a[5]-=1;
			case 6:
				a[6]-=1;
			case 7:
				a[7]-=1;
			case 8:
				a[8]-=1;
			case 9:
				a[9]-=1;
			default:
				break;
			}
		}
		double avg=0;
		double sqrt=0;
		for(int i=0;i<a.length;i++) {
			avg+=a[i];
		}
		for(int i=0;i<a.length;i++) {
			System.out.print(a[i]+" ");
		}

		avg=avg/10;
		for(int i=0;i<10;i++) {
			sqrt+=Math.pow((a[i]-avg),2);
		}
		sqrt=Math.sqrt(sqrt);
		return sqrt;
	
	}
	
	public ClassSolution myclone() {
		ClassSolution cs = null;
        try {
        	// д���ֽ���
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            cs = (ClassSolution) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cs;
	}
}

