package GA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import tools.ExcelReader;
import tools.GetUsable;
import tools.clashDetect;

public class WholeSolution {
	public ArrayList<ClassSolution> wholeSolution=new ArrayList<ClassSolution>();
	public ClassSolution best=null;
	/*
	 * �����еĿγ̶���ʼ������֤�����еĿγ̶�����ʼ����
	 */
	public void init() {
			for(int i=0;i<ExcelReader.speciesNum;i++) {
				/*
				 * ��ʼ�����пγ���Ϣ
				 */
				 ArrayList<int[]> allCourse=new ArrayList<int[]>();
				ArrayList<int[]> usableTeacher=new ArrayList<int[]>();
				ArrayList<Integer> freeTeacher=new ArrayList<Integer>();
				for(int allCourseNum=0; allCourseNum<ExcelReader.allCourse.size(); allCourseNum++) {
					
					int[] temp=new int[10];
					
					for(int j=0;j<ExcelReader.allCourse.get(allCourseNum).length;j++) {
						
						temp[j]=ExcelReader.allCourse.get(allCourseNum)[j];
						
					}
					allCourse.add(temp);
				}
				
				/*
				 * ��ʼ�����п��ý�ʦ��Ϣ
				 */
				
				for(int teacherNum=0;teacherNum<ExcelReader.usableTeacher.size();teacherNum++) {
					int[] temp=new int[40];
					for(int j=0;j<ExcelReader.usableTeacher.get(teacherNum).length;j++) {
						temp[j]=ExcelReader.usableTeacher.get(teacherNum)[j];
					}
					freeTeacher.add(teacherNum+1);
					usableTeacher.add(temp);
				}
				
				/*
				 * ��ʼ�������༶�Ŀα�
				 */
				
				ClassSolution cs=new ClassSolution(allCourse,usableTeacher,freeTeacher);
				cs.init();
				
				for(int classnum=1;classnum<ExcelReader.classNum;classnum++) {
					System.out.println("������β��ӵ�"+classnum+"���α�");
					ClassSolution cn=new ClassSolution(allCourse,usableTeacher,freeTeacher);
					cn.init();
					tools.AddIndividul.add(cs, cn);
				}
				wholeSolution.add(cs);
			}
			System.out.println("�α��ʼ��ȫ������");
	}
	
	public  void traverse(int speciesNo) {
		ClassSolution cs=wholeSolution.get(speciesNo);
		int classNo=0;
		do{
			classNo++;
			System.out.println(classNo);
			for(int i=0;i<cs.solution.size();i++) {
				for(int j=0;j<cs.solution.get(i).length;j++) {
					System.out.print(cs.solution.get(i)[j]+"    ");
				}
				System.out.println();
			}
			cs=cs.next;
			
		}while(cs!=null);
	}
	/*
	 * �������,ײ���ĸ���̫�󣬴˴�ѡ��ֱ���½�
	 */
	public void crossover() {
		init();
	}
	
