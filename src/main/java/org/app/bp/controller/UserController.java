package org.app.bp.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.app.bp.models.Poste;
import org.app.bp.models.Users;
import org.app.bp.services.UsersServices;
import org.app.bp.utils.Utils;
import org.controlsfx.control.MaskerPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private Button btn_retour;

    @FXML
    private TableView<Users> table_users;

    @FXML
    private TableColumn<Users, Integer> COL_USERS_ID;

    @FXML
    private TableColumn<Users, String> COL_USERS_NOM;

    @FXML
    private TableColumn<Users, String> COL_USERS_PRENOM;

    @FXML
    private TableColumn<Users, String> COL_USERS_SITES;

    @FXML
    private TableColumn<Users, String> COL_USERS_CONTACT;

    @FXML
    private TableColumn<Users, String> COL_USERS_ROLE;

    @FXML
    private TableColumn<Users, String> COL_USERS_MATRICULE;

    @FXML
    private TableColumn<Users, String> COL_USERS_USERNAME;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_contact;

    @FXML
    private ComboBox cbx_sites;

    @FXML
    private ComboBox cbx_role;

    @FXML
    private TextField txt_matricule;

    @FXML
    private MaskerPane table_loading;

    Utils appUtils = new Utils();

    UsersServices usersServices = new UsersServices();

    @FXML
    private Button btn_enregistrer;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController){
        this.dashboardController = dashboardController;
    }

    public boolean handleValidateForm(){
        if (txt_nom.getText().isEmpty() || txt_prenom.getText().isEmpty() || txt_username.getText().isEmpty() || txt_matricule.getText().isEmpty()){
            appUtils.warningAlertDialog("AVERTISSEMENT","VEUILLEZ COMPLETEZ TOUS LES CHAMPS");
            return false;
        }
        return true;
    }

    @FXML
    private void retourVersPremiereScene(ActionEvent event) throws IOException {
        // Utiliser la référence au contrôleur précédent pour revenir à la première scène
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home/dashboard.fxml"));
        Parent premiereSceneParent = loader.load();
        Scene premiereScene = new Scene(premiereSceneParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(premiereScene);
        stage.show();
    }

    @FXML
    private void handleAddClubButton(ActionEvent actionEvent){

        ObservableList<Poste> postes = FXCollections.observableArrayList(Poste.values());
        cbx_role.getItems().addAll(postes);

        System.out.println("CBX SITES 1 : "+cbx_sites.getValue().toString());
        System.out.println("CBX ROLES 2 : "+cbx_role.getValue().toString());

        if (handleValidateForm()){

            System.out.println("*-*-*-*-*-- CBX SITES 1 : "+cbx_sites.getValue().toString());
            System.out.println("*-*-*-*-*-*-* CBX ROLES 2 : "+cbx_role.getValue().toString());

            Users new_user = new Users(
                    txt_username.getText().trim(),
                    txt_matricule.getText().trim(),
                    txt_nom.getText().trim(),
                    txt_prenom.getText().trim(),
                    txt_contact.getText().trim(),
                    cbx_role.getValue().toString(),
                    cbx_sites.getValue().toString()
            );
            Service<Boolean> addNewUsers = usersServices.addNewUsers(new_user);
            addNewUsers.setOnSucceeded(onSucceededEvent -> {
                if (addNewUsers.getValue()){
                    Service<Integer> getLastIdUserService = usersServices.getLastIdFromUsersTable();
                    getLastIdUserService.setOnSucceeded((t) -> {
                        new_user.setId_user(getLastIdUserService.getValue());
                        table_users.getItems().add(new_user);
                        appUtils.successAlertDialog("SUCCESS",new_user.getNom() + " Ajouter ");
                        //clearForm();
                    });
                    getLastIdUserService.start();
                } else {
                    appUtils.erreurAlertDialog("ERREUR","Erreur lors de l'ajout de nouveau club");
                    //clearForm();
                }
            });
            addNewUsers.start();
        }
    }

    @FXML
    private void handleDeleteUsers(){
        Users users_a_effacer = table_users.getSelectionModel().getSelectedItem();
        Service<Boolean> userDeleteService = usersServices.deleteUsers(users_a_effacer);
        userDeleteService.setOnSucceeded((onSucceededEvent) -> {
            if(userDeleteService.getValue()){
                table_users.getItems().remove(users_a_effacer);
                appUtils.successAlertDialog("SUCCESS","Utilisateur " + users_a_effacer.getNom() + " Effacer");
            } else {
                appUtils.erreurAlertDialog("ERREUR","Erreur lors de la suppression du club " + users_a_effacer.getNom());
            }
        });
        userDeleteService.setOnFailed(setOnFailedEvent -> {
            userDeleteService.getException().getMessage();
        });
        userDeleteService.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit_menu = new MenuItem("EDITER");
        MenuItem delete_menu = new MenuItem("EFFACER");
        contextMenu.getItems().addAll(edit_menu, delete_menu);
        table_users.setContextMenu(contextMenu);

        delete_menu.setOnAction((actionEvent -> {
            handleDeleteUsers();
        }));

        //COL_USERS_ID.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        COL_USERS_USERNAME.setCellValueFactory(new PropertyValueFactory<>("username"));
        COL_USERS_NOM.setCellValueFactory(new PropertyValueFactory<>("nom"));
        COL_USERS_PRENOM.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        COL_USERS_SITES.setCellValueFactory(new PropertyValueFactory<>("sites"));
        COL_USERS_CONTACT.setCellValueFactory(new PropertyValueFactory<>("contact"));
        COL_USERS_ROLE.setCellValueFactory(new PropertyValueFactory<>("poste"));
        COL_USERS_MATRICULE.setCellValueFactory(new PropertyValueFactory<>("matricule"));

        UsersServices usersServices = new UsersServices();

        Platform.runLater(() -> {

            cbx_role.getItems().clear();
            cbx_role.getItems().addAll(
                    "SECURITE",
                    "RECEPTIONNISTE",
                    "LIVREUR"
            );

            cbx_sites.getItems().clear();
            cbx_sites.getItems().addAll(
                    "ANALAKELY PAVILLON 3",
                    "SITES 2 3",
                    "AUTRE SITES PAVILLON 3",
                    "SITES PAVILLON 3"
            );

            table_loading.setDisable(false);
            table_loading.toFront();
            Service<List<Users>> getUsersDataService = usersServices.getUsersData();
            getUsersDataService.setOnSucceeded(onSucceededEvent -> {
                table_users.getItems().setAll(getUsersDataService.getValue());
                table_loading.setDisable(true);
                table_loading.toBack();
            });
            getUsersDataService.start();
        });
    }
}
