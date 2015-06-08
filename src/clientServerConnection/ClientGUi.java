package clientServerConnection;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClientGUi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea area;
	private JButton list;
	private Client client;

	public ClientGUi() {

		setSize(800, 600);
		setTitle("Client Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		area = new JTextArea();
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		container.add(area, BorderLayout.CENTER);
		list = new JButton("LIST");
		list.addActionListener(buttonListener);
		Container south = new Container();
		south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
		south.add(list);
		container.add(south, BorderLayout.SOUTH);
		JScrollPane scrollPane = new JScrollPane(area);
		container.add(scrollPane);
		try {
			client = new Client(area);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	ActionListener buttonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton buttonClicked = (JButton) e.getSource();
			String message = buttonClicked.getText();
			client.write(message);
		}
	};

}
