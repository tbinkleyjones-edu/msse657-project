/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.model;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.regis.msse657.scis.service.ProgramAndCourseContract;

/**
 * A utility class used to convert Program objects to and from alternative representations.
 */
public class Programs {

    private Programs() {
    }

    /**
     * Convet a JSON document into a collection of Programs
     * @param json
     * @return
     */
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

    /**
     * Convert a Cursor into a Program object. Assumes the column order is ID, NAME.
     * @param cursor
     * @return
     */
    public static Program fromCursor(Cursor cursor) {
        // fill fields in the same order the appear in the SQL_COLUMNS array.
        long programId = cursor.getInt(0);
        String name = cursor.getString(1);

        Program program = new Program(programId, name);
        return program;
    }

    /**
     * Convert a Program into a ContentValues object
     * @param program
     * @return
     */
    public static ContentValues toContentValues(Program program) {
        // copy the reference's values into a ContentValues map.
        ContentValues values = new ContentValues();
        values.put(ProgramAndCourseContract.ProgramTable._ID, program.getId());
        values.put(ProgramAndCourseContract.ProgramTable.COLUMN_NAME_NAME, program.getName());
        return values;
    }
}
