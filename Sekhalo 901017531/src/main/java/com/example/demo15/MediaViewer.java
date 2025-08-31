package com.example.demo15;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MediaViewer {

    private Stage stage;
    private String mediaPath;
    private GalleryView gallery;
    private Scene galleryScene;

    public MediaViewer(Stage stage, String mediaPath, GalleryView gallery, Scene galleryScene) {
        this.stage = stage;
        this.mediaPath = mediaPath;
        this.gallery = gallery;
        this.galleryScene = galleryScene;
    }

    public void showFullScreen() {
        BorderPane layout = new BorderPane();

        try {
            Image image = new Image(getClass().getResourceAsStream("/images/" + mediaPath));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(stage.getWidth());
            imageView.setFitHeight(stage.getHeight());

            layout.setCenter(imageView);
        } catch (Exception e) {
            System.out.println("Error loading full image: " + mediaPath + " - " + e.getMessage());
        }

        Button backBtn = new Button("Back");
        backBtn.getStyleClass().add("button");
        backBtn.setOnAction(e -> {
            stage.setFullScreen(false);
            stage.setScene(galleryScene);
        });

        HBox controls = new HBox(backBtn);
        controls.getStyleClass().add("hbox");
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(15));

        layout.setBottom(controls);

        Scene scene = new Scene(layout, 900, 600);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());

        stage.setScene(scene);
        stage.setFullScreen(true);
    }
}
