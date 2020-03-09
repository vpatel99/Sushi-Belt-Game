package sushigame.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import comp401sushi.AvocadoPortion;
import comp401sushi.CrabPortion;
import comp401sushi.EelPortion;
import comp401sushi.IngredientPortion;
import comp401sushi.Nigiri;
import comp401sushi.Nigiri.NigiriType;
import comp401sushi.RicePortion;
import comp401sushi.Roll;
import comp401sushi.Sashimi;
import comp401sushi.Sashimi.SashimiType;
import comp401sushi.SeaweedPortion;
import comp401sushi.ShrimpPortion;
import comp401sushi.Sushi;
import comp401sushi.TunaPortion;
import comp401sushi.YellowtailPortion;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private int position;
	private String sushiString;
	private String plateString;
	private String ingredientString;
	private int belt_size;
	private double goldPrice;
	private JLabel positionLabel;
	private JLabel sushiLabel;
	private JLabel ingredientLabel;
	private JLabel plateLabel;
	private JButton makeThisPlate;
	private JButton plateNextButton;
	private JButton sushiNextButton;
	private JButton makeSNButton;
	private JButton makeRollButton;
	double avocadoAmount;
	double riceAmount;
	double crabAmount;
	double eelAmount;
	double shrimpAmount;
	double seaweedAmount;
	double tunaAmount;
	double yellowtailAmount;
	JSlider avocadoSlider;
	JSlider crabSlider;
	JSlider eelSlider;
	JComboBox<String> plateBox;
	JSlider posSlider;
	JSlider priceSlider;
	JComboBox<String> sushiBox;
	JComboBox<String> ingredientBox;
	boolean isRollPressed = false;
	private JSlider shrimpSlider;
	private JSlider riceSlider;
	private JSlider seaweedSlider;
	private JSlider tunaSlider;
	private JSlider yellowtailSlider;

	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		posSlider = new JSlider(0, 19);
		ingredientLabel = new JLabel("Position on belt");
		ingredientLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(new JLabel(" "));
		add(ingredientLabel);
		posSlider.setPaintTicks(true);
		posSlider.setSnapToTicks(false);
		posSlider.setPaintLabels(true);
		posSlider.setMajorTickSpacing(2);
		add(posSlider);
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));

		plateBox = new JComboBox<String>();
		plateBox.addItem(" ");
		plateBox.addItem("Red Plate");
		plateBox.addItem("Blue Plate");
		plateBox.addItem("Green Plate");
		plateBox.addItem("Gold Plate");
		plateLabel = new JLabel("Plate");
		plateLabel.setAlignmentX(CENTER_ALIGNMENT);
		plateBox.setActionCommand("Plates");
		plateBox.addActionListener(this);
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(plateLabel);
		add(plateBox);
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		

		sushiBox = new JComboBox<String>();
		sushiBox.addItem(" ");
		sushiBox.addItem("Sashimi");
		sushiBox.addItem("Nigiri");
		sushiBox.addItem("Roll");
		sushiLabel = new JLabel("Sushi");
		sushiBox.setActionCommand("Sushi");

		sushiNextButton = new JButton("Select Ingredients");
		sushiNextButton.setActionCommand("sushi next button");
		sushiNextButton.addActionListener(this);

		ingredientBox = new JComboBox<String>();
		ingredientBox.addItem(" ");
		ingredientBox.addItem("Crab");
		ingredientBox.addItem("Eel");
		ingredientBox.addItem("Shrimp");
		ingredientBox.addItem("Tuna");
		ingredientBox.addItem("Yellowtail");
		ingredientLabel = new JLabel("Ingredient");
		ingredientBox.setActionCommand("Ingr");
		makeSNButton = new JButton("Make this plate");
		makeSNButton.setActionCommand("SN");
		makeSNButton.addActionListener(this);

		makeRollButton = new JButton("Make a Roll!");
		makeRollButton.setActionCommand("Roll");
		makeRollButton.addActionListener(this);

		priceSlider = new JSlider(500, 1000);
		priceSlider.setPaintTicks(true);
		priceSlider.setMajorTickSpacing(100);
		priceSlider.setPaintLabels(true);
		priceSlider.setMinorTickSpacing(25);
		priceSlider.setSnapToTicks(false);
		Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(500, new JLabel("5.00"));
        labels.put(600, new JLabel("6.00"));
        labels.put(700, new JLabel("7.00"));
        labels.put(800, new JLabel("8.00"));
        labels.put(900, new JLabel("9.00"));
        labels.put(1000, new JLabel("10.00"));
        priceSlider.setLabelTable(labels);
       
   
		add(new JLabel("Price (only for GOLD plate)"));
		add(priceSlider);

		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		
		plateNextButton = new JButton("Select Your Sushi");
		plateNextButton.addActionListener(this);
		plateNextButton.setActionCommand("SelectSushi");
		add(plateNextButton);

		makeThisPlate = new JButton("Make this plate!");
		isRollPressed = false;
		makeThisPlate.setActionCommand("Make this plate");
		makeThisPlate.addActionListener(this);
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame rollDialog = new JFrame("ingredients");
		rollDialog.setSize(280, 300);
		rollDialog.setLayout(new GridLayout());
		JPanel slidersFrame = new JPanel();
		if (e.getActionCommand().equals("Plates")) {
			plateString = (String) plateBox.getSelectedItem();
			goldPrice = (double) priceSlider.getValue() / 100;
			position = posSlider.getValue();
		}

		if (e.getActionCommand().equals("SelectSushi")) {
			slidersFrame.add(sushiBox);
			slidersFrame.add(sushiNextButton);
			rollDialog.add(slidersFrame);
			rollDialog.setVisible(true);
		}
		if (e.getActionCommand().equals("sushi next button")) {
			sushiString = (String) sushiBox.getSelectedItem();
			if (sushiString.equals("Sashimi") || sushiString.equals("Nigiri")) {
				slidersFrame.setVisible(true);
				slidersFrame.add(ingredientLabel);
				slidersFrame.add(ingredientBox);
				slidersFrame.add(makeSNButton);
				rollDialog.add(slidersFrame);
				rollDialog.setVisible(true);
				ingredientString = (String) ingredientBox.getSelectedItem();
			} else if (sushiString.equals("Roll")){
				slidersFrame.setVisible(true);
				rollDialog.setSize(280, 600);
				
				 Hashtable<Integer, JLabel> labels = new Hashtable<>();
			        labels.put(0, new JLabel("0"));
			        labels.put(5, new JLabel("0.5"));
			        labels.put(10, new JLabel("1.0"));
			        labels.put(15, new JLabel("1.5"));
			   
				
				avocadoSlider = new JSlider(0, 15);
				avocadoSlider.setValue(15);
				avocadoSlider.setMajorTickSpacing(3);
				avocadoSlider.setPaintLabels(true);
				avocadoSlider.setLabelTable(labels);
				avocadoSlider.setSnapToTicks(false);
				riceSlider = new JSlider(0, 15);
				riceSlider.setValue(0);
				riceSlider.setMajorTickSpacing(3);
				riceSlider.setPaintLabels(true);
				riceSlider.setLabelTable(labels);
				riceSlider.setSnapToTicks(false);
				crabSlider = new JSlider(0, 15);
				crabSlider.setMajorTickSpacing(3);
				crabSlider.setPaintLabels(true);
				crabSlider.setValue(0);
				crabSlider.setLabelTable(labels);
				crabSlider.setSnapToTicks(false);
				eelSlider = new JSlider(0, 15);
				eelSlider.setMajorTickSpacing(3);
				eelSlider.setPaintLabels(true);
				eelSlider.setValue(0);
				eelSlider.setLabelTable(labels);
				eelSlider.setSnapToTicks(false);
				seaweedSlider = new JSlider(0, 15);
				seaweedSlider.setMajorTickSpacing(3);
				seaweedSlider.setPaintLabels(true);
				seaweedSlider.setValue(0);
				seaweedSlider.setLabelTable(labels);
				seaweedSlider.setSnapToTicks(false);
				shrimpSlider = new JSlider(0, 15);
				shrimpSlider.setMajorTickSpacing(3);
				shrimpSlider.setPaintLabels(true);
				shrimpSlider.setValue(0);
				shrimpSlider.setLabelTable(labels);
				shrimpSlider.setSnapToTicks(false);
				tunaSlider = new JSlider(0, 15);
				tunaSlider.setMajorTickSpacing(3);
				tunaSlider.setPaintLabels(true);
				tunaSlider.setValue(0);
				tunaSlider.setLabelTable(labels);
				tunaSlider.setSnapToTicks(false);
				yellowtailSlider = new JSlider(0, 15);
				yellowtailSlider.setMajorTickSpacing(3);
				yellowtailSlider.setPaintLabels(true);
				yellowtailSlider.setValue(0);
				yellowtailSlider.setLabelTable(labels);
				yellowtailSlider.setSnapToTicks(false);
				slidersFrame.add(new JLabel("Make a roll from these ingredients"));
				slidersFrame.add(new JLabel("Make sure they add up to atmost 1.50!"));
				slidersFrame.add(new JLabel("Avocado amount"));
				slidersFrame.add(avocadoSlider);
				slidersFrame.add(new JLabel("Rice amount"));
				slidersFrame.add(riceSlider);
				slidersFrame.add(new JLabel("Crab amount"));
				slidersFrame.add(crabSlider);
				slidersFrame.add(new JLabel("Eel amount"));
				slidersFrame.add(eelSlider);
				slidersFrame.add(new JLabel("Seaweed amount"));
				slidersFrame.add(seaweedSlider);
				slidersFrame.add(new JLabel("Shrimp amount"));
				slidersFrame.add(shrimpSlider);
				slidersFrame.add(new JLabel("Tuna amount"));
				slidersFrame.add(tunaSlider);
				slidersFrame.add(new JLabel("Yellowtail amount"));
				slidersFrame.add(yellowtailSlider);
				slidersFrame.add(makeRollButton);
				rollDialog.add(slidersFrame);
				rollDialog.setVisible(true);
			}
			else {
				
			}
		}

		if (e.getActionCommand().equals("SN")) {
			ingredientString = (String) ingredientBox.getSelectedItem();
			try {
			if (plateString.equals("Red Plate")) {
				if (sushiString.equals("Nigiri")) {
					if (ingredientString.equals("Crab")) {
						makeRedPlateRequest(new Nigiri(NigiriType.CRAB), position);
					} else if (ingredientString.equals("Eel")) {
						makeRedPlateRequest(new Nigiri(NigiriType.EEL), position);
					} else if (ingredientString.equals("Shrimp")) {
						makeRedPlateRequest(new Nigiri(NigiriType.SHRIMP), position);
					} else if (ingredientString.equals("Tuna")) {
						makeRedPlateRequest(new Nigiri(NigiriType.TUNA), position);
					} else if (ingredientString.equals("Yellowtail")) {
						makeRedPlateRequest(new Nigiri(NigiriType.YELLOWTAIL), position);
					} else {
						System.out.println("Can't make this nigiri with this red plate");
					}
				} else if (sushiString.equals("Sashimi")) {
					if (ingredientString.equals("Crab")) {
						makeRedPlateRequest(new Sashimi(SashimiType.CRAB), position);
					} else if (ingredientString.equals("Eel")) {
						makeRedPlateRequest(new Sashimi(SashimiType.EEL), position);
					} else if (ingredientString.equals("Tuna")) {
						makeRedPlateRequest(new Sashimi(SashimiType.TUNA), position);
					} else if (ingredientString.equals("Shrimp")) {
						makeRedPlateRequest(new Sashimi(SashimiType.SHRIMP), position);
					} else if (ingredientString.equals("Yellowtail")) {
						makeRedPlateRequest(new Sashimi(SashimiType.YELLOWTAIL), position);
					}
					else {
						System.out.println("Can't make this sashimi with this red plate");
					}
				}
			}
			if (plateString.equals("Blue Plate")) {
				if (sushiString.equals("Nigiri")) {
					if (ingredientString.equals("Crab")) {
						makeBluePlateRequest(new Nigiri(NigiriType.CRAB), position);
					} else if (ingredientString.equals("Eel")) {
						makeBluePlateRequest(new Nigiri(NigiriType.EEL), position);
					} else if (ingredientString.equals("Shrimp")) {
						makeBluePlateRequest(new Nigiri(NigiriType.SHRIMP), position);
					} else if (ingredientString.equals("Tuna")) {
						makeBluePlateRequest(new Nigiri(NigiriType.TUNA), position);
					} else if (ingredientString.equals("Yellowtail")) {
						makeBluePlateRequest(new Nigiri(NigiriType.YELLOWTAIL), position);
					} else {
						System.out.println("Can't make this nigiri with this blue plate");
					}
				} else if (sushiString.equals("Sashimi")) {
					if (ingredientString.equals("Crab")) {
						makeBluePlateRequest(new Sashimi(SashimiType.CRAB), position);
					} else if (ingredientString.equals("Eel")) {
						makeBluePlateRequest(new Sashimi(SashimiType.EEL), position);
					} else if (ingredientString.equals("Tuna")) {
						makeBluePlateRequest(new Sashimi(SashimiType.TUNA), position);
					} else if (ingredientString.equals("Shrimp")) {
						makeBluePlateRequest(new Sashimi(SashimiType.SHRIMP), position);
					} else if (ingredientString.equals("Yellowtail")) {
						makeBluePlateRequest(new Sashimi(SashimiType.YELLOWTAIL), position);
					} else {
						System.out.println("Can't make this sashimi with this plate");
					}
				}
			}
			if (plateString.equals("Green Plate")) {
				if (sushiString.equals("Nigiri")) {
					if (ingredientString.equals("Crab")) {
						makeGreenPlateRequest(new Nigiri(NigiriType.CRAB), position);
					} else if (ingredientString.equals("Eel")) {
						makeGreenPlateRequest(new Nigiri(NigiriType.EEL), position);
					} else if (ingredientString.equals("Shrimp")) {
						makeGreenPlateRequest(new Nigiri(NigiriType.SHRIMP), position);
					} else if (ingredientString.equals("Tuna")) {
						makeGreenPlateRequest(new Nigiri(NigiriType.TUNA), position);
					} else if (ingredientString.equals("Yellowtail")) {
						makeGreenPlateRequest(new Nigiri(NigiriType.YELLOWTAIL), position);
					} else {
						System.out.println("Can't make this nigiri with this green plate");
					}
				} else if (sushiString.equals("Sashimi")) {
					if (ingredientString.equals("Crab")) {
						makeGreenPlateRequest(new Sashimi(SashimiType.CRAB), position);
					} else if (ingredientString.equals("Eel")) {
						makeGreenPlateRequest(new Sashimi(SashimiType.EEL), position);
					} else if (ingredientString.equals("Tuna")) {
						makeGreenPlateRequest(new Sashimi(SashimiType.TUNA), position);
					} else if (ingredientString.equals("Shrimp")) {
						makeGreenPlateRequest(new Sashimi(SashimiType.SHRIMP), position);
					} else if (ingredientString.equals("Yellowtail")) {
						makeGreenPlateRequest(new Sashimi(SashimiType.YELLOWTAIL), position);
					} else {
						System.out.println("Can't make this sashimi with this green plate");
					}
				}
			}
			if (plateString.equals("Gold Plate")) {
				if (sushiString.equals("Nigiri")) {
					if (ingredientString.equals("Crab")) {
						makeGoldPlateRequest(new Nigiri(NigiriType.CRAB), position, goldPrice);
					} else if (ingredientString.equals("Eel")) {
						makeGoldPlateRequest(new Nigiri(NigiriType.EEL), position, goldPrice);
					} else if (ingredientString.equals("Shrimp")) {
						makeGoldPlateRequest(new Nigiri(NigiriType.SHRIMP), position, goldPrice);
					} else if (ingredientString.equals("Tuna")) {
						makeGoldPlateRequest(new Nigiri(NigiriType.TUNA), position, goldPrice);
					} else if (ingredientString.equals("Yellowtail")) {
						makeGoldPlateRequest(new Nigiri(NigiriType.YELLOWTAIL), position, goldPrice);
					} else {
						System.out.println("Can't make this nigiri");
					}
				} else if (sushiString.equals("Sashimi")) {
					if (ingredientString.equals("Crab")) {
						makeGoldPlateRequest(new Sashimi(SashimiType.CRAB), position, goldPrice);
					} else if (ingredientString.equals("Eel")) {
						makeGoldPlateRequest(new Sashimi(SashimiType.EEL), position, goldPrice);
					} else if (ingredientString.equals("Shrimp")) {
						makeGoldPlateRequest(new Sashimi(SashimiType.SHRIMP), position, goldPrice);
					} else if (ingredientString.equals("Tuna")) {
						makeGoldPlateRequest(new Sashimi(SashimiType.TUNA), position, goldPrice);
					} else if (ingredientString.equals("Yellowtail")) {
						makeGoldPlateRequest(new Sashimi(SashimiType.YELLOWTAIL), position, goldPrice);
					} else {
						System.out.println("Can't make this sashimi");
					}
				}
			}
			}
			catch (Exception t) {
				System.out.println("Please select all");
			}
		}
		
		if(e.getActionCommand().equals("Roll")) {
			avocadoAmount = (double) avocadoSlider.getValue() / 10;
			riceAmount = (double) riceSlider.getValue() / 10;
			crabAmount = (double) crabSlider.getValue() / 10;
			eelAmount = (double) eelSlider.getValue() / 10;
			seaweedAmount = (double) seaweedSlider.getValue() / 10;
			shrimpAmount = (double) shrimpSlider.getValue() / 10;
			tunaAmount = (double) tunaSlider.getValue() / 10;
			yellowtailAmount = (double) yellowtailSlider.getValue() / 10;
			double[] allAmounts = new double[] {avocadoAmount, 
												riceAmount, 
												crabAmount,
												eelAmount,
												seaweedAmount,
												shrimpAmount,
												tunaAmount,
												yellowtailAmount 
												};
			
			int counter = 0;
			List<IngredientPortion> baseArrayList = new ArrayList<IngredientPortion>();
			for(int i = 0; i < allAmounts.length; i++) {
				if(allAmounts[i] != 0.0) {
					if(i == 0) {
						baseArrayList.add(new AvocadoPortion(avocadoAmount));
					} else if (i == 1) {
						baseArrayList.add(new RicePortion(riceAmount));
					}else if (i == 2) {
						baseArrayList.add(new CrabPortion(crabAmount));
					}else if (i == 3) {
						baseArrayList.add(new EelPortion(eelAmount));
					}else if (i == 4) {
						baseArrayList.add(new SeaweedPortion(seaweedAmount));
					}else if (i == 5) {
						baseArrayList.add(new ShrimpPortion(shrimpAmount));
					}else if (i == 6) {
						baseArrayList.add(new TunaPortion(tunaAmount));
					}else {
						baseArrayList.add(new YellowtailPortion(yellowtailAmount));
					}
				}
			}
			
			IngredientPortion[] rollIngredientPortions = new IngredientPortion[baseArrayList.size()];
			for(int i = 0; i < baseArrayList.size(); i++) {
				rollIngredientPortions[i] = baseArrayList.get(i);
			}
			for(int i = 0; i < rollIngredientPortions.length; i++) {
				counter += rollIngredientPortions[i].getAmount();
			}
			try {
				if(counter > 1.5) {
					throw new RuntimeException("");
				}
				if(plateString.equals("Red Plate")) {
					makeRedPlateRequest(new Roll("Random", rollIngredientPortions), position);
				} else if (plateString.equals("Blue Plate")) {
					makeBluePlateRequest(new Roll("Random", rollIngredientPortions), position);
				} else if (plateString.equals("Green Plate")) {
					makeGreenPlateRequest(new Roll("Random", rollIngredientPortions), position);
				} else {
					makeGoldPlateRequest(new Roll ("Random" , rollIngredientPortions), position, goldPrice);
				}
					
				}
				catch (Exception r) {
					System.out.println("Can't make this roll");
				}
		}
	}
}
