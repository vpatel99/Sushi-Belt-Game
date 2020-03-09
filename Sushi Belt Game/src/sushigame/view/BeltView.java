package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import comp401sushi.IngredientPortion;

import comp401sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JPanel implements BeltObserver, ActionListener {

	private Belt belt;
	private JButton[] belt_buttons;
	JPopupMenu plateMenu = new JPopupMenu();
	private int position;

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		setLayout(new GridLayout(belt.getSize(), 1));
		belt_buttons = new JButton[belt.getSize()];
		for (int i = 0; i < belt.getSize(); i++) {
			JButton plabel = new JButton("Empty");
			plabel.setMinimumSize(new Dimension(300, 20));
			plabel.setPreferredSize(new Dimension(300, 20));
			plabel.setOpaque(true);
			plabel.setBackground(Color.GRAY);
			plabel.setName(i + "Plate");
			add(plabel);
			belt_buttons[i] = plabel;
		}
		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		plateMenu.setVisible(false);
		refresh();
	}

	private void refresh() {
		for (int i = 0; i < belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JButton plabel = belt_buttons[i];
			if (p == null) {
				plabel.setText("");
				plabel.setBackground(Color.DARK_GRAY);
			} else {
				plabel.setText(p.getChef().getName() + "'s " + p.getContents().getName());
				plabel.addActionListener(this);
				plabel.setActionCommand(i + "Plate");
				switch (p.getColor()) {
				case RED:
					plabel.setBackground(Color.RED);
					break;
				case GREEN:
					plabel.setBackground(Color.GREEN);
					break;
				case BLUE:
					plabel.setBackground(Color.BLUE);
					break;
				case GOLD:
					plabel.setBackground(Color.YELLOW);
					break;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		plateMenu.removeAll();
		plateMenu.setVisible(false);
		for(int i = 0; i < belt_buttons.length; i++) {
			try {
			if(e.getActionCommand().equals(i + "Plate")) {
				String ingredients = new String();
				IngredientPortion[] y = belt.getPlateAtPosition(i).getContents().getIngredients().clone();
				for(int n = 0; n < y.length; n++) {
					String result = String.format("%.2f", y[n].getAmount());
					ingredients +=  " " + result + " "  +y[n].getName();
				}
				plateMenu.add(new JLabel("Position: " + i));
				plateMenu.add(new JLabel("Chef: " + belt.getPlateAtPosition(i).getChef().getName()));
				plateMenu.add("Color: "  + belt.getPlateAtPosition(i).getColor());
				if(belt.getPlateAtPosition(i).getContents().typeOfSushi().equals("Sashimi") || belt.getPlateAtPosition(i).getContents().typeOfSushi().equals("Nigiri")) {
					plateMenu.add("Type of Sushi: "  + belt.getPlateAtPosition(i).getContents().getName());
				} else {
					plateMenu.add("Contents: " + ingredients);
				}
				if(belt.getPlateAtPosition(i).getColor().equals(comp401sushi.Plate.Color.GOLD)) {
					plateMenu.add("Gold Plate's Price: " + String.format("%.2f", belt.getPlateAtPosition(i).getPrice()));
				}
				plateMenu.add("Age: " + belt.getAgeOfPlateAtPosition(i));
			}
			}
			catch (Exception ew) {
			}
		}
		plateMenu.setVisible(true);
	}
}
