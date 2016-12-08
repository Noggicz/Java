/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicOptionPaneUI;

/**
 *
 * @author Dro0076
 */
public class Chess extends Application {

    /*Design*/
    private JButton startgame;
    private JPanel gamePanel;
    private JComboBox option;
    private GridLayout layout;

    private Model model;
    private View view;
    private AI ai;
    private Controller controller;
    private Movement movement;
    private ArrayList<ModelObject> objects = new ArrayList<>();
    Point direction = new Point();
    Point pozice = new Point();
    boolean sideToMove = true;
    boolean chooseAndPlay = true;
    boolean rightToMove = false;
    boolean rightToMovePawn = false;
    boolean AImove = true;

    public Chess() {
        model = new Model();
        movement = new Movement();
        ai = new AI();
    }

    @Override
    public void start(Stage primaryStage) {
        /*Design Meny*/
        JFrame okno = new JFrame();
        okno.setTitle("Chess");
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setSize(250, 300);
        okno.setLocation(200, 200);
        
        okno.setVisible(true);
        
        /*Volba*/
        String[] items ={"White","Black"};
        option = new JComboBox(items);
        /*Buton*/
        startgame = new JButton("Start Game");

      
     
        /*Panel*/
        
        
        gamePanel = new JPanel( new GridLayout(1, 1));
        gamePanel.add(startgame);
        gamePanel.add(option);
        okno.add(gamePanel);
        

        gamePanel.setSize(250, 300);

        startgame.addActionListener((e) -> {
            if (controller.isRunning()) {
                controller.stop();
                startgame.setText("Start game");
            } else {
                model.initGame();
                controller.start();
                startgame.setText("Stop game");
            }

        });

        AnchorPane basePane = new AnchorPane();

        Pane root = new Pane();
        Canvas canvas = new Canvas(View.width, View.height);
        root.getChildren().add(canvas);
        canvas.scaleXProperty().bind(root.widthProperty().divide(View.width));
        canvas.scaleYProperty().bind(root.heightProperty().multiply(1.0 / View.height));
        canvas.translateXProperty().bind(root.widthProperty().subtract(View.width).divide(2));
        canvas.translateYProperty().bind(root.heightProperty().subtract(View.height).divide(2));

        view = new View(canvas.getGraphicsContext2D(), model);
        controller = new Controller(view, model);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (movement.isKingTaken(model.getObjects())) {
                    System.out.println("Konec hry");
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText("The king has been taken");
                    alert.setContentText("Game Over");
                    alert.showAndWait();
                    System.exit(0);
                }
                if (AImove == false) {
                    ai.MakeMove(model.getObjects());
                    if (model.isFree(ai.GetTo())) {
                        model.getObjectAt1(ai.GetFrom(), ai.GetTo());
                    } else {
                        model.remove(ai.GetTo());
                        model.getObjectAt1(ai.GetFrom(), ai.GetTo());
                    }
                    AImove = true;
                } else {
                    if (chooseAndPlay == true) {
                        int xosa = (int) event.getSceneX();
                        int yosa = (int) event.getSceneY();
                        xosa = xosa / 90;
                        yosa = yosa / 90;

                        pozice = new Point(xosa, yosa);
                        for (ModelObject object : model.getObjects()) {
                            if (object.position.equals(pozice)) {
                                if (object instanceof Figures) {

                                    if (((Figures) object).getType() == Figures.Type.Pawn) {
                                        System.out.println("Vybral jsi pincla na pozici " + pozice);
                                    }
                                    if (((Figures) object).getType() == Figures.Type.Rook) {
                                        System.out.println("Vybral jsi vez na pozici " + pozice);
                                    }
                                    if (((Figures) object).getType() == Figures.Type.Bishop) {
                                        System.out.println("Vybral jsi strelce na pozici " + pozice);
                                    }
                                    if (((Figures) object).getType() == Figures.Type.Knight) {
                                        System.out.println("Vybral jsi kone na pozici " + pozice);
                                    }
                                    if (((Figures) object).getType() == Figures.Type.King) {
                                        System.out.println("Vybral jsi krale na pozici " + pozice);
                                    }
                                    if (((Figures) object).getType() == Figures.Type.Queen) {
                                        System.out.println("Vybral jsi kralovnu na pozici " + pozice);
                                    }
                                    chooseAndPlay = false;
                                }
                            }
                        }
                    } else {
                        AImove = false;
                        rightToMove = false;
                        rightToMovePawn = false;
                        int xosa = (int) event.getSceneX();
                        int yosa = (int) event.getSceneY();
                        xosa = xosa / 90;
                        yosa = yosa / 90;
                        //Figures.Color coloro = null;

                        direction = new Point(xosa, yosa);
                        for (ModelObject object : model.getObjects()) {

                            if (object.position.equals(pozice)) {
                                if (object instanceof Figures) {
                                    if (movement.MoveThatFigure(((Figures) object).getColor(), ((Figures) object).getType(), pozice, direction, model.getObjects())) {
                                        //coloro = ((Figures) object).getColor();
                                        if (((Figures) object).getType() == Figures.Type.Pawn) {
                                            rightToMovePawn = true;
                                        } else if (((Figures) object).getType() != Figures.Type.Pawn) {
                                            rightToMove = true;
                                        }
                                    }
                                }
                            }
                        }

                        if (chooseAndPlay == false) {

                            if (model.isFree(direction)) {
                                if (rightToMovePawn == true) {
                                    model.getObjectAt1(pozice, direction);
                                    // model.remove(pozice);
                                }
                            } else if (rightToMovePawn == true && movement.PawnTaking() == true) {
                                // if(coloro==model.getColorOfDirection(direction)){
                                model.remove(direction);
                                model.getObjectAt1(pozice, direction);
                                //model.remove(pozice);
                                /*  }else{
                                System.out.println("Beres svojeho vole");
                                coloro=null;
                              }*/
                            }
                            if (rightToMove == true) {
                                if (model.isFree(direction)) {
                                    model.getObjectAt1(pozice, direction);
                                    model.remove(pozice);
                                } else {
                                    // if(coloro==model.getColorOfDirection(direction)){
                                    model.remove(direction);
                                    model.getObjectAt1(pozice, direction);
                                    /* }else{
                                System.out.println("Beres svojeho vole");
                                coloro=null;
                              } */
                                }
                            } else if (rightToMove == false && rightToMovePawn == false) {
                                System.out.println("Nepovoleny tah, nebo je na tahu protivnik");
                            }

                            /* for (ModelObject object: model.getObjects()) {
                     System.out.println("x"+object.getX() + "y" + object.getY());
                     }*/
                        }
                        chooseAndPlay = true;
                    }
                    view.update();
                }
            }

        });

        basePane.getChildren().add(root);
        Scene scene = new Scene(basePane, View.width, View.height);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
/*model.getObjectAt1(moves.get(index).from, moves.get(index).to);
                                model.remove(moves.get(index).from);*/
