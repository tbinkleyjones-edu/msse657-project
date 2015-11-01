package edu.regis.msse655.scis.service.tasks;

import java.util.List;

import edu.regis.msse655.scis.model.Program;
import edu.regis.msse655.scis.model.Programs;
import edu.regis.msse655.scis.service.IProgramAndCoursesService;

/**
 * An asynchronous task to retrieve a list of Programs from the Regis REST web service.
 */
public class GetProgramsTask extends HttpTask {

    private final IProgramAndCoursesService.ProgramCallback callback;

    public GetProgramsTask(IProgramAndCoursesService.ProgramCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String result) {
        List<Program> programs = Programs.fromJson(result);
        callback.execute(programs);
    }
}
