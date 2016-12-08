/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Dro0076
 */
public class Figures extends ModelObject {
    
    private final Model model;
    private ArrayList<ModelObject> objects = new ArrayList<>();

     public enum Color {
        Black,White;
    }
     private final Color color;
     
     public enum Type{
         Pawn,Rook,Bishop,Knight,Queen,King;
     }
     private final Type type;
     
    public Figures(Point position, Model model, Color color, Type type) {
        super(position);
        this.model = model;
        this.color = color;
        this.type = type;
    }
    
    public Figures.Type getType(){
        return type;
    }  
      
    public Figures.Color getColor() {
        return color;
    }
    @Override
    public void process() {
       
    }
}
