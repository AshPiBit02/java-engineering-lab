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
    private Slider zooSlider;

    private LinkedList<Image> images = new LinkedList<>();
    private ListIterator<Image> iterator;

    @FXML
    public void initailize() {
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
    }

}
