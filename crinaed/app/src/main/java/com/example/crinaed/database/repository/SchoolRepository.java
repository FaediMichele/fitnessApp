package com.example.crinaed.database.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.SchoolDao;
import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.join.CourseSearchData;
import com.example.crinaed.database.entity.join.CourseWithExercise;
import com.example.crinaed.util.Lambda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class SchoolRepository extends Repository {
    private SchoolDao schoolDao;
    private long lastSchoolId=-1;
    private long lastCourseId=-1;

    public SchoolRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        schoolDao = db.schoolDao();
        setContext(context);
    }

    public Future<?> insert(final School... school){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long[]>() {
            @Override
            public Long[] call() {
                for(School s : school){
                    s.idSchool = lastSchoolId--;
                }
                return schoolDao.insert(school);
            }
        });
    }

    public LiveData<List<CourseWithExercise>> getCourse(boolean archived){
        return schoolDao.getCourseWithExercise(archived);
    }

    public LiveData<CourseWithExercise> getCourseById(long id){
        return schoolDao.getCourseWithExerciseById(id);
    }

    public LiveData<List<CourseSearchData>> getSearchData(long[] idCourses){
        return schoolDao.getSearchedCourse(idCourses);
    }

    public Future<?> deleteOldSearch() {
        return AppDatabase.databaseWriteExecutor.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        schoolDao.deleteCoursesSearched();
                        Log.d("naed", "deleted old search");
                    }
                });
    }

    public Future<?> insert(final Course... courses){
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long[]>() {
            @Override
            public Long[] call() {
                for(Course c : courses){
                    c.idCourse = lastCourseId--;
                }
                return schoolDao.insert(courses);
            }
        });
    }

    public Future<?> update(final School... schools){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.update(schools);
            }
        });
    }

    public Future<?> update(final Course... courses){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.update(courses);
            }
        });
    }

    public Future<?> delete(final School... schools){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.delete(schools);
            }
        });
    }

    public Future<?> delete(final Course... courses){
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.delete(courses);
            }
        });
    }

    @Override
    public Future<?> loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("School");
        final List<School> schools = new ArrayList<>();
        final List<Course> courses = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            final School s = new School(array.getJSONObject(i));
            schools.add(s);
            downloadImage(array, i, new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    if((Boolean) paramether[0]){
                        File f = (File) paramether[1];
                        s.image=f.getAbsolutePath();
                        s.imageDownloaded=true;
                        update(s);
                    }
                    return new Object[0];
                }
            });
        }
        array = data.getJSONArray("Course");
        for(int i = 0; i < array.length(); i++) {
            final Course c = new Course(array.getJSONObject(i));
            courses.add(c);
            try {
                downloadImageArray(array, i, new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        try {
                            if ((Boolean) paramether[0]) {
                                c.images = (String[]) paramether[1];
                                c.imagesDownloaded = true;
                                update(c);
                                Log.d("naed", "update of course done: " + c.idCourse);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return new Object[0];
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
            downloadVideo(array, i, new Lambda() {
                @Override
                public Object[] run(Object... paramether) {
                    if ((Boolean) paramether[0]) {
                        File f = (File) paramether[1];
                        c.video = f.getAbsolutePath();
                        c.videoDownloaded = true;
                        update(c);
                    }
                    return new Object[0];
                }
            });
        }
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                schoolDao.insert(schools.toArray(new School[0]));
                schoolDao.insert(courses.toArray(new Course[0]));
            }
        });
    }

    @Override
    public Future<?> extractData(final JSONObject root) {
        return AppDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    root.put("School", listToJSONArray(schoolDao.getSchoolList()));
                    root.put("Course", listToJSONArray(schoolDao.getCourseList()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
