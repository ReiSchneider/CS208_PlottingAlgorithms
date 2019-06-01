/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PixelAlgorithms;

import java.util.ArrayList;

/**
 *
 * @author Kyle Cancio symmetricPLot
 * @author Micah Encarnacion midPointPlot
 */
public class PlotAlgorithms {

    public static ArrayList<ArrayList<Integer>> DDAPlot(int x1, int y1, int x2, int y2) {
        ArrayList<ArrayList<Integer>> coords = new ArrayList<>();

        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        double m = 0;

        double tempx2 = x1;
        double tempy2 = y1;
        if (dx == 0 || dy == 0) {
            m = 0.0;
        } else {
            m = Math.abs((double) dy / (double) dx);
        }
        int step = 0;
        if (Math.abs(dx) > Math.abs(dy)) {
            step = Math.abs(dx);
        } else {
            step = Math.abs(dy);
        }
        double xStep = (double) dx / (double) step;
        double yStep = (double) dy / (double) step;

        ArrayList<Integer> coord;

        coord = new ArrayList<>();
        coord.add((int) Math.round(tempx2));
        coord.add((int) Math.round(tempy2));
        coords.add(coord);

        for (int i = 1; i < step + 1; i++) {
            if (x1 > x2) {
                tempx2 = tempx2 - xStep;
            } else if (x1 < x2) {
                tempx2 = tempx2 + xStep;
            }
            if (y1 < y2) {
                tempy2 = tempy2 + yStep;
            } else {
                tempy2 = tempy2 - yStep;
            }
            coord = new ArrayList<>();
            coord.add((int) Math.round(tempx2));
            coord.add((int) Math.round(tempy2));
            coords.add(coord);

        }

        return coords;
    }

    public static ArrayList<ArrayList<Integer>> bresenhamPlot(int x1, int y1, int x2, int y2) {
        ArrayList<ArrayList<Integer>> coords = new ArrayList<>();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int twiceDx = 2 * dx;
        int twiceDy = 2 * dy;
        double m = 0;

        ArrayList<Integer> coord;

        if (dx == 0 || dy == 0) {
            m = 0;
        } else {
            m = (double) dy / (double) dx;
        }
        int p = 0;
        int tempX = x1;
        int tempY = y1;
        if (m == 0) {
            if (dx == 0) {    // INC/DEC Y
                p = twiceDx - dy;
                for (int k = 0; k <= dy; k++) {
                    coord = new ArrayList<>();
                    coord.add(tempX);
                    coord.add(tempY);
                    coords.add(coord);
                    if (y1 < y2) {
                        tempY++; // INC y -> Y0 to Y1  
                    } else if (y1 > y2) {
                        tempY--; // DEC y -> Y0 to Y1
                    }
                    if (p < 0) {
                        p = p + twiceDx;
                    } else {
                        p = p + (twiceDx - twiceDy);
                    }
                }
            } else if (dy == 0) {
                p = twiceDy - dx;
                for (int k = 0; k <= dx; k++) {
                    coord = new ArrayList<>();
                    coord.add(tempX);
                    coord.add(tempY);
                    coords.add(coord);

                    if (x1 < x2) {
                        tempX++; // INC y -> Y0 to Y1  
                    } else if (x1 > x2) {
                        tempX--; // DEC y -> Y0 to Y1
                    }
                    if (p < 0) {
                        p = p + twiceDy;
                    } else {
                        p = p + (twiceDy - twiceDx);
                    }
                }
            }
        } else if (m < 1) {
            p = twiceDy - dx;
            for (int k = 0; k <= dx; k++) {
                coord = new ArrayList<>();
                coord.add(tempX);
                coord.add(tempY);
                coords.add(coord);

                if (x1 < x2) {        // CASE 1
                    tempX++;        // INCREASE X.
                } else if (x1 > x2) {   // CASE 2
                    tempX--;        // DECREASE X.
                }
                if (p < 0) {
                    p = p + twiceDy;    // P = P + 2DELTAX - FORMULA FOR NEXT P-RETAIN
                } else {
                    if (y1 > y2) {
                        tempY--;
                    } else if (y1 < y2) {
                        tempY++;
                    }
                    p = p + (twiceDy - twiceDx); // P = P + 2DELTAY - 2DELTAX - FORMULA FOR NEXT P-RETAIN
                }
            }
        } else {
            p = twiceDx - dy;
            for (int k = 0; k <= dy; k++) {
                coord = new ArrayList<>();
                coord.add(tempX);
                coord.add(tempY);
                coords.add(coord);

                if (y1 < y2) {
                    tempY++; // INC y -> Y0 to Y1  
                } else if (y1 > y2) {
                    tempY--; // DEC y -> Y0 to Y1
                }
                if (p < 0) {
                    p = p + twiceDx;
                } else {
                    if (x1 < x2) {
                        tempX++;       // INC X -> X0 TO X1
                    } else if (x1 > x2) {
                        tempX--;       // DEC X -> X0 TO X1
                    }
                    p = p + (twiceDx - twiceDy);
                }
            }
        }
        return coords;
    }

