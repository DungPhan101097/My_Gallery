package com.example.dungit.gallery;

import java.util.ArrayList;

/**
 * Created by DUNGIT on 4/18/2018.
 */

public class ListPhotoSameDate {
    private ArrayList<Photo> lstPhotoHaveSameDate;
    private String date;

    public ListPhotoSameDate() {
        this.lstPhotoHaveSameDate = new ArrayList<>();
    }

    public ArrayList<Photo> getLstPhotoHaveSameDate() {
        return lstPhotoHaveSameDate;
    }

    public void setLstPhotoHaveSameDate(ArrayList<Photo> lstPhotoHaveSameDate) {
        this.lstPhotoHaveSameDate = lstPhotoHaveSameDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addPhoto(Photo photo){
        lstPhotoHaveSameDate.add(photo);
    }

}
