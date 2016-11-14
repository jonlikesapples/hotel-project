import java.awt.GridLayout;

import javax.swing.*;

public class ScreenManagerView extends JPanel {

	private HotelController controller;
	
	public ScreenManagerView() {
		controller = new HotelController();
		
		setLayout(new GridLayout(3, 2, 20, 20));
		
		JTextArea monthView = new JTextArea("Month view with clickable dates");
		JTextArea monthInfo = new JTextArea("Room information:\nIndividualRooms:\nOccupiedRooms:");
		JTextArea roomView = new JTextArea("All rooms; clickable room numbers go here");
		JTextArea roomInfo = new JTextArea("Info of individual room; if reserved, show user ID as well");

		JButton back = new JButton("Go Back");
		
		back.addActionListener(controller.changeScreen(new ScreenManager()));
		
		add(monthView);
		add(monthInfo);
		add(roomView);
		add(roomInfo);
		add(back);
		
	}

}
