package edu.regis.msse655.scis.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

import edu.regis.msse655.scis.model.Program;

public class GetProgramsReceiver extends BroadcastReceiver {

    private static final String ACTION_GETPROGRAMS_RESULT = "edu.regis.msse655.scis.service.action.PROGRAMS_RESULT";

    private static final String EXTRA_PROGRAMS = "edu.regis.msse655.scis.service.extra.PROGRAMS";

    private final ProgramCallback callback;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void sendBroadcastGetPrograms(Context context, List<Program> programs) {
        Intent intent = new Intent();
        intent.setAction(ACTION_GETPROGRAMS_RESULT);
        intent.putExtra(EXTRA_PROGRAMS, new ArrayList<>(programs));
        context.sendBroadcast(intent);
    }

    public GetProgramsReceiver(ProgramCallback callback) {
        this.callback = callback;
    }

    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(ACTION_GETPROGRAMS_RESULT));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<Program> programs = (ArrayList<Program>)intent.getSerializableExtra(EXTRA_PROGRAMS);
        callback.execute(programs);
    }





}
