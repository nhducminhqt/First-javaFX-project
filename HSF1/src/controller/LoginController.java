package controller;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import fall.hsf301.slot02.pojo.Account;
import fall.hsf301.slot02.pojo.Student;
import fall.hsf301.slot02.service.AccountService;
import fall.hsf301.slot02.service.IAccountService;
import fall.hsf301.slot02.service.IStudentService;
import fall.hsf301.slot02.service.StudentService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;


public class LoginController {
	
	private IAccountService iAccountService;
	
	public LoginController(){
		iAccountService = new AccountService("JPAs");
	}
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML public void loginOnaction() throws IOException {
		Stage win =(Stage)username.getScene().getWindow();
		win.close();
		String userName = username.getText();
		String passWord = password.getText();
		Account account =  iAccountService.findByUserName(userName);
		if((account != null)&& account.getPassWord().equals(passWord)) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/StudentManagementForm.fxml"));
			Parent root = loader.load();
			StudentManagementController smController = loader.getController();
			smController.setRole(account.getRole());	
			Scene scene = new Scene(root);		
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
	
		}else {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Warning");
	        alert.setContentText("Invalid username or password.");
			alert.show();
			
		}
	}
	@FXML public void CancelOnaction() throws IOException {
		Platform.exit();
	}

}