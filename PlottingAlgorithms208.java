/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PixelAlgorithms;

/**
 *
 * @author Kyle Cancio
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.border.Border;

class PlottingAlgorithms208 {

    static HashMap<String, JPanel> plane;

    static int grid[][];
    static JPanel com;
    static JTextArea statusText;
    static JButton enter;
    static JTextField hcenter, kcenter, radius, x_1, y_1, x_2, y_2;
    static int X = 7, Y = 7;
    JFrame c;

    public static void main(String args[]) {

        PlottingAlgorithms208 cx = new PlottingAlgorithms208();
    }

    public PlottingAlgorithms208() {
        c = new JFrame();
        c.setTitle("Plotting Algorithms CS208");
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setLayout(new BorderLayout());

        //Control Panel
        JPanel con = new JPanel();
        con.setLayout(new FlowLayout());

        //Toolbar
        JPanel topcon = new JPanel();
        topcon.setLayout(new BorderLayout());

        //Side Panel
        JPanel side = new JPanel();
        side.setLayout(new BorderLayout());

        JComboBox algos = new JComboBox();

        //Enter Button
        enter = new JButton("ENTER");
        //Enter Button ActionListener

        hcenter = new JTextField();
        hcenter.setColumns(5);

        kcenter = new JTextField();
        kcenter.setColumns(5);

        radius = new JTextField();
        radius.setColumns(5);

        x_1 = new JTextField();
        x_1.setColumns(5);

        y_1 = new JTextField();
        y_1.setColumns(5);

        x_2 = new JTextField();
        x_2.setColumns(5);

        y_2 = new JTextField();
        y_2.setColumns(5);

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String algo = algos.getSelectedItem().toString();

                try {
                    switch (algo) {
                        case "Differential Digital Analyzer":
                            int x1 = Integer.parseInt(x_1.getText());
                            int y1 = Integer.parseInt(y_1.getText());
                            int x2 = Integer.parseInt(x_2.getText());
                            int y2 = Integer.parseInt(y_2.getText());

                            plotDDA(x1, y1, x2, y2);

                            break;
                        case "Bresenham Algorithm":
                            x1 = Integer.parseInt(x_1.getText());
                            y1 = Integer.parseInt(y_1.getText());
                            x2 = Integer.parseInt(x_2.getText());
                            y2 = Integer.parseInt(y_2.getText());

                            plotBresenham(x1, y1, x2, y2);
                            break;
                        case "Midpoint Circle Algorithm":
                            int h = Integer.parseInt(hcenter.getText());
                            int k = Integer.parseInt(kcenter.getText());
                            int r = Integer.parseInt(radius.getText());

                            //plotDDA(-6, -6, -4, -20);
                            plotMidPoint(h, k, r);

                            break;
                        case "Symmetric Circle Plotting":
                            h = Integer.parseInt(hcenter.getText());
                            k = Integer.parseInt(kcenter.getText());
                            r = Integer.parseInt(radius.getText());

                            //plotDDA(-6, -6, -4, -20);
                            plotSymmetric(h, k, r);

                            break;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                c.revalidate();
                c.repaint();

            }
        });

        com = drawGrid(X, Y);

        algos.addItem("Differential Digital Analyzer");
        algos.addItem("Bresenham Algorithm");
        algos.addItem("Midpoint Circle Algorithm");
        algos.addItem("Symmetric Circle Plotting");

        algos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox comboBox = (JComboBox) event.getSource();

                Object selected = comboBox.getSelectedItem();
                con.removeAll();

                switch (selected.toString()) {
                    case "Differential Digital Analyzer":
                    case "Bresenham Algorithm":

                        con.add(new JLabel("Point A("));
                        con.add(x_1);
                        con.add(new JLabel(", "));
                        con.add(y_1);
                        con.add(new JLabel(")        Point B("));
                        con.add(x_2);
                        con.add(new JLabel(", "));
                        con.add(y_2);
                        con.add(enter);

                        break;
                    case "Midpoint Circle Algorithm":
                    case "Symmetric Circle Plotting":
                        con.add(new JLabel("(h,k) => ("));
                        con.add(hcenter);
                        con.add(new JLabel(", "));
                        con.add(kcenter);
                        con.add(new JLabel(")"));
                        con.add(new JLabel("        RADIUS => "));
                        con.add(radius);

                        con.add(enter);

                        break;
                }

                con.revalidate();
                con.repaint();
            }
        });

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());
        header.add(new JLabel("CANCIO | FRIAS | ENCARNACION | GRANADINO"));
        topcon.add(header, BorderLayout.PAGE_START);
        header = new JPanel();
        header.add(new JLabel("Choose your algorithm"));
        header.add(algos);

        topcon.add(header, BorderLayout.CENTER);

        con.add(new JLabel("Point A("));
        con.add(x_1);
        con.add(new JLabel(", "));
        con.add(y_1);
        con.add(new JLabel(")        Point B("));
        con.add(x_2);
        con.add(new JLabel(", "));
        con.add(y_2);
        con.add(enter);

        statusText = new JTextArea();
        statusText.setColumns(30);
        statusText.setEditable(false);
        statusText.setLineWrap(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        statusText.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        side.add(statusText, BorderLayout.CENTER);
        side.add(new JScrollPane(statusText));

        c.add(topcon, BorderLayout.PAGE_START);
        c.add(com, BorderLayout.CENTER);
        c.add(side, BorderLayout.LINE_END);
        c.add(con, BorderLayout.PAGE_END);
        c.setSize(1300, 1000);
        c.setResizable(false);
        c.setLocationRelativeTo(null);
        c.setVisible(true);
    }

    public void plotDDA(int x1, int y1, int x2, int y2) {
        ArrayList<ArrayList<Integer>> dx = PlotAlgorithms.DDAPlot(x1, y1, x2, y2);

        System.out.println(dx.size());

        int max = Math.max(Math.abs(x1), Math.max(Math.abs(x2), Math.max(Math.abs(y1), Math.abs(y2))));

        System.out.println(max);

        if ((max = (max * 2)) % 2 == 0) {
            max += 3;
        }

        c.remove(com);
        com = drawGrid(max, max);
        c.add(com, BorderLayout.CENTER);

        statusText.append("DDA PLOTTING OF A LINE");
        statusText.append("\n---------------------------------------------------");

        statusText.append("\nGIVEN: A(" + x1 + ", " + y1 + ") and B(" + x2 + ", " + y2 + ")\n");
        statusText.append("\nX1\tY1\n");

        for (ArrayList<Integer> d : dx) {
            String coord1 = "(" + (d.get(0)) + ", " + (d.get(1)) + ")";

            statusText.append("" + (d.get(0)) + "\t" + (d.get(1)) + "\n");

            plane.get(coord1).setBackground(Color.red);

        }
    }

    public void plotBresenham(int x1, int y1, int x2, int y2) {
        ArrayList<ArrayList<Integer>> dx = PlotAlgorithms.bresenhamPlot(x1, y1, x2, y2);

        System.out.println(dx.size());

        int max = Math.max(Math.abs(x1), Math.max(Math.abs(x2), Math.max(Math.abs(y1), Math.abs(y2))));

        System.out.println(max);

        if ((max = (max * 2)) % 2 == 0) {
            max += 3;
        }

        c.remove(com);
        com = drawGrid(max, max);
        c.add(com, BorderLayout.CENTER);

        statusText.append("BRESENHAM ALGORITHM PLOTTING OF A LINE");
        statusText.append("\n---------------------------------------------------");

        statusText.append("\nGIVEN: A(" + x1 + ", " + y1 + ") and B(" + x2 + ", " + y2 + ")\n");
        statusText.append("\nX1\tY1\n");

        for (ArrayList<Integer> d : dx) {
            String coord1 = "(" + (d.get(0)) + ", " + (d.get(1)) + ")";

            statusText.append("" + (d.get(0)) + "\t" + (d.get(1)) + "\n");

            plane.get(coord1).setBackground(Color.red);

        }
    }

    public void plotMidPoint(int h, int k, int r) {
        ArrayList<ArrayList<Integer>> dx = PlotAlgorithms.midPointPlot(h, k, r);

        int max = Math.max(Math.abs(h), Math.abs(k));

        if ((max = ((max + r) * 2)) % 2 == 0) {
            max += 5;
        }

        c.remove(com);
        com = drawGrid(max, max);
        c.add(com, BorderLayout.CENTER);

        statusText.append("MIDPOINT CIRCLE ALGORITHM PLOTTING");
        statusText.append("\n---------------------------------------------------");

        statusText.append("\nGIVEN: C(" + h + ", " + k + ") with r = " + r + "\n");
        statusText.append("\nX\tY\n");

        for (ArrayList<Integer> d : dx) {
            String coord1 = "(" + (d.get(0)) + ", " + (d.get(1)) + ")";

            statusText.append((d.get(0)) + "\t" + (d.get(1)) + "\n");

            plane.get(coord1).setBackground(Color.red);

        }
    }

    public void plotSymmetric(int h, int k, int r) {
        ArrayList<ArrayList<Integer>> dx = PlotAlgorithms.symmetricPlot(h, k, r);

        /*for (ArrayList<Integer> d : dx) {
            for (int xx : d) {
                System.out.print(xx + " ");
            }
            System.out.println();
        }*/
        int max = Math.max(Math.abs(h), Math.abs(k));

        if ((max = ((max + r) * 2)) % 2 == 0) {
            max += 5;
        }

        //System.out.println(max);
        c.remove(com);
        com = drawGrid(max, max);
        c.add(com, BorderLayout.CENTER);

        statusText.append("SYMMETRIC PLOTTING OF A CIRCLE");
        statusText.append("\n---------------------------------------------------");

        statusText.append("\nGIVEN: C(" + h + ", " + k + ") with r = " + r + "\n");
        statusText.append("Mirrored Coordinates not included\n\nX\tY\n");

        for (ArrayList<Integer> d : dx) {
            String coord1 = "(" + (d.get(0) + h) + ", " + (d.get(1) + k) + ")";
            String coord2 = "(" + (-d.get(0) + h) + ", " + (d.get(1) + k) + ")";
            String coord3 = "(" + (d.get(0) + h) + ", " + (-d.get(1) + k) + ")";
            String coord4 = "(" + (-d.get(0) + h) + ", " + (-d.get(1) + k) + ")";

            String coord5 = "(" + (d.get(1) + h) + ", " + (-d.get(0) + k) + ")";
            String coord6 = "(" + (d.get(1) + h) + ", " + (d.get(0) + k) + ")";
            String coord7 = "(" + (-d.get(1) + h) + ", " + (-d.get(0) + k) + ")";
            String coord8 = "(" + (-d.get(1) + h) + ", " + (d.get(0) + k) + ")";

            statusText.append((d.get(0) + h) + "\t" + (d.get(1) + k) + "\n");

            plane.get(coord1).setBackground(Color.red);
            plane.get(coord2).setBackground(Color.red);
            plane.get(coord3).setBackground(Color.red);
            plane.get(coord4).setBackground(Color.red);

            plane.get(coord5).setBackground(Color.red);
            plane.get(coord6).setBackground(Color.red);
            plane.get(coord7).setBackground(Color.red);
            plane.get(coord8).setBackground(Color.red);

        }

    }

    public static JPanel drawGrid(int X, int Y) {
        plane = new HashMap<>();
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(X, Y, 2, 2));
        board.setBackground(Color.BLACK);

        int X2 = X / 2;
        int Y2 = Y / 2;

        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                JPanel x = new JPanel();

                int X3 = 0, Y3 = 0;

                if (j < X2) {
                    X3 = -(X2 - j);
                } else {
                    X3 = j - X2;
                }

                if (i < Y2) {
                    Y3 = Y2 - i;
                } else {
                    Y3 = -(i - Y2);
                }

                /*if ((i + j) % 2 == 0) {
                    x.setBackground(Color.WHITE);
                } else {
                    x.setBackground(Color.WHITE);
                }*/
                if ((X3 == 0) || (Y3 == 0)) {
                    x.setBackground(Color.GRAY);
                } else {
                    x.setBackground(Color.WHITE);
                }

                String val = "(" + X3 + ", " + Y3 + ")";
                //System.out.println(val);
                x.add(new JLabel(val));
                plane.put(val, x);

                board.add(x);

            }
        }
        return board;

    }

    public static JPanel drawGrid(int X, int Y, ArrayList<ArrayList<Integer>> coordinates) {
        plane = new HashMap<>();
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(X, Y, 2, 2));
        board.setBackground(Color.BLACK);

        int X2 = X / 2;
        int Y2 = Y / 2;

        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                JPanel x = new JPanel();

                int X3 = 0, Y3 = 0;

                if (j < X2) {
                    X3 = -(X2 - j);
                } else {
                    X3 = j - X2;
                }

                if (i < Y2) {
                    Y3 = Y2 - i;
                } else {
                    Y3 = -(i - Y2);
                }

                if ((X3 == 0) || (Y3 == 0)) {
                    x.setBackground(Color.GRAY);
                } else {
                    x.setBackground(Color.WHITE);
                }

                for (ArrayList<Integer> d : coordinates) {
                    System.out.println(d.get(0) + " " + d.get(1));
                    if (Math.abs(X3) == d.get(1) && Math.abs(Y3) == d.get(0)) {
                        x.setBackground(Color.GRAY);
                    }
                }

                String val = "(" + X3 + ", " + Y3 + ")";
                x.add(new JLabel(val));
                plane.put(val, x);

                board.add(x);

            }
        }
        return board;

    }
}
