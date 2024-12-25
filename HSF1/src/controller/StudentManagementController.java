package controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fall.hsf301.slot02.pojo.Student;
import fall.hsf301.slot02.service.IStudentService;
import fall.hsf301.slot02.service.StudentService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentManagementController implements Initializable{
	private IStudentService iStudentService;
	@FXML
	private TextField txtStudentID;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtFindByName;
	@FXML
	private TextField txtMark;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnDelete;
	
	private String role;
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
		switch (this.role) {
		case "ADMIN":
			this.btnAdd.setDisable(false);
			this.btnUpdate.setDisable(false);
			this.btnDelete.setDisable(false);
			break;
		case "STUDENT":
			this.btnAdd.setDisable(true);
			this.btnUpdate.setDisable(true);
			this.btnDelete.setDisable(true);
			break;
		}
	}
	public TextField getTxtStudentID() {
		return txtStudentID;
	}
	public void setTxtStudentID(TextField txtStudentID) {
		this.txtStudentID = txtStudentID;
	}
	public TextField getTxtFirstName() {
		return txtFirstName;
	}
	public void setTxtFirstName(TextField txtFirstName) {
		this.txtFirstName = txtFirstName;
	}
	public TextField getTxtLastName() {
		return txtLastName;
	}
	public void setTxtLastName(TextField txtLastName) {
		this.txtLastName = txtLastName;
	}
	public TextField getTxtMark() {
		return txtMark;
	}
	public void setTxtMark(TextField txtMark) {
		this.txtMark = txtMark;
	}
	
	public IStudentService getStudentService() {
		return studentService;
	}
	public void setStudentService(IStudentService studentService) {
		this.studentService = studentService;
	}
	public TableView<Student> getTblStudents() {
		return tblStudents;
	}
	public void setTblStudents(TableView<Student> tblStudents) {
		this.tblStudents = tblStudents;
	}
	public TableColumn<Student, Long> getStudentId() {
		return studentId;
	}
	public void setStudentId(TableColumn<Student, Long> studentId) {
		this.studentId = studentId;
	}
	public TableColumn<Student, String> getFirstName() {
		return firstName;
	}
	public void setFirstName(TableColumn<Student, String> firstName) {
		this.firstName = firstName;
	}
	public TableColumn<Student, String> getLastName() {
		return lastName;
	}
	public void setLastName(TableColumn<Student, String> lastName) {
		this.lastName = lastName;
	}
	public TableColumn<Student, Integer> getMark() {
		return mark;
	}
	public void setMark(TableColumn<Student, Integer> mark) {
		this.mark = mark;
	}
	public ObservableList<Student> getTableModel() {
		return tableModel;
	}
	public void setTableModel(ObservableList<Student> tableModel) {
		this.tableModel = tableModel;
	}
	private IStudentService studentService;
	@FXML
	private TableView<Student> tblStudents;
	@FXML
	private TableColumn<Student, Long> studentId;
	@FXML
	private TableColumn<Student, String> firstName;
	@FXML
	private TableColumn<Student, String> lastName;
	@FXML
	private TableColumn<Student, Integer> mark;
	private ObservableList<Student>tableModel;
	@FXML public void CancelOnaction() throws IOException {
		Platform.exit();
	}
	@FXML public void AddOnaction()throws IOException{
		Student st = new Student(txtFirstName.getText(),txtLastName.getText(),Integer.parseInt(txtMark.getText()));
		studentService.save(st);
		 tableModel.clear(); 
		 tableModel.addAll(studentService.getStudents()); 
		 tblStudents.setItems(tableModel);  
	}
	@FXML public void updateOnaction() throws IOException{
		Student st = studentService.findByID(Long.parseLong(txtStudentID.getText()));
		st.setFirstName(txtFirstName.getText());
		st.setLastName(txtLastName.getText());
		st.setMark(Integer.parseInt(txtMark.getText()));
		studentService.update(st);
		 tableModel.clear(); 
		 tableModel.addAll(studentService.getStudents()); 
		 tblStudents.setItems(tableModel);  
		
		
	}
	@FXML public void deleteOnaction()throws IOException{
		studentService.delete(Long.parseLong(txtStudentID.getText()));
		 tableModel.clear(); 
		 tableModel.addAll(studentService.getStudents()); 
		 tblStudents.setItems(tableModel);  
	}
	@FXML public void SearchOnaction()throws IOException{
	    String searchKeyword = txtFindByName.getText();
	    tableModel.clear();
	    if (!searchKeyword.isEmpty()) {
	    	ObservableList<Student> searchResults = FXCollections.observableArrayList();
	        for (Student student : studentService.getStudents()) {
	            if (student.getFirstName().toLowerCase().contains(searchKeyword.toLowerCase())) {
	                searchResults.add(student);
	            }
	        }
	        if (!searchResults.isEmpty()) {
	            tableModel.addAll(searchResults);
	        } else {
	            tableModel.addAll(studentService.getStudents());
	            showAlert("Không tìm thấy", "Không có sinh viên nào phù hợp với từ khóa tìm kiếm.");
	        }
	    } else {
	        tableModel.addAll(studentService.getStudents());
	    }


	    tblStudents.setItems(tableModel);
	}
	public StudentManagementController()
	{
		studentService = new StudentService("JPAs");
		tableModel = FXCollections.observableArrayList(studentService.getStudents());
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		studentId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
		mark.setCellValueFactory(new PropertyValueFactory<>("Mark"));
		tblStudents.setItems(tableModel);
		
		tblStudents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object index) {
				if (tblStudents.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = tblStudents.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedCells();
					TablePosition tablePosition = (TablePosition) selectedCells.get(0);
					Object studentID = tablePosition.getTableColumn().getCellData(index);
					try {
						Student student = iStudentService.findByID(Long.valueOf(studentID.toString())); 
						showStudent(student);
					} catch (Exception ex) { 
						showAlert("Infomation Board!", "Please choose the First Cell !");
					}
				}
				
			}
	});
	}
	protected void showAlert(String string, String string2) {
		// TODO Auto-generated method stub
		
	}
	protected void showStudent(Student student) {
		this.txtStudentID.setText(String.valueOf(student.getId()));
		this.txtFirstName.setText(student.getFirstName());
		this.txtLastName.setText(student.getLastName());
		this.txtMark.setText(String.valueOf(student.getMark()));
		
	}
}
