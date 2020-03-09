package sushigame.view;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver {

	private SushiGameModel game_model;
	private JLabel display;
	private boolean highToLow;
	private boolean spoiled;
	private boolean eaten;

	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);

		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		display.setText(makeScoreboardHTML());
		highToLow = true;
		spoiled = false;
		eaten = false;
	}

	private String makeScoreboardHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";
		// Create an array of all chefs and sort by balance.
		if (highToLow) {
			Chef[] opponent_chefs = game_model.getOpponentChefs();
			Chef[] chefs = new Chef[opponent_chefs.length + 1];
			chefs[0] = game_model.getPlayerChef();
			for (int i = 1; i < chefs.length; i++) {
				chefs[i] = opponent_chefs[i - 1];
			}
			Arrays.sort(chefs, new HighToLowBalanceComparator());

			for (Chef c : chefs) {
				sb_html += c.getName() + " ($" + Math.round(c.getBalance() * 100.0) / 100.0 + ") <br>";
			}
			return sb_html;
		} else if(spoiled) {
			Chef[] opponent_chefs = game_model.getOpponentChefs();
			Chef[] chefs = new Chef[opponent_chefs.length + 1];
			chefs[0] = game_model.getPlayerChef();
			for (int i = 1; i < chefs.length; i++) {
				chefs[i] = opponent_chefs[i - 1];
			}
			Arrays.sort(chefs, new MostSpoiledComparator());

			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getSushiSpoiled() * 100.0) / 100.0 + ") <br>";
			}
			return sb_html;
		} else {
			Chef[] opponent_chefs = game_model.getOpponentChefs();
			Chef[] chefs = new Chef[opponent_chefs.length + 1];
			chefs[0] = game_model.getPlayerChef();
			for (int i = 1; i < chefs.length; i++) {
				chefs[i] = opponent_chefs[i - 1];
			}
			Arrays.sort(chefs, new MostEaten());

			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getSushiEaten() * 100.0) / 100.0 + ") <br>";
			}
			return sb_html;
		}
	}

	public void refresh() {
		display.setText(makeScoreboardHTML());
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}
	}
	public void setLow(boolean tf) {
		highToLow = tf;
		eaten = !tf;
		spoiled = !tf;
	}
	public void setSpoiled(boolean tf) {
		highToLow = !tf;
		eaten = !tf;
		spoiled = tf;
	}
	public void setEaten(boolean tf) {
		highToLow = !tf;
		eaten = tf;
		spoiled = !tf;
	}

}
