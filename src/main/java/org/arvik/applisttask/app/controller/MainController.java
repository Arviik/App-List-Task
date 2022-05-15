package org.arvik.applisttask.app.controller;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.arvik.applisttask.MainApp;
import org.arvik.applisttask.modele.*;
import org.arvik.applisttask.repository.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final User user;
    private final ListRepository listRepo = new ListRepository();
    private List selectedList;
    private boolean listSwitch;
    private final StateRepository stateRepo = new StateRepository();
    private State selectedState;
    private boolean stateSwitch;
    private final TaskRepository taskRepo = new TaskRepository();
    private Task selectedTask;
    private boolean taskSwitch;
    private final TypeRepository typeRepo = new TypeRepository();
    private Type selectedType;
    private boolean typeSwitch;
    private final UserRepository userRepo = new UserRepository();
    @FXML private Label accountLabel;

    public MainController(User user) {
        this.user = user;
        listSwitch = false;
        stateSwitch = false;
        taskSwitch = false;
        typeSwitch = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAccountLabel();
        setupList();
        setupTask();
        setupState();
        setupType();
    }

    private void setupAccountLabel() {
        accountLabel.setText(user.getNom()+" "+user.getPrenom());
        accountLabel.setPrefWidth(accountLabel.getText().length()*10);//5.8441x + 3.02769
        //Todo ajuster la longueur du label afin d'afficher tout le texte de façon dynamique
        accountLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            accountLabel.setPrefWidth(accountLabel.getText().length()*5.8441+3.02769);//5.8441x + 3.02769
        });
    }

    private void setupList() {
        MFXTableColumn<List> libelleColumn = new MFXTableColumn<>("Libelle", true, Comparator.comparing(List::getLibelle));
        MFXTableColumn<List> desColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(List::getDescription));
        MFXTableColumn<List> usersColum = new MFXTableColumn<>("Comptes",true, Comparator.comparing(List::getUsers));
        MFXTableColumn<List> tasksColum = new MFXTableColumn<>("Tâches", true, Comparator.comparing(List::getTasks));

        libelleColumn.setRowCellFactory(list -> new MFXTableRowCell<>(List::getLibelle));
        desColumn.setRowCellFactory(list -> new MFXTableRowCell<>(List::getDescription));
        usersColum.setRowCellFactory(list -> new MFXTableRowCell<>(List::getUsers));
        tasksColum.setRowCellFactory(list -> new MFXTableRowCell<>(List::getTasks));

        listTable.getTableColumns().add(libelleColumn);
        listTable.getTableColumns().add(desColumn);
        listTable.getTableColumns().add(usersColum);
        listTable.getTableColumns().add(tasksColum);

        listTable.getFilters().add(new StringFilter<>("Libelle", List::getLibelle));
        listTable.getFilters().add(new StringFilter<>("Description", List::getDescription));
        listTable.getFilters().add(new StringFilter<>("Compte", List::getUsers));
        listTable.getFilters().add(new StringFilter<>("Tâches", List::getTasks));

        listTable.getItems().addAll(listRepo.getListsByUser(user));

        listListeComboBox.getItems().addAll(listRepo.getListsByUser(user));
        listListeComboBox.setConverter(FunctionalStringConverter.to(list -> (list == null) ? "" : list.getLibelle()));

        listUserComboBox.getItems().addAll(userRepo.getUsers());
        listUserComboBox.setConverter(FunctionalStringConverter.to(user1 -> (user1 == null) ? "" : user1.toString()));
    }

    private void setupTask() {
        MFXTableColumn<Task> libelleColumn = new MFXTableColumn<>("Libelle", true, Comparator.comparing(Task::getLibelle));
        MFXTableColumn<Task> desColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(Task::getDescription));
        MFXTableColumn<Task> difColumn = new MFXTableColumn<>("Difficulté", true, Comparator.comparing(Task::getDifficulte));
        MFXTableColumn<Task> date_debutColumn = new MFXTableColumn<>("Date début", true, Comparator.comparing(Task::getDate_debut));
        MFXTableColumn<Task> date_finColumn = new MFXTableColumn<>("Date fin", true, Comparator.comparing(Task::getDate_fin));
        MFXTableColumn<Task> date_butoirColumn = new MFXTableColumn<>("Date butoir", true, Comparator.comparing(Task::getDate_butoir));
        MFXTableColumn<Task> auteurColumn = new MFXTableColumn<>("Auteur", true, Comparator.comparing(Task::getRef_compteToString));
        MFXTableColumn<Task> listeColumn = new MFXTableColumn<>("Liste", true, Comparator.comparing(Task::getRef_listeLibelle));
        MFXTableColumn<Task> etatColumn = new MFXTableColumn<>("Etat", true, Comparator.comparing(Task::getRef_etatEtat));
        MFXTableColumn<Task> typeColumn = new MFXTableColumn<>("Type", true, Comparator.comparing(Task::getRef_typeLibelle));

        libelleColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getLibelle));
        desColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getDescription));
        difColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getDifficulte));
        date_debutColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getDate_debut));
        date_finColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getDate_fin));
        date_butoirColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getDate_butoir));
        auteurColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getRef_compteToString));
        listeColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getRef_listeLibelle));
        etatColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getRef_etatEtat));
        typeColumn.setRowCellFactory(task -> new MFXTableRowCell<>(Task::getRef_typeLibelle));

        taskTable.getTableColumns().add(libelleColumn);
        taskTable.getTableColumns().add(desColumn);
        taskTable.getTableColumns().add(difColumn);
        taskTable.getTableColumns().add(date_debutColumn);
        taskTable.getTableColumns().add(date_finColumn);
        taskTable.getTableColumns().add(date_butoirColumn);
        taskTable.getTableColumns().add(auteurColumn);
        taskTable.getTableColumns().add(listeColumn);
        taskTable.getTableColumns().add(etatColumn);
        taskTable.getTableColumns().add(typeColumn);

        taskTable.getFilters().add(new StringFilter<>("Libelle", Task::getLibelle));
        taskTable.getFilters().add(new StringFilter<>("Description", Task::getDescription));
        taskTable.getFilters().add(new StringFilter<>("Difficulté", Task::getDifficulte));
        taskTable.getFilters().add(new StringFilter<>("Date début", Task::getDate_debut));
        taskTable.getFilters().add(new StringFilter<>("Date fin", Task::getDate_fin));
        taskTable.getFilters().add(new StringFilter<>("Date butoir", Task::getDate_butoir));
        taskTable.getFilters().add(new StringFilter<>("Auteur", Task::getRef_compteToString));
        taskTable.getFilters().add(new StringFilter<>("Liste", Task::getRef_listeLibelle));
        taskTable.getFilters().add(new StringFilter<>("Etat", Task::getRef_etatEtat));
        taskTable.getFilters().add(new StringFilter<>("Type", Task::getRef_typeLibelle));

        taskTable.getItems().addAll(taskRepo.getTasksByLists(listRepo.getListsByUser(user)));

        taskListComboBox.getItems().addAll(listRepo.getListsByUser(user));
        taskListComboBox.setConverter(FunctionalStringConverter.to(list -> (list == null) ? "" : list.getLibelle()));

        taskTypeComboBox.getItems().add(new Type(0,"Aucun",0));
        taskTypeComboBox.getItems().addAll(typeRepo.getTypes());
        taskTypeComboBox.setConverter(FunctionalStringConverter.to(type -> (type == null) ? "" : type.getLibelle()));
        taskTypeComboBox.getSelectionModel().selectIndex(0);
    }

    private void setupState() {
        MFXTableColumn<State> etatColumn = new MFXTableColumn<>("Etat", true, Comparator.comparing(State::getEtat));
        etatColumn.setRowCellFactory(state -> new MFXTableRowCell<>(State::getEtat));
        stateTable.getTableColumns().add(etatColumn);
        stateTable.getFilters().add(new StringFilter<>("Etat", State::getEtat));
        stateTable.getItems().addAll(stateRepo.getStates());
    }

    private void setupType() {
        MFXTableColumn<Type> libelleColumn = new MFXTableColumn<>("Libelle", true, Comparator.comparing(Type::getLibelle));
        MFXTableColumn<Type> typeParentColumn = new MFXTableColumn<>("Type Parent", true, Comparator.comparing(Type::getRef_typeLibelle));

        libelleColumn.setRowCellFactory(type -> new MFXTableRowCell<>(Type::getLibelle));
        typeParentColumn.setRowCellFactory(type -> new MFXTableRowCell<>(Type::getRef_typeLibelle));

        typeTable.getTableColumns().add(libelleColumn);
        typeTable.getTableColumns().add(typeParentColumn);

        typeTable.getFilters().add(new StringFilter<>("Libelle", Type::getLibelle));
        typeTable.getFilters().add(new StringFilter<>("Type Parent", Type::getRef_typeLibelle));

        typeTable.getItems().addAll(typeRepo.getTypes());

        typeParentTypeComboBox.getItems().add(new Type(0,"Aucun",0));
        typeParentTypeComboBox.getItems().addAll(typeRepo.getTypes());
        typeParentTypeComboBox.setConverter(FunctionalStringConverter.to(type -> (type == null) ? "" : type.getLibelle()));
        typeParentTypeComboBox.getSelectionModel().selectIndex(0);
    }

    /**
     * List
     **/
    @FXML private MFXTableView<List> listTable;
    @FXML private Label listLabel;
    @FXML private MFXComboBox<User> listUserComboBox;
    @FXML private MFXComboBox<List> listListeComboBox;
    @FXML void onListUserSubmitButtonClick() throws SQLException {
        listRepo.addUserToList(
                listUserComboBox.getSelectedItem(),
                listListeComboBox.getSelectedItem()
        );
        listTable.getItems().clear();
        listTable.getItems().addAll(listRepo.getListsByUser(user));
    }
    @FXML private MFXTextField listLibelleField;
    @FXML private MFXTextField listDesField;
    @FXML void onListSubmitButtonClick() throws SQLException {
        List list = listRepo.save(user, new List(
                listLibelleField.getText(),
                listDesField.getText()
        ));
        listTable.getItems().add(list);
    }

    /**
     * Task
     **/

    @FXML private MFXDatePicker taskButtoirDate;
    @FXML private MFXDatePicker taskDebutDate;
    @FXML private MFXTextField taskDesField;
    @FXML private MFXTextField taskDifField;
    @FXML private MFXDatePicker taskFinDate;
    @FXML private Label taskLabel;
    @FXML private MFXTextField taskLibelleField;
    @FXML private MFXComboBox<List> taskListComboBox;
    @FXML private MFXComboBox<Type> taskTypeComboBox;
    @FXML private MFXTableView<Task> taskTable;
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
        ArrayList<List> lists = listRepo.getListsByUser(user);

        listTable.getItems().clear();
        listTable.getItems().addAll(lists);

        taskTable.getItems().clear();
        taskTable.getItems().addAll(taskRepo.getTasksByLists(lists));
    }

    /**
     * State
     **/
    @FXML private MFXTableView<State> stateTable;
    @FXML private MFXTextField stateLibelleField;
    @FXML private Label stateLabel;
    @FXML void onStateSubmitButtonClick() throws SQLException {
        State state = stateRepo.save(new State(
                stateLibelleField.getText()
        ));
    }

    /**
     * Type
     **/
    @FXML private MFXTableView<Type> typeTable;
    @FXML private Label typeLabel;
    @FXML private MFXTextField typeLibelleField;
    @FXML private MFXComboBox<Type> typeParentTypeComboBox;
    @FXML private MFXButton typeSubmitButton;
    @FXML void onTypeSubmitButtonClick() throws SQLException {
        Type type = typeRepo.save(new Type(
                typeLibelleField.getText(),
                typeParentTypeComboBox.getSelectedItem()
        ));
        typeTable.getItems().clear();
        typeTable.getItems().addAll(typeRepo.getTypes());

        typeParentTypeComboBox.getItems().add(type);
    }

    @FXML void onTypeSwitchButtonClick() {
        if (typeSwitch){
            typeSwitch = false;
            typeLabel.setText("Ajouter un type :");
            typeLibelleField.setText("");
        } else {
            typeSwitch = true;
            typeLabel.setText("Modifier un type :");
        }
    }

    /**
     * Others
     **/
    @FXML void onRowClick() {
        System.out.println(listTable.getSelectionModel().getSelection());
    }

    @FXML void onLogoutButtonClick() {
        MainApp.changeScene("/view/sign_In_Up-view", new Sign_In_UpController());
    }
    //TODO permettre la modification et la suppresion
}