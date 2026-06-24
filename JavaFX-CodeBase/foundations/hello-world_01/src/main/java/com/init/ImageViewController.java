package com.init;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;

import java.util.LinkedList;
import java.util.ListIterator;

public class ImageViewController {
    @FXML
    private ImageView imageView;
    @FXML
    private Button NxtBtn;
    @FXML
    private Button PrevBtn;
    @FXML
    private Slider zoomSlider;

    private LinkedList<Image> images = new LinkedList<>();
    private ListIterator<Image> iterator;

    @FXML
    public void initialize() {
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/one.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/two.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/three.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/four.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/five.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/six.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/seven.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/eight.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/nine.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/ten.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/eleven.png")));
        images.add(new Image(getClass().getResourceAsStream("/ImageViewer_Imgs/twelve.png")));

        iterator = images.listIterator();
        if (iterator.hasNext()) {
            imageView.setImage(iterator.next());
        }

        zoomSlider.setMin(0.5);
        zoomSlider.setMax(3.0);
        zoomSlider.setValue(1.0);
        zoomSlider.valueProperty().addListener((obs, odlVal, newVal) -> {
            imageView.setScaleX(newVal.doubleValue());
            imageView.setScaleY(newVal.doubleValue());
        });

    }

    @FXML
    private void handleNext() {
        if (iterator.hasNext()) {
            imageView.setImage(iterator.next());
        }
    }

    @FXML
    private void handlePrevious() {
        if (iterator.hasPrevious()) {
            imageView.setImage(iterator.previous());
        }
    }

}
