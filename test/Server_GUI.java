package TCP_달력_변환기_서버;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server_GUI extends JFrame implements ActionListener {
	Server_Thread th;
	Container ct;
	JTextArea jta;
	JTextField jtf_port;
	JTextField day;
	JButton comunication;
	boolean check = true;
	public Server_GUI()
	{
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		init();
		setSize(500,400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		jta = new JTextArea();
	
		jtf_port = new JTextField();
		day = new JTextField();
		comunication = new JButton("통신");
		comunication.addActionListener(this);

		JLabel jl_port = new JLabel("Client Port", JLabel.CENTER);
		
		
		JPanel jp_grid1 = new JPanel(new GridLayout(2,3));
		
		JLabel jl = new JLabel("");
		JLabel jl2 = new JLabel("");
		JLabel jl3 = new JLabel("");
		JLabel jl4 = new JLabel("");

		jp_grid1.add(jl);
		jp_grid1.add(jl_port);
		jp_grid1.add(jl2);
		jp_grid1.add(jl3);
		jp_grid1.add(jtf_port);
		jp_grid1.add(jl4);
		

		ct.add(jp_grid1, BorderLayout.NORTH);
		ct.add(jta, BorderLayout.CENTER);
		ct.add(comunication, BorderLayout.SOUTH);
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int port = Integer.parseInt(jtf_port.getText());
		if(check)
		{
			check=false;
			th = new Server_Thread(port,jta);
			th.start();
		}
		else
		{
			th.new_socket();
			
		}
	
	
	}
}
