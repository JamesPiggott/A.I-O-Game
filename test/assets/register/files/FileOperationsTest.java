package assets.register.files;

import org.junit.Test;

public class FileOperationsTest {

	@Test
	// Test if global variables contains empty string as file name and an empty HashMap
	public void testCreateEmptyFile() {
		FileOperations file = new FileOperations("");
		assert(file.getName().contentEquals(""));
		assert(file.getValues().isEmpty());
		assert(file.getLocationHandler() == 0);
	}
	
	@Test
	// Test if global variables contains name == "test" as file name and three String in the HashMap
	public void testCreateNamedFileWithData() {
		FileOperations file = new FileOperations("test");
		assert(file.getLocationHandler() == 0);
		file.insertValue("One");
		assert(file.getLocationHandler() == 1);
		file.insertValue("Two");
		assert(file.getLocationHandler() == 2);
		file.insertValue("Three");
		assert(file.getLocationHandler() == 3);
		assert(file.getName().contentEquals("test"));
		assert(!file.getValues().isEmpty());
	}

}
