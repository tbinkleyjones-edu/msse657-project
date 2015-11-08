/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse655.scis.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Program;
import edu.regis.msse655.scis.service.tasks.GetCoursesTask;
import edu.regis.msse655.scis.service.tasks.GetProgramsTask;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ProgramAndCoursesIntentService extends IntentService {

    public static final String HOST = "http://regisscis.net";
    public static final String PROGRAMS = HOST + "/Regis2/webresources/regis2.program";
    public static final String COURSES = HOST + "/Regis2/webresources/regis2.course";

    private static final String ACTION_GETPROGRAMS = "edu.regis.msse655.scis.service.action.PROGRAMS";
    private static final String ACTION_GETCOURSES = "edu.regis.msse655.scis.service.action.COURSES";

    private static final String EXTRA_PROGRAMID = "edu.regis.msse655.scis.service.extra.PROGRAMID";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetPrograms(Context context) {
        Intent intent = new Intent(context, ProgramAndCoursesIntentService.class);
        intent.setAction(ACTION_GETPROGRAMS);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetCourses(Context context, int programId) {
        Intent intent = new Intent(context, ProgramAndCoursesIntentService.class);
        intent.setAction(ACTION_GETCOURSES);
        intent.putExtra(EXTRA_PROGRAMID, programId);
        context.startService(intent);
    }

    public ProgramAndCoursesIntentService() {
        super("ProgramAndCoursesIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GETPROGRAMS.equals(action)) {
                handleActionGetPrograms();
            } else if (ACTION_GETCOURSES.equals(action)) {
                final int programId = intent.getIntExtra(EXTRA_PROGRAMID, -1);
                handleActionGetCourses(programId);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetPrograms() {
        Log.i("ProgramsAndCoursesIS", "handleActionGetPrograms()");
        List<Program> programs = new GetProgramsTask().execute(PROGRAMS);
        GetProgramsReceiver.sendBroadcastGetPrograms(ProgramAndCoursesIntentService.this, programs);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetCourses(int programId) {
        Log.i("ProgramsAndCoursesIS", "handleActionGetCourses()");
        List<Course> courses = new GetCoursesTask(programId).execute(COURSES);
        GetCoursesReceiver.sendBroadcastGetCourses(ProgramAndCoursesIntentService.this, courses);
    }
}
