package edu.regis.msse655.scis.service;

/**
 * A simple Service Locator object that abstracts service implementations.
 */
public class ServiceLocator {

    private static ServiceLocator instance;

    private IProgramAndCoursesService programAndCoursesService;

    private ServiceLocator(IProgramAndCoursesService programAndCoursesService) {
        this.programAndCoursesService = programAndCoursesService;
    }

    private static ServiceLocator getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ServiceLocator is not initialized. Call createInstance before calling get operations.");
        }
        return instance;
    }

    public static void createInstance(IProgramAndCoursesService programAndCoursesService) {
        instance = new ServiceLocator(
                programAndCoursesService
        );
    }

    /**
     * Gets a Reference Service implementation
     *
     * @return
     */
    public static IProgramAndCoursesService getProgramAndCoursesService() {
        return getInstance().programAndCoursesService;
    }

}
