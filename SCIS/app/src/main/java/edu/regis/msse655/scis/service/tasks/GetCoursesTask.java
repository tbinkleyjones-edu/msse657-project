/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse655.scis.service.tasks;

import android.util.Log;

import java.util.List;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Courses;

/**
 * An synchronous task to retrieve a list of Courses from the Regis REST web service.
 */
public class GetCoursesTask extends HttpTask {

    private final int programId;

    public GetCoursesTask(int programId) {
        Log.i("GetCoursesTask", "constructor()");
        this.programId = programId;
    }

    public List<Course> execute(String params) {
        Log.i("GetCoursesTask", "execute()");
        String result = doGet(params);
        List<Course> courses = Courses.fromJson(programId, result);
        return courses;
    }
}
