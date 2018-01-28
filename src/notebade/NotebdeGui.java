package notebade;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public  class NotebdeGui extends Pane {

    protected final MenuBar menuBar;
    protected final Menu menu;
    protected final MenuItem menuItem;
    protected final MenuItem menuItem0;
    protected final MenuItem menuItem1;
    protected final MenuItem menuItem2;
    protected final MenuItem menuItem3;
    protected final Menu menu0;
    protected final MenuItem menuItem4;
    protected final MenuItem menuItem5;
    protected final MenuItem menuItem6;
    protected final MenuItem menuItem7;
    protected final MenuItem menuItem8;
    protected final Menu menu1;
    protected final MenuItem menuItem9;
    protected final TextArea textArea;

    public NotebdeGui() {

        menuBar = new MenuBar();
        menu = new Menu();
        menuItem = new MenuItem();
        menuItem0 = new MenuItem();
        menuItem1 = new MenuItem();
        menuItem2 = new MenuItem();
        menuItem3 = new MenuItem();
        menu0 = new Menu();
        menuItem4 = new MenuItem();
        menuItem5 = new MenuItem();
        menuItem6 = new MenuItem();
        menuItem7 = new MenuItem();
        menuItem8 = new MenuItem();
        menu1 = new Menu();
        menuItem9 = new MenuItem();
        textArea = new TextArea();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        menuBar.setPrefHeight(25.0);
        menuBar.setPrefWidth(600.0);

        menu.setMnemonicParsing(false);
        menu.setText("File");

        menuItem.setId("new");
        menuItem.setMnemonicParsing(false);
        menuItem.setText("New          ");

        menuItem0.setMnemonicParsing(false);
        menuItem0.setText("Open");

        menuItem1.setMnemonicParsing(false);
        menuItem1.setText("save");

        menuItem2.setMnemonicParsing(false);
        menuItem2.setText("Save As");

        menuItem3.setMnemonicParsing(false);
        menuItem3.setText("Exit");

        menu0.setMnemonicParsing(false);
        menu0.setText("Edit");

        menuItem4.setMnemonicParsing(false);
        menuItem4.setText("Undo       ctrl+z");
       

        menuItem5.setMnemonicParsing(false);
        menuItem5.setText("Cut          ctrl+x");

        menuItem6.setMnemonicParsing(false);
        menuItem6.setText("Copy       ctrl+c");

        menuItem7.setMnemonicParsing(false);
        menuItem7.setText("Paste       ctrl+v");

        menuItem8.setMnemonicParsing(false);
        menuItem8.setText("SelectAll ctrl+A");

        menu1.setMnemonicParsing(false);
        menu1.setText("Help");

        menuItem9.setMnemonicParsing(false);
        menuItem9.setText("About Nodebad");

        textArea.setLayoutY(25.0);
        textArea.setPrefHeight(374.0);
        textArea.setPrefWidth(600.0);

        menu.getItems().add(menuItem);
        menu.getItems().add(menuItem0);
        menu.getItems().add(menuItem1);
        menu.getItems().add(menuItem2);
        menu.getItems().add(menuItem3);
        menuBar.getMenus().add(menu);
        menu0.getItems().add(menuItem4);
        menu0.getItems().add(menuItem5);
        menu0.getItems().add(menuItem6);
        menu0.getItems().add(menuItem7);
        menu0.getItems().add(menuItem8);
        menuBar.getMenus().add(menu0);
        menu1.getItems().add(menuItem9);
        menuBar.getMenus().add(menu1);
        getChildren().add(menuBar);
        getChildren().add(textArea);

    }
}
