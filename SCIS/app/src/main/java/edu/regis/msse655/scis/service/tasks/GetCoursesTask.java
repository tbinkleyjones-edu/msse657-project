package edu.regis.msse655.scis.service.tasks;

import android.util.Log;

import java.util.List;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Courses;
import edu.regis.msse655.scis.service.IProgramAndCoursesService;

/**
 * Created by Tim on 10/31/15.
 */
public class GetCoursesTask extends HttpTask {

    private final int programId;
    private final IProgramAndCoursesService.CourseCallback callback;

    public GetCoursesTask(int programId, IProgramAndCoursesService.CourseCallback callback) {
        Log.i("GetCoursesTask", "constructor()");
        this.programId = programId;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i("GetCoursesTask", "onPostExecute()");
        List<Course> courses = Courses.fromJson(programId, result);
        callback.execute(courses);
    }
}
