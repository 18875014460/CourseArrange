package window;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import tools.ExcelReader;

public class ModelOn extends JFrame{
	JComboBox<String> jc=new JComboBox<>(new BooleanComboBox());
	JLabel jl=new JLabel();
	public ModelOn(JMenuItem jt) {
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(800, 450, 450, 150);
        setTitle(jt.getText());
        
		setResizable(false);
        setVisible(true);
        jl.setBounds(10, 10, 130, 15);
        jc.setBounds(150, 7, 150, 21);
        
        switch(jt.getText()) {
        case "����ÿ���ʱ�Ż�":
        	jl.setText("ÿ���ʱ�Ż�");
        	break;
        case "����ǰ����ܿ�ʱ�Ż�":
        	jl.setText("ǰ����ܿ�ʱ�Ż�");
        	break;
        case "����ѧ���Ż�":
        	jl.setText("ѧ���Ż�");
        	break;
        default:
        		break;
        }
        getContentPane().add(jl);
        getContentPane().add(jc);
        JButton button_2 = new JButton("ȷ��");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	switch(jt.getText()) {
            	  case "����ÿ���ʱ�Ż�":
                  	ExcelReader.selectByDayAverage=(String)jc.getSelectedItem();
                  	break;
                  case "����ǰ����ܿ�ʱ�Ż�":
                	  ExcelReader.selectByHalfTerm=(String)jc.getSelectedItem();
                  	break;
                  case "����ѧ���Ż�":
                	  ExcelReader.selectByPoint=(String)jc.getSelectedItem();
                  	break;
                  default:
                  		break;
            	}
            	dispose();
            }
        });
        button_2.setBounds(182, 69, 93, 23);
        getContentPane().add(button_2);
        
        
	}
}
