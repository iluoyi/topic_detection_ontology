package GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	Result_Panel result_panel = new Result_Panel();
	JTabbedPane jTabbedPane = new JTabbedPane();
	
	public MainFrame(){
		this.setTitle("LUO Yi_Final Year Project_2012");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1250,750);
		this.setLocationRelativeTo(null);//in the center place
		this.setResizable(false);
		
		
		jTabbedPane.addTab("Results", null, result_panel, null);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(jTabbedPane, BorderLayout.CENTER);
	}
	
}
