package sushigame.view;

import java.util.Comparator;

import sushigame.model.Chef;

public class MostSpoiledComparator implements Comparator<Chef> {

	@Override
	public int compare(Chef a, Chef b) {
		
		return (int) (Math.round(a.getSushiSpoiled()*100.0) - 
				Math.round(b.getSushiSpoiled())*100);
	}

}
