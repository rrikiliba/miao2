package GUI;

import Data.Ingredient;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Buttons.Actions;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.IllegalFormatConversionException;

public class Miao2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Image idle = new Image("Files//idle.gif");
    Image love = new Image("Files//love.gif");
    Image error = new Image("Files//error.gif");
    ImageView logo = new ImageView(idle);

    @Override
    public void start(Stage primaryStage) {

        TextArea recipeBox = new TextArea("Let's add something to cook...");
        recipeBox.setEditable(false);

        HBox addBox = new HBox();
        TextField nameField = new TextField("Ingredient");
        nameField.setOnMouseClicked(e->nameField.clear());
        TextField quantityField = new TextField("Quantity");
        quantityField.setOnMouseClicked(e->quantityField.clear());
        Button addButton = new Button("Add");
        addButton.setOnAction(e->{
            try{
                Actions.add(new Ingredient(nameField.getText(), Double.parseDouble(quantityField.getText().split(" ")[0]),quantityField.getText().split(" ")[1]));
                recipeBox.clear();
                recipeBox.appendText(Actions.recipeToString());
                nameField.setText("Ingredient");
            }
            catch(NumberFormatException exception){
                errorPopUp("Invalid Quantity!\nTry putting a number in", exception);
            }
            catch(IndexOutOfBoundsException exception){
                Actions.add(new Ingredient(nameField.getText(), Double.parseDouble(quantityField.getText().split(" ")[0]), ""));
                recipeBox.clear();
                recipeBox.appendText(Actions.recipeToString());
                nameField.setText("Ingredient");
            }
            finally {
                quantityField.setText("Quantity");
            }


        });
        addBox.getChildren().addAll(nameField,quantityField,addButton);

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e->{
            recipeBox.clear();
            removePopUp();
            recipeBox.appendText(Actions.recipeToString());
        });

        Label da = new Label("Da ");
        Label personeA = new Label(" persone a ");
        Label persone = new Label(" persone");
        TextField ogNumField = new TextField("Num");
        ogNumField.setOnMouseClicked(e->ogNumField.clear());
        ogNumField.setMaxWidth(48);
        TextField newNumField = new TextField("Num");
        newNumField.setOnMouseClicked(e->newNumField.clear());
        newNumField.setMaxWidth(48);
        HBox conversionBox = new HBox(da, ogNumField, personeA, newNumField, persone);
        conversionBox.setAlignment(Pos.CENTER);

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e->{
            recipeBox.clear();
            try {
                Actions.convert(Integer.parseInt(ogNumField.getText()), Integer.parseInt(newNumField.getText()));
            }
            catch (NumberFormatException Iforgor){
                errorPopUp("You forgot to set the\nconversion numbers, silly\n..or did you?", Iforgor);
            }
            finally {
                recipeBox.appendText(Actions.recipeToString());
            }
        });

        Button importButton = new Button("Import");
        importButton.setOnAction(e->{
            String filepath = importPopUp();
            if (!filepath.equals("")) {
                int check = Actions.importFromFile(new File(filepath));
                if (check == 0) {
                    recipeBox.clear();
                    recipeBox.appendText(Actions.recipeToString());
                } else if (check == 1)
                    errorPopUp("Entered invalid file path\nCheck twice, cut once amirite?", new FileNotFoundException());
                else if (check == 2)
                    errorPopUp("Text illegally formatted\nEach entry must be:\nIngredient: quantity, (optional)unit.", new IOException());
            }
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e->primaryStage.close());

        HBox bottomBox = new HBox(addBox, importButton, removeButton, convertButton, exitButton);
        bottomBox.setAlignment(Pos.CENTER);

        logo.setPreserveRatio(true);
        logo.setFitHeight(128);
        logo.setOnMouseClicked(e->{
            logo.setImage(love);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(f -> {
                logo.setImage(idle);
            });
            pause.play();
        });

        VBox topBox = new VBox(conversionBox, logo);
        topBox.setAlignment(Pos.TOP_CENTER);
        topBox.setSpacing(24);

        BorderPane mainPane = new BorderPane();
        mainPane.setTop(topBox);
        mainPane.setBottom(bottomBox);
        mainPane.setCenter(recipeBox);

        primaryStage.setTitle(Actions.getTitle());
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(610);
        primaryStage.show();
    }

    public void errorPopUp(String comment, Exception exception){
        logo.setImage(error);

        Label formattedError = new Label(exception.toString() + "\n" + comment);
        formattedError.setAlignment(Pos.TOP_CENTER);
        Button closePopUp = new Button("OK");
        VBox errorBox = new VBox(formattedError, closePopUp);
        errorBox.setAlignment(Pos.TOP_CENTER);
        errorBox.setSpacing(10);
        Stage popUp = new Stage();
        popUp.setTitle("Error");
        popUp.setScene(new Scene(errorBox, 400, 120));
        closePopUp.setOnAction(e -> popUp.close());
        popUp.setAlwaysOnTop(true);
        popUp.showAndWait();

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(f -> logo.setImage(idle));
        pause.play();
    }

    public void removePopUp(){

        String[] strings = Actions.recipeToStringArray();
        if(strings.length!=0) {
            CheckBox[] checkBoxes = new CheckBox[strings.length];
            Label[] labels = new Label[strings.length];
            VBox forLabels = new VBox();
            VBox forCheckBoxes = new VBox();
            for (int i = 0; i < strings.length; i++) {
                labels[i] = new Label(strings[i]);
                checkBoxes[i] = new CheckBox();
                forLabels.getChildren().add(labels[i]);
                forCheckBoxes.getChildren().add(checkBoxes[i]);
            }

            Button closePopUp = new Button("OK");
            HBox removeBoxCenter = new HBox(forLabels, forCheckBoxes);
            removeBoxCenter.setAlignment(Pos.CENTER);
            removeBoxCenter.setSpacing(10);
            VBox removeBox = new VBox(removeBoxCenter, closePopUp);
            removeBox.setAlignment(Pos.CENTER);

            Stage popUp = new Stage();
            popUp.setTitle("Remove Ingredients");
            popUp.setScene(new Scene(removeBox, 400, 200));
            closePopUp.setOnAction(e -> {
                int counter = 0;
                for (int j = 0; j < checkBoxes.length; j++)
                    try {
                        if (checkBoxes[j].isSelected())
                            if (!Actions.remove(j-counter))
                                errorPopUp("Invalid index\nHow did this even happen..?", new IndexOutOfBoundsException());
                            else
                                counter++;
                    } catch (NullPointerException fuckMe) {
                        errorPopUp("I genuinely don't know why this happened\nSorry sis, try again", fuckMe);
                    }
                popUp.close();
            });
            popUp.setAlwaysOnTop(true);
            popUp.showAndWait();
        }
        else{
            errorPopUp("The list is empty!\nYou cannot remove anything", new NullPointerException());
        }
    }

    public String importPopUp(){
        Label infoLabel = new Label("Insert file path");
        TextField input = new TextField("");
        Button closePopUp = new Button("OK");
        VBox importBox = new VBox(infoLabel, input, closePopUp);
        importBox.setSpacing(10);
        importBox.setAlignment(Pos.CENTER);
        Stage popUp = new Stage();
        popUp.setTitle("Import from file");
        popUp.setScene(new Scene(importBox, 400, 120));
        popUp.setAlwaysOnTop(true);
        closePopUp.setOnAction(e-> popUp.close());
        popUp.showAndWait();
        return input.getText();
    }
}