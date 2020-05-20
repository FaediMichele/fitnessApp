package com.example.crinaed.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crinaed.database.AppDatabase;
import com.example.crinaed.database.dao.SchoolDao;
import com.example.crinaed.database.entity.Course;
import com.example.crinaed.database.entity.School;
import com.example.crinaed.database.entity.Step;
import com.example.crinaed.database.entity.join.SchoolData;
import com.example.crinaed.util.Lambda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SchoolRepository implements Repository {
    private SchoolDao schoolDao;
    private LiveData<List<SchoolData>> data;

    public SchoolRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        schoolDao = db.schoolDao();
        data = schoolDao.get();
    }

    public LiveData<List<SchoolData>> getSchool(){
        return data;
    }

    public LiveData<List<SchoolData>> getSchoolTrained(int idTrainer){
        return schoolDao.getSchoolTrained(idTrainer);
    }

    /**
     * Insert new schools in the db
     * @param l callback function that will be called when the insert is done. Pass a parameter(long[])
     * @param school the schools to add
     */
    public void insert( final Lambda l, final School... school){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                l.run((Object) schoolDao.insert(school));
            }
        });
    }

    /**
     * Insert new schools in the db
     * @param l callback function that will be called when the insert is done. Pass a parameter(long[])
     * @param courses the courses to add
     */
    public void insert( final Lambda l, final Course... courses){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                l.run((Object) schoolDao.insert(courses));
            }
        });
    }

    public void update(final School... schools){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                schoolDao.update(schools);
            }
        });
    }

    public void update(final Course... courses){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                schoolDao.update(courses);
            }
        });
    }

    public void delete(final School... schools){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                schoolDao.delete(schools);
            }
        });
    }

    public void delete(final Course... courses){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                schoolDao.delete(courses);
            }
        });
    }

    @Override
    public void loadData(JSONObject data) throws JSONException {
        JSONArray array = data.getJSONArray("School");
        final List<School> schools = new ArrayList<>();
        final List<Course> courses = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject obj = array.getJSONObject(i);
            School school = new School();
            school.idSchool = obj.getLong("idSchool");
            school.idTrainer = obj.getLong("idTrainer");
            school.name = obj.getString("name");
            school.address = obj.getString("address");
            school.email = obj.getString("email");
            schools.add(school);
        }
        array = data.getJSONArray("Course");
        for(int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Course course = new Course();
            course.idCourse = obj.getLong("idCourse");
            course.idSchool = obj.getLong("idSchool");
            course.cat = obj.getString("cat");
            course.name = obj.getString("name");
            course.desc = obj.getString("desc");
            course.minimumLevel = obj.getInt("minimumLevel");
            courses.add(course);
        }
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                schoolDao.insert((School[]) schools.toArray());
                schoolDao.insert((Course[]) courses.toArray());
            }
        });

    }
}
