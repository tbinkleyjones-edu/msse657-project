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

import edu.regis.msse657.scis.service.ProgramAndCourseContract.CourseTable;

/**
 * A utility class used to convert Course objects to and from alternative representations.
 */
public class Courses {

    private Courses() {
    }

    /**
     * Convert a JSON document into a collection of courses.
     *
     * @param json
     * @return
     */
    public static List<Course> fromJson(String json) {

        List<Course> result = new ArrayList<Course>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(new Course(
                                jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getJSONObject("pid").getInt("id"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Convert a Cursor into a Course object. Assumes column order is ID, NAME, PROGRAM_ID
     *
     * @param cursor
     * @return
     */
    public static Course fromCursor(Cursor cursor) {
        // fill fields in the same order the appear in the SQL_COLUMNS array.

        long courseId = cursor.getInt(0);
        String name = cursor.getString(1);
        long programId = cursor.getInt(2);

        Course program = new Course(courseId, name, programId);
        return program;
    }

    /**
     * Convert a Course object into a ContentValues object. Uses CourseTable for column names.
     *
     * @param course
     * @return
     */
    public static ContentValues toContentValues(Course course) {
        ContentValues values = new ContentValues();
        values.put(CourseTable._ID, course.getId());
        values.put(CourseTable.COLUMN_NAME_NAME, course.getName());
        values.put(CourseTable.COLUMN_NAME_PROGRAM_ID, course.getProgramId());
        return values;
    }
}
