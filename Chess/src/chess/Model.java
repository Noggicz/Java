/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Dro0076
 */
public class Model {

    /**
     *
     */
    public int Heigth;
    public int Width;

    private ArrayList<ModelObject> objects = new ArrayList<>();
    private ArrayList<ModelObject> back = new ArrayList<>();
    private Figures figure;

    public ArrayList<ModelObject> getObjects() {
        return this.objects;
    }

    public ArrayList<ModelObject> getBackground() {
        return this.back;
    }

    public Model() {

    }

    public synchronized void initGame() {
        objects.clear();
        back.clear();
        String[] init = {
            "BWBWBWBW",
            "WBWBWBWB",
            "BWBWBWBW",
            "WBWBWBWB",
            "BWBWBWBW",
            "WBWBWBWB",
            "BWBWBWBW",
            "WBWBWBWB"
        };
        /*background*/
        Heigth = init.length;
        int i = 0;
        for (String s : init) {
            Width = s.length();
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == 'W') {
                    back.add(new Square(new Point(j, i), Square.Type.White_back));
                } else if (s.charAt(j) == 'B') {
                    back.add(new Square(new Point(j, i), Square.Type.Black_back));
                }
            }
            i++;
        }
        /*Figures*/
        for (int k = 0; k < 8; k++) {
            Point possition = new Point(k, 6);
            figure = new Figures(possition, this, Figures.Color.White, Figures.Type.Pawn);
            objects.add(figure);
            possition = new Point(k, 1);
            figure = new Figures(possition, this, Figures.Color.Black, Figures.Type.Pawn);
            objects.add(figure);
        }

        for (int k = 1; k < 7; k = k + 5) {
            Point possition = new Point(k, 7);
            figure = new Figures(possition, this, Figures.Color.White, Figures.Type.Knight);
            objects.add(figure);
            possition = new Point(k, 0);
            figure = new Figures(possition, this, Figures.Color.Black, Figures.Type.Knight);
            objects.add(figure);
        }

        for (int k = 0; k < 8; k = k + 7) {
            Point possition = new Point(k, 7);
            figure = new Figures(possition, this, Figures.Color.White, Figures.Type.Rook);
            objects.add(figure);
            possition = new Point(k, 0);
            figure = new Figures(possition, this, Figures.Color.Black, Figures.Type.Rook);
            objects.add(figure);
        }

        for (int k = 2; k < 6; k = k + 3) {
            Point possition = new Point(k, 7);
            figure = new Figures(possition, this, Figures.Color.White, Figures.Type.Bishop);
            objects.add(figure);
            possition = new Point(k, 0);
            figure = new Figures(possition, this, Figures.Color.Black, Figures.Type.Bishop);
            objects.add(figure);
        }

        Point possition = new Point(4, 7);
        figure = new Figures(possition, this, Figures.Color.White, Figures.Type.Queen);
        objects.add(figure);
        possition = new Point(4, 0);
        figure = new Figures(possition, this, Figures.Color.Black, Figures.Type.Queen);
        objects.add(figure);

        possition = new Point(3, 7);
        figure = new Figures(possition, this, Figures.Color.White, Figures.Type.King);
        objects.add(figure);
        possition = new Point(3, 0);
        figure = new Figures(possition, this, Figures.Color.Black, Figures.Type.King);
        objects.add(figure);
    }

    public boolean isFree(Point point) {
        return objects.stream().noneMatch((object) -> (object.getPosition().equals(point)));
    }

    public synchronized Figures.Color getColorOfDirection(Point direction) {
        for (ModelObject object : objects) {
            if (object.getPosition().equals(direction)) {
                return figure.getColor();
            }
        }
        return null;
    }

    public synchronized ModelObject getObjectAt1(Point position, Point direction) {
        for (ModelObject object : objects) {
            if (object.getPosition().equals(position)) {
                //object.getPosition().setLocation(direction);
                object.position = direction;
            }
        }
        return null;
    }

    public synchronized void remove(Point position) {
        for (ModelObject object : objects) {
            if (object.getPosition().equals(position)) {
                objects.remove(object);
                return;
            }
        }
    }
}
