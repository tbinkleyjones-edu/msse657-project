/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.provider.BaseColumns;

/**
 * A container for constants that define names for URs, tables, and columns.
 */
public final class ProgramAndCoursesContract {

    /**
     * A private constructor to prevent instantiation.
     */
    private ProgramAndCoursesContract() {
    }

    public static final String DATABASE_NAME = "ProgramsAndCourse.db";
    public static final int DATABASE_VERSION = 1;

    public static abstract class ProgramTable implements BaseColumns {
        public static final String TABLE_NAME = "program";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class CourseTable implements BaseColumns {
        public static final String TABLE_NAME = "course";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PROGRAM_ID = "programId";
    }

}
