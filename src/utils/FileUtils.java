package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The Class FileUtils.
 * 
 * @author Amelia Reynolds, Joseph Fuller, Kyle Riggs, Timothy Brooks
 * @version Spring 2020
 */
public class FileUtils {

	public static final IOException FILE_EMPTY = new IOException("File Does Not Exist Or Is Empty");
	public static final IOException FILE_TOO_LARGE = new IOException("File Is Too Large");

	/**
	 * Read file.
	 *
	 * @param filePath the file path
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] readFile(String filePath) throws IOException {

		File file = new File(filePath);
		long length = file.length();

		if (length == 0) {
			throw FileUtils.FILE_EMPTY;
		}

		if (length > Integer.MAX_VALUE) {
			throw FileUtils.FILE_TOO_LARGE;
		}

		byte[] data = new byte[(int) length];

		try (FileInputStream fis = new FileInputStream(file)) {

			fis.read(data);

		}

		return data;
	}

	/**
	 * Read lines.
	 *
	 * @param filePath the file path
	 * @return the string[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String[] readLines(String filePath) throws IOException {

		String raw = new String(FileUtils.readFile(filePath));
		raw = FileUtils.condenseNewLines(raw);

		return raw.split("\n");

	}

	/**
	 * Read csv.
	 *
	 * @param filePath the file path
	 * @return the string[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String[] readCsv(String filePath) throws IOException {

		String raw = new String(FileUtils.readFile(filePath));
		raw = FileUtils.condenseNewLines(raw);
		raw = raw.replace("\n", ",");

		return raw.split(",");

	}

	private static String condenseNewLines(String value) {
		return value.replace("\r", "\n").replace("\n\n", "\n");
	}

}
