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

import edu.regis.msse657.scis.model.Program;

/**
 * A BroadcastReceiver and helper methods used in conjunction with retrieving program data from the Regis web service.
 */
public class GetProgramsReceiver extends BroadcastReceiver {

    private static final String ACTION_GETPROGRAMS_RESULT = "edu.regis.msse655.scis.service.action.PROGRAMS_RESULT";

    private static final String EXTRA_PROGRAMS = "edu.regis.msse655.scis.service.extra.PROGRAMS";

    private final ProgramCallback callback;

    /**
     * A helper method used to send programs via a broadcast intent.
     * @param context context used to send the broadcast.
     */
    public static void sendBroadcastGetPrograms(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_GETPROGRAMS_RESULT);
        context.sendBroadcast(intent);
    }

    /**
     * Constructor
     * @param callback method called when a broadcast is received by this BroadcastReceiver.
     */
    public GetProgramsReceiver(ProgramCallback callback) {
        this.callback = callback;
    }

    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(ACTION_GETPROGRAMS_RESULT));
    }

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast. When a broadcast is received, program data is extracted from the
     * intent and sent to the callback.
     * @param context context in which the receiver is running.
     * @param intent intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        IProgramAndCourseCache cache = new ProgramAndCourseCacheContentProviderImpl(context.getContentResolver());
        List<Program> programs = cache.retrieveAllPrograms();
        callback.execute(programs);
    }


    /**
     * Callback that will receive the retrieved Program objects.
     */
    public interface ProgramCallback {
        void execute(List<Program> programs);
    }
}
