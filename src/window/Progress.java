package window;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

 /*
  * ׼������̬����������δ���
  */
@SuppressWarnings("serial")
public class Progress extends JFrame implements Runnable {
 
	// ������ش��ڴ�С
	public static final int LOAD_WIDTH = 455;
	public static final int LOAD_HEIGHT = 295;
	// ��ȡ��Ļ���ڴ�С
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	// ������������
	public JProgressBar progressbar;
	// �����ǩ���
 
	// ���캯��
	public Progress() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
    	setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(800, 450, 350, 60);
        setTitle("���ɿα���....");
        getContentPane().setLayout(null);
		// ����������
		progressbar = new JProgressBar();
		// ��ʾ��ǰ����ֵ��Ϣ
		progressbar.setStringPainted(true);
		// ���ý������߿���ʾ
		progressbar.setBorderPainted(false);
		// ���ý�������ǰ��ɫ
		progressbar.setForeground(new Color(0, 210, 40));
		// ���ý������ı���ɫ
		progressbar.setBackground(new Color(188, 190, 194));
		progressbar.setBounds(0,0,350,60);
		// ������
		 getContentPane().add(progressbar);

 
	}
 
	public static void main(String[] args) {
		Progress t = new Progress();
		new Thread(t).start();
	}
 
	@Override
	public void run() {
		int i;
		for (i = 0; i < 100; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			progressbar.setValue(i);
		}
		if(i==100)
		JOptionPane.showMessageDialog(this, "�������");
		
		dispose();
 
	}
 
}

