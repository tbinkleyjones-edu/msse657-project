/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse655.scis.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class used to convert Program objects to and from alternative representations.
 */
public class Programs {

    private Programs() {
    }

    public static List<Program> fromJson(String json) {
        List<Program> result = new ArrayList<Program>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                result.add(new Program(
                                jsonObject.getInt("id"),
                                jsonObject.getString("name"))
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
