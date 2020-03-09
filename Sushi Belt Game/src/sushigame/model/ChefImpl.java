package sushigame.model;

import java.util.ArrayList;
import java.util.List;

import comp401sushi.Plate;
import comp401sushi.RedPlate;
import comp401sushi.Sushi;
import comp401sushi.IngredientPortion;
import comp401sushi.Nigiri.NigiriType;
import comp401sushi.Plate.Color;
import comp401sushi.Sashimi.SashimiType;

public class ChefImpl implements Chef, BeltObserver {

	private double balance;
	private double sushiEaten;
	private double sushiSpoiled;
	private List<HistoricalPlate> plate_history;
	private String name;
	private ChefsBelt belt;
	private boolean already_placed_this_rotation;
	private int playerPlateCount;
	private int chef2Count;
	private int chef3Count;
	private int chef4Count;

	public ChefImpl(String name, double starting_balance, ChefsBelt belt) {
		this.name = name;
		this.balance = starting_balance;
		this.belt = belt;
		sushiEaten = 0.0;
		sushiSpoiled = 0.0;
		belt.registerBeltObserver(this);
		already_placed_this_rotation = false;
		plate_history = new ArrayList<HistoricalPlate>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String n) {
		this.name = n;
	}

	@Override
	public HistoricalPlate[] getPlateHistory(int history_length) {
		if (history_length < 1 || (plate_history.size() == 0)) {
			return new HistoricalPlate[0];
		}

		if (history_length > plate_history.size()) {
			history_length = plate_history.size();
		}
		return plate_history.subList(plate_history.size() - history_length, plate_history.size() - 1)
				.toArray(new HistoricalPlate[history_length]);
	}

	@Override
	public HistoricalPlate[] getPlateHistory() {
		return getPlateHistory(plate_history.size());
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public void makeAndPlacePlate(Plate plate, int position)
			throws InsufficientBalanceException, BeltFullException, AlreadyPlacedThisRotationException {

		if (already_placed_this_rotation) {
			throw new AlreadyPlacedThisRotationException();
		}

		if (plate.getContents().getCost() > balance) {
			throw new InsufficientBalanceException();
		}
		belt.setPlateNearestToPosition(plate, position);
		balance = balance - plate.getContents().getCost();
		already_placed_this_rotation = true;
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.PLATE_CONSUMED) {
			IngredientPortion[] y;
			double amount = 0.0;
			Plate plate = ((PlateEvent) e).getPlate();
			if (plate.getChef() == this) {
				y = plate.getContents().getIngredients().clone();
				for (int i = 0; i < y.length; i++) {
					amount = amount + y[i].getAmount();
				}
				balance += plate.getPrice();
				sushiEaten++;
				sushiEaten += sushiEaten * amount;
				Customer consumer = belt.getCustomerAtPosition(((PlateEvent) e).getPosition());
				plate_history.add(new HistoricalPlateImpl(plate, consumer));
			}
		} else if (e.getType() == BeltEvent.EventType.PLATE_SPOILED) {
			IngredientPortion[] y;
			double amount = 0.0;
			Plate plate = ((PlateEvent) e).getPlate();
			if (plate.getChef() == this) {
				y = plate.getContents().getIngredients().clone();
				for (int i = 0; i < y.length; i++) {
					amount = amount + y[i].getAmount();
				}
				sushiSpoiled++;
				sushiSpoiled += sushiSpoiled * amount;
				plate_history.add(new HistoricalPlateImpl(plate, null));
				
			}
		} else if (e.getType() == BeltEvent.EventType.ROTATE) {
			already_placed_this_rotation = false;
		}
	}

	@Override
	public boolean alreadyPlacedThisRotation() {
		return already_placed_this_rotation;
	}

	public int getPlayerPlatesCount() {
		return playerPlateCount;
	}

	public int getChef2PlatesCount() {
		return chef2Count;
	}

	public int getChef3PlatesCount() {
		return chef3Count;
	}

	public int getChef4PlatesCount() {
		return chef4Count;
	}

	public double getSushiEaten() {
		// TODO Auto-generated method stub
		return sushiEaten;
	}

	@Override
	public double getSushiSpoiled() {
		// TODO Auto-generated method stub
		return sushiSpoiled;
	}

}
