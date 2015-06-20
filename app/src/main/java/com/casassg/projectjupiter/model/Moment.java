package com.casassg.projectjupiter.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by casassg on 19/06/15.
 *
 * @author casassg
 */
public class Moment {
    private String title;
    private double rating;
    private double x_coord;
    private double y_coord;
    private Date date;
    private long id;

    public Moment() {
        date = new Date();
        id = -1;
        x_coord = -1;
        y_coord = -1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getY_coord() {
        return y_coord;
    }

    public void setY_coord(double y_coord) {
        this.y_coord = y_coord;
    }

    public double getX_coord() {
        return x_coord;
    }

    public void setX_coord(double x_coord) {
        this.x_coord = x_coord;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return title + " - " + format.format(date);
    }
}
