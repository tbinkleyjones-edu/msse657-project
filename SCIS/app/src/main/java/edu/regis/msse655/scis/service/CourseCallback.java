package edu.regis.msse655.scis.service;

import java.util.List;

import edu.regis.msse655.scis.model.Course;

/**
 * Callback that will receive the retrieved Course objects. Called on the UI thread.
 */
public interface CourseCallback {
    void execute(List<Course> courses);
}
