package com.example.crinaed.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.crinaed.util.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity
public class Course implements MyEntity{
    @PrimaryKey public long idCourse;
    public String name;
    public String desc;
    public long idSchool;
    public boolean isArchived;
    public double price;
    public boolean isBought;

    @TypeConverters(ImagesConverter.class)
    public String[] images;

    @TypeConverters(MyStep.CategoryConverter.class)
    public Category cat;

    public double review;

    public String video;
    public boolean imagesDownloaded=false;
    public boolean videoDownloaded=false;

    public Course(){
    }

    public Course(JSONObject obj) throws JSONException {
        idCourse = obj.getLong("idCourse");
        idSchool = obj.getLong("idSchool");
        name = obj.getString("name");
        desc = obj.getString("desc");
        video = obj.getString("video");
        cat = Category.valueOf(obj.getString("cat"));
        review = obj.getDouble("review");
        isBought = obj.getBoolean("isBought");
        if(!isBought){
            isArchived=false;
        }else{
            isArchived = obj.getBoolean("isArchived");
        }
        price = obj.getDouble("price");

        JSONArray images = obj.getJSONArray("image");
        this.images=new String[images.length()];
        for(int i=0;i<images.length(); i++){
            this.images[i]=images.getString(i);
        }
        imagesDownloaded=false;
        videoDownloaded=false;
    }

    public static class ImagesConverter{
        @TypeConverter
        public String[] fromString(String value){
            String[] ret;
            try {
                JSONArray array = new JSONArray(value);
                ret = new String[array.length()];
                for(int i=0;i<array.length();i++){
                    ret[i]=array.getString(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                ret = new String[0];
            }
            return ret;
        }
        @TypeConverter
        public String toString(String[] c){
            JSONArray array = new JSONArray();
            for (String s : c) {
                array.put(s);
            }
            return array.toString();
        }
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject();
    }

}
