package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtil {

	public static String convertToString(InputStream responseEntityContent) {
		try {
			String result = extractStringFrom(responseEntityContent);
			responseEntityContent.close();
			return result;
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
		return content.toString();
	}

}
