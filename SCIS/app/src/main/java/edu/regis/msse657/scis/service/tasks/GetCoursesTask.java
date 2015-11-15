/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service.tasks;

import android.util.Log;

import java.util.List;

import edu.regis.msse657.scis.model.Course;
import edu.regis.msse657.scis.model.Courses;

/**
 * A synchronous task to retrieve a list of Courses from the Regis REST web service.
 */
public class GetCoursesTask extends HttpTask {

    private final int programId;

    public GetCoursesTask(int programId) {
        Log.i("GetCoursesTask", "constructor()");
        this.programId = programId;
    }

    /**
     * Retrieves course data.
     * @param url REST endpoint to be called.
     * @return a list with zero or more courses.
     */
    public List<Course> execute(String url) {
        Log.i("GetCoursesTask", "execute()");
        String result = doGet(url);
        List<Course> courses = Courses.fromJson(programId, result);
        return courses;
    }
}
