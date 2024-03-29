package com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by numan on 20/11/2014.
 */
public class Logs {
    private int _id;
    private int _activiy_id;
    private String _text;
    private String _description;
    private String _image;
    private String _speech;
    private String _location;
    private String _longitude;
    private String _latitude;
    private String _dateTime;

    public Logs() {

    }
    public Logs(int _activiy_id,String text){
        this(_activiy_id,text, null ,null,null,null,null,null,new Timestamp(new Date().getTime()).toString());
    }
    public Logs(int _activiy_id, String _text, String _description, String _image, String _speech, String _location, String _longitude, String _latitude, String _dateTime) {
        this._activiy_id = _activiy_id;
        this._text = _text;
        this._description = _description;
        this._image = _image;
        this._speech = _speech;
        this._location = _location;
        this._longitude = _longitude;
        this._latitude = _latitude;
        this._dateTime = _dateTime;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_activiy_id() {
        return _activiy_id;
    }

    public void set_activiy_id(int _activiy_id) {
        this._activiy_id = _activiy_id;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public String get_image() {

        return _image;
    }

    public Bitmap get_image_bitmap(){
        if(this._image == null || this._image.equals("")){
            return null;
        }
        byte[] bytarray = Base64.decode(this._image, Base64.DEFAULT);
        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                bytarray.length);
        return bmimage;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public void set_image(Bitmap _image){
        String encodedImageString = "";
        if(_image != null) {
            int targetWidth = 600;
            int targetHeight = (int) (_image.getHeight() * targetWidth / (double) _image.getWidth());
            Bitmap bmp = Bitmap.createScaledBitmap(_image,targetWidth,targetHeight,true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            encodedImageString = Base64.encodeToString(b, Base64.DEFAULT);
        }
        this._image = encodedImageString;
    }
    public String get_speech() {
        if(_speech == null) return "";
        return _speech;
    }

    public void set_speech(String _path) {
        this._speech = _path;
    }


    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_longitude() {
        return _longitude;
    }

    public void set_longitude(String _longitude) {
        this._longitude = _longitude;
    }

    public String get_dateTime() {
        try {
            Date dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(_dateTime);
            return new SimpleDateFormat("c,d MMMM y hh:mm").format(dateFormat);

        }catch(Exception e){
            return _dateTime;
        }
    }

    public void set_dateTime(String _dateTime) {
        this._dateTime = _dateTime;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }

    public boolean hasLocation(){
        return get_latitude() != null && !get_latitude().equals("") && get_longitude() != null && !get_longitude().equals("");
    }
    @Override
    public String toString(){
        return this.get_id() + "," + this.get_activiy_id() + "," + this.get_text()+ "," + this.get_description() + "," + this.get_image() + "," + this.get_speech() + "," + this.get_location() + "," + this.get_longitude() + "," + this.get_latitude() + "," + this.get_dateTime();
    }
}
