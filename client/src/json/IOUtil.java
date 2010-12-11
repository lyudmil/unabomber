package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtil {

	public static String convertToString(InputStream responseEntityContent) {
		try {
			return extractStringFrom(responseEntityContent);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static String extractStringFrom(InputStream responseEntityContent) throws IOException {
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntityContent));
		StringBuilder content = new StringBuilder();
		while ((line = reader.readLine()) != null) {
		    content.append(line);
		}
		responseEntityContent.close();
		return content.toString();
	}

}
