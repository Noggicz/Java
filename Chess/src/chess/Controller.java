/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author Dro0076
 */
public class Controller {
    private Timeline timer;
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        timer = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                synchronized (model) {
                    for (ModelObject object : model.getObjects()) {
                        object.process();
                    }
                }
                view.update();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        this.model = model;
        this.view = view;
    }

    public boolean isRunning() {
        return timer.getStatus() == Timeline.Status.RUNNING;
    }

    void stop() {
        timer.stop();
    }

    void start() {
        view.update();
        timer.play();
    }
}
