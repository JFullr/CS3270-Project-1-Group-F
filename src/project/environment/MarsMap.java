package project.environment;

import java.awt.Point;
import java.util.ArrayList;

import utils.FileUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class MarsMap.
 */
public class MarsMap {

	/** The Constant UNBALANCED_MAP. */
	private static final Exception UNBALANCED_MAP = new Exception("Specified Map Size != Found Map Data");

	/** The tiles. */
	private ArrayList<ArrayList<MarsTile>> tiles;

	/** The map width. */
	private int mapWidth;

	/** The map height. */
	private int mapHeight;

	/** The start state. */
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
	 * Gets the start tile.
	 *
	 * @return the start tile
	 */
	public MarsTile getStartTile() {
		return this.startState;
	}
	
	public MarsTile getState(Point pos) {
		return this.getState(pos.x,pos.y);
	}

	/**
	 * Gets the state.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the state, MarsTile.ILLEGAL_TILEE if illegal
	 */
	public MarsTile getState(int x, int y) {
		
		if(x < 0 || x >= this.mapWidth || y < 0 || y >= this.mapHeight) {
			return MarsTile.ILLEGAL_TILE;
		}
		
		return this.tiles.get(y).get(x);
	}

	/**
	 * Creates the map from csv.
	 *
	 * @param filePath the file path
	 */
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

	/**
	 * Adds the tile.
	 *
	 * @param x   the x
	 * @param y   the y
	 * @param raw the raw
	 */
	private void addTile(int x, int y, String raw) {
		while (this.tiles.size() <= y) {
			this.tiles.add(new ArrayList<MarsTile>());
		}

		Point position = new Point(x, y);

		double value = 0;

		raw = raw.toLowerCase();
		if (raw.startsWith("loi")) {
			value = Double.parseDouble(raw.substring(4).trim());
		} else if (raw.startsWith("start")) {
			value =Double.NEGATIVE_INFINITY;
		} else {
			value = Double.parseDouble(raw.trim());
		}

		this.tiles.get(y).add(new MarsTile(position, value));

		if (raw.startsWith("start")) {
			this.startState = this.getState(x, y);
		}

	}
	
	public String getDisplayMap() {
		
		StringBuilder build = new StringBuilder();
		build.append("[");
		
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				build.append(this.getState(x, y).getWeight());
				if (x != this.getWidth() - 1) {
					build.append(", ");
				}
			}
			
			if (y != this.getHeight() - 1) {
				build.append("\n");
			}
		}
		
		
		return build.toString();
	}

	/**
	 * Gets the dimensions.
	 *
	 * @param rawValues the raw values
	 * @return the dimensions
	 * @throws NumberFormatException the number format exception
	 */
	private Dimension getDimensions(String rawValues) throws NumberFormatException {

		String[] rawDims = rawValues.toLowerCase().split("x");

		int width = Integer.parseInt(rawDims[1].trim());
		int height = Integer.parseInt(rawDims[0].trim());

		return new Dimension(width, height);
	}

	/**
	 * The Class Dimension.
	 */
	private class Dimension {

		/** The width. */
		private int width;

		/** The height. */
		private int height;

		/**
		 * Instantiates a new dimension.
		 *
		 * @param width  the width
		 * @param height the height
		 */
		public Dimension(int width, int height) {
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
