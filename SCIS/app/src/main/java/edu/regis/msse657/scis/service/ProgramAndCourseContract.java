/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * A container for constants that define names for URs, tables, and columns.
 */
public final class ProgramAndCourseContract {

    /**
     * A private constructor to prevent instantiation.
     */
    private ProgramAndCourseContract() {
    }

    public static final String URI_AUTHORITY = "content://edu.regis.msse657.scis.service.programandcoursecontentprovider";
    public static final Uri URI_CLEAR_CACHE = Uri.parse(URI_AUTHORITY + "/*");

    public static final String DATABASE_NAME = "ProgramsAndCourse.db";
    public static final int DATABASE_VERSION = 1;

    public static abstract class ProgramTable implements BaseColumns {
        public static final Uri TABLE_URI = Uri.parse(URI_AUTHORITY + "/programs");
        public static final String TABLE_NAME = "program";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class CourseTable implements BaseColumns {
        public static final String TABLE_URI_SEGMENT = "/courses";
        public static final Uri TABLE_URI = Uri.parse(URI_AUTHORITY + TABLE_URI_SEGMENT);
        public static final String TABLE_NAME = "course";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PROGRAM_ID = "programId";
    }

}
