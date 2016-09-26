/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optrutas;

import java.awt.geom.Point2D;
import java.util.List;

/**
 *
 * @author jmlb
 */
public class Camion {
    List<Point2D> puntos;
    double dist;
    int num;

    public Camion() {
        
    }
    
    public Camion(List<Point2D> points) {
        this.puntos = points;
    }

    public Camion(List<Point2D> puntos, double dist) {
        this.puntos = puntos;
        this.dist = dist;
    }

    public Camion(int num) {
        this.num = num;
    }
    
    
    

    public List<Point2D> getPoints() {
        return puntos;
    }

    public void setPoints(List<Point2D> points) {
        this.puntos = points;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
 
    
    
}