	/*
	 * �������
	 */
	public void mutate() {
		Random r=new Random();
		System.out.println("��ʼ�����������");
		for(int species=0;species<wholeSolution.size();species++) {
			System.out.println("��"+species+"���������");
			int mutateNum=r.nextInt(20);
			System.out.println("Ԥ�Ʊ���"+mutateNum+"��");
			for(int successNum=0;successNum<=mutateNum;successNum++) {
				System.out.println("���ڱ����"+successNum+"������");
				double mutateP=r.nextDouble();
				if(mutateP<=ExcelReader.mutatePossibility) {
					System.out.println("�������");
					ClassSolution cs=wholeSolution.get(species);
					//ȷ��Ҫ������ٸ��γ̱���
					int ok=0;
					do {
						int No=r.nextInt(cs.allCourse.size());
						/*
						 * ֻ�е�ȷ������Ŀγ�Ϊ�ѳ�ʼ�����Ŀγ̺�ſ����˳�ѭ��
						 */
						if(cs.allCourse.get(No)[ExcelReader.CLASSROOM_COL]!=1) {
							int classNo=cs.allCourse.get(No)[ExcelReader.NAME_COL];
							int teacherNo=cs.allCourse.get(No)[ExcelReader.TEACHER_COL];
							int term=cs.allCourse.get(No)[ExcelReader.TERM_COL];
							int classroomtemp=cs.allCourse.get(No)[ExcelReader.CLASSROOM_COL];
							ArrayList<ArrayList<Integer>> usableTime=new ArrayList<ArrayList<Integer>>();
							/*
							 * ����ÿ���༶�Ŀα����ѡ�������ſΣ���������������˳��Կα�ı���
							 */
							int classN=0;
							do {
								for(int i=0;i<cs.solution.size();i++) {
									if(cs.solution.get(i)[ExcelReader.NAME_COL]==classNo&&cs.solution.get(i)[ExcelReader.TEACHER_COL]==teacherNo&&cs.solution.get(i)[ExcelReader.CLASSROOM_COL]==classroomtemp) {
										ArrayList<Integer> temp=GetUsable.getUsableTime(cs.solution);
										System.out.println("�ð༶�α�Ϊ");
										for(int k=0;k<cs.solution.size();k++) {
											for(int l=0;l<cs.solution.get(k).length;l++) {
												System.out.print(cs.solution.get(k)[l]+"  ");
											}
											System.out.println();
										}
										System.out.println("δ�����Ŀ���ʱ��");
										for(int k=0;k<temp.size();k++) {
											System.out.print(temp.get(k)+"  ");
										}
										System.out.println();
										if(term==0) {
											temp.add(cs.solution.get(i)[ExcelReader.WEEKTIME1_COL]);
											if(cs.solution.get(i)[ExcelReader.WEEKTIME2_COL]!=-1) {
												temp.add(cs.solution.get(i)[ExcelReader.WEEKTIME2_COL]);
											}
										}else if(term==1) {
											temp.add((cs.solution.get(i)[ExcelReader.WEEKTIME1_COL]+20));
											if(cs.solution.get(i)[ExcelReader.WEEKTIME2_COL]!=-1) {
												temp.add((cs.solution.get(i)[ExcelReader.WEEKTIME2_COL]+20));
											}
										}else if(term==2) {
											temp.add((cs.solution.get(i)[ExcelReader.WEEKTIME1_COL]+20));
											temp.add((cs.solution.get(i)[ExcelReader.WEEKTIME2_COL]+20));
											temp.add(cs.solution.get(i)[ExcelReader.WEEKTIME1_COL]);
											temp.add(cs.solution.get(i)[ExcelReader.WEEKTIME2_COL]);
										}
										System.out.println("��"+classN+"���༶ѡ�������ǿ�");
										System.out.println("������Ŀ���ʱ��");
										for(int k=0;k<temp.size();k++) {
											System.out.print(temp.get(k)+"  ");
										}
										System.out.println();
										usableTime.add(temp);
										break;
									}
								}
								cs=cs.next;
								classN++;
							}while(cs!=null);
							
							System.out.println("���а༶�������");
							
							cs=wholeSolution.get(species);
							/*
							 * �ҳ���Щ����ʱ��Ĺ�ֵͬ��Ȼ����������Ŀγ�
							 */
							ArrayList<Integer> commonTime=new ArrayList<Integer>();
							for(int i=0;i<usableTime.size();i++) {
								if(i==0) {
									for(int k=0;k<usableTime.get(i).size();k++) {
										commonTime.add(usableTime.get(i).get(k));
									}
								}else {
									for(int k=0;k<commonTime.size();) {
										if(usableTime.get(i).indexOf(commonTime.get(k))==-1) {
											commonTime.remove(k);
										}
										k++;
									}
								}
							}
							/*
							 * commonTime��ʱ������������
							 */
							 if(commonTime.size()!=0) {
								 
								 
								 int[] a=new int[commonTime.size()];
								 for(int i=0;i<a.length;i++) {
									 a[i]=commonTime.get(i);
								 }
								 Arrays.sort(a);
								 System.out.println("��Щ�༶���õĹ�ͬʱ��Ϊ");
								 for(int i=0;i<a.length;i++) {
									 System.out.print(a[i]+"   ");
								 }
								 System.out.println();
								 
								 int half=0;
								 for(int i=0;i<a.length;i++) {
									 if(a[i]>20) {
										 half=i;
										 break;
									 }
								 }
								 /*
								    * ���ȿ���ֻ���Ǻ���ܵĿα�
								  */
								 if(half<2) {
									 /*
									  * ֻ���Ǻ���ܵĿα�
									  */
									 System.out.println("���ʱ��ֻ��ѡ������");
									 if(term==2) {
										 System.out.println("termΪ2�޷�ʵ�ֱ仯");
										 continue;
									 }else {
										 /*
										  * ��ȡ����֮���µ�ʱ��
										  * temp��ÿγ̱�����ͬ����⵽��ͻ�����¸�ֵ�������г�ͻ������֮�����·���ֵ���ÿγ̱���
										  */
										 int[] temp=new int[cs.allCourse.get(No).length];
										 int[] tempCourse=cs.allCourse.get(No);
										 for(int i=0;i<cs.allCourse.get(No).length;i++) {
											 temp[i]=cs.allCourse.get(No)[i];
										 }
										 term=1;
									     tempCourse[ExcelReader.TERM_COL]=1;
									     int tryNum=0;
									     do {
									    	 tryNum++;
									    	 System.out.println("��"+tryNum+"����Ϊ�γ̵�ʱ�丳ֵ");
									    	 int newTime1=r.nextInt(a.length-half)+half;
									    	 /*
									    	  * �Ѿ��Կα���Ĺ���
									    	  */
									    	 tempCourse[ExcelReader.WEEKTIME1_COL]=a[newTime1]-20;
									    	 int newTime2=0;
									    	 System.out.println("��1��"+"ʱ��Ϊ"+(a[newTime1]-20));
									    	 if(temp[ExcelReader.WEEKTIME2_COL]!=1) {
									    		 do {
									    			 newTime2=r.nextInt(a.length-half)+half;
									    			 /*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
									    			 tempCourse[ExcelReader.WEEKTIME2_COL]=a[newTime2]-20;
									    			 System.out.println("��2��"+"ʱ��Ϊ"+(a[newTime2]-20));
									    		 }while(newTime1==newTime2);
									    	 }
									
										 /*
										  * �������Ƿ��ͻ
										  * @ע���ʱ��λ᲻��û�н���
										  */
										System.out.println("�����ʱ���Ƿ�ᵼ�½��ҳ�ͻ");
										 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
											 System.out.println("�еڶ���ʱ��");
											 int classroomNo=temp[ExcelReader.CLASSROOM_COL];
											 boolean classroomClash=clashDetect.DetectClassroom(cs.allCourse, a[newTime1]-20, term, classroomNo)||clashDetect.DetectClassroom(cs.allCourse, a[newTime2]-20, term, classroomNo);
											 if(classroomClash) {
												 System.out.println("���ҷ����˳�ͻ");
												 String type=ExcelReader.classroomHashMap.get(temp[ExcelReader.CLASSROOM_COL]);
												 ArrayList<Integer> classroom=GetUsable.getUsableClassroom(cs.allCourse, a[newTime1]-20, term, type);
												 ArrayList<Integer> classroom2=GetUsable.getUsableClassroom(cs.allCourse, a[newTime2]-20, term, type);
												 ArrayList<Integer> finalRoom=new ArrayList<Integer>();
												 for(int i=0;i<classroom.size();i++) {
													 if(classroom2.indexOf(classroom.get(i))!=-1) {
														 finalRoom.add(classroom.get(i));
													 }
												 }
												 if(finalRoom.size()==0) {
													 for(int i=0;i<tempCourse.length;i++) {
														 cs.allCourse.get(No)[i]=temp[i];
													 }
													 continue;
												 }
												 System.out.println("�����õĽ���Ϊ");
												 for(int i=0;i<finalRoom.size();i++) {
													 System.out.print(finalRoom.get(i)+"  ");
												 }
												 int classroomIndex=r.nextInt(finalRoom.size());
												 System.out.println("������Ľ���Ϊ"+finalRoom.get(classroomIndex));
												 /*
										    	  * �Ѿ��Կα���Ĺ���
										    	  */
												 tempCourse[ExcelReader.CLASSROOM_COL]=finalRoom.get(classroomIndex);
											 }
										}else {
											System.out.println("�޵ڶ���ʱ��");
											 int classroomNo=cs.allCourse.get(No)[ExcelReader.CLASSROOM_COL];
											 if(clashDetect.DetectClassroom(cs.allCourse, a[newTime1]-20, term, classroomNo)) {
												 System.out.println("�����˳�ͻ");
												String type=ExcelReader.classroomHashMap.get(cs.allCourse.get(No)[ExcelReader.CLASSROOM_COL]);
												ArrayList<Integer> classroom=GetUsable.getUsableClassroom(cs.allCourse, a[newTime1]-20, term, type);			
												int classroomIndex=r.nextInt(classroom.size());
												/*
										    	  * �Ѿ��Կα���Ĺ���
										    	  */
												System.out.println("������Ľ���Ϊ"+classroom.get(classroomIndex));
												tempCourse[ExcelReader.CLASSROOM_COL]=classroom.get(classroomIndex);			
											}
										}
										 
										 /*
										  * ���֮���ʱ������ʦ��ͻ�������ѡ��һ������ʦ
										  * ע��᲻����ʱ��û�н�ʦ�п�
										  */
										 System.out.println("�鿴��ʦ�Ƿ��ͻ");
										 int teacherIndex=temp[ExcelReader.TEACHER_COL];
										 int newteacher;
										 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
											 System.out.println("�ڶ���ʱ�䲻Ϊ��");
											 boolean teacherClash=clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime1]-20,term)||clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime2]-20,term);
											 if(teacherClash) {
												 
												 System.out.println("��ʦ�����˳�ͻ");
												 String type=ExcelReader.courseTypeHashMap.get(temp[ExcelReader.NAME_COL]);
												 newteacher=GetUsable.getUsableTeacher(cs.usableTeacher,a[newTime1]-20,a[newTime2]-20,term,type);
												 if(newteacher==-1) {
													 
													 for(int i=0;i<tempCourse.length;i++) {
														 cs.allCourse.get(No)[i]=temp[i];
													 }
													 
													 continue;
												 }else {
													 System.out.println("������Ŀ��ý�ʦΪ"+newteacher);
												 }
												 tempCourse[ExcelReader.TEACHER_COL]=newteacher;
												 cs.usableTeacher.get(newteacher-1)[a[newTime1]-1-20]=1;
												 cs.usableTeacher.get(newteacher-1)[a[newTime2]-1-20]=1;
											 }
										 }else {
											 System.out.println("�ڶ���ʱ��Ϊ��");
										 		if(clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime1]-20,term)){
										 			String type=ExcelReader.courseTypeHashMap.get(temp[ExcelReader.NAME_COL]);
										 			newteacher=GetUsable.getUsableTeacher(cs.usableTeacher,a[newTime1]-20,-1,term,type);
										 			if(newteacher==-1) {
										 				
										 				for(int i=0;i<tempCourse.length;i++) {
															 cs.allCourse.get(No)[i]=temp[i];
														 }
										 				continue;
										 			}else {
														 System.out.println("������Ŀ��ý�ʦΪ"+newteacher);
													 }
										 			tempCourse[ExcelReader.TEACHER_COL]=newteacher;
										 			cs.usableTeacher.get(newteacher-1)[a[newTime1]-1-20]=1;
										 		}
										 }
										 tryNum=41;
									   }while(tryNum<40);
									     /*
									      * ����Ǵ������Ƶ����˳����ģ���ָ��γ�����
									      */
									     if(tryNum!=41) {
									    	 System.out.println("���ڴ������Ƴ����ˣ��ָ��˿γ�����");
									    	 for(int i=0;i<tempCourse.length;i++) {
												 cs.allCourse.get(No)[i]=temp[i];
											 }
									     }
										ok=20;
									}
								 }
							/*
							 * ����ֻ����ǰ����	 
							 */
								 
							else if((a.length-half)<2){
								System.out.println("ֻ����ǰ����");
									 /*
									  * ֻ����ǰ����
									  */
									 if(term==2) {
										 continue;
									 }else {
										 /*
										  * ��ȡ����֮���µ�ʱ��
										  * temp��ÿγ̱�����ͬ����⵽��ͻ�����¸�ֵ�������г�ͻ������֮�����·���ֵ���ÿγ̱���
										  */
										 
										 int[] temp=new int[cs.allCourse.get(No).length];
										 int[] tempCourse=cs.allCourse.get(No);
										 for(int i=0;i<cs.allCourse.get(No).length;i++) {
											 temp[i]=cs.allCourse.get(No)[i];
										 }
										 term=0;
										 /*
								    	  * �Ѿ��Կα���Ĺ���
								    	  */
										 tempCourse[ExcelReader.TERM_COL]=term;
										 int tryNum=0;
										 do {
										System.out.println("��"+tryNum+"�γ���");
										 tryNum++;
										 int newTime1=r.nextInt(half);
										 /*
								    	  * �Ѿ��Կα���Ĺ���
								    	  */
										 tempCourse[ExcelReader.WEEKTIME1_COL]=a[newTime1];
										 System.out.println("�������ʱ��1Ϊ"+a[newTime1]);
										 int newTime2=0;
										 if(temp[ExcelReader.WEEKTIME2_COL]!=1) {
											 do {
											 	newTime2=r.nextInt(half);
											 	tempCourse[ExcelReader.WEEKTIME2_COL]=a[newTime2];
											 	System.out.println("�������ʱ��2Ϊ"+a[newTime2]);
											 }while(newTime1==newTime2);
										 }
										 /*
										  * �������Ƿ��ͻ
										  * @ע���ʱ��λ᲻��û�н���
										  */
										 System.out.println("�鿴�Ƿ���н��ҳ�ͻ");
										 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
											 System.out.println("�ڶ���ʱ�䲻Ϊ��");
											 int classroomNo=temp[ExcelReader.CLASSROOM_COL];
											 boolean classroomClash=clashDetect.DetectClassroom(cs.allCourse, a[newTime1], term, classroomNo)||clashDetect.DetectClassroom(cs.allCourse, a[newTime2], term, classroomNo);
											 if(classroomClash) {
												 System.out.println("���ҷ�������ײ");
												 String type=ExcelReader.classroomHashMap.get(temp[ExcelReader.CLASSROOM_COL]);
												 ArrayList<Integer> classroom=GetUsable.getUsableClassroom(cs.allCourse, a[newTime1], term, type);
												 ArrayList<Integer> classroom2=GetUsable.getUsableClassroom(cs.allCourse, a[newTime2], term, type);
												 ArrayList<Integer> finalRoom=new ArrayList<Integer>();
												 for(int i=0;i<classroom.size();i++) {
													 if(classroom2.indexOf(classroom.get(i))!=-1) {
														 finalRoom.add(classroom.get(i));
													 }
												 }
												 if(finalRoom.size()==0) {
													 
													 for(int i=0;i<tempCourse.length;i++) {
														 cs.allCourse.get(No)[i]=temp[i];
													 }
													 continue;
												 }
												 System.out.println("���ý���Ϊ");
												 for(int i=0;i<finalRoom.size();i++) {
													 System.out.print(finalRoom.get(i)+"   ");
												 }
												 
												 System.out.println();
												 int classroomIndex=r.nextInt(finalRoom.size());
												 /*
										    	  * �Ѿ��Կα���Ĺ���
										    	  */
												 tempCourse[ExcelReader.CLASSROOM_COL]=finalRoom.get(classroomIndex);
											 }
										}else {
											System.out.println("�ڶ���ʱ��Ϊ��");
											 int classroomNo=temp[ExcelReader.CLASSROOM_COL];
											 if(clashDetect.DetectClassroom(cs.allCourse, a[newTime1], term, classroomNo)) {
												 System.out.println("�����˽�����ײ");
												String type=ExcelReader.classroomHashMap.get(temp[ExcelReader.CLASSROOM_COL]);
												ArrayList<Integer> classroom=GetUsable.getUsableClassroom(cs.allCourse, a[newTime1], term, type);			
												int classroomIndex=r.nextInt(classroom.size());
												/*
										    	  * �Ѿ��Կα���Ĺ���
										    	  */
												System.out.println("������Ľ���Ϊ"+classroom.get(classroomIndex));
												tempCourse[ExcelReader.CLASSROOM_COL]=classroom.get(classroomIndex);			
											}
										}
										 
										 /*
										  * ���֮���ʱ������ʦ��ͻ�������ѡ��һ������ʦ
										  * ע��᲻����ʱ��û�н�ʦ�п�
										  */
										 System.out.println("�鿴�Ƿ���н�ʦ��ͻ");
										 int teacherIndex=temp[ExcelReader.TEACHER_COL];
										 int newteacher;
										 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
											 System.out.println("�ڶ���ʱ�䲻Ϊ��");
											 boolean teacherClash=clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime1],term)||clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime2],term);
											 if(teacherClash) {
												 System.out.println("��ʦ��������ײ");
												 String type=ExcelReader.courseTypeHashMap.get(temp[ExcelReader.NAME_COL]);
												 newteacher=GetUsable.getUsableTeacher(cs.usableTeacher,a[newTime1],a[newTime2],term,type);
												 if(newteacher==-1) {
													 for(int i=0;i<tempCourse.length;i++) {
														 cs.allCourse.get(No)[i]=temp[i];
													 }
													 continue;
												 }
												 /*
										    	  * �Ѿ��Կα���Ĺ���
										    	  */
												 System.out.println("������Ľ�ʦΪ"+newteacher);
												 tempCourse[ExcelReader.TEACHER_COL]=newteacher;
												 cs.usableTeacher.get(newteacher-1)[a[newTime1]-1]=1;
												 cs.usableTeacher.get(newteacher-1)[a[newTime2]-1]=1;
											 }
										 }else {
											 System.out.println("�ڶ���ʱ��Ϊ��");
										 		if(clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime1],term)){
										 			System.out.println("�����˽�ʦ��ײ");
										 			String type=ExcelReader.courseTypeHashMap.get(temp[ExcelReader.NAME_COL]);
										 			newteacher=GetUsable.getUsableTeacher(cs.usableTeacher,a[newTime1],-1,term,type);
										 			if(newteacher==-1) {
										 				
										 				for(int i=0;i<tempCourse.length;i++) {
															 cs.allCourse.get(No)[i]=temp[i];
														 }
										 				continue;
										 			}
										 			System.out.println("������Ľ�ʦΪ"+newteacher);
										 			/*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
										 			tempCourse[ExcelReader.TEACHER_COL]=newteacher;
										 			cs.usableTeacher.get(newteacher-1)[a[newTime1]-1]=1;
										 		}
										 }
										 tryNum=41;
										}while(tryNum<40);
										 if(tryNum!=41) {
											 System.out.println("���ڴ������ƿγ���Ϣ������");
											 for(int i=0;i<tempCourse.length;i++) {
												 cs.allCourse.get(No)[i]=temp[i];
											 }
										 }
										ok=20;
								} 
						}
							/*
							 * ����ǰ����ܶ�����
							 */
							
							else {
									 /*
									  * ǰ����ܶ�ʱ�����
									  */
								System.out.println("����ǰ����ܶ����Եķֶ�");
									 if(term==2) {
										 System.out.println("ѡ���Ŀγ���48��ʱ");
										 ArrayList<Integer> usableTime2=new ArrayList<Integer>();
										 for(int i=0;i<half;i++) {
											 if(commonTime.indexOf((a[i])+20)!=-1) {
												 usableTime2.add(a[i]);
											 }
										 }
										 if(usableTime2.size()<2) {
											 break;
										 }else {
											 for(int i=0;i<usableTime2.size();i++) {
												 System.out.print(usableTime2.get(i)+"   ");
											 }
											 System.out.println();
											 int time1;
											 int time2;
											/*
											  * ��ȡ����֮���µ�ʱ��
											  * temp��ÿγ̱�����ͬ����⵽��ͻ�����¸�ֵ�������г�ͻ������֮�����·���ֵ���ÿγ̱���
											  */
											  int[] tempCourse=cs.allCourse.get(No);
											 int[] temp=new int[cs.allCourse.get(No).length];
											 for(int i=0;i<cs.allCourse.get(No).length;i++) {
												 temp[i]=cs.allCourse.get(No)[i];
											 }
											 int tryNum=0;
											 do {
												 tryNum++;
												 do{
													 int time1Index=r.nextInt(usableTime2.size());
													 int time2Index=r.nextInt(usableTime2.size());
													 time1=usableTime2.get(time1Index);
													 time2=usableTime2.get(time2Index);
													 System.out.println("ѡ����ʱ��ֱ�Ϊ"+time1+" "+time2);
													 /*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
													 tempCourse[ExcelReader.WEEKTIME1_COL]=time1;
													 tempCourse[ExcelReader.WEEKTIME2_COL]=time2;
												 }while(time1==time2);
												 /*
												  * ������ҳ����˳�ͻ
												  */
												 int classroomNo=temp[ExcelReader.CLASSROOM_COL];
												 boolean classClash=clashDetect.DetectClassroom(cs.allCourse, time1, term, classroomNo)||clashDetect.DetectClassroom(cs.allCourse, time2, term, classroomNo);
												 if(classClash) {
													 System.out.println("�����˽��ҳ�ͻ");
													 String type=ExcelReader.classroomHashMap.get(temp[ExcelReader.CLASSROOM_COL]);
													 ArrayList<Integer> classroom=GetUsable.getUsableClassroom(cs.allCourse, time1, term, type);
													 ArrayList<Integer> classroom2=GetUsable.getUsableClassroom(cs.allCourse, time2, term, type);
													 ArrayList<Integer> finalRoom=new ArrayList<Integer>();
													 for(int i=0;i<classroom.size();i++) {
														 if(classroom2.indexOf(classroom.get(i))!=-1) {
															 finalRoom.add(classroom.get(i));
														 }
													 }
													 if(finalRoom.size()==0) {
														 
														 for(int i=0;i<tempCourse.length;i++) {
															 cs.allCourse.get(No)[i]=temp[i];
														 }
														 continue;
													 }
													 System.out.println("�ҵ��Ŀ��ý���Ϊ");
													 for(int i=0;i<finalRoom.size();i++) {
														 System.out.print(finalRoom.get(i)+"  ");
													 }
													 System.out.println();
													 int classroomIndex=r.nextInt(finalRoom.size());
													 /*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
													 tempCourse[ExcelReader.CLASSROOM_COL]=finalRoom.get(classroomIndex);
												 }
												 /*
												  * �����ʦ��ʱ������˳�ͻ
												  */
												 System.out.println("����Ƿ���ֽ�ʦ��ͻ");
												 int teacherIndex=temp[ExcelReader.TEACHER_COL];
												 int newteacher;
												 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
													 boolean teacherClash=clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,time1,term)||clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,time2,term);
													 if(teacherClash) {
														 System.out.println("��ʦ��ͻ��");
														 String type=ExcelReader.courseTypeHashMap.get(temp[ExcelReader.NAME_COL]);
														 newteacher=GetUsable.getUsableTeacher(cs.usableTeacher,time1,time2,term,type);
														 if(newteacher==-1) {
															 for(int i=0;i<tempCourse.length;i++) {
																 cs.allCourse.get(No)[i]=temp[i];
															 }
															 continue;
														 }
														 /*
												    	  * �Ѿ��Կα���Ĺ���
												    	  */
														 System.out.println("�µĽ�ʦΪ"+newteacher);
														 tempCourse[ExcelReader.TEACHER_COL]=newteacher;
														 cs.usableTeacher.get(newteacher-1)[time1-1]=1;
														 cs.usableTeacher.get(newteacher-1)[time2-1]=1;
													 }
												 }
												 tryNum=41;
											 }while(tryNum<40);
											 if(tryNum!=41) {
												 System.out.println("���ڴ������ƿγ���Ϣ������");
												 for(int i=0;i<tempCourse.length;i++) {
													 cs.allCourse.get(No)[i]=temp[i];
												 }
											 }
											 ok=20;
										 }
									 }else {
										 /*
										  * ��ǰ����ܶ��������
										  */
										 System.out.println("����ǰ����ܶ�����");
										 int logo=0;
										 int num=0;
										 term=r.nextInt(2);
										do {
											
											term=(term+1)%2;
											System.out.println(term+"����");
											if(term==0) {
												int[] tempCourse=cs.allCourse.get(No);
										 	 int[] temp=new int[cs.allCourse.get(No).length];
										 	 for(int i=0;i<cs.allCourse.get(No).length;i++) {
										 		 temp[i]=cs.allCourse.get(No)[i];
										 	 }
										 	/*
									    	  * �Ѿ��Կα���Ĺ���
									    	  */
											 tempCourse[ExcelReader.TERM_COL]=term;
											 int tryNum=0;
											 do {
											
											 tryNum++;
											 int newTime1=r.nextInt(half);
											 tempCourse[ExcelReader.WEEKTIME1_COL]=a[newTime1];
											 System.out.println("������ĵ�һ��ʱ��Ϊ"+a[newTime1]);
											 int newTime2=0;
											 if(temp[ExcelReader.WEEKTIME2_COL]!=1) {
												 do {
												 	newTime2=r.nextInt(half);
												 	/*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
												 	tempCourse[ExcelReader.WEEKTIME2_COL]=a[newTime2];
												 	System.out.println("������ĵڶ���ʱ��Ϊ"+a[newTime2]);
												 }while(newTime1==newTime2);
											 }
											 /*
											  * �������Ƿ��ͻ
											  * @ע���ʱ��λ᲻��û�н���
											  */
											System.out.println("�����ҳ�ͻ");
											 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
												 System.out.println("�ڶ���ʱ�䲻Ϊ��");
												 int classroomNo=temp[ExcelReader.CLASSROOM_COL];
												 boolean classClash=clashDetect.DetectClassroom(cs.allCourse, a[newTime1], term, classroomNo)||clashDetect.DetectClassroom(cs.allCourse, a[newTime2], term, classroomNo);
												 if(classClash) {
													 System.out.println("���ҳ�ͻ��");
													 String type=ExcelReader.classroomHashMap.get(temp[ExcelReader.CLASSROOM_COL]);
													 ArrayList<Integer> classroom=GetUsable.getUsableClassroom(cs.allCourse, a[newTime1], term, type);
													 ArrayList<Integer> classroom2=GetUsable.getUsableClassroom(cs.allCourse, a[newTime2], term, type);
													 ArrayList<Integer> finalRoom=new ArrayList<Integer>();
													 for(int i=0;i<classroom.size();i++) {
														 if(classroom2.indexOf(classroom.get(i))!=-1) {
															 finalRoom.add(classroom.get(i));
														 }
													 }
													 if(finalRoom.size()==0) {
														 for(int i=0;i<tempCourse.length;i++) {
															 cs.allCourse.get(No)[i]=temp[i];
														 }
														 continue;
													 }
													 System.out.println("���ý���Ϊ");
													 for(int i=0;i<finalRoom.size();i++) {
														 System.out.print(finalRoom.get(i)+"  ");
													 }
													 System.out.println();
													 int classroomIndex=r.nextInt(finalRoom.size());
													 /*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
													 System.out.println("������Ľ���Ϊ"+finalRoom.get(classroomIndex));
													 tempCourse[ExcelReader.CLASSROOM_COL]=finalRoom.get(classroomIndex);
												 }
											}else {
												System.out.println("�ڶ���ʱ��Ϊ��");
												 int classroomNo=temp[ExcelReader.CLASSROOM_COL];
												 System.out.println("�����ҳ�ͻ");
												 if(clashDetect.DetectClassroom(cs.allCourse, a[newTime1], term, classroomNo)) {
													 System.out.println("���ҳ�ͻ��");
													String type=ExcelReader.classroomHashMap.get(temp[ExcelReader.CLASSROOM_COL]);
													ArrayList<Integer> classroom=GetUsable.getUsableClassroom(cs.allCourse, a[newTime1], term, type);			
													int classroomIndex=r.nextInt(classroom.size());
													System.out.println("������Ľ���Ϊ"+classroom.get(classroomIndex));
													/*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
													tempCourse[ExcelReader.CLASSROOM_COL]=classroom.get(classroomIndex);			
												}
											}
											 
											 /*
											  * ���֮���ʱ������ʦ��ͻ�������ѡ��һ������ʦ
											  * ע��᲻����ʱ��û�н�ʦ�п�
											  */
											 System.out.println("����ʦ��ͻ");
											 int teacherIndex=temp[ExcelReader.TEACHER_COL];
											 int newteacher;
											 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
												 System.out.println("�ڶ���ʱ��Ƭ��Ϊ��");
												 boolean teacherClash=clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime1],term)||clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime2],term);
												 if(teacherClash) {
													 System.out.println("��ʦ��ͻ��");
													 String type=ExcelReader.courseTypeHashMap.get(temp[ExcelReader.NAME_COL]);
													 newteacher=GetUsable.getUsableTeacher(cs.usableTeacher,a[newTime1],a[newTime2],term,type);
													 if(newteacher==-1) {
														 for(int i=0;i<tempCourse.length;i++) {
															 cs.allCourse.get(No)[i]=temp[i];
														 }
														 continue;
													 }
													 /*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
													 System.out.println("������Ľ�ʦΪ"+newteacher);
													 tempCourse[ExcelReader.TEACHER_COL]=newteacher;
													 cs.usableTeacher.get(newteacher-1)[a[newTime1]-1]=1;
													 cs.usableTeacher.get(newteacher-1)[a[newTime2]-1]=1;
												 }
											 }else {
												 System.out.println("�ڶ���ʱ��ƬΪ��");
					
											 		if(clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime1],term)){
											 			String type=ExcelReader.courseTypeHashMap.get(temp[ExcelReader.NAME_COL]);
											 			newteacher=GetUsable.getUsableTeacher(cs.usableTeacher,a[newTime1],-1,term,type);
											 			if(newteacher==-1) {
											 				for(int i=0;i<tempCourse.length;i++) {
																 cs.allCourse.get(No)[i]=temp[i];
															 }
											 				continue;
											 			}
											 			/*
												    	  * �Ѿ��Կα���Ĺ���
												    	  */
											 			tempCourse[ExcelReader.TEACHER_COL]=newteacher;
											 			cs.usableTeacher.get(newteacher-1)[a[newTime1]-1]=1;
											 			System.out.println("������Ľ�ʦΪ"+newteacher);
											 		}
											 }
											
											 logo=1;
											 tryNum=41;
											}while(tryNum<40);
											 if(tryNum!=41) {
												 System.out.println("���ڴ������ƻ�ԭ�γ���Ϣ");
												 for(int i=0;i<tempCourse.length;i++) {
													 cs.allCourse.get(No)[i]=temp[i];
												 }
											 }
											 
										 }
										 /*
										  * ǰ�������,תΪ�����
										  */
										 
										 else {
											 System.out.println("��������");
											 int[] tempCourse=cs.allCourse.get(No);
											 int[] temp=new int[cs.allCourse.get(No).length];
											 for(int i=0;i<cs.allCourse.get(No).length;i++) {
												 temp[i]=cs.allCourse.get(No)[i];
											 }
											 term=1;
											 /*
									    	  * �Ѿ��Կα���Ĺ���
									    	  */
										     tempCourse[ExcelReader.TERM_COL]=term;
										     int tryNum=0;
										     do {
										    	 tryNum++;
										    	 int newTime1=r.nextInt(a.length-half)+half;
										    	 /*
										    	  * �Ѿ��Կα���Ĺ���
										    	  */
										    	 tempCourse[ExcelReader.WEEKTIME1_COL]=a[newTime1]-20;
										    	 System.out.println("�������ʱ��1Ϊ"+(a[newTime1]-20));
										    	 int newTime2=0;
										    	 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
										    		 do {
										    			 newTime2=r.nextInt(a.length-half)+half;
										    			 /*
												    	  * �Ѿ��Կα���Ĺ���
												    	  */
										    			 tempCourse[ExcelReader.WEEKTIME2_COL]=a[newTime2]-20;
										    			 System.out.println("�������ʱ��2Ϊ"+(a[newTime2]-20));
										    		 }while(newTime1==newTime2);
										    	 }
										
											 /*
											  * �������Ƿ��ͻ
											  * @ע���ʱ��λ᲻��û�н���
											  */
											System.out.println("�����ҳ�ͻ");
											 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
												 System.out.println("�ڶ���ʱ��Ƭ��Ϊ��");
												 int classroomNo=temp[ExcelReader.CLASSROOM_COL];
												 boolean classroomClash=clashDetect.DetectClassroom(cs.allCourse, a[newTime1]-20, term, classroomNo)||clashDetect.DetectClassroom(cs.allCourse, a[newTime2]-20, term, classroomNo);
												 if(classroomClash) {
													 System.out.println("���ҳ�ͻ��");
													 String type=ExcelReader.classroomHashMap.get(temp[ExcelReader.CLASSROOM_COL]);
													 ArrayList<Integer> classroom=GetUsable.getUsableClassroom(cs.allCourse, a[newTime1]-20, term, type);
													 ArrayList<Integer> classroom2=GetUsable.getUsableClassroom(cs.allCourse, a[newTime2]-20, term, type);
													 ArrayList<Integer> finalRoom=new ArrayList<Integer>();
													 for(int i=0;i<classroom.size();i++) {
														 if(classroom2.indexOf(classroom.get(i))!=-1) {
															 finalRoom.add(classroom.get(i));
														 }
													 }
													 if(finalRoom.size()==0) {
														 for(int i=0;i<tempCourse.length;i++) {
															 cs.allCourse.get(No)[i]=temp[i];
														 }
														 continue;
													 }
													 System.out.println("���ý���Ϊ");
													 for(int i=0;i<finalRoom.size();i++) {
														 System.out.print(finalRoom.get(i)+"   ");
													 }
													 System.out.println();
													 int classroomIndex=r.nextInt(finalRoom.size());
													 /*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
													 System.out.println("ѡ��Ľ���Ϊ"+finalRoom.get(classroomIndex));
													 tempCourse[ExcelReader.CLASSROOM_COL]=finalRoom.get(classroomIndex);
												 }
											}else {
												System.out.println("�ڶ���ʱ��Ϊ��");
												 int classroomNo=cs.allCourse.get(No)[ExcelReader.CLASSROOM_COL];
												 if(clashDetect.DetectClassroom(cs.allCourse, a[newTime1]-20, term, classroomNo)) {
													 System.out.println("�����˽�����ײ");
													String type=ExcelReader.classroomHashMap.get(cs.allCourse.get(No)[ExcelReader.CLASSROOM_COL]);
													ArrayList<Integer> classroom=GetUsable.getUsableClassroom(cs.allCourse, a[newTime1]-20, term, type);			
													int classroomIndex=r.nextInt(classroom.size());
													/*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
													 System.out.println("���ý���Ϊ");
													 for(int i=0;i<classroom.size();i++) {
														 System.out.println(classroom.get(i)+"   ");
													 }
													 System.out.println();
													tempCourse[ExcelReader.CLASSROOM_COL]=classroom.get(classroomIndex);			
												}
											}
											 
											 /*
											  * ���֮���ʱ������ʦ��ͻ�������ѡ��һ������ʦ
											  * ע��᲻����ʱ��û�н�ʦ�п�
											  */
											 System.out.println("����ʦ��ͻ");
											 int teacherIndex=temp[ExcelReader.TEACHER_COL];
											 int newteacher;
											 if(temp[ExcelReader.WEEKTIME2_COL]!=-1) {
												 System.out.println("�ڶ���ʱ�䲻Ϊ��");
												 boolean teacherClash=clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime1]-20,term)||clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime2]-20,term);
												 if(teacherClash) {
													 System.out.println("��ʦ�����˳�ͻ");
													 String type=ExcelReader.courseTypeHashMap.get(temp[ExcelReader.NAME_COL]);
													 newteacher=GetUsable.getUsableTeacher(cs.usableTeacher,a[newTime1]-20,a[newTime2]-20,term,type);
													 if(newteacher==-1) {
														 for(int i=0;i<tempCourse.length;i++) {
															 cs.allCourse.get(No)[i]=temp[i];
														 }
														 continue;
													 }
													 /*
											    	  * �Ѿ��Կα���Ĺ���
											    	  */
													 System.out.println("������Ľ�ʦΪ"+newteacher);
													 tempCourse[ExcelReader.TEACHER_COL]=newteacher;
													 cs.usableTeacher.get(newteacher-1)[a[newTime1]-1-20]=1;
													 cs.usableTeacher.get(newteacher-1)[a[newTime2]-1-20]=1;
												 }
											 }else {
												 System.out.println("�ڶ���ʱ��Ϊ��");
											 		if(clashDetect.DetectTeacher(cs.usableTeacher, teacherIndex,a[newTime1]-20,term)){
											 			String type=ExcelReader.courseTypeHashMap.get(temp[ExcelReader.NAME_COL]);
											 			newteacher=GetUsable.getUsableTeacher(cs.usableTeacher,a[newTime1]-20,-1,term,type);
											 			if(newteacher==-1) {
											 				for(int i=0;i<tempCourse.length;i++) {
																 cs.allCourse.get(No)[i]=temp[i];
															 }
											 				continue;
											 			}
											 			System.out.println("������Ľ�ʦΪ"+newteacher);
											 			/*
												    	  * �Ѿ��Կα���Ĺ���
												    	  */
											 			tempCourse[ExcelReader.TEACHER_COL]=newteacher;
											 			cs.usableTeacher.get(newteacher-1)[a[newTime1]-1-20]=1;
											 		}
											 }

											 logo=1;
											 tryNum=41;
										   }while(tryNum<40);
										     if(tryNum!=41) {
										    	 System.out.println("���ڴ������ƻ�ԭ�γ���Ϣ");
										    	 for(int i=0;i<tempCourse.length;i++) {
													 cs.allCourse.get(No)[i]=temp[i];
												 }
										     }
										}
											if(logo==1) {
												break;
											}else {
												num++;
											}
										}while(num<2);
										ok=20;
									 }
								 }
							 }//ѡ��γ̵İ༶û�й�ͬʱ�� 
							 
						}//ѡ����һ��û�г�ʼ���Ŀγ�
						
					}while(ok<20);
				}
			}
		}
		System.out.println("�������");
	}
	
	public void select() {
		/*
		 * ѡ��ķ���1:���ֵ͵ĸ����ܱ���̭
		 */
		ClassSolution bestTemp = null;
		double bestPointTemp=0;
		ArrayList<double[]> individualPoint=new ArrayList<double[]>();
		ArrayList<double[]> incrementPoint;
		/*
		 * ���ֶ���ѡ��Ĭ��
		 */
		for(int i=0;i<wholeSolution.size();i++) {
			double[] a= {i,0};
			individualPoint.add(a);			
		}
		
		if(ExcelReader.selectByDayAverage.equals("false")&&ExcelReader.selectByHalfTerm.equals("false")&&ExcelReader.selectByPoint.equals("false")){
			System.out.println("����Ĭ�ϳ���");
			/*
			 * example������������α�ķ���
			 * individualPoint�������±��Լ�����
			 */
			double sum=0;
			double[] temp=new double[wholeSolution.size()];
			for(int speciesNum=0;speciesNum<wholeSolution.size();speciesNum++) {
				ClassSolution cs=wholeSolution.get(speciesNum);
				double point;
				do {
					point=cs.getPoint();
					temp[speciesNum]+=point;
					sum+=point;
					//System.out.print(point+" ");
					cs=cs.next;
				}while(cs!=null);
				/*
				 * if(temp[1]>bestPoint) {
					bestPoint=temp[1];
					best=wholeSolution.get(speciesNum).clone();
				}*/

			}
			
			for(int i=0;i<temp.length;i++) {
				temp[i]/=sum;
				individualPoint.get(i)[1]+=temp[i];
			}
		}
		/*
		 * ����ѧ���Ż�
		 */
		if(ExcelReader.selectByPoint.equals("true")) {
			System.out.println("����ѧ���Ż�");
			double sum=0;
			double[] temp=new double[wholeSolution.size()];
			for(int speciesNum=0;speciesNum<wholeSolution.size();speciesNum++) {
				ClassSolution cs=wholeSolution.get(speciesNum);
				double point;
				do {
					point=cs.getPoint();
					temp[speciesNum]+=point;
					sum+=point;
					//System.out.print(point+" ");
					cs=cs.next;
				}while(cs!=null);
				/*
				 * if(temp[1]>bestPoint) {
					bestPoint=temp[1];
					best=wholeSolution.get(speciesNum).clone();
				}*/

			}
			
			for(int i=0;i<temp.length;i++) {
				temp[i]/=sum;
				individualPoint.get(i)[1]+=temp[i];
			}
		}
		/*
		 * ������ѧ���Ż�
		 */
		if(ExcelReader.selectByHalfTerm.equals("true")){
			System.out.println("������ѧ���Ż�");
			double sum=0;
			double[] temp=new double[wholeSolution.size()];
			for(int speciesNum=0;speciesNum<wholeSolution.size();speciesNum++) {
				ClassSolution cs=wholeSolution.get(speciesNum);
				double point;
				do {
					point=cs.getHalfTerm();
					temp[speciesNum]+=point;
					sum+=point;
					//System.out.print(point+" ");
					cs=cs.next;
				}while(cs!=null);
				/*
				 * if(temp[1]>bestPoint) {
					bestPoint=temp[1];
					best=wholeSolution.get(speciesNum).clone();
				}*/

			}
			
			for(int i=0;i<temp.length;i++) {
				temp[i]/=sum;
				individualPoint.get(i)[1]+=temp[i];
			}
		}
		/*
		 * ����ÿ��ƽ��
		 */
		if(ExcelReader.selectByDayAverage.equals("true")){
			System.out.println("����ÿ��ƽ��");
			double sum=0;
			double[] temp=new double[wholeSolution.size()];
			for(int speciesNum=0;speciesNum<wholeSolution.size();speciesNum++) {
				ClassSolution cs=wholeSolution.get(speciesNum);
				double point;
				do {
					point=cs.getDayAverage();
					temp[speciesNum]+=point;
					sum+=point;
					//System.out.print(point+" ");
					cs=cs.next;
				}while(cs!=null);
				/*
				 * if(temp[1]>bestPoint) {
					bestPoint=temp[1];
					best=wholeSolution.get(speciesNum).clone();
				}*/

			}
			
			for(int i=0;i<temp.length;i++) {
				temp[i]/=sum;
				individualPoint.get(i)[1]+=temp[i];
			}
		}
		double sum=0;
		for(int i=0;i<individualPoint.size();i++) {
			sum+=individualPoint.get(i)[1];
		}
		
		for(int i=0;i<individualPoint.size();i++) {
			
			individualPoint.get(i)[1]/=sum;
			System.out.println(individualPoint.get(i)[1]);
			//�õ���ǰ��һ������߷�
			if(bestPointTemp<individualPoint.get(i)[1]) {
				bestPointTemp=individualPoint.get(i)[1];
				 bestTemp=wholeSolution.get((int)individualPoint.get(i)[0]).myclone();
			}
		}
		/*
		 * ����������߷�����ʷ��߽��бȽ�
		 */
		double p1=0;
		double p2=0;
		if(best==null) {
			best=bestTemp.myclone();
		}else {
			if(ExcelReader.selectByDayAverage.equals("false")&&ExcelReader.selectByHalfTerm.equals("false")&&ExcelReader.selectByPoint.equals("false")) {
				p1+=bestTemp.getPoint()/(bestTemp.getPoint()+best.getPoint());
				p2+=best.getPoint()/(bestTemp.getPoint()+best.getPoint());
			}
			if(ExcelReader.selectByDayAverage.equals("true")) {
				p1+=bestTemp.getDayAverage()/(bestTemp.getDayAverage()+best.getDayAverage());
				p2+=best.getDayAverage()/(bestTemp.getDayAverage()+best.getDayAverage());
			}
			if(ExcelReader.selectByHalfTerm.equals("true")) {
				p1+=bestTemp.getHalfTerm()/(bestTemp.getHalfTerm()+best.getHalfTerm());
				p2+=best.getHalfTerm()/(bestTemp.getHalfTerm()+best.getHalfTerm());
			}
			if(ExcelReader.selectByPoint.equals("true")) {
				p1+=bestTemp.getPoint()/(bestTemp.getPoint()+best.getPoint());
				p2+=best.getPoint()/(bestTemp.getPoint()+best.getPoint());
			}
			if(p1>p2) {
				best=bestTemp.myclone();
				
			}	
		
		}
		
		incrementPoint=GetUsable.refreshPoint(individualPoint);
		
		//System.out.println("��ʼ��ʱ�����б��ֵ");
		/*for(int i=0;i<individualPoint.size();i++) {
			System.out.println(individualPoint.get(i)[0]+"  "+individualPoint.get(i)[1]);
		}
		System.out.println();
		for(int i=0;i<incrementPoint.size();i++) {
			System.out.println(incrementPoint.get(i)[0]+"  "+incrementPoint.get(i)[1]);
		}
		*/
		
		Random r=new Random();
		int[] remain=new int[ExcelReader.speciesNum];
		for(int i=0;i<ExcelReader.speciesNum;i++) {
				int op=r.nextInt(1000);
				double chance=(double)(op)/1000;
				for(int j=0;j<incrementPoint.size();j++) {
					if(j==0) {
						if(incrementPoint.get(j)[1]>=chance) {
							remain[i]=(int)incrementPoint.get(j)[0];
							individualPoint.remove(j);
							break;
						}
					}else if(incrementPoint.get(j)[1]>=chance&&incrementPoint.get(j-1)[1]<chance) {
							remain[i]=(int)incrementPoint.get(j)[0];
							individualPoint.remove(j);
							break;
					}
					
				}
				incrementPoint=GetUsable.refreshPoint(individualPoint);
				System.out.println("ѡ����"+(i+1)+"���������б��ֵ");
				for(int k=0;k<individualPoint.size();k++) {
					System.out.println(individualPoint.get(k)[0]+"  "+individualPoint.get(k)[1]);
				}
				/*
				System.out.println();
				for(int k=0;k<incrementPoint.size();k++) {
					System.out.println(incrementPoint.get(k)[0]+"  "+incrementPoint.get(k)[1]);
				}*/
		}
		/*
		 * ���������µļ�������
		 */
		
		ArrayList<ClassSolution> tempSolution=new ArrayList<ClassSolution>();
		for(int i=0;i<ExcelReader.speciesNum;i++) {
			tempSolution.add(wholeSolution.get(remain[i]));
		}
		wholeSolution=tempSolution;
		
	}
}
