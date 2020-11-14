package pl.mwrobel.contactstoreapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.mwrobel.contactstoreapp.datamodel.Contact;

public class DialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField notesField;

    public Contact getNewContact(){
        Contact newContact = new Contact(firstNameField.getText(),
                lastNameField.getText(),
                phoneNumberField.getText(),
                notesField.getText());
        return newContact;
    }

    public void editContact(Contact selectedContact) {
        firstNameField.setText(selectedContact.getFirstName());
        lastNameField.setText(selectedContact.getLastName());
        phoneNumberField.setText(selectedContact.getLastName());
        notesField.setText(selectedContact.getNotes());
    }

    public void updateContact(Contact selectedContact) {
        selectedContact.setFirstName(firstNameField.getText());
        selectedContact.setLastName(lastNameField.getText());
        selectedContact.setPhoneNumber(phoneNumberField.getText());
        selectedContact.setNotes(notesField.getText());

    }
}
