package window;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import GA.WholeSolution;

public class DrawPicture extends JFrame{
	WholeSolution ws;
	public DrawPicture(WholeSolution ws,int type){
		this.ws=ws;
		CategoryDataset datasetMemory=createDataset(type);
        createChart(datasetMemory,type);
	}

	public  CategoryDataset createDataset(int type) {
		   DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
		   String series="ѧϰЧ������";   //��������
			
			if(type==0){
				series="ѧϰЧ������";
			}else if(type==1){
				series="ǰ����ܿ�ʱ����";
			}else if(type==2){
				series="ÿ�տ�ʱ����";
			}
			int size=ws.historyDay.size();
			System.out.println(ws.historyDay.size());
		   Double [][]array3 = new Double[size][3];
		   DecimalFormat df = new DecimalFormat(".00");
		  
		   for(int i=0;i<3;i++) {
			   for(int j=0;j<size;j++) {
				   switch(i) {
				   case 0:
					   array3[j][i]=Double.valueOf(df.format(ws.historyPoint.get(j)));
					   break;
				   case 1:
					   array3[j][i]=Double.valueOf(df.format(ws.historyHalfTerm.get(j)));
					   break;
				   case 2:
					   array3[j][i]=Double.valueOf(df.format(ws.historyDay.get(j)));
					   
					   break;
				   default:
						   break;
				   }
			   }
		   }
		   
		   for(int i=0;i<size;i++){
	          dataset.addValue(Double.valueOf(array3[i][type]), series,String.valueOf(i+1));  //�����ֱ�������ֵ���������ơ�����ֵ
		   }
	               
	       return dataset;
	}
	
	public void createChart(CategoryDataset dataset,int type) {

		
		JFreeChart chart = ChartFactory.createLineChart("ѧϰЧ������", "��������",
				"��߷�", dataset, PlotOrientation.VERTICAL, true, true,
				true);
		if(type==0){
		    chart = ChartFactory.createLineChart("ѧϰЧ������", "��������",
				"��߷�", dataset, PlotOrientation.VERTICAL, true, true,
				true);
		}else if(type==1){
			chart = ChartFactory.createLineChart("ǰ���������", "��������",
					"��߷�", dataset, PlotOrientation.VERTICAL, true, true,
					true);
		}else if(type==2){
			chart = ChartFactory.createLineChart("ÿ��ѧʱ����", "��������",
					"��߷�", dataset, PlotOrientation.VERTICAL, true, true,
					true);
		}
		
 
		CategoryPlot cp = chart.getCategoryPlot();
		cp.setBackgroundPaint(ChartColor.WHITE); // ����ɫ����
		cp.setRangeGridlinePaint(ChartColor.GRAY); // ������ɫ����
		cp.setDomainGridlinePaint(ChartColor.BLACK);
		cp.setNoDataMessage("û������");
		// ���������Բ���
		NumberAxis rangeAxis = (NumberAxis) cp.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true); // �Զ�����
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);
		rangeAxis.setAutoRange(false);
 
		// ������Ⱦ���� ��Ҫ�Ƕ�����������
		CategoryPlot plot = chart.getCategoryPlot();
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();
		
		renderer.setBaseItemLabelsVisible(false);// ���������Ƿ���ʾ���ݵ�
		// ����������ʾ�����ݵ��ֵ
		renderer.setSeriesPaint(0, Color.black); // �������ߵ���ɫ
 
		renderer.setBaseShapesFilled(true);
 
		renderer.setBaseItemLabelsVisible(false);
 
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
 
		ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
 
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
 
		
		chart.getTitle().setFont(new Font("����", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
		CategoryAxis domainAxis = plot.getDomainAxis();
		/**
		 * x ����������
		 */
		domainAxis.setTickLabelFont(new Font("����", Font.PLAIN, 11));
		/**
		 * x���������
		 */
		domainAxis.setLabelFont(new Font("����", Font.PLAIN, 12));
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		/**
		 * y���ϵ�����
		 */
		numberaxis.setTickLabelFont(new Font("����", Font.PLAIN, 12));
		/**
		 * y����
		 */
		numberaxis.setLabelFont(new Font("����", Font.PLAIN, 12));
 

		JPanel jPanel = new ChartPanel(chart);
		JFrame frame = new JFrame("�ݻ�ͼ");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(jPanel);
		frame.setBounds(800, 450, 800, 600);
		frame.setVisible(true);
 
	} 
	
}
