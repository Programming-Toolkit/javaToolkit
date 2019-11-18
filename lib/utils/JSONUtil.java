package javaToolkit.lib.utils;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtil {

	public static JSONObject parseJSONFromFile(String jsonFpath) {
		FileReader reader = null;
		JSONObject object = null;
		try {
			reader = new FileReader(jsonFpath);
			JSONParser jsonParser = new JSONParser();

			object = (JSONObject) jsonParser.parse(reader);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;

	}

}
