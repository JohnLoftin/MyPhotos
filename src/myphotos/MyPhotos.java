/*  *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *  My Photos                                                               *
 *  John Loftin                                                             *
 *  A GUI-based photo album.                                                *
 *  *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   */

package myphotos;

import java.util.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author John
 */
public class MyPhotos extends Application {
    // create root pane
    final BorderPane root = new BorderPane();
    // set max character limits
    final int titleMaxLength = 100;
    final int descMaxLength = 300;
    // create textfields - used for image info
    TextField name = new TextField();
    TextField date = new TextField();
    TextField desc = new TextField();
    TextField loc = new TextField();
    // create arraylist - to be used in adding/removing images (WIP)
    ArrayList<String> imageTitle = new ArrayList();
    // create arraylists - used for sorting images (WIP)
    ArrayList<String> titleSort = new ArrayList();
    ArrayList<String> dateSort = new ArrayList();
    ArrayList<String> locSort = new ArrayList();
    
    @Override
    public void start(Stage primaryStage) {
        // adds default images arraylist
        addDefaultImages(imageTitle);
        
        // array holds image paths
        String[] titleArray = imageTitle.toArray(new String[imageTitle.size()]);
        
        // set root pane style
        root.setStyle("-fx-background-color: black;");
        // sets/anchors tilepane to right side of borderpane (root)
        root.setRight(addAnchorPane(addTilePane(titleArray)));
        
        // create scene
        Scene scene = new Scene(root, 1200, 800);
        
        // set/show scene
        primaryStage.setTitle("My Photos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // add buttons w/thumbnails to primary stage
    private TilePane addTilePane(String[] titleArray){
        // create/set tilepane
        TilePane tile = new TilePane();
        tile.setHgap(4);
        tile.setVgap(4);
        tile.setPrefColumns(2);
        tile.setPadding(new Insets(5, 5, 5, 5));
        
        // create button array
        Button[] btns = new Button[titleArray.length];
        
        // creates buttons and sets style/action for each - clickable thumbs
        for(int i = 0; i < titleArray.length; i++){
            final int ii = i;
            btns[i] = new Button();
            // set current image as button graphic
            btns[i].setGraphic(new ImageView(new Image(titleArray[ii], 125, 125, 
                    false, false)));
            btns[i].setStyle("-fx-base: skyblue;");
            // lambda sets action when button clicked
            btns[i].setOnAction(e -> enlargeImage(titleArray, btns, ii));
            // adds button to tilepane
            tile.getChildren().add(btns[i]);
        }
        return tile;
    }
    // anchor TilePane to primary stage
    private AnchorPane addAnchorPane(TilePane tile){
        
        AnchorPane anchorPane = new AnchorPane();
        
        anchorPane.getChildren().add(tile);
        
        return anchorPane;
    }
    // adds image locations to array list
    public ArrayList addDefaultImages(ArrayList imageTitle){
        // populating array list with image locations
        imageTitle.add("resources/images/image1.jpg");
        imageTitle.add("resources/images/image2.jpg");
        imageTitle.add("resources/images/image3.jpg");
        imageTitle.add("resources/images/image4.jpg");
        imageTitle.add("resources/images/image5.jpg");
        imageTitle.add("resources/images/image6.jpg");
        imageTitle.add("resources/images/image7.jpg");
        imageTitle.add("resources/images/image8.jpg");
        imageTitle.add("resources/images/image9.jpg");
        imageTitle.add("resources/images/image10.jpg");
        
        return imageTitle;  // return populated list
    }
    // adds selected image to center stack pane - creates buttons
    private void enlargeImage(String[] titleArray, Button[] btns, int list_index){
        
        // create dropshadow effect object
        DropShadow shadow = new DropShadow();
        
        // create stackpane - enlarged image placement center screen
        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(10, 10, 10, 10));
        stackPane.setAlignment(Pos.CENTER);
        
        // create hbox - button placement
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(15, 12, 15, 12));
        buttons.setSpacing(30);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        
        // set dropshadow settings
        shadow.setRadius(35.0);
        shadow.setColor(Color.AQUA);
        
        // create edit button/set button style
        Button btnEdit = new Button("Edit Info");
        btnEdit.setStyle("-fx-font-size: 12pt; -fx-base: black; -fx-text-fill: aqua;"
                + " -fx-effect: dropshadow(gaussian, aqua, 5, 0.5, 1, 1);");
        // create sort button/set button style
        Button btnSort = new Button("Sort Images");
        btnSort.setStyle("-fx-font-size: 12pt; -fx-base: black; -fx-text-fill: aqua;"
                + " -fx-effect: dropshadow(gaussian, aqua, 5, 0.5, 1, 1);");
        // add tooltip to sort button - alerts user of sorting requirements
        btnSort.setTooltip(new Tooltip("Edit Info before Sorting. Images with "
                + "no\ninformation will not be sorted."));
        // add buttons to hbox
        buttons.getChildren().addAll(btnEdit, btnSort);
        
        // displays selected image with shadow effect in stackpane
        ImageView img = new ImageView(new Image(titleArray[list_index], 800, 500, 
                false, false));
        img.setEffect(shadow);
        stackPane.getChildren().add(img);
        
        // sets actions for clicked buttons
        btnEdit.setOnAction(e -> editImageInfo(btns, list_index));
        btnSort.setOnAction(e -> sortImages(btns));
        
        // set panes to root pane
        root.setCenter(stackPane);
        root.setBottom(buttons);
    }
    // sorts buttons (images) - WIP - incomplete
    private void sortImages(Button[] btns){
        Stage stage = new Stage();
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: black");
        
        Collections.sort(titleSort, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(dateSort, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(locSort, String.CASE_INSENSITIVE_ORDER);
    }
    // creates new stage where user inputs image information
    private void editImageInfo(Button[] btns, int list_index){
        
        Stage stage = new Stage();  // create new stage
        
        // create Submit button w/styling
        Button btnSubmit = new Button("Submit");
        btnSubmit.setPadding(new Insets(10, 10, 10, 10));
        btnSubmit.setStyle("-fx-font-size: 12pt; -fx-base: black; -fx-text-fill: aqua;"
                + " -fx-effect: dropshadow(gaussian, aqua, 5, 0.5, 1, 1);");
        
        // create new gridpane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: black");
        
        // create labels w/styling - guides user input
        Label imgName = new Label("Image Name: ");
        imgName.setStyle("-fx-text-fill: skyblue; -fx-font-size: 18px; "
                + "-fx-effect: dropshadow(gaussian, aqua, 5, 0.1, 1, 1);");
        Label imgDate = new Label("Date: ");
        imgDate.setStyle("-fx-text-fill: skyblue; -fx-font-size: 18px; "
                + "-fx-effect: dropshadow(gaussian, aqua, 5, 0.1, 1, 1);");
        Label imgDesc = new Label("Description: ");
        imgDesc.setStyle("-fx-text-fill: skyblue; -fx-font-size: 18px; "
                + "-fx-effect: dropshadow(gaussian, aqua, 5, 0.1, 1, 1);");
        Label imgLoc = new Label("Location: ");
        imgLoc.setStyle("-fx-text-fill: skyblue; -fx-font-size: 18px; "
                + "-fx-effect: dropshadow(gaussian, aqua, 5, 0.1, 1, 1);");
        
        // set tooltips for name/description - alerts user of char max
        name.setTooltip(new Tooltip("Max Characters: 100"));
        desc.setTooltip(new Tooltip("Max Characters: 300"));
        
        // add nodes to grid
        grid.add(imgName, 0, 0);
        grid.add(name, 1, 0);
        grid.add(imgDate, 0, 1);
        grid.add(date, 1, 1);
        grid.add(imgDesc, 0, 2);
        grid.add(desc, 1, 2);
        grid.add(imgLoc, 0, 3);
        grid.add(loc, 1, 3);
        grid.add(btnSubmit, 1, 6);
        
        // perform actions in setInfo when Submit button clicked
        btnSubmit.setOnAction(e -> setInfo(btns, list_index, stage));
        
        // create scene - show stage
        Scene editInfoScene = new Scene(grid, 500, 400);
        stage.setTitle("Edit Image Information");
        stage.setScene(editInfoScene);
        stage.show();
    }
    // sets tooltip info for each thumbnail
    private void setInfo(Button[] btns, int list_index, Stage stage){
        
        addTitleTextLimit(name);    // check char length of title
        addDescTextLimit(desc);     // chech char length of description
        
        // strings set to values from user input textfields
        String n = name.getText();
        String da = date.getText();
        String de = desc.getText();
        String l = loc.getText();
        
        titleSort.add(n);
        dateSort.add(da);
        locSort.add(l);
        
        // create and set new tooltip for selected thumb with user's info
        btns[list_index].setTooltip(new Tooltip("Title: " + n + "\n" +
                "Date: " + da + "\n" + "Description: "
                + de + "\n" + "Location: " + l));
        
        // clear textfields
        name.clear();
        date.clear();
        desc.clear();
        loc.clear();
        // close window upon completion
        stage.close();  
    }
    // sets text limit (100 char) on title - chars past limit removed
    public TextField addTitleTextLimit(TextField tf){
        if(tf.getText().length() > titleMaxLength){
            String s = tf.getText().substring(0, titleMaxLength);
            tf.setText(s);
        }
        return tf;  // return shortened text
    }
    // sets text limit (300 char) on description - chars past limit removed
    public TextField addDescTextLimit(TextField tf){
        if(tf.getText().length() > descMaxLength){
            String s = tf.getText().substring(0, descMaxLength);
            tf.setText(s);
        }
        return tf;  // return shortened text
    }
}