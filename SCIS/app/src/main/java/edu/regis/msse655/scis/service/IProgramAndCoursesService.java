package edu.regis.msse655.scis.service;

import java.util.List;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Program;

/**
 * A service used to retrieve Program and Course objects asynchronously .
 */
public interface IProgramAndCoursesService {

    void getProgramsAsync(ProgramCallback callback);

    void getCoursesAsync(int programId, CourseCallback callback);

    /**
     * Callback that will receive the retrieved Program objects. Called on the UI thread.
     */
    interface ProgramCallback {
        void execute(List<Program> programs);
    }

    /**
     * Callback that will receive the retrieved Course objects. Called on the UI thread.
     */
    interface CourseCallback {
        void execute(List<Course> courses);
    }
}
