package com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel;

/**
 * Created by numan on 07/12/2014.
 */
public class Widgets {
    private int _id;
    private int _widgetId;
    private int _activityId;

    public Widgets() {
    }

    public Widgets(int _id, int _widgetId, int _activityId) {
        this._id = _id;
        this._widgetId = _widgetId;
        this._activityId = _activityId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_widgetId() {
        return _widgetId;
    }

    public void set_widgetId(int _widgetId) {
        this._widgetId = _widgetId;
    }

    public int get_activityId() {
        return _activityId;
    }

    public void set_activityId(int _activityId) {
        this._activityId = _activityId;
    }
}
