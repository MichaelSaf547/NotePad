/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author AL Lewaa Company
 */
public class NotePad extends Application {
    
    MenuBar bar;
    Menu File;
    Menu Edit;
    Menu Help;
    BorderPane b;
    SeparatorMenuItem SepFE;
    SeparatorMenuItem SepEU;
    SeparatorMenuItem SepES;
    
    MenuItem New;
    MenuItem Open;
    MenuItem Save;
    MenuItem Exit;
    MenuItem Undo;
    MenuItem Cut;
    MenuItem Copy;
    MenuItem Paste;
    MenuItem Delete;
    MenuItem Select_All;
    MenuItem About;
    MenuItem Compile_Run;
    
    
    TextArea Area;
    VBox vbox;
    
    FileChooser File_ChooserS;
    FileChooser File_ChooserO;
    
    int heigth = 500;
    int width = 500;
    int Save_Flag = 1;
    int start, end;
    
    String StringS = "";
    String location = "";
    
    @Override
    public void init()
    {
        File_ChooserS = new FileChooser();
        File_ChooserS.setTitle("Save File");
        File_ChooserO = new FileChooser();
        File_ChooserO.setTitle("Open File");
        File_ChooserS.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
        
        //Create a Border Pane 
        b = new BorderPane();
                 
                 
        //Create a menu bar
        bar = new MenuBar();
        
        
        //Create three menus
        File = new Menu("File");
        Edit = new Menu("Edit");
        Help = new Menu("Help");
        
        //File Items
        New = new MenuItem("New");
        New.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
        
        Open = new MenuItem("Open");
        Open.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        
        Save = new MenuItem("Save");
        Save.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        
        Exit = new MenuItem("Exit");
        Exit.setAccelerator(KeyCombination.keyCombination("ESC"));
    
        
        //Edit Items
        Undo = new MenuItem("Undo");
        Undo.setAccelerator(KeyCombination.keyCombination("Ctrl+z"));
        
        Cut = new MenuItem("Cut");
        Cut.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));
        
        Copy = new MenuItem("Copy");
        Copy.setAccelerator(KeyCombination.keyCombination("Ctrl+c"));
        
        Paste = new MenuItem("Paste");
        Paste.setAccelerator(KeyCombination.keyCombination("Ctrl+v"));
        
        Delete = new MenuItem("Delete");
        
        Select_All = new MenuItem("Select All");
        Select_All.setAccelerator(KeyCombination.keyCombination("ctrl+a"));
        
        //Help Items
        
        About = new MenuItem("About");
        Compile_Run = new MenuItem("Compile and Run");
        
        //add Items to each menu:
        File.getItems().addAll(New, Open, Save, Exit);
        Edit.getItems().addAll(Undo, Cut, Copy, Paste, Delete, Select_All);
        Help.getItems().addAll(About, Compile_Run);
        
        //Create a Separators
        SepFE = new SeparatorMenuItem();
        SepEU = new SeparatorMenuItem();
        SepES = new SeparatorMenuItem();
        
        //Add a separator for the file menu
        File.getItems().add(3, SepFE);
        
        
        //Add a separator for the Edit menu
        Edit.getItems().add(1, SepEU);
        Edit.getItems().add(6, SepES);
        
       
        
        //Add menus to the bar
        bar.getMenus().addAll(File, Edit, Help);
        
        //Add bar to the Pane
        b.setTop(bar);
        
        
        Area = new TextArea();
        
        Area.setPrefHeight(heigth);
        Area.setPrefWidth(width);
        Area.setPrefColumnCount(20);
        
        vbox = new VBox();
        vbox.getChildren().addAll(b, Area);
        
        
        
    }
    
    
    
    @Override
    public void start(Stage primaryStage) {
        
        Dialog<String> DC = new Dialog<String>();
        DC.setTitle("Close");
        DC.setContentText("The data is not saved do you want to save it?");
        ButtonType YesC = new ButtonType("Yes");
        ButtonType NoC = new ButtonType("No");
        
        DC.getDialogPane().getButtonTypes().addAll(YesC, NoC);
        
        
        Dialog<String> DA = new Dialog<String>();
        DA.setTitle("About");
        DA.setContentText("This project is created by Michael Safwat.");
        ButtonType OkA = new ButtonType("Ok");
        
        DA.getDialogPane().getButtonTypes().addAll(OkA);
        
        Scene scene = new Scene(vbox, width, heigth);
        
        
        primaryStage.setTitle("Notepad");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
        New.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                if(Save_Flag == 0)
                {
                    
                    DC.setResultConverter(ButtonType::getText);
                    String result = DC.showAndWait().orElse(null);
                    
                    if(result.equals("Yes"))
                    {
                        FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                        File_ChooserS.getExtensionFilters().add(extFilter);

                        //Show save file dialog
                        File file = File_ChooserS.showSaveDialog(primaryStage);

                        if(file != null)
                        {
                            FileWriter fileWriter = null;
                            try {
                                fileWriter = new FileWriter(file);
                            } catch (IOException ex) {
                                Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                fileWriter.write(Area.getText());
                            } catch (IOException ex) {
                                Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                fileWriter.close();
                            } catch (IOException ex) {
                                Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        Area.clear();
                    }
                    else
                    {
                        Area.clear();
                    }
                }
                else
                {
                    Area.clear();
                }
            }
        
            
        });
        
        Open.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                
                location = File_ChooserS.showOpenDialog(primaryStage).toString();
                try {
                        Scanner s = new Scanner(new File(location)).useDelimiter("\\s+");
                        while (s.hasNextLine()) 
                        {
                         // check if next token is an int
                          Area.appendText(s.nextLine() + "\n"); // display the found integer
                        }
                    } 
                catch (FileNotFoundException ex) {
                    System.err.println(ex);
                }
            }
    
        });
        
        Save.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                File_ChooserS.getExtensionFilters().add(extFilter);
            
                //Show save file dialog
                File file = File_ChooserS.showSaveDialog(primaryStage);
                
                if(file != null)
                {
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter(file);
                    } catch (IOException ex) {
                        Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        fileWriter.write(Area.getText());
                    } catch (IOException ex) {
                        Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        fileWriter.close();
                    } catch (IOException ex) {
                        Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Save_Flag = 1; 
            }
        
        });
        
        Exit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(Save_Flag == 0)
                {
                    
                    DC.setResultConverter(ButtonType::getText);
                    String result = DC.showAndWait().orElse(null);
                    
                    if(result.equals("Yes"))
                    {
                        FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                        File_ChooserS.getExtensionFilters().add(extFilter);

                        //Show save file dialog
                        File file = File_ChooserS.showSaveDialog(primaryStage);

                        if(file != null)
                        {
                            FileWriter fileWriter = null;
                            try {
                                fileWriter = new FileWriter(file);
                            } catch (IOException ex) {
                                Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                fileWriter.write(Area.getText());
                            } catch (IOException ex) {
                                Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                fileWriter.close();
                            } catch (IOException ex) {
                                Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        primaryStage.close();
                    }
                    else
                    {
                        primaryStage.close();
                    }
                }
                else
                {
                    primaryStage.close();
                }
                
            }
        
        });
        
        
        Undo.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Area.undo();
                Save_Flag = 0;
            }
        
        });
        
        Cut.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                StringS = Area.getSelectedText();
                Area.cut();
                Save_Flag = 0;
            }
        
        });
        
        Copy.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                StringS = Area.getSelectedText();
                Area.copy();
                Save_Flag = 0;
            }
        
        });
        
        
        Paste.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                int pos = Area.getCaretPosition();
                Area.insertText(pos, StringS);
                Save_Flag = 0;
            }
        
        });
        
        Delete.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                IndexRange range = new IndexRange(0, 0);
                range = Area.getSelection();
                if(range.getLength() == 0)
                {
                    Area.deletePreviousChar();
                }
                else
                {
                    Area.deleteText(range);
                }
            }
        
        });
        
        Select_All.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Area.selectAll();
            }
        
        });
        
        
        About.setOnAction(new EventHandler<ActionEvent>(){
           
            @Override
            public void handle(ActionEvent event) {
                DA.showAndWait();
            }
        });
        
        Compile_Run.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Area.getText();
            }
        
        });
        
        
        
    }

    
    
}
