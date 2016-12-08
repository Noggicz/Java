/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Point;

/**
 *
 * @author Dro0076
 */
public class Square extends ModelObject {

    public enum Type {
        Black_back, White_back;
    }

    private final Type type;

    public Square(Point position, Type type) {
        super(position);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void process() {

    }

}
