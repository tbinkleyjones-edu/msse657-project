package edu.regis.msse655.scis.service;

import java.util.List;

import edu.regis.msse655.scis.model.Program;

/**
 * Callback that will receive the retrieved Program objects. Called on the UI thread.
 */
public interface ProgramCallback {
    void execute(List<Program> programs);
}
