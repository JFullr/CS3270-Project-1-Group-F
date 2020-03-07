package project.agent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import project.environment.MarsMap;
import project.environment.MarsTile;
import qlearning.QSelector;
import qlearning.QTuple;
import qlearning.QValue;

/**
 * The Class MarsAgent.
 * 
 * @author Amelia Reynolds, Joseph Fuller, Kyle Riggs, Timothy Brooks
 * @version Spring 2020
 */
public class MarsAgent {

	private static final double FAIL_STATE = -50;
	private static final double SUCCESS_STATE = 100;
	private ArrayList<MarsTile> traversed;
	
	private MarsMap map;
	private QSelector selector;
	
	private HashMap<QValue, Iterable<QValue>> validStates;

	/**
	 * Instantiates a new mars agent.
	 *
	 * @param map the map
	 */
	public MarsAgent(MarsMap map) {
		this.traversed = new ArrayList<MarsTile>();
		this.map = map;
		this.validStates = this.initStateMap();
		this.selector = new QSelector(this.validStates);
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public ArrayList<MarsTile> getPath() {
		return this.traversed;
	}

	/**
	 * Traverse the given map with the qlearning selector.
	 *
	 * @param epsilon the epsilon
	 * @param gamma the gamma
	 * @param alpha the alpha
	 */
	public void traverse(double epsilon, double gamma, double alpha) {

		this.traversed.clear();

		MarsTile currentState = this.map.getStartTile();
		double mapState = 0;
		QSelector sel = this.selector.makeCopy();
		sel.setAlpha(alpha);
		sel.setEpsilon(epsilon);
		sel.setGamma(gamma);

		this.traversed.add(currentState);
		do {

			QTuple intermediateState = sel.select(currentState);

			currentState = (MarsTile) intermediateState.getState();

			this.traversed.add(currentState);

			mapState = this.map.getState(currentState.getPosition()).getWeight();

		} while (mapState < SUCCESS_STATE && mapState > FAIL_STATE);

	}

	/**
	 * Gets the epsilon.
	 *
	 * @return the epsilon
	 */
	public double getEpsilon() {
		return this.selector.getEpsilon();
	}

	/**
	 * Sets the epsilon.
	 *
	 * @param value the new epsilon
	 */
	public void setEpsilon(double value) {
		this.selector.setEpsilon(value);
	}

	/**
	 * Gets the neighbor states.
	 *
	 * @param tile the tile
	 * @return the neighbor states
	 */
	public MarsTile[] getNeighborStates(MarsTile tile) {
		return new MarsTile[] { this.getNorthState(tile), this.getSouthState(tile), this.getEastState(tile),
				this.getWestState(tile) };
	}

	/**
	 * Gets the north state.
	 *
	 * @param tile the tile
	 * @return the north state
	 */
	public MarsTile getNorthState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y - 1);
	}

	/**
	 * Gets the south state.
	 *
	 * @param tile the tile
	 * @return the south state
	 */
	public MarsTile getSouthState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x, pos.y + 1);
	}

	/**
	 * Gets the east state.
	 *
	 * @param tile the tile
	 * @return the east state
	 */
	public MarsTile getEastState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x + 1, pos.y);
	}

	/**
	 * Gets the west state.
	 *
	 * @param tile the tile
	 * @return the west state
	 */
	public MarsTile getWestState(MarsTile tile) {
		Point pos = tile.getPosition();
		return this.map.getState(pos.x - 1, pos.y);
	}

	/**
	 * Gets the memory map.
	 *
	 * @return the memory map
	 */
	public String getMemoryMap() {

		StringBuilder build = new StringBuilder();

		for (int y = 0; y < this.map.getHeight(); y++) {
			for (int x = 0; x < this.map.getWidth(); x++) {
				build.append(this.selector.getMemoryValue(this.map.getState(x, y)));
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

	private HashMap<QValue, Iterable<QValue>> initStateMap() {

		HashMap<QValue, Iterable<QValue>> stateMap = new HashMap<QValue, Iterable<QValue>>();

		for (int x = 0; x < this.map.getWidth(); x++) {
			for (int y = 0; y < this.map.getHeight(); y++) {
				MarsTile state = this.map.getState(x, y);
				List<QValue> list = Arrays.asList(this.getNeighborStates(state));
				stateMap.put(state, list);
			}
		}

		return stateMap;

	}

}
