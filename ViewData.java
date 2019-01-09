import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewData extends Application {
    private ObservableList<Item> data;
    private TableView<Item> table;

    private void loadData() throws SQLException {
        data = FXCollections.observableArrayList();
        ResultSet rs = SQLTest.getAllData();
        while (rs.next()) {
            Item i = new Item(rs.getInt("uniqueID"),
                    rs.getString("type"),
                    rs.getInt("quantity"),
                    rs.getString("manufacturer"),
                    rs.getDouble("price"),
                    rs.getString("note"));
            data.add(i);
        }
        table.setItems(data);
    }

    private void loadData(ResultSet rs) throws SQLException {
        data = FXCollections.observableArrayList();
        while (rs.next()) {
            Item i = new Item(rs.getInt("uniqueID"),
                    rs.getString("type"),
                    rs.getInt("quantity"),
                    rs.getString("manufacturer"),
                    rs.getDouble("price"),
                    rs.getString("note"));
            data.add(i);
        }
        table.setItems(data);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        // Initial Set Up
        table = new TableView<>();
        TableColumn<Item, Integer> uniqueIDColumn = new TableColumn<>("ID");
        TableColumn<Item, String> typeColumn = new TableColumn<>("Type");
        TableColumn<Item, String> manufacturerColumn = new TableColumn<>("Manufacturer");
        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<Item, String> noteColumn = new TableColumn<>("Note");

        uniqueIDColumn.setCellValueFactory(new PropertyValueFactory<>("uniqueID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        table.getColumns().addAll(
                uniqueIDColumn,
                typeColumn,
                manufacturerColumn,
                priceColumn,
                quantityColumn,
                noteColumn
        );

        loadData();

        primaryStage.setTitle("View Data");

        // Designing GUI
        VBox itemData = new VBox();
        itemData.setSpacing(10);
        itemData.setPadding(new Insets(20));

        // Right-side Pane -- Search and Table Pane
        VBox rightPane = new VBox();

        HBox uniqueIDSearchPane = new HBox();
        Label uniqueIDVal = new Label("ID: ");
        TextField uniqueIDSearch = new TextField();
        uniqueIDSearchPane.getChildren().addAll(uniqueIDVal, uniqueIDSearch);

        HBox typeSearchPane = new HBox();
        Label typeVal = new Label("Type: ");
        TextField typeSearch = new TextField();
        typeSearchPane.getChildren().addAll(typeVal, typeSearch);

        HBox manufacturerSearchPane = new HBox();
        Label manufacturerVal = new Label("Manufacturer: ");
        TextField manufacturerSearch = new TextField();
        manufacturerSearchPane.getChildren().addAll(manufacturerVal, manufacturerSearch);

        HBox noteSearchPane = new HBox();
        Label noteVal = new Label("Note: ");
        TextField noteSearch = new TextField();
        noteSearchPane.getChildren().addAll(noteVal, noteSearch);

        VBox priceSearchPane = new VBox();
        Label priceVal = new Label("Price: ");
        TextField priceSearch = new TextField();
        RadioButton lessThan = new RadioButton("Less Than");
        RadioButton greaterThan = new RadioButton("Greater Than");
        ToggleGroup priceToggle = new ToggleGroup();
        lessThan.setToggleGroup(priceToggle);
        greaterThan.setToggleGroup(priceToggle);
        priceSearchPane.getChildren().addAll(priceVal, lessThan, greaterThan, priceSearch);

        VBox quantitySearchPane = new VBox();
        Label quantityVal = new Label("Price: ");
        TextField quantitySearch = new TextField();
        RadioButton lessThanQuan = new RadioButton("Less Than");
        RadioButton greaterThanQuan = new RadioButton("Greater Than");
        ToggleGroup quantityToggle = new ToggleGroup();
        lessThanQuan.setToggleGroup(quantityToggle);
        greaterThanQuan.setToggleGroup(quantityToggle);
        quantitySearchPane.getChildren().addAll(quantityVal, lessThanQuan, greaterThanQuan, quantitySearch);

        // Left-side Pane -- Item Data Pane
        HBox uniqueIDData = new HBox();
        Label uniqueID = new Label("ID: ");
        TextField uniqueIDField = new TextField();
        uniqueIDData.getChildren().addAll(uniqueID, uniqueIDField);

        HBox typeData = new HBox();
        Label type = new Label("Type: ");
        TextField typeField = new TextField();
        typeData.getChildren().addAll(type, typeField);

        HBox manufacturerData = new HBox();
        Label manufacturer = new Label("Manufacturer: ");
        TextField manufacturerField = new TextField();
        manufacturerData.getChildren().addAll(manufacturer, manufacturerField);

        HBox priceData = new HBox();
        Label price = new Label("Price: ");
        TextField priceField = new TextField();
        priceData.getChildren().addAll(price, priceField);

        HBox quantityData = new HBox();
        Label quantity = new Label("Quantity: ");
        TextField quantityField = new TextField();
        quantityData.getChildren().addAll(quantity, quantityField);

        HBox noteData = new HBox();
        Label note = new Label("Note: ");
        TextArea noteField = new TextArea();
        noteData.getChildren().addAll(note, noteField);

        // Functionality -- Action Listeners
        Button searchButton = new Button("Search");
        Label searchLabel = new Label("Search: ");
        searchLabel.setFont(new Font("Arial", 20));
        VBox searchPane = new VBox();
        searchPane.setPadding(new Insets(20));
        searchPane.setSpacing(20);
        searchPane.getChildren().addAll(
                searchLabel,
                uniqueIDSearchPane,
                typeSearchPane,
                manufacturerSearchPane,
                priceSearchPane,
                quantitySearchPane,
                noteSearchPane,
                searchButton
        );

        rightPane.getChildren().addAll(table, searchPane);

        Button submit = new Button("Add/Modify");
        HBox submitPane = new HBox();
        submitPane.setAlignment(Pos.CENTER);
        submitPane.getChildren().add(submit);

        submit.setOnAction(e -> {
            if (uniqueIDField.getText() == null) {
                throw new IllegalArgumentException();
            }
            try {
                SQLTest.update(new Item(
                        Integer.parseInt(uniqueIDField.getText()),
                        typeField.getText(),
                        Integer.parseInt(quantityField.getText()),
                        manufacturerField.getText(),
                        Double.parseDouble(priceField.getText()),
                        noteField.getText()
                ));
                loadData();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            uniqueIDField.setText(null);
            typeField.setText(null);
            manufacturerField.setText(null);
            priceField.setText(null);
            noteField.setText(null);
            quantityField.setText(null);
        });

        table.setOnMouseClicked(e -> {
            Item selected = table.getSelectionModel().getSelectedItem();
            try {
                uniqueIDField.setText(selected.getUniqueID() + "");
                typeField.setText(selected.getType());
                manufacturerField.setText(selected.getType());
                priceField.setText(selected.getPrice() + "");
                quantityField.setText(selected.getQuantity() + "");
                noteField.setText(selected.getNote());
            } catch (Exception ex) {
                // Do Nothing
            }

        });

        searchButton.setOnAction(e -> {

        });

        uniqueIDSearch.setOnKeyTyped(e -> {
            try {
                loadData(SQLTest.getUniqueIDData(Integer.parseInt(uniqueIDSearch.getText())));
            } catch (Exception ex) {
                uniqueIDSearch.setText("");
            }
        });

        uniqueIDSearch.setOnMouseClicked(e -> {
            typeSearch.setText("");
            manufacturerSearch.setText("");
            priceSearch.setText("");
            quantitySearch.setText("");
            noteSearch.setText("");
        });

        typeSearch.setOnKeyTyped(e -> {
            try {
                loadData(SQLTest.getTypeData(typeSearch.getText()));
            } catch (Exception ex) {
                typeSearch.setText("");
            }
        });

        typeSearch.setOnMouseClicked(e -> {
            uniqueIDSearch.setText("");
            manufacturerSearch.setText("");
            priceSearch.setText("");
            quantitySearch.setText("");
            noteSearch.setText("");
        });

        manufacturerSearch.setOnKeyTyped(e -> {
            try {
                loadData(SQLTest.getManufacturerData(manufacturerSearch.getText()));
            } catch (Exception ex) {
                manufacturerSearch.setText("");
            }
        });

        manufacturer.setOnMouseClicked(e -> {
            uniqueIDSearch.setText("");
            typeSearch.setText("");
            priceSearch.setText("");
            quantitySearch.setText("");
            noteSearch.setText("");
        });

        noteSearch.setOnKeyTyped(e -> {
            try {
                loadData(SQLTest.getNoteData(noteSearch.getText()));
            } catch (Exception ex) {
                noteSearch.setText("");
            }
        });

        noteSearch.setOnMouseClicked(e -> {
            uniqueIDSearch.setText("");
            manufacturerSearch.setText("");
            priceSearch.setText("");
            quantitySearch.setText("");
            typeSearch.setText("");
        });

        priceSearch.setOnAction(e -> {
            uniqueIDSearch.setText("");
            manufacturerSearch.setText("");
            quantitySearch.setText("");
            typeSearch.setText("");
            noteSearch.setText("");
            try {
                loadData(SQLTest.getPriceData(Double.parseDouble(priceSearch.getText()), lessThan.isSelected()));
            } catch (Exception ex) {
                priceSearch.setText("");
            }
        });

        quantitySearch.setOnAction(e -> {
            uniqueIDSearch.setText("");
            manufacturerSearch.setText("");
            priceSearch.setText("");
            typeSearch.setText("");
            noteSearch.setText("");
            try {
                loadData(SQLTest.getQuantityData(Double.parseDouble(quantitySearch.getText()), lessThan.isSelected()));
            } catch (Exception ex) {
                quantitySearch.setText("");
            }
        });

        itemData.getChildren().addAll(
                uniqueIDData,
                typeData,
                manufacturerData,
                priceData,
                quantityData,
                noteData,
                submitPane);

        VBox leftPane = new VBox();
        Label heading = new Label("Item Data:");
        heading.setFont(new Font("Arial", 20));
        heading.setPadding(new Insets(20));
        leftPane.getChildren().addAll(heading, itemData);
        HBox fullPane = new HBox();
        fullPane.setSpacing(50);
        fullPane.getChildren().addAll(leftPane, rightPane);
        Scene scene = new Scene(fullPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
