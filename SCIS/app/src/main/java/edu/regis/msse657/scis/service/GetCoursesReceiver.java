/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

import edu.regis.msse657.scis.model.Course;

/**
 * A BroadcastReceiver and helper methods used in conjunction with retrieving course data from the Regis web service.
 */
public class GetCoursesReceiver extends BroadcastReceiver {

    private static final String ACTION_GETCOURSES_RESULT = "edu.regis.msse655.scis.service.action.COURSES_RESULT";

    private static final String EXTRA_PROGRAMID = "edu.regis.msse655.scis.service.extra.PROGRAMID";

    private final CourseCallback callback;

    /**
     * A helper method used to send courses via a broadcast intent.
     * @param context context used to send the broadcast.
     * @param programId .
     */
    public static void sendBroadcastGetCourses(Context context, long programId) {
        Intent intent = new Intent();
        intent.setAction(ACTION_GETCOURSES_RESULT);
        intent.putExtra(EXTRA_PROGRAMID, programId);
        context.sendBroadcast(intent);
    }

    /**
     * Constructor
     * @param callback method called when a broadcast is received by this BroadcastReceiver.
     */
    public GetCoursesReceiver(CourseCallback callback) {
        this.callback = callback;
    }

    /**
     * A help method used to register this BroadcastReceiver with the appropriate IntentFilter.
     * @param context context used to register the receiver.
     */
    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(ACTION_GETCOURSES_RESULT));
    }

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast. When a broadcast is received, course data is extracted from the
     * intent and sent to the callback.
     * @param context context in which the receiver is running.
     * @param intent intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        IProgramAndCourseCache cache = new ProgramAndCourseCacheContentProviderImpl(context.getContentResolver());
        final long programId = intent.getLongExtra(EXTRA_PROGRAMID, -1);
        List<Course> courses = cache.retrieveCoursesForProgram(programId);
        callback.execute(courses);
    }

    /**
     * Callback that will receive the retrieved Course objects.
     */
    public interface CourseCallback {
        void execute(List<Course> courses);
    }
}
