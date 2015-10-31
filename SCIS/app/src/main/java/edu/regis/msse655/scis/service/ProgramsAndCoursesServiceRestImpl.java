package edu.regis.msse655.scis.service;

import android.util.Log;

import edu.regis.msse655.scis.service.tasks.GetCoursesTask;
import edu.regis.msse655.scis.service.tasks.GetProgramsTask;

/**
 * Created by Tim on 10/31/15.
 */
public class ProgramsAndCoursesServiceRestImpl implements IProgramAndCoursesService {

    public static final String HOST = "http://regisscis.net";
    public static final String PROGRAMS = HOST + "/Regis2/webresources/regis2.program";
    public static final String COURSES = HOST + "/Regis2/webresources/regis2.course";

    @Override
    public void getProgramsAsync(ProgramCallback callback) {
        Log.i("ProgramsAndCoursesSRI", "getProgramsAsync()");
        new GetProgramsTask(callback).execute(PROGRAMS);
    }

    @Override
    public void getCoursesAsync(int programId, CourseCallback callback) {
        Log.i("ProgramsAndCoursesSRI", "getCoursesAsync()");
        new GetCoursesTask(programId, callback).execute(COURSES);
    }
}
