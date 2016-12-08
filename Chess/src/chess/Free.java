/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Point;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author Dro0076
 */
public class Free {

    private Model model;
    private View view;
    private Controller controller;
    private Movement movement;
    //private ArrayList<ModelObject> objects = new ArrayList<>();

    public Free() {
        model = new Model();
    }

    public boolean isWayClear(Point point, ArrayList<ModelObject> objectsForMovement) {
        ArrayList<ModelObject> objectsForFree = new ArrayList<ModelObject>(objectsForMovement.size());
        for (ModelObject obj : objectsForMovement) {
            objectsForFree.add((ModelObject) obj);
        }
        /* for (ModelObject object: objectsForFree) {
           System.out.println("x"+object.getX() + "y" + object.getY());
        }*/
        for (ModelObject object : objectsForFree) {
            if (object.getPosition().equals(point)) {
                return false;
            }
        }

        return true;
    }

    public boolean isFree(Point point, ArrayList<ModelObject> objectsForMovement) {
        ArrayList<ModelObject> objectsForFree = new ArrayList<ModelObject>(objectsForMovement.size());
        for (ModelObject obj : objectsForMovement) {
            objectsForFree.add((ModelObject) obj);
        }
        return objectsForFree.stream().noneMatch((object) -> (object.getPosition().equals(point)));
    }
}
