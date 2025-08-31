package com.example.demo15;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GalleryView {

    private Stage stage;
    private FlowPane thumbnailPane;
    private TextField searchBox;
    private BorderPane root;

    private static final String[] images = {
            "6a6829d55a824815abb7861512e0e5e5.jpg",
            "170811793.jpg",
            "1737451913589.jpg",
            "b2bcd75e35b64395b55f3de120de6f33.jpg"
    };

    private List<VBox> thumbnailBoxes = new ArrayList<>();

    public GalleryView(Stage stage) {
        this.stage = stage;

        searchBox = new TextField();
        searchBox.setPromptText("Search");
        searchBox.setMaxWidth(500);
        searchBox.textProperty().addListener((obs, oldVal, newVal) -> filterThumbnails(newVal));

        thumbnailPane = new FlowPane();
        thumbnailPane.setPadding(new Insets(15));
        thumbnailPane.setHgap(10);
        thumbnailPane.setVgap(10);
        thumbnailPane.setAlignment(Pos.CENTER);
        thumbnailPane.getStyleClass().add("flow-pane");

        loadThumbnails();

        root = new BorderPane();
        root.setTop(searchBox);
        BorderPane.setAlignment(searchBox, Pos.CENTER);
        BorderPane.setMargin(searchBox, new Insets(10, 0, 10, 20));
        ScrollPane scrollPane = new ScrollPane(thumbnailPane);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);
    }

    public BorderPane getView() {
        return root;
    }

    private void loadThumbnails() {
        thumbnailBoxes.clear();
        thumbnailPane.getChildren().clear();

        for (String img : images) {
            try {
                Image image = new Image(getClass().getResourceAsStream("/images/" + img));
                ImageView thumbnail = getImageView(img, image);

                String name = img.contains(",") ? img.substring(0, img.lastIndexOf(',')) : img;
                Label label = new Label(name);
                label.getStyleClass().add("photo-label");

                VBox box = new VBox(thumbnail, label);
                box.setAlignment(Pos.CENTER);
                box.setSpacing(5);

                thumbnailBoxes.add(box);
                thumbnailPane.getChildren().add(box);
            } catch (Exception e) {
                System.out.println("Error loading image: " + img + " - " + e.getMessage());
            }
        }
    }

    private ImageView getImageView(String img, Image image) {
        ImageView thumbnail = new ImageView(image);
        thumbnail.setFitWidth(100);
        thumbnail.setFitHeight(100);
        thumbnail.setPreserveRatio(true);
        thumbnail.setSmooth(true);
        thumbnail.setCache(true);
        thumbnail.getStyleClass().add("image-view");

        thumbnail.setOnMouseClicked(event -> {
            Main mainApp = (Main) stage.getUserData();
            MediaViewer viewer = new MediaViewer(stage, img, this, mainApp.getGalleryScene());
            viewer.showFullScreen();
        });

        return thumbnail;
    }

    private void filterThumbnails(String filter) {
        String lowerFilter = filter.toLowerCase();

        thumbnailPane.getChildren().clear();

        List<VBox> filtered = thumbnailBoxes.stream().filter(box -> {
            Label label = (Label) box.getChildren().get(1);
            return label.getText().toLowerCase().contains(lowerFilter);
        }).collect(Collectors.toList());

        thumbnailPane.getChildren().addAll(filtered);
    }
}
