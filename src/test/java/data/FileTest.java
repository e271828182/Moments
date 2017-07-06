package data;

import java.io.File;

public class FileTest {

	public static void main(String[] args) {

		String path = "C:/Users/WSM/Desktop/temp222/";
		File file = new File(path);
		System.out.println(file.mkdirs());
	}

}
