package clientServerConnection;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ClientGUi extends JFrame {

	private JTextArea area;
	private JButton upload;
	private JButton download;
	private JButton list;

	public ClientGUi() {

		setSize(800, 600);
		setTitle("Client Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		area = new JTextArea();
		container.add(area, BorderLayout.CENTER);
		upload = new JButton("upload");
		download = new JButton("download");
		list = new JButton("list");
		Container south = new Container();
		south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
		south.add(list);
		south.add(upload);
		south.add(download);
		container.add(south, BorderLayout.SOUTH);

	}
	
	
}
