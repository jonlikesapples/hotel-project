import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author BlanchyPolangcos
 * @author ChristopherNavy
 * @author JonathanWong
 *
 * A View which displays all vacant rooms which comply with the
 * dates and price specified in the model GuestSession.
 *
 **/
public class ScreenVacancies extends JPanel {

	private HotelView view; 
	private ChangeListener listener;
	private GuestSession gs;
	
	/**
	 * Constructor for classes of this object.
	 * @param v HotelView object upon which this JPanel is mounted.
	 */
	public ScreenVacancies(HotelView v) {
		view = v;
		gs = (GuestSession) view.getUserSession();
		setLayout(new FlowLayout());
		
		JPanel vacancies = new JPanel();
		
		vacancies.setLayout(new FlowLayout());
		JTextArea rooms = new JTextArea(); 
		rooms.setText(printRooms(gs.getRooms()));
		rooms.setEditable(false);
		
		JScrollPane scrollRooms = new JScrollPane(rooms, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollRooms.setPreferredSize(new Dimension(200,400));
		
		listener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				ArrayList<Room> vacants = gs.getRooms(); /*gets available rooms*/
				rooms.setText(printRooms(vacants));
				rooms.repaint();
				vacancies.repaint();
				repaint();
			}
		};
		gs.attach(listener);
		
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		JLabel enterPrompt = new JLabel("Enter room number:");
		JTextField enterRoom = new JTextField();
		JButton confirm = new JButton("Confirm");
		JButton	moreReservations = new JButton("More reservations?");
		JButton done = new JButton("Done");
		
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int roomNum = Integer.parseInt(enterRoom.getText());
					
					boolean found = false;
					
					for (Room r : gs.getRooms()) {
						if (r.getRoomNumber() + 1 == roomNum) { // +1 because getRoomNumber returns array position
							found = true;
						}
					}
					
					if (found) {
						gs.addReservation(roomNum - 1);
					}
					else {
						JOptionPane.showMessageDialog(null, "Room not available!", "Error", 
								JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Invalid input!", "Error", 
							JOptionPane.INFORMATION_MESSAGE);
				}
				enterRoom.setText("");
			}

		});
		
		/*
		 * pops-up screen to choose start/end dates and price
		 */
		moreReservations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame popup = new JFrame();
				popup.add(new ScreenMakeRes(view, true));
				popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				popup.pack();
				popup.setVisible(true);
				/* vacancies will be updated when this window is completed */
			}
		});
		
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				ArrayList<Reservation> newRes = ((GuestSession) view.getUserSession()).getNewReservations();
//				for (Reservation r : newRes) {
//					view.getHotel().addReservation(r);
//				}
				view.changeScreen(new ScreenReceiptOptions(view, gs));
			}
		});
		

		vacancies.add(scrollRooms);
		
		buttons.add(enterPrompt);
		buttons.add(enterRoom);
		buttons.add(confirm);
		buttons.add(moreReservations);
		buttons.add(done);
		
		add(vacancies);
		add(buttons);
		
	}

	/**
	 * Gets vacant rooms that match the price and reservation dates
	 * @param vacants ArrayList of vacant rooms
	 * @return formatted String of vacant rooms 
	 */
	public String printRooms(ArrayList<Room> vacants) {
		String text = "Rooms for " + gs.getStart() + " - " + gs.getEnd() + "\n\n";

			for (Room r : vacants) {
				text = text + (r.getRoomNumber() + 1) + "\n";
			}
		
		return text;
	}
}
