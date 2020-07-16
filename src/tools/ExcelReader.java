package tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelReader {
	public static byte NAME_COL=0;
	public static byte VOLUME_COL=1;
	public static byte TYPE_COL=2;
	public static byte CLASSROOMTYPE_COL=3;
	public static byte LEARNTIME_COL=4;
	public static byte WEEKTIME1_COL=5;
	public static byte WEEKTIME2_COL=6;
	public static byte CLASSROOM_COL=7;
	public static byte TERM_COL=8;
	public static byte TEACHER_COL=9;
	public static int classNum;
	public static int studentsNum;
	public static int speciesNum;
	public static int circumsNum;
	public static String TeacherInfo;
	public static String ClassroomInfo;
	public static String CourseInfo;
	public static String ArrangeInfo;
	public static String savePath;
	public static String savePath_course;
	public static String selectByPoint;
	public static String selectByDayAverage;
	public static String selectByHalfTerm;
	public static double crossoverPossibility;
	public static double mutatePossibility;
	public static String about_params;
	public static String aboutSystem;
	public static String input_info_format;
	public static String out_info_format;
	public static String aboutFailure;
	public static String savePath_history;
	public static String savePath_courseHistory;

	public static ArrayList<byte[]> usableTeacher=new ArrayList<byte[]>();

	
	public static ArrayList<int[]> allCourse=new ArrayList<int[]>();
	public static HashMap<Integer,String> courseNameHashMap=new HashMap<Integer,String>();
	public static HashMap<Integer,String> courseTypeHashMap=new HashMap<Integer,String>();
	public static HashMap<String,Integer> typeCourseHashMap=new HashMap<String,Integer>();
	public static HashMap<String,Float> coursePointHashMap=new HashMap<String,Float>();
	public static HashMap<String,Float> courseArrangeHashMap=new HashMap<String,Float>();
	public static HashMap<Integer,String> classroomHashMap=new HashMap<Integer,String>();
	public static HashMap<Integer,String> teacherProHashMap=new HashMap<Integer,String>();
	public static HashMap<Integer,String> teacherNameHashMap=new HashMap<Integer,String>();


	public static void init() {
		   Properties params=new Properties();
		   String configFile="config.properties";
		   InputStream is =ExcelReader.class.getClassLoader().getResourceAsStream(configFile);
		   try {
			   params.load(is);
		   }catch(IOException e) {
			   e.printStackTrace();
		   }
		   TeacherInfo=params.getProperty("TeacherInfo");
		   ClassroomInfo=params.getProperty("ClassroomInfo");
		   CourseInfo=params.getProperty("CourseInfo");
		   ArrangeInfo=params.getProperty("ArrangeInfo");
		   savePath=params.getProperty("savePath");
		   classNum=Integer.parseInt(params.getProperty("classNum"));
		   speciesNum=Integer.parseInt(params.getProperty("speciesNum"));
		   circumsNum=Integer.parseInt(params.getProperty("circumsNum"));
		   studentsNum=Integer.parseInt(params.getProperty("studentsNum"));
		   selectByPoint=params.getProperty("selectByPoint");
		   selectByDayAverage=params.getProperty("selectByDayAverage");
		   selectByHalfTerm=params.getProperty("selectByHalfTerm");
		   crossoverPossibility=Double.parseDouble(params.getProperty("crossoverPossibility"));
		   mutatePossibility=Double.parseDouble(params.getProperty("mutatePossibility"));
		   savePath_course=params.getProperty("savePath_course");
		   out_info_format=params.getProperty("out_info_format");
		   input_info_format=params.getProperty("input_info_format");
		   aboutSystem=params.getProperty("aboutSystem");
		   about_params=params.getProperty("about_params");
		   aboutFailure=params.getProperty("aboutFailure");
		   savePath_history=params.getProperty("savePath_history");
		   savePath_courseHistory=params.getProperty("savePath_courseHistory");
	   }
	static {
		init();
	}
	
	public static void loadCourse() throws EncryptedDocumentException, IOException {
		File xlsFile = new File(CourseInfo);
		Workbook workbook = WorkbookFactory.create(xlsFile);
		Sheet sheet = workbook.getSheetAt(0);
		int rowNumbers = sheet.getLastRowNum();
		Row temp = sheet.getRow(0);
		int cells = temp.getPhysicalNumberOfCells();
		for (int row = 1; row <= rowNumbers; row++) {
			int courseNums=0;
			int[] b1=new int[10];
			Row r = sheet.getRow(row);
			for (int col = 0; col < cells; col++) {
				switch(col) {
				/*
				 * ����������γ����Ĺ�ϣӳ�� ��1��ʼ
				 */
				case 0:
					String courseName=r.getCell(col).toString();
					courseNameHashMap.put(row, courseName);
					b1[col]=row;
					break;
				/*
				 * �õ��γ����� 200�˵�Ϊ127
				 */
				case 1:
					String volume=r.getCell(col).toString();
					int volumeNums=(int)Float.parseFloat(volume);
					b1[col]=volumeNums;
					break;
				/*
				 * �õ��γ����ͣ�д�ڻ�����
				 */
				case 2:
					String type=r.getCell(col).toString();
					int b=typeCourseHashMap.get(type);
					b1[col]=b;
					break;
				/*
				 * �õ���ע��Ϣ��д�ڻ�����
				 */
				case 3:
					if(r.getCell(col)!=null) {
					String extra=r.getCell(col).toString();
					if(extra.equals("����")) {
						b1[col]=1;
					}
					
					}
					break;
				/*
				 * �õ�ѧʱ���洢�ڻ�����
				 */
				case 4:
					String courseTime=r.getCell(col).toString();
					int courseTimeNums=(int)Float.parseFloat(courseTime);
					b1[col]=courseTimeNums;
					break;				
				/*
				 * �õ�ѧ�֣�����д�ڻ����У�����hashmap�洢
				 */
				case 5:
					String point=r.getCell(col).toString();
					Float pointNums=Float.parseFloat(point);
					String name=ExcelReader.courseNameHashMap.get(row);
					coursePointHashMap.put(name, pointNums);
					break;
				/*
				* �õ�ѧ����Ϣ,��ʱ����
				
				case 6:
					String terms=r.getCell(col).toString();
					break;
				
				 * ��������������д�ڻ����У�
				 */
				case 7:
					String course=r.getCell(col).toString();
					courseNums=(int)Float.parseFloat(course);
					break;
				default:
					break;
			}
			/*
			 * ��6λ�洢ǰ����ʱ�� ��7λ�����ʱ��,��8λλ��ʾ���ң���9λ��ʾ�Ƿ�����ѧ�ڵ�;,��10λ��ʾ��ʦ���
			 */
				b1[5]=-1;
				b1[6]=-1;
				b1[7]=-1;
				b1[8]=-1;
				b1[9]=-1;
			for(int i=0;i<courseNums;i++) {
				int[] a=new int[10];
				for(int k=0;k<10;k++) {
					a[k]=b1[k];
				}
				allCourse.add(a);
			}
			}
		}
	
	}
	/*
	 * ��excel����е����ݶ�ȡ�����أ���Ҫ֪�����ͣ���ţ����� ����1���� 1���� 0��ͨ��//��λ���32���������ڣ���λ������256λ
	���ڣ�һ��ʮ��λ����ά���� ����
	*/
	public static void loadClassroom() throws EncryptedDocumentException, IOException {
		File file=new File(ClassroomInfo);
		Workbook workbook=WorkbookFactory.create(file);
		Sheet sheet=workbook.getSheetAt(0);
		int rowNums=sheet.getLastRowNum();
		Row temp = sheet.getRow(0);
		int cells = temp.getPhysicalNumberOfCells();
		for(int row=1;row<=rowNums;row++) {
			Row rowContent=sheet.getRow(row);
			int index=0;
			String type=null;
			for(int col=0;col<cells;col++) {
				if(col==0) {
					String volume=rowContent.getCell(col).toString();
					index=(int)Float.parseFloat(volume);
				}else if(col==1){
					type=rowContent.getCell(col).toString();
				}
			}

			classroomHashMap.put(index,type);
		}
	}
	
	public static void loadArrangeInfo() throws EncryptedDocumentException, IOException{
		File file=new File(ArrangeInfo);
		Workbook workbook=WorkbookFactory.create(file);
		Sheet sheet=workbook.getSheetAt(0);
		int rowNum=sheet.getLastRowNum();
		Row row=sheet.getRow(0);
		String courseName=null;
		float point=0;
		int cellsNum=row.getPhysicalNumberOfCells();
		for(int rowIndex=1;rowIndex<=rowNum;rowIndex++) {
			row=sheet.getRow(rowIndex);
			for(int col=0;col<cellsNum;col++) {
				switch(col) {
				case 0:
					courseName=row.getCell(col).toString();
					break;
				case 1:
					point=Float.parseFloat(row.getCell(col).toString());
					break;
				default:
					break;
				}
			}
			courseTypeHashMap.put(rowIndex,courseName);
			typeCourseHashMap.put(courseName,rowIndex);
			courseArrangeHashMap.put(courseName,point);
		}
	}
	/*
	 * �����ʦ��Ϣ������
	 */
	public static void loadTeacher() throws EncryptedDocumentException, IOException {
		File file=new File(TeacherInfo);
		Workbook workbook=WorkbookFactory.create(file);
		Sheet sheet=workbook.getSheetAt(0);
		int rowNum=sheet.getLastRowNum();
		Row row=sheet.getRow(0);
		String teacherName=null;
		String teacherPro=null;

		int cellsNum=row.getPhysicalNumberOfCells();
		for(int rowIndex=1;rowIndex<=rowNum;rowIndex++) {
			row=sheet.getRow(rowIndex);
			for(int col=1;col<cellsNum;col++) {
				switch(col) {
				case 1:
					teacherName=row.getCell(col).toString();
					break;
				case 2:
					teacherPro=row.getCell(col).toString();
					break;
				default:
					break;
				}
			}
			byte[] teacher=new byte[40];
			usableTeacher.add(teacher);
			teacherNameHashMap.put(rowIndex,teacherName);
			teacherProHashMap.put(rowIndex,teacherPro);
		}
	}
	/*
	public static void drawPicture() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	}*/
	
}
