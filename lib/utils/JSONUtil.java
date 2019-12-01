package javaToolkit.lib.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtil {

    public static JSONObject parseJSONFromFile(String jsonFpath) {
        FileReader reader;
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


    public static void writeMapAsJson(Map<String, Integer> map, String jsonFilePath) {
        Map<String, String> newMap = new HashMap<String, String>();
        for (String key : map.keySet()) {
            newMap.put(key, String.valueOf(map.get(key)));
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(newMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        FileUtil.writeStr2File(json, jsonFilePath);
    }


}
