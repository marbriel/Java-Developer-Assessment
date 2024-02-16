package com.example.javarassessment;

import com.example.javarassessment.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormController {
    @FXML
    private Button btnClear;

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker dateBirthDay;

    @FXML
    private RadioButton radFemale;

    @FXML
    private RadioButton radMale;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private ScrollPane usersHolder;
    private List<User> users = new ArrayList<>();


    private String gender = null;

    public void clearFields(){
        radFemale.setSelected(false);
        radMale.setSelected(false);
        txtEmail.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        dateBirthDay.setValue(null);


    }

    public void save(){
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String gender = this.gender;
        String birthday = formatBirthday(dateBirthDay.getValue());


        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || gender == null || birthday.isEmpty()){
            showNotificationError();
        }else{
            if(isValidEmail(email)){
                System.out.println(new User(firstName, lastName, email, birthday, gender));
                users.add(new User(firstName, lastName, email, birthday, gender));
                showSuccessNotification(firstName, lastName);
                clearFields();
                displayUsers();
            }else{
                showNotificationEmailError();
            }
        }

    }
    



   public void setGender(){
        if(radMale.isSelected() || radFemale.isSelected()){
            gender = radMale.isSelected() ? radMale.getText() : radFemale.getText();
        }else{
            gender = null;
        }
   }


   public void showNotificationError(){
        Notifications.create()
                .title("Error")
                .text("Please enter all information in the form")
                .showError();
   }

    public void showNotificationEmailError(){
        Notifications.create()
                .title("Email Error")
                .text("Please enter a correct email address")
                .showError();
    }

   public void showSuccessNotification(String firstName, String lastName){
        Notifications.create()
                .title("Save successfully")
                .text("Users " + firstName + " " + lastName + " was saved successfully")
                .showInformation();
   }

    private String formatBirthday(LocalDate localDate) {
        if (localDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            return localDate.format(formatter);
        }
        return "";
    }

    private void displayUsers() {
        // Clear existing content in the ScrollPane
        VBox vBoxContainer = new VBox();
        for (User user : users) {
            VBox userVBox = createUserVBox(user);
            vBoxContainer.getChildren().add(userVBox);
        }
        usersHolder.setContent(vBoxContainer);
    }

    private VBox createUserVBox(User user) {
        Label nameLabel = new Label(user.getFirstName() + " " + user.getLastName() + " ( " + user.getGender() + " )");
        Label birthdayLabel = new Label("Born on  " + user.getBirthday());
        Label emailLabel = new Label(user.getEmail());

        VBox labelsVBox = new VBox(nameLabel, birthdayLabel, emailLabel);
        labelsVBox.setStyle("-fx-border-style: solid; -fx-border-width: 0 0 1 0; -fx-padding: 0 0 5 0;"); // Bottom border and padding

        HBox userHBox = new HBox(labelsVBox);
        HBox.setHgrow(labelsVBox, Priority.ALWAYS); // Allow the VBox to expand horizontally

        VBox userVBox = new VBox(userHBox);
        userVBox.setStyle("-fx-padding: 16;");
        VBox.setVgrow(userHBox, Priority.ALWAYS); // Allow the HBox to expand vertically

        return userVBox;
    }

    public boolean isValidEmail(String email) {
        // Define the email pattern using a regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(email);

        // Return true if the email matches the pattern, otherwise false
        return matcher.matches();
    }




}
