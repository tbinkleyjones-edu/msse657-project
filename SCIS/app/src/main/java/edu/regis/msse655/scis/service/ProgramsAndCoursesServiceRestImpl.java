package edu.regis.msse655.scis.service;

import java.util.Arrays;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Program;

/**
 * Created by Tim on 10/31/15.
 */
public class ProgramsAndCoursesServiceRestImpl implements IProgramAndCoursesService {
    @Override
    public void getProgramsAsync(ProgramCallback callback) {

        callback.execute(Arrays.asList(
                new Program(1, "CIS"),
                new Program(2, "CN")
        ));
    }

    @Override
    public void getCoursesAsync(int programId, CourseCallback callback) {
        callback.execute(Arrays.asList(
                new Course(1,"CIS 206 Business Software Applications")
        ));
    }
}
