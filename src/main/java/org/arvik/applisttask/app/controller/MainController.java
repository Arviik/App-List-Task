package org.arvik.applisttask.app.controller;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import org.arvik.applisttask.MainApp;
import org.arvik.applisttask.modele.*;
import org.arvik.applisttask.repository.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final User user;
    private final ListRepository listRepo = new ListRepository();
    private final StateRepository stateRepo = new StateRepository();
    private final TaskRepository taskRepo = new TaskRepository();
    private final TypeRepository typeRepo = new TypeRepository();
    private final UserRepository userRepo = new UserRepository();
    private List selectedList;
    @FXML private Label accountLabel;
    @FXML private MFXTableView<List> table;
    @FXML private Label listLabel;
    @FXML private Label stateLabel;
    @FXML private Tab taskTab;
    @FXML private Label typeLabel;

    public MainController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountLabel.setText(user.getNom()+" "+user.getPrenom());
        accountLabel.setPrefWidth(accountLabel.getText().length()*10);//5.8441x + 3.02769
        //Todo ajuster la longueur du label afin d'afficher tout le texte de façon dynamique
        accountLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            accountLabel.setPrefWidth(accountLabel.getText().length()*5.8441+3.02769);//5.8441x + 3.02769
        });

        typeParentTypeComboBox.getItems().add(new Type(0,"Aucun",null));
        typeParentTypeComboBox.getItems().addAll(typeRepo.getTypes());
        typeParentTypeComboBox.setConverter(FunctionalStringConverter.to(type -> (type == null) ? "" : type.getLibelle()));
        typeParentTypeComboBox.getSelectionModel().selectIndex(0);

        listListeComboBox.getItems().addAll(listRepo.getListsByUser(user, taskRepo));
        listListeComboBox.setConverter(FunctionalStringConverter.to(list -> (list == null) ? "" : list.getLibelle()));

        listUserComboBox.getItems().addAll(userRepo.getUsers());
        listUserComboBox.setConverter(FunctionalStringConverter.to(user1 -> (user1 == null) ? "" : user1.toString()));

        taskListComboBox.getItems().addAll(listRepo.getListsByUser(user, taskRepo));
        taskListComboBox.setConverter(FunctionalStringConverter.to(list -> (list == null) ? "" : list.getLibelle()));

        taskTypeComboBox.getItems().add(new Type(0,"Aucun",null));
        taskTypeComboBox.getItems().addAll(typeRepo.getTypes());
        taskTypeComboBox.setConverter(FunctionalStringConverter.to(type -> (type == null) ? "" : type.getLibelle()));
        taskTypeComboBox.getSelectionModel().selectIndex(0);
        setupTable();
    }

    private void setupTable() {
        MFXTableColumn<List> libelleColumn = new MFXTableColumn<>("Libelle", true, Comparator.comparing(List::getLibelle));
        MFXTableColumn<List> desColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(List::getDescription));
        MFXTableColumn<List> usersColum = new MFXTableColumn<>("Comptes",true, Comparator.comparing(List::getUsers));
        MFXTableColumn<List> tasksColum = new MFXTableColumn<>("Tâches", true, Comparator.comparing(List::getTasks));

        libelleColumn.setRowCellFactory(list -> new MFXTableRowCell<>(List::getLibelle));
        desColumn.setRowCellFactory(list -> new MFXTableRowCell<>(List::getDescription));
        usersColum.setRowCellFactory(list -> new MFXTableRowCell<>(List::getUsers));
        tasksColum.setRowCellFactory(list -> new MFXTableRowCell<>(List::getTasks));

        table.getTableColumns().addAll(libelleColumn, desColumn, usersColum, tasksColum);
        table.getFilters().addAll(
                new StringFilter<>("Libelle", List::getLibelle),
                new StringFilter<>("Description", List::getDescription),
                new StringFilter<>("Compte", List::getUsers),
                new StringFilter<>("Tâches", List::getTasks)
        );
        table.getItems().addAll(listRepo.getListsByUser(user,taskRepo));
    }


    @FXML private MFXTextField listLibelleField;
    @FXML private MFXTextField listDesField;
    @FXML void onListSubmitButtonClick() throws SQLException {
        List list = listRepo.save(user, new List(
                listLibelleField.getText(),
                listDesField.getText()
        ));
        table.getItems().removeAll();
        table.getItems().addAll(listRepo.getListsByUser(user,taskRepo));
    }

    @FXML private MFXTextField typeLibelleField;
    @FXML private MFXComboBox<Type> typeParentTypeComboBox;
    @FXML void onTypeSubmitButtonClick() throws SQLException {
        Type type = typeRepo.save(new Type(
                typeLibelleField.getText(),
                typeParentTypeComboBox.getSelectedItem()
        ));
        typeParentTypeComboBox.getItems().add(type);
    }

    @FXML private MFXTextField stateLibelleField;
    @FXML void onStateSubmitButtonClick() throws SQLException {
        State state = stateRepo.save(new State(
                stateLibelleField.getText()
        ));
    }

    @FXML private MFXComboBox<User> listUserComboBox;
    @FXML private MFXComboBox<List> listListeComboBox;
    @FXML void onListUserSubmitButtonClick() throws SQLException {
        listRepo.addUserToList(
                listUserComboBox.getSelectedItem(),
                listListeComboBox.getSelectedItem()
        );
    }

    @FXML private MFXDatePicker taskButtoirDate;
    @FXML private MFXDatePicker taskDebutDate;
    @FXML private MFXTextField taskDesField;
    @FXML private MFXTextField taskDifField;
    @FXML private MFXDatePicker taskFinDate;
    @FXML private Label taskLabel;
    @FXML private MFXTextField taskLibelleField;
    @FXML private MFXComboBox<List> taskListComboBox;
    @FXML private MFXComboBox<Type> taskTypeComboBox;
    @FXML void onTaskSubmitButtonClick() throws SQLException {
        Task task = taskRepo.save(new Task(
                taskLibelleField.getText(),
                taskDesField.getText(),
                taskDifField.getText(),
                taskDebutDate.getValue().toString(),
                taskFinDate.getValue().toString(),
                taskButtoirDate.getValue().toString(),
                user,
                taskListComboBox.getSelectedItem(),
                taskTypeComboBox.getSelectedItem()
        ));
        table.getItems().removeAll();
        table.getItems().addAll(listRepo.getListsByUser(user,taskRepo));
    }

    @FXML void onRowClick() {
        //table.getSelectionModel().getSelectedValues();
        System.out.println("Debug");
        System.out.println(table.getSelectionModel().getSelectedValues());
    }

    @FXML void onLogoutButtonClick() {
        MainApp.changeScene("/view/sign_In_Up-view", new Sign_In_UpController());
    }
}