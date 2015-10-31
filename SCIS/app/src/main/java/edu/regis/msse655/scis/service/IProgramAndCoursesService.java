package edu.regis.msse655.scis.service;

import java.util.List;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Program;

/**
 * Created by Tim on 10/31/15.
 */
public interface IProgramAndCoursesService {

    void getProgramsAsync(ProgramCallback callback);

    void getCoursesAsync(int programId, CourseCallback callback);

    interface ProgramCallback {
        void execute(List<Program> programs);
    }

    interface CourseCallback {
        void execute(List<Course> courses);
    }
}
