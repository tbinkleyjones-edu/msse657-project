/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import edu.regis.msse657.scis.service.ProgramAndCourseContract.ProgramTable;
import edu.regis.msse657.scis.service.ProgramAndCourseContract.CourseTable;


/**
 * A content provider used to cache program and course data from the Regis REST web service.
 * <p>
 * This content provider only supports query and insert operations. If an item to be inserted
 * is already in the cache, the existing version is removed and the new version inserted.
 * <p>
 * Delete is supported only to remove all items from a cache
 */
public class ProgramAndCourseContentProvider extends ContentProvider {

    private IProgramAndCoursesService service;

    /**
     * public constructor used by the OS
     */
    public ProgramAndCourseContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Only the uri is accepted; error if a selection is also sent.
        if (selection != null || selectionArgs != null) {
            throw new IllegalArgumentException("Selection is not supported");
        }

        if (ProgramAndCourseContract.URI_CLEAR_CACHE.equals(uri)) {
            return service.clear();
        } else if (uri.toString().startsWith(ProgramTable.TABLE_URI.toString())) {
            return service.deleteProgram(ContentUris.parseId(uri));
        } else if (uri.toString().startsWith(CourseTable.TABLE_URI.toString())) {
            return service.deleteCourse(ContentUris.parseId(uri));
        } else {
            throw new IllegalArgumentException(uri + " is not supported");
        }
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id;
        if(uri.toString().startsWith(ProgramTable.TABLE_URI.toString())) {
            id = service.insertProgram(values);
        } else if (uri.toString().startsWith(CourseTable.TABLE_URI.toString())) {
            id = service.insertCourse(values);
        } else {
            throw new IllegalArgumentException("Unsupported Uri " + uri);
        }
        assert(id == ContentUris.parseId(uri));
        return uri;
    }

    @Override
    public boolean onCreate() {
        service = new ProgramAndCourseServiceSQLiteImpl(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if(ProgramTable.TABLE_URI.equals(uri)) {
            return service.queryPrograms(projection, selection, selectionArgs, sortOrder);
        } else if (CourseTable.TABLE_URI.equals(uri)) {
            return service.queryCourses(projection, selection, selectionArgs, sortOrder);
        } else {
            throw new IllegalArgumentException("Unsupported Uri " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
