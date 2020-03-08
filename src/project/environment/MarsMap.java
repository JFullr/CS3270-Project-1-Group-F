package project.environment;

import java.awt.Point;
import java.util.ArrayList;

import utils.FileUtils;

/**
 * The Class MarsMap.
 * 
 * @author Amelia Reynolds, Joseph Fuller, Kyle Riggs, Timothy Brooks
 * @version Spring 2020
 */
public class MarsMap {

	private static final Exception UNBALANCED_MAP = new Exception("Specified Map Size != Found Map Data");

	
	private int mapWidth;
	private int mapHeight;

	private ArrayList<ArrayList<MarsTile>> tiles;
	private MarsTile startState;

	/**
	 * Instantiates a new mars map.
	 *
	 * @param csvMapFilePath the csv map file path
	 */
	public MarsMap(String csvMapFilePath) {
		this.tiles = new ArrayList<ArrayList<MarsTile>>();
		this.mapHeight = -2;
		this.mapWidth = 0;

		this.createMapFromCsv(csvMapFilePath);
	}

	/**
	 * Gets the map width.
	 *
	 * @return the map width
	 */
	public int getWidth() {
		return this.mapWidth;
	}

	/**
	 * Gets the map height.
	 *
	 * @return the map height
	 */
	public int getHeight() {
		return this.mapHeight;
	}

	/**
	 * Gets the start tile of the agent.
	 *
	 * @return the start tile
	 */
	public MarsTile getStartTile() {
		return this.startState;
	}

	/**
	 * Gets the state at the given position of type Point.
	 *
	 * @param pos the position of type Point
	 * @return the current state of the agent
	 */
	public MarsTile getState(Point pos) {
		return this.getState(pos.x, pos.y);
	}
	
	
	/**
	 * Gets the list of all available states.
	 * 
	 * @return the internal list
	 */
	public ArrayList<ArrayList<MarsTile>> getAllStates(){
		return this.tiles;
	}

	/**
	 * Gets the state at the given position located at the give points x and y.
	 *
	 * @param xCoord the x
	 * @param yCoord the y
	 * @return the state, MarsTile.ILLEGAL_TILEE if illegal
	 */
	public MarsTile getState(int xCoord, int yCoord) {

		if (xCoord < 0 || xCoord >= this.mapWidth || yCoord < 0 || yCoord >= this.mapHeight) {
			return null;
		}

		return this.tiles.get(yCoord).get(xCoord);
	}

	/**
	 * Gets the display map.
	 *
	 * @return the display map
	 */
	public String getDisplayMap() {

		StringBuilder build = new StringBuilder();

		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				build.append(this.getState(x, y).padDataForOutputTable(14));
			}

			if (y != this.getHeight() - 1) {
				build.append("\n");
			}
		}

		return build.toString();
	}

	private Dimension getDimensions(String rawValues) throws NumberFormatException {

		String[] rawDims = rawValues.toLowerCase().split("x");

		int width = Integer.parseInt(rawDims[1].trim());
		int height = Integer.parseInt(rawDims[0].trim());

		return new Dimension(width, height);
	}
	
	private void createMapFromCsv(String filePath) {

		try {

			String[] csvValues = FileUtils.readCsv(filePath);
			Dimension size = this.getDimensions(csvValues[0]);

			int width = size.getWidth();

			int realHeight = (csvValues.length - 1) / width;
			if (realHeight != size.getHeight()) {
				throw MarsMap.UNBALANCED_MAP;
			}

			this.mapHeight = size.getHeight();
			this.mapWidth = size.getWidth();

			for (int y = 0; y < realHeight; y++) {
				for (int x = 0; x < width; x++) {
					this.addTile(x, y, csvValues[y * width + x + 1]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addTile(int xCoord, int yCoord, String raw) {
		while (this.tiles.size() <= yCoord) {
			this.tiles.add(new ArrayList<MarsTile>());
		}

		Point position = new Point(xCoord, yCoord);

		double value = 0;

		raw = raw.toLowerCase();
		if (raw.startsWith("loi")) {
			value = Double.parseDouble(raw.substring(4).trim());
		} else if (raw.startsWith("start")) {
			value = 0;
		} else {
			value = Double.parseDouble(raw.trim());
		}

		this.tiles.get(yCoord).add(new MarsTile(position, value));

		if (raw.startsWith("start")) {
			this.startState = this.getState(xCoord, yCoord);
		}

	}

	private class Dimension {

		private int width;
		private int height;

		/**
		 * Instantiates a new dimension.
		 *
		 * @param width  the width for the dimensions
		 * @param height the height for the dimensions
		 */
		Dimension(int width, int height) {
			this.width = width;
			this.height = height;
		}

		/**
		 * Gets the height.
		 *
		 * @return the height
		 */
		public int getHeight() {
			return this.height;
		}

		/**
		 * Gets the width.
		 *
		 * @return the width
		 */
		public int getWidth() {
			return this.width;
		}

	}

}
