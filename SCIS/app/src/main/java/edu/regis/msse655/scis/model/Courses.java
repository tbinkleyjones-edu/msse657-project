package edu.regis.msse655.scis.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class used to convert Course objects to and from alternative representations.
 */
public class Courses {

    private Courses() {
    }

    public static List<Course> fromJson(int programId, String json) {

        List<Course> result = new ArrayList<Course>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (programId == jsonObject.getJSONObject("pid").getInt("id")) {
                    result.add(new Course(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("name"))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
