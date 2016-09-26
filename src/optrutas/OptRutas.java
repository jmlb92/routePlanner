/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optrutas;

import com.google.common.collect.Iterables;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmlb
 */
public class OptRutas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        FileInputStream fstream = null;
        int camiones = 0;
        int tiendas;
        String[] res;
        ArrayList coorx = new ArrayList();
        ArrayList coory = new ArrayList();
        List<Point2D> points = new ArrayList<Point2D>();
        List<Point2D> points3 = new ArrayList<Point2D>();
        List<Point2D> points4 = new ArrayList<Point2D>();
        List<List<Point2D>> points2 = new ArrayList<List<Point2D>>();
        File file = new File("Salida.txt");
        FileWriter writer = new FileWriter(file);
        double porcam;
        try {
            // TODO code application logic here
            // Open the file
            fstream = new FileInputStream("./entrada100.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                if (camiones == 0) {
                    camiones = Integer.parseInt(strLine);
                    //System.out.println("Camiones: " + camiones);
                } else if (camiones != 0) {
                    res = strLine.split(",");
                    //System.out.println("Tamaño res: " + res.length);
                    coorx.add(res[0]);
                    coory.add(res[1]);
                }

                //System.out.println(strLine);
            }   //Close the input stream
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OptRutas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(OptRutas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        tiendas = coorx.size();
        System.out.println("Numero de Camiones: " + camiones);
        System.out.println("Numero de Tiendas: " + coorx.size());
        System.out.println("Tienda de Origen: " + coorx.get(0) + " , " + coory.get(0));
        System.out.println("Tiendas:");
        for (int i = 0; i < coorx.size(); i++) {
            //System.out.println(coorx.get(i) + " , " + coory.get(i));
            points.add(new Point2D.Double(Double.parseDouble(coorx.get(i).toString()), Double.parseDouble(coory.get(i).toString())));
        }

        porcam = Math.ceil(((double) tiendas / (double) camiones));
        System.out.println("Tiendas por camión: " + porcam);
        System.out.println("points: " + points.size());

        Point2D centro = points.get(0);
        Point2D centro2 = points.get(0);
        Collections.sort(points, createComparator(centro));

        for (int i = 0; i < points.size(); i++) {
            //System.out.println(points.get(i));
        }

        //asignar(camiones, points, porcam);
        points2 = split(points, camiones);

        for (int i = 0; i < points2.size(); i++) {
            double dist = 0.0;
            points3 = points2.get(i);
            //points4 = points2.get(i);
            //System.out.println("Distancia a Centro");
            //points4.add(0, centro);
            writer.write("Camión #" + i + ": " + " Numero de Tiendas en su ruta: " + points3.size() + " " + "\n");
            writer.write("Puntos Visitados:" + "\n");
            //System.out.println("Ruta #" + i + ": " + " Numero de Tiendas: " + points3.size());
            //System.out.println("Puntos: ");
            for (int j = 0; j < points3.size(); j++) {
                //System.out.println("Sub lista #" + i + ": Valores"+ points3.get(j));

                dist = dist + points3.get(j).distance(centro2);
                centro2 = points3.get(j);

                file.createNewFile();
                writer.write("[" + points3.get(j).getX() + "," + points3.get(j).getY() + "]" + " ");
                

                System.out.print("[" + points3.get(j).getX() + "," + points3.get(j).getY() + "]" + " ");
            }
            dist = dist + centro2.distance(centro);
            writer.write("\n");
            writer.write("Camión #" + i + ": Distancia Total: " + dist + "\n \n");
            writer.flush();
            
            //System.out.println("");
            //System.out.println("Ruta # " + i + ": Distancia Total: " + dist);

        }
        writer.close();

    }// fin main

    private static Comparator<Point2D> createComparator(Point2D p) {
        final Point2D finalP = new Point2D.Double(p.getX(), p.getY());
        return new Comparator<Point2D>() {
            @Override
            public int compare(Point2D p0, Point2D p1) {
                double ds0 = p0.distanceSq(finalP);
                double ds1 = p1.distanceSq(finalP);
                return Double.compare(ds0, ds1);
            }

        };
    }

    public static <Point2D> List<List<Point2D>> split(List<Point2D> list, int numberOfLists) {
        if (list == null) {
            throw new NullPointerException("The list parameter is null.");
        }
        if (numberOfLists <= 0) {
            throw new IllegalArgumentException(
                    "The number of lists parameter must be more than 0.");
        }

        int sizeOfSubList = list.size() / numberOfLists + 1;
        int remainder = list.size() % numberOfLists;

        List<List<Point2D>> subLists = new ArrayList<List<Point2D>>(numberOfLists);

        // if there is a remainder, let the first sub-lists have one length...
        for (int i = 0; i < numberOfLists - remainder; i++) {
            subLists.add(list.subList(i * sizeOfSubList, (i + 1) * sizeOfSubList));
        }

        // ... the remaining sub-lists will have -1 size than the first.
        sizeOfSubList--;
        for (int i = numberOfLists - remainder; i < numberOfLists; i++) {
            subLists.add(list.subList(i * sizeOfSubList, (i + 1) * sizeOfSubList));
        }

        return subLists;
    }

}
