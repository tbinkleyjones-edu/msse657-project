/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service.tasks;

import java.util.List;

import edu.regis.msse657.scis.model.Program;
import edu.regis.msse657.scis.model.Programs;

/**
 * An synchronous task to retrieve a list of Programs from the Regis REST web service.
 */
public class GetProgramsTask extends HttpTask {

    /**
     * Retrieves program data.
     * @param url REST endpoint to be called.
     * @return a list with zero or more programs.
     */
    public List<Program> execute(String url) {
        String result = doGet(url);
        List<Program> programs = Programs.fromJson(result);
        return programs;
    }
}
