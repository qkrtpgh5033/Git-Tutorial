package �ȵ���̵�_����;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Management.CallBack;
import Serial.Serial;

public class GUI extends JFrame implements ActionListener {

	CallBack cb;
	Container ct;
	JButton jb_generate;
	JButton jb_send;
	
	JScrollPane jsp;
	JTextArea jta;
	JTextField jtf_port;
	JTextField jtf_msg;
	
	TcpServer server;
	Serial si;
	boolean flag = true;
	
	public GUI()
	{
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		cb = new CallBack();
		init();
		
		setSize(300,400);
		setVisible(true);
		setTitle("�ȵ���̵� ä��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	void init()
	{
		JPanel jp_grid = new JPanel(new GridLayout(2,2,5,5));
		JPanel jp_br = new JPanel(new BorderLayout());
		jtf_port = new JTextField();
		jb_generate = new JButton("Open");
		
		jb_send = new JButton("������");
		JLabel jl_chat = new JLabel("ä��");
		jta = new JTextArea();
		jsp = new JScrollPane(jta);
		jtf_port =new JTextField();
		jtf_msg = new JTextField();
		
		JLabel jl1 = new JLabel("Server Port",JLabel.CENTER);
		JLabel jl2 = new JLabel(" ",JLabel.CENTER);
		
		jb_generate.addActionListener(this);
		jb_send.addActionListener(this);
		
		jp_grid.add(jl1);
		jp_grid.add(jl2);
		jp_grid.add(jtf_port);
		jp_grid.add(jb_generate);
		
		
		
		ct.add(jp_grid,BorderLayout.NORTH);
		ct.add(jsp,BorderLayout.CENTER);
	
		
		
		
		jtf_port.setText(9999+"");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(jb_generate))	// ��Ʈ ����
		{
			if(flag)
			{
				int ip = Integer.parseInt(jtf_port.getText());
				
				server = new TcpServer(ip, jta, cb);
				si = new Serial(cb);
				cb.set_tcp(server);
				cb.set_serial(si);
				
				server.start();
				jb_generate.setEnabled(false);
			}
			flag = false;
		}
	
	}

}