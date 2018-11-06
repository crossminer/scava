import carolinedb.AccessManager;
import carolinedb.DBAccess;
import carolinedb.DBInfo;

public class Demo {

	private static final String ACCESS_URL = "http://12.34.56.789:1011/public/data";

	public static void main(String[] args) {

		// Access database
		AccessManager access = new DBAccess();
		access.connect(ACCESS_URL);

		// Read data
		DBInfo rawData = access.readData();
		int[] data = rawData.asArray();

		// Sort data
		int[] sortedData = Sorter.sort(data);

		// Display data
		if (sortedData.length > 0) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(sortedData[0]);
			for (int i = 1; i < sortedData.length; i++) {
				stringBuilder.append(", ");
				stringBuilder.append(sortedData[i]);
			}
			System.out.println(stringBuilder.toString());
		} else {
			System.out.println("Nothing to show.");
		}
	}
	
}









































/*
	public static void main_new(String[] args) {

		// Access database
		DataFetcher access = new URLFetcher(ACCESS_URL, RequestMethod.TCP);

		// Read data
		FetchedData rawData = access.fetch();
		int[] data = rawData.toArray();

		// Sort data
		int[] sortedData = Sorter.sort(data);

		// Display data
		if (sortedData.length > 0) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(sortedData[0]);
			for (int i = 1; i < sortedData.length; i++) {
				stringBuilder.append(", ");
				stringBuilder.append(sortedData[i]);
			}
			System.out.println(stringBuilder.toString());
		} else {
			System.out.println("Nothing to show.");
		}
	}

	public static void main_old(String[] args) {

		// Access database
		AccessManager access = new DBAccess();
		access.connect(ACCESS_URL);

		// Read data
		DBInfo rawData = access.readData();
		int[] data = rawData.asArray();

		// Sort data
		int[] sortedData = Sorter.sort(data);

		// Display data
		if (sortedData.length > 0) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(sortedData[0]);
			for (int i = 1; i < sortedData.length; i++) {
				stringBuilder.append(", ");
				stringBuilder.append(sortedData[i]);
			}
			System.out.println(stringBuilder.toString());
		} else {
			System.out.println("Nothing to show.");
		}
	}*/