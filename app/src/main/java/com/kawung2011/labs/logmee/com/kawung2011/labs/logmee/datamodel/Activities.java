package com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel;

/**
 * Created by numan on 20/11/2014.
 */
public class Activities {
    private int _id;
    private String _name;
    private String _status;
    private String _image;
    private String _dateTime;
    private int _count_logs_text;
    private int _count_logs_image;
    private int _count_logs_speech;

    public Activities() {

    }

    public Activities(String name, String status, String image, String dateTime) {
        this._name = name;
        this._status = status;
        this._image = image;
        this._dateTime = dateTime;
    }

    public int get_count_logs_text() {
        return _count_logs_text;
    }

    public void set_count_logs_text(int _count_logs_text) {
        this._count_logs_text = _count_logs_text;
    }

    public int get_count_logs_image() {
        return _count_logs_image;
    }

    public void set_count_logs_image(int _count_logs_image) {
        this._count_logs_image = _count_logs_image;
    }

    public int get_count_logs_speech() {
        return _count_logs_speech;
    }

    public void set_count_logs_speech(int _count_logs_speech) {
        this._count_logs_speech = _count_logs_speech;
    }

    public Activities(String name, String status, String image, String dateTime, int count_logs_text, int count_logs_image, int count_logs_speech) {
        this._name = name;
        this._status = status;
        this._image = image;

        this._dateTime = dateTime;
        this._count_logs_text = count_logs_text;
        this._count_logs_image = count_logs_image;
        this._count_logs_speech = count_logs_speech;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public String get_dateTime() {
        return _dateTime;
    }

    public void set_dateTime(String _dateTime) {
        this._dateTime = _dateTime;
    }

    @Override
    public String toString(){
        return this.get_id() + "," + this.get_name() + "," + this.get_status() + "," + this.get_image() + "," + this.get_dateTime();
    }
}
