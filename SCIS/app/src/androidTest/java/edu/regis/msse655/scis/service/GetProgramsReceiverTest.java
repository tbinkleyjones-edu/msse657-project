package edu.regis.msse655.scis.service;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.test.ApplicationTestCase;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.regis.msse655.scis.model.Program;

/**
 * Created by Tim on 11/7/15.
 */
public class GetProgramsReceiverTest extends ApplicationTestCase<Application> {

    public GetProgramsReceiverTest() {
        super(Application.class);
    }

    /**
     * Verify that the sendBroadcastGetPrograms method calls context.sendBroadcast with
     * an properly built Intent.
     */
    @SmallTest
    public void testSendBroadcast () {

        Context context = new MockContext() {
            @Override
            public void sendBroadcast(Intent intent) {
                assertEquals("edu.regis.msse655.scis.service.action.PROGRAMS_RESULT", intent.getAction());
                ArrayList<Program> programs = (ArrayList<Program>)intent.getSerializableExtra("edu.regis.msse655.scis.service.extra.PROGRAMS");
                assertNotNull(programs);
                assertEquals(1, programs.size());
                assertEquals(new Program(1, "test program"), programs.get(0));
            }
        };

        List<Program> programs = Arrays.asList(new Program(1, "test program"));

        GetProgramsReceiver.sendBroadcastGetPrograms(context, programs);
    }

    /**
     * Verify that the onReceive method calls the callback with a valid list of programs
     */
    @SmallTest
    public void testOnReceive() {

        GetProgramsReceiver receiver = new GetProgramsReceiver(new ProgramCallback() {
            @Override
            public void execute(List<Program> programs) {
                assertEquals(1, programs.size());
                assertEquals(new Program(1, "test program"), programs.get(0));
            }
        });


        ArrayList<Program> programs = new ArrayList<>();
        programs.add(new Program(1, "test program"));

        Intent intent = new Intent();
        intent.setAction("edu.regis.msse655.scis.service.action.PROGRAMS_RESULT");
        intent.putExtra("edu.regis.msse655.scis.service.extra.PROGRAMS", programs);

        receiver.onReceive(new MockContext(), intent);

    }
}
