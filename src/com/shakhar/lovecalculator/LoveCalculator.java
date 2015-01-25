package com.shakhar.lovecalculator;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoveCalculator extends Application {

    @Override
    public void start(final Stage primaryStage) {
        GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.CENTER);
        grid1.setHgap(5);
        grid1.setVgap(5);
        grid1.setPadding(new Insets(5, 5, 5, 5));
        RowConstraints authorRow = new RowConstraints();
        authorRow.setPercentHeight(10);
        grid1.getRowConstraints().add(authorRow);
        Text authorText = new Text("This software has been made by:\n Shakhar Dasgupta");
        authorText.setFont(Font.loadFont(this.getClass().getResourceAsStream("LCALLIG.TTF"), 11));
        authorText.setTextAlignment(TextAlignment.CENTER);
        authorText.setOpacity(0.0);
        FadeTransition authorTextAppear = new FadeTransition(Duration.millis(2000), authorText);
        authorTextAppear.setDelay(Duration.millis(5000));
        authorTextAppear.setFromValue(0.0);
        authorTextAppear.setToValue(1.0);
        FadeTransition authorTextDisappear = new FadeTransition(Duration.millis(2000), authorText);
        authorTextDisappear.setDelay(Duration.millis(10000));
        authorTextDisappear.setFromValue(1.0);
        authorTextDisappear.setToValue(0.0);
        SequentialTransition authorTextTransition = new SequentialTransition(authorTextAppear, authorTextDisappear);
        Text title = new Text("Love Calculator");
        title.setFont(Font.loadFont(this.getClass().getResourceAsStream("LCALLIG.TTF"), 25));
        title.setFill(Color.RED);
        final TextField name1 = new TextField();
        name1.setPromptText("First Name");
        Image love = new Image(this.getClass().getResourceAsStream("love.png"));
        ImageView loveView = new ImageView(love);
        final TextField name2 = new TextField();
        name2.setPromptText("Second Name");
        Button calculate = new Button("Calculate");
        calculate.setDefaultButton(true);
        GridPane.setConstraints(authorText, 0, 0, 3, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(title, 0, 1, 3, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(name1, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(loveView, 1, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(name2, 2, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(calculate, 0, 3, 3, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        grid1.getChildren().addAll(authorText, title, name1, loveView, name2, calculate);
        final Scene scene1 = new Scene(grid1, 500, 300);
        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(10, 10, 10, 10));
        final Text loveString = new Text();
        loveString.setFont(Font.loadFont(this.getClass().getResourceAsStream("LCALLIG.TTF"), 20));
        loveString.setFill(Color.RED);
        final Text result = new Text();
        result.setFont(Font.loadFont(this.getClass().getResourceAsStream("LCALLIG.TTF"), 20));
        result.setFill(Color.RED);
        result.setTextAlignment(TextAlignment.CENTER);
        Button back = new Button("Back");
        back.setCancelButton(true);
        GridPane.setConstraints(loveString, 0, 0, 1, 1, HPos.CENTER, VPos.BOTTOM, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(result, 0, 1, 1, 1, HPos.CENTER, VPos.BOTTOM, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(back, 0, 2, 1, 1, HPos.CENTER, VPos.BOTTOM, Priority.ALWAYS, Priority.ALWAYS);
        grid2.getChildren().addAll(loveString, result, back);
        final Scene scene2 = new Scene(grid2, 500, 300);
        final SequentialTransition sequence = new SequentialTransition();
        calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (name1.getLength() != 0 && name2.getLength() != 0) {
                    if (name1.getText().matches("[a-zA-Z ]+") && name2.getText().matches("[a-zA-Z ]+")) {
                        loveString.setText(name1.getText() + " LOVES " + name2.getText());
                        TranslateTransition loveStringTranslate = new TranslateTransition(Duration.millis(2000), loveString);
                        loveStringTranslate.setFromX(primaryStage.getWidth() - loveString.getLayoutBounds().getMinX());
                        loveStringTranslate.setToX(0);
                        RotateTransition loveStringRotate = new RotateTransition(Duration.millis(2000), loveString);
                        loveStringRotate.setFromAngle(360.0);
                        loveStringRotate.setToAngle(0.0);
                        ParallelTransition loveStringAnimation = new ParallelTransition(loveStringTranslate, loveStringRotate);
                        final Animation resultAnimation = new Transition() {
                            {
                                setCycleDuration(Duration.millis(5000));
                            }

                            @Override
                            protected void interpolate(double frac) {
                                result.setText("Love Percentage: \n" + Math.round(calculate(name1.getText(), name2.getText()) * (float) frac) + "%");
                            }
                        };
                        sequence.getChildren().setAll(loveStringAnimation, resultAnimation);
                        sequence.play();
                    } else {
                        result.setText("Please Enter Valid Names");
                    }
                } else {
                    result.setText("Please Enter Both the Names.");
                }
                primaryStage.setScene(scene2);
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                sequence.stop();
                result.setText("");
                loveString.setText("");
                primaryStage.setScene(scene1);
            }
        });
        primaryStage.setTitle("Love Calculator");
        primaryStage.getIcons().add(love);
        primaryStage.setScene(scene1);
        primaryStage.show();
        authorTextTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private int calculate(String name1, String name2) {
        String loveString = name1.toUpperCase() + "LOVES" + name2.toUpperCase();
        String numbers = "";
        for (int i = 0; i < loveString.length(); i++) {
            char ch = loveString.charAt(i);
            if (!loveString.substring(0, i).contains(Character.toString(ch)) && ch != 32) {
                int c = 0;
                for (int j = i; j < loveString.length(); j++) {
                    if (loveString.charAt(j) == ch) {
                        c++;
                    }
                }
                numbers += c;
            }
        }
        while (!(numbers.length() <= 2 || numbers.equals("100"))) {
            String temp = "";
            int len = numbers.length();
            for (int i = 0; i < len / 2; i++) {
                temp += Integer.parseInt(Character.toString(numbers.charAt(i))) + Integer.parseInt(Character.toString(numbers.charAt(len - i - 1)));
            }
            if (len % 2 != 0) {
                temp += numbers.charAt(len / 2);
            }
            numbers = temp;
        }
        return Integer.parseInt(numbers);
    }
}
