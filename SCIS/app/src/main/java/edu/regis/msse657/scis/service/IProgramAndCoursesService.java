/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * A persistance service that stores Program and Course objects.
 */
public interface IProgramAndCoursesService {

    int deleteProgram(long programId);
    int deleteCourse(long courseId);

    long insertProgram(ContentValues values);
    long insertCourse(ContentValues values);

    Cursor queryPrograms(String[] projection, String selection, String[] selectionArgs, String sortOrder);
    Cursor queryCourses(String[] projection, String selection, String[] selectionArgs, String sortOrder);

    int clear();
}
