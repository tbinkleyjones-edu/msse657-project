/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import edu.regis.msse657.scis.model.Course;
import edu.regis.msse657.scis.model.Program;
import edu.regis.msse657.scis.service.tasks.GetCoursesTask;
import edu.regis.msse657.scis.service.tasks.GetProgramsTask;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread. This class uses HttpTasks to retrieve
 * data from the Regis web service.
 */
public class ProgramAndCoursesIntentService extends IntentService {

    public static final String HOST = "http://regisscis.net";
    public static final String PROGRAMS = HOST + "/Regis2/webresources/regis2.program";
    public static final String COURSES = HOST + "/Regis2/webresources/regis2.course";

    private static final String ACTION_GETPROGRAMS = "edu.regis.msse655.scis.service.action.PROGRAMS";
    private static final String ACTION_GETCOURSES = "edu.regis.msse655.scis.service.action.COURSES";

    private static final String EXTRA_PROGRAMID = "edu.regis.msse655.scis.service.extra.PROGRAMID";

    /**
     * Starts this service to perform action GetPrograms with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     * @param context context used to start the serivce.
     */
    public static void startActionGetPrograms(Context context) {
        Intent intent = new Intent(context, ProgramAndCoursesIntentService.class);
        intent.setAction(ACTION_GETPROGRAMS);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action GetCourses with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     * @param context context used to start the service.
     * @param programId programId used to retrieve course data.
     */
    public static void startActionGetCourses(Context context, long programId) {
        Intent intent = new Intent(context, ProgramAndCoursesIntentService.class);
        intent.setAction(ACTION_GETCOURSES);
        intent.putExtra(EXTRA_PROGRAMID, programId);
        context.startService(intent);
    }

    public ProgramAndCoursesIntentService() {
        super("ProgramAndCoursesIntentService");
    }

    /**
     * This method is invoked on the worker thread with a request to process. The work
     * to execute is determined by the intent's action.
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GETPROGRAMS.equals(action)) {
                handleActionGetPrograms();
            } else if (ACTION_GETCOURSES.equals(action)) {
                final long programId = intent.getLongExtra(EXTRA_PROGRAMID, -1);
                handleActionGetCourses(programId);
            }
        }
    }

    /**
     * Handle action GetPrograms in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetPrograms() {
        Log.i("ProgramsAndCoursesIS", "handleActionGetPrograms()");
        List<Program> programs = new GetProgramsTask().execute(PROGRAMS);
        GetProgramsReceiver.sendBroadcastGetPrograms(ProgramAndCoursesIntentService.this, programs);
    }

    /**
     * Handle action GetCourses in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetCourses(long programId) {
        Log.i("ProgramsAndCoursesIS", "handleActionGetCourses()");
        List<Course> courses = new GetCoursesTask(programId).execute(COURSES);
        GetCoursesReceiver.sendBroadcastGetCourses(ProgramAndCoursesIntentService.this, courses);
    }
}
