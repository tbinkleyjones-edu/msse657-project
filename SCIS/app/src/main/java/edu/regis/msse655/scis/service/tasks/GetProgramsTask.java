package edu.regis.msse655.scis.service.tasks;

import java.util.List;

import edu.regis.msse655.scis.model.Program;
import edu.regis.msse655.scis.model.Programs;

/**
 * An synchronous task to retrieve a list of Programs from the Regis REST web service.
 */
public class GetProgramsTask extends HttpTask {

    public List<Program> execute(String... params) {
        String result = doGet(params);
        List<Program> programs = Programs.fromJson(result);
        return programs;
    }
}
