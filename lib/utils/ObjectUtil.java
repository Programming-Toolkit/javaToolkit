package javaToolkit.lib.utils;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

//import java.awt.List;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import smu.bowen.epr.FileUtil;

public class ObjectUtil {

	public static void WriteObjectToFile(Object serObj, String filepath) {

		try {

			FileOutputStream fileOut = new FileOutputStream(filepath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serObj);
			objectOut.close();
			System.out.println("The Object  was succesfully written to a file");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Object ReadObjectFromFile(String filepath) {

		try {

			FileInputStream fileIn = new FileInputStream(filepath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			Object obj = objectIn.readObject();
			objectIn.close();
			return obj;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

//	public static void WriteObjectToJson(Object obj, String path) {
//		String objJsonStr = JSON.toJSONString(obj);
//		FileUtil.writeStringToFile(path, objJsonStr);
//	}
//
//	public static Object ReadJsonToObj(String path) {
//		String objStr = FileUtil.readStringFromFile(path);
//		return JSON.parseObject(objStr,Object.class);
//	}

}
