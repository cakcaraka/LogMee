package com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel;

/**
 * Created by numan on 20/11/2014.
 */
public class Logs {
    private int _id;
    private int _activiy_id;
    private String _text;
    private String _image;
    private String _speech;
    private String _location;
    private String _longitude;
    private String _latitude;
    private String _dateTime;

    public Logs(int _id, int _activiy_id, String _text, String _image, String _speech, String _location, String _longitude, String _latitude, String _dateTime) {
        this._id = _id;
        this._activiy_id = _activiy_id;
        this._text = _text;
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

    public void set_image(String _image) {
        this._image = _image;
    }

    public String get_speech() {
        return _speech;
    }

    public void set_speech(String _speech) {
        this._speech = _speech;
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
        return _dateTime;
    }

    public void set_dateTime(String _dateTime) {
        this._dateTime = _dateTime;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }
}