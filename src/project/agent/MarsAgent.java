package project.agent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import project.environment.MarsMap;
import project.environment.MarsTile;
import qlearning.QMemory;
import qlearning.QSelector;
import qlearning.QTuple;

public class MarsAgent {

	private static final double FAIL_STATE = -50;
	private static final double SUCCESS_STATE = 1;

	private ArrayList<MarsTile> traversed;
	private MarsMap map;

	private QSelector selector;
	private QMemory memory;

	public MarsAgent(MarsMap map) {
		this.traversed = new ArrayList<MarsTile>();
		this.map = map;
		this.memory = new QMemory(this.map.getWidth(), this.map.getHeight());
		this.selector = new QSelector(this.memory);
	}

	public ArrayList<MarsTile> getPath() {
		return this.traversed;
	}

	public void traverse() {

		this.traversed.clear();

		MarsTile currentState = this.map.getStartTile();
		double currentWeight = 0;
		double mapState = 0;
		QSelector sel = this.selector.makeCopy();

		/// TODO implement loop and make sure it's correct
		this.traversed.add(currentState);
		do {

			QTuple intermediateState = sel.select(Arrays.asList(this.getNeighborStates(currentState)), currentWeight);
			currentState = (MarsTile) intermediateState.getState();
			currentWeight = intermediateState.getWeight();
			// currentWeight+=10;
			this.traversed.add(currentState);
			// mapState = this.memory.getWeight(currentState.getPosition());
			currentWeight += .1;
			// double ep = sel.getEpsilon();
			// sel.setEpsilon(ep-.0001);

			mapState = this.map.getState(currentState.getPosition()).getWeight();
		} while (mapState < SUCCESS_STATE && mapState > FAIL_STATE);

		/*
		 * if (mapState <= FAIL_STATE) { //System.out.println("FAIL");
		 * this.memory.setWeight(currentState.getPosition(), Double.NEGATIVE_INFINITY);
		 * } if (mapState >= SUCCESS_STATE) {
		 * this.memory.setWeight(currentState.getPosition(), Double.POSITIVE_INFINITY);
		 * }
		 */

	}

	private MarsTile[] getNeighborStates(MarsTile tile) {
		return new MarsTile[] { this.getNorthState(tile), this.getEastState(tile), this.getSouthState(tile),
				this.getWestState(tile) };
	}

	private MarsTile getCenterState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y);
	}

	private MarsTile getNorthState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y - 1);
	}

	private MarsTile getSouthState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y + 1);
	}

	private MarsTile getEastState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x + 1, pos.y);
	}

	private MarsTile getWestState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x - 1, pos.y);
	}

	public String getMemoryMap() {

		StringBuilder build = new StringBuilder();
		build.append("[");

		for (int y = 0; y < this.map.getHeight(); y++) {
			for (int x = 0; x < this.map.getWidth(); x++) {
				build.append(this.memory.getWeight(x, y));
				if (x != this.map.getWidth() - 1) {
					build.append(", ");
				}
			}

			if (y != this.map.getHeight() - 1) {
				build.append("\n");
			}
		}

		return build.toString();
	}

}
