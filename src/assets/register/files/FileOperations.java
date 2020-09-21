package assets.register.files;

import java.util.HashMap;

/**
 * FileOperations determines the functionality possible by files. Files can be created, fetched, iterated through, altered, wiped and dropped.
 * Files have permanence, unlike registers, they can be transmitted to other computers and will remain even after a computer has been rebooted, 
 * unlike main memory. A file is basically a dictionary of key value pairs. The key is an integer between 0 and 9999 while the value is any,
 * alphanumeric string with a length of no more than 10. 
 * @author Kane
 *
 */
public class FileOperations {
	
	private String file_name;
	private HashMap<Integer, String> file_contents;
	private Integer location_handler;
	
	// FCH or fetch creates a new file if one does not already exist
	public FileOperations(String name) {
		this.file_name = name;
		this.file_contents = new HashMap<Integer, String>();
	}
	
	public void seekHandler(String operand, int location_change) {
		if (operand.contentEquals("+")) {
			if (location_change + location_handler > file_contents.size() - 1) {
				location_handler = file_contents.size() - 1;
			} else {
				location_handler = location_change + location_handler;
			}
		}
		
		if (operand.contentEquals("-")) {
			if (location_handler - location_change < 0) {
				location_handler = 0;
			} else {
				location_handler = location_handler - location_change;
			}
		}
 	}
	
	public void insertValue(String value) {
		if (value.length() < 30) {
			file_contents.put(location_handler+1, value);
			location_handler++;
		}
	}
		
	/*
	 * Remove the value currently index by location_handler
	 */
	public void removeValue(String value) {
		this.file_contents.remove(Integer.parseInt(value));
	}
	
	/*
	 * Clear all key value pairs from file_contents.
	 */
	public void wipeContents() {
		this.file_contents.clear();
	}
	
	public String getName() {
		return this.file_name;
	}
}