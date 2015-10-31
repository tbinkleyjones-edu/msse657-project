package edu.regis.msse655.scis.service.tasks;

import java.util.List;

import edu.regis.msse655.scis.model.Program;
import edu.regis.msse655.scis.model.Programs;
import edu.regis.msse655.scis.service.IProgramAndCoursesService;

/**
 * Created by Tim on 10/31/15.
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
