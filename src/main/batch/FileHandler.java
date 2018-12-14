package batch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
	
	public void downloadFile(String content, String path, String filename, String extension) {
		File file = new File(path + filename + extension);
		int probe = 0;
		while (true) {
			try {
				if (file.createNewFile()) {
					FileWriter writer = new FileWriter(file);
					writer.write(content);
					writer.close();
					return;
				} else {
					probe++;
					file = new File(path + filename + "(" + probe + ")"
							+ extension);
				}
			} catch (IOException exception) {
				exception.printStackTrace();
				break;
			}
		}
	}

}
