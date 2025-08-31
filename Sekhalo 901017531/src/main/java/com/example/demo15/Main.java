package com.example.demo15;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private GalleryView gallery;
    private Scene galleryScene;

    @Override
    public void start(Stage primaryStage) {

        gallery = new GalleryView(primaryStage);
        galleryScene = new Scene(gallery.getView(), 800, 500);
        galleryScene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());

        primaryStage.setTitle("JavaFX Media Gallery");
        primaryStage.setScene(galleryScene);
        primaryStage.setUserData(this); // Save Main instance for later access
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public GalleryView getGallery() {
        return gallery;
    }

    public Scene getGalleryScene() {
        return galleryScene;
    }
}
