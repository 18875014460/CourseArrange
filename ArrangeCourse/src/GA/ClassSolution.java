package GA;

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
public class ClassSolution {
	public ArrayList<byte[]> solution=new ArrayList<byte[]>();
	public ArrayList<byte[]> solution2=new ArrayList<byte[]>();
	HashMap<String,Float> need=new HashMap<String,Float>(); 
	ArrayList<Byte> usableTime=new ArrayList<Byte>();
	float solutionPoint;
	/*
	 * �������������ȡѡ�η����е�Ҫ���������һ�ſα�
	 */
	public void init() {
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
            	byte courseType=ExcelReader.typeCourseHashMap.get(key);
            	
            	ArrayList<byte[]> toChoose=new ArrayList<byte[]>();
            	
            	for(int i=0;i<ExcelReader.allCourse.size();i++) {
            		
            		byte[] a=ExcelReader.allCourse.get(i);
            		/*
            		 * �ҵ�������ͬ��δ����ʼ���Ŀγ̣�����δѡ���Ŀγ̶�����г�ʼ��
            		 */
            		if(a[ExcelReader.TYPE_COL]==courseType) {
            			if(a[ExcelReader.CLASSROOM_COL]==-1&&!clashDetect.Detect(solution, a)) {
            				toChoose.add(a);
            			}
            		}
            	}
            	/*
            	 * ����û�пγ̱���ʼ��������ʱ��ѡ��ÿγ�
            	 */
            	
            	if(toChoose.size()==0) {
            		break;
            	}else {
            		/*
            		for(int i=0;i<toChoose.size();i++) {
            			for(int j=0;j<toChoose.get(i).length;j++) {
            				System.out.print(toChoose.get(i)[j]+"  ");
            			}
            			System.out.println();
            		}*/
            		
            		usableTime=GetUsable.getUsableTime(solution);
            		/*for(int i=0;i<usableTime.size();i++) {
            				System.out.print(usableTime.get(i)+"  ");
            		}*/
            		
            		int temp=r.nextInt(toChoose.size());
                	byte[] tempCourse=toChoose.get(temp);
                	System.out.println("������Ŀγ�����Ϊ"+tempCourse[ExcelReader.NAME_COL]);
                	/*
                	 * ���¸ÿγ̵Ŀ�����
                	 */
                	System.out.println("�ÿγ̿�����Ϊ"+tempCourse[ExcelReader.VOLUME_COL]);
                	tempCourse[ExcelReader.VOLUME_COL]=(byte) (tempCourse[ExcelReader.VOLUME_COL]-60);
                	
                	byte a=tempCourse[ExcelReader.NAME_COL];
                	
                	String name=ExcelReader.courseNameHashMap.get(a);
                	System.out.println("������Ŀγ���Ϊ"+name);
                	
                	float newKey=ExcelReader.coursePointHashMap.get(name);
                	System.out.println("��ѡ��Ŀγ�ѧ��Ϊ"+newKey);
                	/*
                	 * ����ѡ�μƻ�����Ҫ��ѧ��
                	 */
                	value=value-newKey;
                	need.put(key, value);
                	value=need.get(key);
                	System.out.println("����֮��"+key+"��Ҫ "+value+"ѧ��");
                	
                	int usableNum=usableTime.size();
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
                	byte learntime=tempCourse[ExcelReader.LEARNTIME_COL];
            		System.out.println("�ÿγ�ѧʱΪ"+learntime);
            		switch(learntime) {
                	case 12:
                		ArrayList<Byte> usableClassroom12=new ArrayList<Byte>();
                		do {
                		int weektimeIndex=r.nextInt(usableNum);
                		System.out.println("����ʱ����"+usableNum+"��");
                		int weektime=usableTime.get(weektimeIndex);
                		System.out.println("�������ʱ��Ϊ"+weektime);
                		byte terms;
                		if(weektime>20) {
                			tempCourse[ExcelReader.WEEKTIME1_COL]=(byte)(weektime-20);
                			terms=1;
                		}else {
                			tempCourse[ExcelReader.WEEKTIME1_COL]=(byte)(weektime);
                    		terms=0;
                		}
                		tempCourse[ExcelReader.TERM_COL]=terms;
                		
                		/*
                		 * ������ң��쳣���Ϊ��ʱ��û�п��õĽ���
                		 */
                		usableClassroom12=GetUsable.getUsableClassroom(weektime, terms, type);
                		int classroomNum=r.nextInt(usableClassroom12.size());
                		byte num12=usableClassroom12.get(classroomNum);
                		tempCourse[ExcelReader.CLASSROOM_COL]=num12;
                		}while(usableClassroom12.size()==0);
                		break;
                	case 24:
                		int ok=0;
                		int noAnswer=0;
                  		int judge=r.nextInt(2);
                  		System.out.println("�����"+judge+"����");
                  		/*
            			 * ����������ܻ����ǰ���ܻ��ߺ���ܶ�ûʱ����
            			 * @����
            			 */
                		do {
                			if(judge==0) {
                				if(half==0||half==1) {
                					judge=1;
                					if(noAnswer==1) {
                						return;
                					}
                					noAnswer=1;
                					continue;
                				}
                				ArrayList<Byte> usableClassroomtemp=new ArrayList<Byte>();
                			/*
                			 * �ȷ���ʱ�䣬���Ҹ�ʱ��ο��õĽ��ң����û�У������·���
                			 */
                				do {
                					int weektimeIndex1=r.nextInt(half);
                					int weektimeIndex2=(weektimeIndex1+4)%half;
                					int weektime1=usableTime.get(weektimeIndex1);
                					int weektime2=usableTime.get(weektimeIndex2);
                					System.out.println("ǰ�������ڿε�ʱ�����������"+weektimeIndex1+weektimeIndex2);
                					tempCourse[ExcelReader.WEEKTIME1_COL]=(byte)(weektime1);
                					tempCourse[ExcelReader.WEEKTIME2_COL]=(byte)(weektime2);
                					tempCourse[ExcelReader.TERM_COL]=0;
                					System.out.println("ǰ�������ڿε�ʱ��ֱ�Ϊ"+weektime1+weektime2);
                					ArrayList<Byte> usableClassroom24=GetUsable.getUsableClassroom(weektime1, judge, type);
                					ArrayList<Byte> usableClassroom241=GetUsable.getUsableClassroom(weektime2, judge, type);
                					/*
                					 * �ҵ���Щ��������ʱ��ζ������Ͽ�
                					 */
                					for(int i=0;i<usableClassroom24.size();i++) {
                						for(byte j=0;j<usableClassroom241.size();j++) {
                							if(usableClassroom24.get(i)==usableClassroom241.get(j)) {
                								usableClassroomtemp.add(j);
                							}
                						}
                					}
                					
                				}while(usableClassroomtemp.size()==0);
                				
                				int classroomNum24=r.nextInt(usableClassroomtemp.size());
                				byte num24=usableClassroomtemp.get(classroomNum24);
                				tempCourse[ExcelReader.CLASSROOM_COL]=num24;
                				ok=1;
                			}else {
                				if(half==usableNum-1) {
                					judge=0;
                					if(noAnswer==1) {
                						return;
                					}
                					noAnswer=1;
                					continue;
                				}
                				
                			ArrayList<Byte> usableClassroomtemp=new ArrayList<Byte>();
                			do {
                			int weektimeIndex1=r.nextInt(usableNum-half)+half;
                    		int weektimeIndex2=(weektimeIndex1+4)%(usableNum-half)+half;
                    		int weektime1=usableTime.get(weektimeIndex1);
                    		int weektime2=usableTime.get(weektimeIndex2);
                    		tempCourse[ExcelReader.WEEKTIME1_COL]=(byte)(weektime1-20);
                    		tempCourse[ExcelReader.WEEKTIME2_COL]=(byte)(weektime2-20);
                    		tempCourse[ExcelReader.TERM_COL]=1;
                    		System.out.println("��������ڿε�ʱ��ֱ�Ϊ"+weektime1+weektime2);
                    		
                    		ArrayList<Byte> usableClassroom24=GetUsable.getUsableClassroom(weektime1, judge, type);
                    		ArrayList<Byte> usableClassroom241=GetUsable.getUsableClassroom(weektime2, judge, type);
                    		
                    		for(int i=0;i<usableClassroom24.size();i++) {
                    			for(byte j=0;j<usableClassroom241.size();j++) {
                    				if(usableClassroom24.get(i)==usableClassroom241.get(j)) {
                    					usableClassroomtemp.add(j);
                    				}
                    			}
                    		}
                			}while(usableClassroomtemp.size()==0);
                			
                			
                    		int classroomNum24=r.nextInt(usableClassroomtemp.size());
                    		byte num24=usableClassroomtemp.get(classroomNum24);
                    		tempCourse[ExcelReader.CLASSROOM_COL]=num24;
                    		ok=1;
                		}
                	}while(ok==0);
                		break;
                	case 48:
                		/*
                		 * �Կ���ʱ���ٴι��ˣ��ҵ���Щʱ��ǰ����ܶ�����
                		 */
                		ArrayList<Byte> usable2=new ArrayList<Byte>(); 
                		for(int i=0;i<half;i++) {
                			//System.out.println(usableTime.get(i));
                			if(usableTime.indexOf((byte)(usableTime.get(i)+20))!=-1) {
                				usable2.add(usableTime.get(i));
                			}
                		}
                		//System.out.println("48����ʱ��Ϊ"+usable2.size());
                		if(usable2.size()<2) {
                			return;
                		}else {
                			ArrayList<Byte> usableClassroomtemp=new ArrayList<Byte>();
                			do {
                			int weektimeIndex3=r.nextInt(usable2.size());
                			int weektimeIndex4=(weektimeIndex3+4)%usable2.size();
                			int weektime3=usableTime.get(weektimeIndex3);
                			int weektime4=usableTime.get(weektimeIndex4);
                			tempCourse[ExcelReader.WEEKTIME1_COL]=(byte)(weektime3);
                    		tempCourse[ExcelReader.WEEKTIME2_COL]=(byte)(weektime4);
                    		tempCourse[ExcelReader.TERM_COL]=2;
                    		
                    		ArrayList<Byte> usableClassroom24=GetUsable.getUsableClassroom(weektime3, 2, type);
                    		ArrayList<Byte> usableClassroom241=GetUsable.getUsableClassroom(weektime4, 2, type);
                    		
                    		for(int i=0;i<usableClassroom24.size();i++) {
                    			for(byte j=0;j<usableClassroom241.size();j++) {
                    				if(usableClassroom24.get(i)==usableClassroom241.get(j)) {
                    					usableClassroomtemp.add(j);
                    				}
                    			}
                    		}
                			}while(usableClassroomtemp.size()==0);
                    		int classroomNum24=r.nextInt(usableClassroomtemp.size());
                    		byte num24=usableClassroomtemp.get(classroomNum24);
                    		tempCourse[ExcelReader.CLASSROOM_COL]=num24;
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
            }
            }
        }
            		
       
        
	}
}
	
