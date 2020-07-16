package window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.ExcelReader;

public class ConfigFrame extends JFrame{
	public ConfigFrame() {
	//setLayout(null);
	setResizable(false);
	setTitle("��ǰ�����ļ�");
	Container container=getContentPane();
	JPanel jp=new JPanel(new GridLayout(12,1,10,0));
	setBounds(800,450,400,600);
	JLabel classroomInfo=new JLabel("������Ϣurl:"+"		"+ExcelReader.ClassroomInfo);
	JLabel teacherInfo=new JLabel("��ʦ��Ϣurl:"+"	"+ExcelReader.TeacherInfo);
	JLabel arrangeInfo=new JLabel("ѧ�ְ���url:"+"	"+ExcelReader.ArrangeInfo);
	JLabel courseInfo=new JLabel("�γ���Ϣurl:"+"	"+ExcelReader.CourseInfo);
	JLabel classNum = new JLabel("�༶����:"+"	  "+String.valueOf(ExcelReader.classNum));
	JLabel studentsNum=new JLabel("ÿ��ѧ������:"+"	"+String.valueOf(ExcelReader.studentsNum));
	JLabel savePath=new JLabel("����·��:"+"		"+ExcelReader.savePath);
	JLabel mutate=new JLabel("�������:"+"	"+String.valueOf(ExcelReader.mutatePossibility));
	JLabel crossover=new JLabel("�������:"+"	"+String.valueOf(ExcelReader.crossoverPossibility));
	JLabel dayaverage=new JLabel("ÿ��ƽ���Ż�:"+"	 "+ExcelReader.selectByDayAverage);
	JLabel effectivity=new JLabel("ѧϰЧ���Ż�:"+"	  "+ExcelReader.selectByPoint);
	JLabel halfterm=new JLabel("ǰ����ܿ�ʱ�Ż�:"+"	  "+ExcelReader.selectByHalfTerm);
	jp.add(classroomInfo);
	jp.add(teacherInfo);
	jp.add(arrangeInfo);
	jp.add(courseInfo);
	jp.add(savePath);
	jp.add(classNum);
	jp.add(studentsNum);
	jp.add(mutate);
	jp.add(crossover);
	jp.add(dayaverage);
	jp.add(effectivity);
	jp.add(halfterm);
	container.add(jp,BorderLayout.CENTER);
	//setBounds(700,700,800,800);
	}
}