    public static ArrayList<ArrayList<Integer>> symmetricPlot(int h, int k, int r) {
        ArrayList<ArrayList<Integer>> coords = new ArrayList<>();

        if (r < 1) {
            throw new java.lang.NumberFormatException();
        }

        double dt = 45.0 / (double) r;

        ArrayList<Integer> coord = new ArrayList<>();

        double x = r * Math.cos(Math.toRadians(0));
        double y = r * Math.sin(Math.toRadians(0));

        coord.add((int) Math.round(x));
        coord.add((int) Math.round(y));
        coords.add(coord);

        coord = new ArrayList<>();

        x = r * Math.cos(Math.toRadians(dt));
        y = r * Math.sin(Math.toRadians(dt));

        coord.add((int) Math.round(x));
        coord.add((int) Math.round(y));
        coords.add(coord);

        while ((int) Math.round(x) != (int) Math.round(y)) {

            double x2 = x, y2 = y;

            x = x2 * Math.cos(Math.toRadians(dt)) - y2 * Math.sin(Math.toRadians(dt));
            y = y2 * Math.cos(Math.toRadians(dt)) + x2 * Math.sin(Math.toRadians(dt));

            coord = new ArrayList<>();
            coord.add((int) Math.round(x));
            coord.add((int) Math.round(y));
            coords.add(coord);
        }

        return coords;
    }

    public static ArrayList<ArrayList<Integer>> midPointPlot(int h, int k, int r) {
        ArrayList<ArrayList<Integer>> coords = new ArrayList<>();

        if (r < 1) {
            throw new java.lang.NumberFormatException();
        }

        int rad = (5 / 4) - r;

        int X = 0, Y = r;
        ArrayList<Integer> coord;

        do {
            coord = new ArrayList<>();
            coord.add((h + X));
            coord.add((k + Y));
            coords.add(coord);

            coord = new ArrayList<>();
            coord.add((h + X));
            coord.add((k - Y));
            coords.add(coord);

            coord = new ArrayList<>();
            coord.add((h - X));
            coord.add((k + Y));
            coords.add(coord);

            coord = new ArrayList<>();
            coord.add((h - X));
            coord.add((k - Y));
            coords.add(coord);

            coord = new ArrayList<>();
            coord.add((h + Y));
            coord.add((k + X));
            coords.add(coord);

            coord = new ArrayList<>();
            coord.add((h + Y));
            coord.add((k - X));
            coords.add(coord);

            coord = new ArrayList<>();
            coord.add((h - Y));
            coord.add((k + X));
            coords.add(coord);

            coord = new ArrayList<>();
            coord.add((h - Y));
            coord.add((k - X));
            coords.add(coord);
            if (rad < 0) {
                rad += 2 * X + 1;
            } else {
                rad += 2 * X - 2 * Y + 1;
                Y--;
            }
            X++;
        } while (X<=Y);

        return coords;
    }

}
