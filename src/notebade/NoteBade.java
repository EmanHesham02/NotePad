package notebade;

import com.sun.scenario.effect.impl.Renderer;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class NoteBade extends Application {

    private FileChooser fileChooser = new FileChooser();
    private File file;
    String str;
    List<String> undoable = new ArrayList<String>();
    boolean isFileSaved = false;
    NotebdeGui root;
    String content;

    @Override
    public void start(Stage stage) throws Exception {
        root = new NotebdeGui();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Stack undoStack = new Stack();

        //new textArea................
        root.menuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                if (root.textArea.getText().trim().isEmpty()) {
                    root.textArea.clear();
                } else if (!isFileSaved) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Notepad");
                    alert.setHeaderText("Do you want to save changes befor exit");

                    ButtonType save = new ButtonType("Save");
                    ButtonType dont_Save = new ButtonType("Don't save");
                    ButtonType cancel = new ButtonType("Cancel");
                    // Remove default ButtonTypes
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().addAll(save, dont_Save, cancel);
                    // option != null.
                    Optional<ButtonType> option = alert.showAndWait();
                    //save file............................................
                    if (option.get() == save) {
                        FileChooser fc = new FileChooser();
                        fc.setTitle("Get Text");
                        fc.getExtensionFilters().addAll(
                                new ExtensionFilter("Text Files", "*.txt"),
                                new ExtensionFilter("All Files", "*.*"));
                        File phil = fc.showSaveDialog(stage);
                        if (phil != null) {
                            try (PrintStream ps = new PrintStream(phil)) {
                                ps.print(root.textArea.getText());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        isFileSaved=true;

                    } else if (option.get() == dont_Save) {
                        root.textArea.clear();

                    }
                }else{
                    root.textArea.clear();
                    isFileSaved=false;
                }

            }
        });
        //open file.................
        root.menuItem0.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("choose TextFile");
                fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

                File phil = fc.showOpenDialog(stage);
                if (phil != null) {
                    try {
                        String content = new Scanner(phil).useDelimiter("\\Z").next();

                        root.textArea.setText(content);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }});
        //save text.....................
        root.menuItem1.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                content = root.textArea.getText();
                save(stage);

            }
        });

        //save as...............
        root.menuItem2.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                fileChooser.getExtensionFilters().addAll(
                        new ExtensionFilter("Text Files", "*.txt"),
                        new ExtensionFilter("All Files", "*.*"));
                file = fileChooser.showSaveDialog(stage);

                String content = root.textArea.getText();
                if (file != null) {
                    Stage stage = (Stage) root.textArea.getScene().getWindow();
                    stage.setTitle(file.getName() + "Notepad");
                    try {
                        // if file doesnt exists, then create it
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileWriter fw = new FileWriter(file.getAbsoluteFile());
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        //exit ........................
        root.menuItem3.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (root.textArea.getText().isEmpty()) {
                    System.exit(0);
                } else {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Notepad");
                    alert.setHeaderText("Do you want to save changes befor exit");

                    ButtonType save = new ButtonType("Save");
                    ButtonType dont_Save = new ButtonType("Don't save");
                    ButtonType cancel = new ButtonType("Cancel");
                    // Remove default ButtonTypes
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().addAll(save, dont_Save, cancel);
                    // option != null.
                    Optional<ButtonType> option = alert.showAndWait();
                    //save file............................................
                    if (option.get() == save) {
                        FileChooser fc = new FileChooser();
                        fc.setTitle("Get Text");
                        fc.getExtensionFilters().addAll(
                                new ExtensionFilter("Text Files", "*.txt"),
                                new ExtensionFilter("All Files", "*.*"));
                        File phil = fc.showSaveDialog(stage);
                        if (phil != null) {
                            try (PrintStream ps = new PrintStream(phil)) {
                                ps.print(root.textArea.getText());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (option.get() == dont_Save) {
                        System.exit(0);

                    }

                }
            }
        });

        //undo...........................
        root.menuItem4.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (undoable.size() > 0) {
                    root.textArea.setText(undoable.get(undoable.size() - 1));
                    undoable.remove(undoable.size() - 1);
                    root.textArea.end();

                }
            }

        });

        //cut...................
        root.menuItem5.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                IndexRange selectedText = root.textArea.getSelection();
                StringBuilder sb = new StringBuilder(root.textArea.getText());
                if (selectedText.getStart() != selectedText.getEnd()) {
                    str = root.textArea.getSelectedText();
                    sb.delete(selectedText.getStart(), selectedText.getEnd());
                    root.textArea.setText(sb.toString());
                    root.textArea.end();
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(str), null);
                }
            }
        });
        //copy................................
        root.menuItem6.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                IndexRange selectedText = root.textArea.getSelection();
                StringBuilder sb = new StringBuilder(root.textArea.getText());
                if (selectedText.getStart() != selectedText.getEnd()) {
                    str = root.textArea.getSelectedText();
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(str), null);
                }

            }
        });

        //paste................................
        root.menuItem7.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    String data = Toolkit.getDefaultToolkit().getSystemClipboard()
                            .getData(DataFlavor.stringFlavor).toString();
                    StringBuilder sb = new StringBuilder(root.textArea.getText());
                    IndexRange selectedText = root.textArea.getSelection();

                    if (selectedText.getStart() != selectedText.getEnd()) {
                        sb.delete(selectedText.getStart(), selectedText.getEnd());
                        sb.insert(selectedText.getStart(), data);
                        root.textArea.setText(sb.toString());
                        root.textArea.end();
                    } else {
                        int caretPos = root.textArea.getCaretPosition();
                        sb.insert(caretPos, data);
                        root.textArea.setText(sb.toString());
                        root.textArea.end();
                    }
                    if (undoable.isEmpty()) {
                        undoable.add("");
                    }
                    undoable.add(root.textArea.getText());

                } catch (UnsupportedFlavorException | IOException ex) {
                    Logger.getLogger(NoteBade.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        //select all........................
        root.menuItem8.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.textArea.selectAll();

            }
        });
        //about notepad....................
        root.menuItem9.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("About Notepd");
                alert.setHeaderText(null);
                alert.setContentText("Notepad is a simple text editor for Microsoft "
                        + "Windows and a basic text-editing program which enables "
                        + "computer users to create documents.\n "
                        + "Developed by : Eman Hesham Ahmed");
                alert.showAndWait();

            }
        });

    }

  
    private void save(Stage stage) {
        if (file != null) {
            try {
                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // open a file dialog box

            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Text Files", "*.txt"),
                    new ExtensionFilter("All Files", "*.*"));
            file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                Stage stages = (Stage) root.textArea.getScene().getWindow();
                stages.setTitle(file.getName() + "Notepad");
                try {
                    // if file doesnt exists, then create it
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        isFileSaved = true;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
