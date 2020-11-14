package pl.mwrobel.contactstoreapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import pl.mwrobel.contactstoreapp.datamodel.Contact;
import pl.mwrobel.contactstoreapp.datamodel.ContactData;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainPanel;

    private ContactData data;

    @FXML
    private TableView<Contact> contactTableView;

    public void initialize(){
        data = new ContactData();
        data.loadContacts();
        contactTableView.setItems(data.getContacts());
    }

    @FXML
    public void showAddContactDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController dialogController = fxmlLoader.getController();
            Contact newContact = dialogController.getNewContact();
            data.addContact(newContact);
            data.saveContacts();
        }
    }
    @FXML
    public void showEditContactDialog(){
        Contact selectedContact = contactTableView.getSelectionModel().getSelectedItem();
        if(selectedContact == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No contact selected!");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DialogController dialogController = fxmlLoader.getController();
        dialogController.editContact(selectedContact);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            dialogController.updateContact(selectedContact);
            data.saveContacts();
        }
    }

    @FXML
    public void deleteContact(){
        Contact selectedContact = contactTableView.getSelectionModel().getSelectedItem();
        if(selectedContact == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No contact selected!");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to delete.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Contact deletion confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected contact: " +
                selectedContact.getFirstName() +
                " " +
                selectedContact.getLastName() +
                "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == (ButtonType.OK)){
            data.deleteContact(selectedContact);
            data.saveContacts();
        }

    }
}
