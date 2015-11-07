package edu.regis.msse655.scis.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Program;

public class GetCoursesReceiver extends BroadcastReceiver {

    private static final String ACTION_GETCOURSES_RESULT = "edu.regis.msse655.scis.service.action.COURSES_RESULT";

    private static final String EXTRA_COURSES = "edu.regis.msse655.scis.service.extra.COURSES";


    private final CourseCallback callback;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void sendBroadcastGetCourses(Context context, List<Course> courses) {
        Intent intent = new Intent();
        intent.setAction(ACTION_GETCOURSES_RESULT);
        intent.putExtra(EXTRA_COURSES, new ArrayList<>(courses));
        context.sendBroadcast(intent);
    }

    public GetCoursesReceiver(CourseCallback callback) {
        this.callback = callback;
    }

    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(ACTION_GETCOURSES_RESULT));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<Course> courses = (ArrayList<Course>) intent.getSerializableExtra(EXTRA_COURSES);
        callback.execute(courses);
    }

}
