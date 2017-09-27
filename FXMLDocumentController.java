package expensetracker.fx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Month;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

/**
 * This controller is to control GUI of My Expense Tracker
 * 
 * @author Hyoeun
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private BorderPane wrapBox;
    
    @FXML
    private GridPane box0, box1, box2;
    
    @FXML
    private ToggleButton add, viewD, viewM;
   
    @FXML
    private Label idField, idField1, totalField, totalField1;
    
    @FXML
    private DatePicker datePicker, datePicker1;
    
    @FXML
    private ComboBox<Category> cateField, cateField1; 

    @FXML
    private ComboBox<Payment> typeField, typeField1;
    
    @FXML
    private ComboBox<Month> monthPicker;
            
    @FXML
    private ComboBox<String> yearPicker;
    
    @FXML
    private TextField itemField, qtyField, priceField, itemField1, qtyField1,
                      priceField1;         
    
    @FXML
    private TextArea displayM;
    
    String[] year = {"2016", "2017", "2018", "2019", "2020"};
    int count = 0;
    private String current = "box0";
    Expense ex = new Expense();
    ExpenseList wholeList = new ExpenseList();
    ExpenseList searchedDList = new ExpenseList();
    ExpenseList searchedMList = new ExpenseList();
    PrintWriter fileOut;
    File file;
  
    /** 
     * Submits the user input data. 
     * After validation, the data is added to the whole list.
     * 
     * @param event triggered upon click the submit button
     */
    @FXML   
    private void submit(ActionEvent event) { 
        Category category = Category.valueOf(cateField.getSelectionModel()
                .getSelectedItem().toString());
        Payment payType = Payment.valueOf(typeField.getSelectionModel()
                .getSelectedItem().toString());
        Expense expense = new Expense("", category, "", 1, 1.0, payType, 1);
        expense.setDate(datePicker.getValue().format(DateTimeFormatter
                 .ofPattern("yyMMdd")));
        expense.setId(datePicker.getValue().format(DateTimeFormatter
                 .ofPattern("yyMMdd")), count);
        
        try {
            expense.setItem(itemField.getText());
            expense.setQty(Integer.parseInt(qtyField.getText()));
            expense.setPrice(Double.parseDouble(priceField.getText()));
            expense.setTotal(Integer.parseInt(qtyField.getText()), 
                    Double.parseDouble(priceField.getText()));
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Data entry");
            alert.setHeaderText("Invalid data input");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        wholeList.add(expense);
        
        datePicker.setValue(LocalDate.now());
        setId(datePicker);
        cateField.setValue(null);
        itemField.clear();
        qtyField.clear();
        priceField.clear();
        typeField.setValue(null);    
    }
    
    /**
     * Cancels the user input and put the fields back to blank.
     * 
     * @param event triggered upon clicking the cancel button on Add New mode.
     */
    @FXML
    private void cancel(ActionEvent event) {
        datePicker.setValue(LocalDate.now());
        setId(datePicker);
        cateField.setValue(null);
        itemField.clear();
        qtyField.clear();
        priceField.clear();
        typeField.setValue(null);   
    }
    
    /**
     * Saves the edited user input data. After validation, the data is updated
     * in the current position of the list.
     * 
     * @param event triggered upon click the save button of DailyView mode
     */
    @FXML 
    private void editAndSave(ActionEvent event) {
        if(!"".equals(itemField1.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Are you sure you wish to edit it?", ButtonType.YES, 
                    ButtonType.CANCEL);
            alert.setTitle("Delet data");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) { 
                Category category = Category.valueOf(cateField1.getSelectionModel()
                        .getSelectedItem().toString());
                Payment payType = Payment.valueOf(typeField1.getSelectionModel()
                        .getSelectedItem().toString());
                Expense expense = new Expense("", category, "", 1, 1.0, payType, 1);
                expense.setDate(datePicker1.getValue().format(DateTimeFormatter
                         .ofPattern("yyMMdd")));
                expense.setId(idField1.getText());

                try {
                    expense.setItem(itemField1.getText());
                    expense.setQty(Integer.parseInt(qtyField1.getText()));
                    expense.setPrice(Double.parseDouble(priceField1.getText()));
                    expense.setTotal(Integer.parseInt(qtyField1.getText()), 
                            Double.parseDouble(priceField1.getText()));
                } catch (IllegalArgumentException e) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Data entry");
                    alert1.setHeaderText("Invalid data input");
                    alert1.setContentText(e.getMessage());
                    alert1.showAndWait();
                }

                for (int i = 0; i < wholeList.length(); i++) {
                    if (expense.getId().equals(wholeList.get(i).getId())) {
                        wholeList.set(i, expense);                
                        wholeList.get(i).category = expense.category;
                        wholeList.get(i).setItem(expense.getItem());
                        wholeList.get(i).setPrice(expense.getPrice());
                        wholeList.get(i).payment = expense.payment;
                        wholeList.get(i).setTotal(expense.getQty(), expense.getPrice());  
                    }     
                }       

                datePicker1.setValue(LocalDate.now());
                setId(datePicker1);
                cateField1.setValue(null);
                itemField1.clear();
                qtyField1.clear();
                priceField1.clear();
                typeField1.setValue(null);
            } else {
                int index = searchedDList.findExpense(idField1.getText());
                int y = Integer.parseInt("20"+ searchedDList
                        .get(index).getDate().substring(0, 2));
                int m = Integer.parseInt(searchedDList
                        .get(index).getDate().substring(2, 4));
                int d = Integer.parseInt(searchedDList
                        .get(index).getDate().substring(4, 6));
                datePicker1.setValue(LocalDate.of(y, m, d));
                cateField1.setValue(searchedDList.get(index).category);
                itemField1.setText(searchedDList.get(index).getItem());
                qtyField1.setText(String.valueOf(searchedDList.get(index).getQty()));
                priceField1.setText(String.valueOf(searchedDList.get(index).getPrice()));
                typeField1.setValue(searchedDList.get(index).payment);   
            }           
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Data");
            alert2.setHeaderText(null);
            alert2.setContentText("Please find the data to edit");
            alert2.showAndWait();
        }
    }
    
    /**
     * Initializes the expense id with date and count.
     * 
     * @param date the date the expense is made
     * @param count the order of the expense happened on the date
     * @return the String id value
     */
    private String initId(String date, int count) {
        String value;      
        if(count < 10) {
            value = date + "0" + count;
        }else {
             value = date + count;
        }
        count++;
        return value;
    }

    /**
     * Makes the data fields editable for user input.
     * 
     * @param event triggered upon clicking the edit button of DailyView mode
     */
    
    @FXML
    private void edit(ActionEvent event) {
        cateField1.setDisable(false);
        itemField1.setDisable(false);
        qtyField1.setDisable(false);
        priceField1.setDisable(false);
        typeField1.setDisable(false);
        cateField1.getItems().addAll(Category.values());
        typeField1.getItems().addAll(Payment.values());
    } 
    
    /**
     * Cancels the user input and fill the fields back with the original data.
     * 
     * @param event triggered upon clicking the cancel button on Daily View mode.
     */
    @FXML
    private void cancelEdit(ActionEvent event) {   
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                        "Quit editing it?", ButtonType.YES, 
                        ButtonType.NO);
        alert.setTitle("Delet data");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            int index = searchedDList.findExpense(idField1.getText());
            int y = Integer.parseInt("20"+ searchedDList
                    .get(index).getDate().substring(0, 2));
            int m = Integer.parseInt(searchedDList
                    .get(index).getDate().substring(2, 4));
            int d = Integer.parseInt(searchedDList
                    .get(index).getDate().substring(4, 6));
            datePicker1.setValue(LocalDate.of(y, m, d));
            cateField1.setValue(searchedDList.get(index).category);
            itemField1.setText(searchedDList.get(index).getItem());
            qtyField1.setText(String.valueOf(searchedDList.get(index).getQty()));
            priceField1.setText(String.valueOf(searchedDList.get(index).getPrice()));
            typeField1.setValue(searchedDList.get(index).payment);   
        }
    }
    
    /**
     * Deletes the current data shown after confirmation.
     * 
     * @param event triggered upon clicking the delete button on DailyView mode
     */
    @FXML
    private void delete(ActionEvent event) {
        if(!"".equals(itemField1.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                        "Are you sure you wish to delete it?", ButtonType.YES, 
                        ButtonType.NO);
            alert.setTitle("Delet data");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                for (int i = 0; i < wholeList.length(); i++) {
                    if (idField1.getText().equals(wholeList.get(i).getId())) {
                        wholeList.remove(i);
                    }    
                }
                datePicker1.setValue(LocalDate.now());
                setId(datePicker1);
                cateField1.setValue(null);
                itemField1.clear();
                qtyField1.clear();
                priceField1.clear();
                typeField1.setValue(null);
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Data");
            alert2.setHeaderText(null);
            alert2.setContentText("Please find the data to delete");
            alert2.showAndWait();
        }
    }
     
    /**
     * Displays daily expense items by date search.
     * 
     * @param event triggered upon clicking the search button on DailyView mode
     */
    @FXML
    private void displayDaily(ActionEvent event) {
        String key = datePicker1.getValue().format(DateTimeFormatter
                            .ofPattern("yyMMdd"));
        searchedDList = wholeList.findDailyExpense(key, wholeList);
        
        if (searchedDList.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No data to show");
            alert.showAndWait();
        } else {
            idField1.setText(searchedDList.get(0).id);
            cateField1.getSelectionModel().select(searchedDList.get(0).category);
            itemField1.setText(searchedDList.get(0).item);
            qtyField1.setText(String.valueOf(searchedDList.get(0).getQty()));
            priceField1.setText(String.valueOf(searchedDList.get(0).getPrice()));
            typeField1.getSelectionModel().select(searchedDList.get(0).payment);           
        }
    }
    
    /**
     * Displays the previous expense of the same date.
     * 
     * @param event triggered upon clicking the prev button on DailyView mode
     */
    @FXML
    private void displayPrev(ActionEvent event) {  
        if(searchedDList.length() > 0) {
            if(idField1.getText().equals(searchedDList.get(0).getId())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Previous data");
                alert.setHeaderText(null);
                alert.setContentText("There is no more data.");
                alert.showAndWait();
            }else {
                int prevIndex = (Integer.parseInt(idField1.getText())-1) % 10;            
                idField1.setText(searchedDList.get(prevIndex).id);
                cateField1.getSelectionModel().select(searchedDList
                        .get(prevIndex).category);
                itemField1.setText(searchedDList
                        .get(prevIndex).item);
                qtyField1.setText(String.valueOf(searchedDList
                        .get(prevIndex).getQty()));
                priceField1.setText(String.valueOf(searchedDList
                        .get(prevIndex).getPrice()));
                typeField1.getSelectionModel().select(searchedDList
                        .get(prevIndex).payment);
            }
        } else { 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Previous data");
            alert.setHeaderText(null);
            alert.setContentText("There is no data.");
            alert.showAndWait();
        }
    }
    
    /**
     * Displays the next expense of the same date.
     * 
     * @param event triggered upon clicking the next button on DailyView mode
     */
    @FXML
    private void displayNext(ActionEvent event) {
        if(searchedDList.length() > 0) {
            if(idField1.getText().equals(searchedDList
                    .get(searchedDList.length()-1).getId())) {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Next data");
               alert.setHeaderText(null);
               alert.setContentText("There is no more data.");
               alert.showAndWait();
           } else {
               int nextIndex = (Integer.parseInt(idField1.getText())+1) % 10;

               idField1.setText(searchedDList.get(nextIndex).id);
               cateField1.getSelectionModel().select(searchedDList
                       .get(nextIndex).category);
               itemField1.setText(searchedDList.get(nextIndex).item);
               qtyField1.setText(String.valueOf(searchedDList
                       .get(nextIndex).getQty()));
               priceField1.setText(String.valueOf(searchedDList
                       .get(nextIndex).getPrice()));
               typeField1.getSelectionModel().select(searchedDList
                       .get(nextIndex).payment);
            }
        } else { 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Next data");
            alert.setHeaderText(null);
            alert.setContentText("There is no data.");
            alert.showAndWait();
        }
    }
    
    /**
     * Displays the monthly expense by categories and the accumulated total
     * 
     * @param event triggered upon clicking the search button on MonthlyView mode
     */
    @FXML
    private void displayMonthly(ActionEvent event) {
        String year = yearPicker.getSelectionModel().getSelectedItem().substring(2,4);
        int month = monthPicker.getSelectionModel().getSelectedItem().getValue();
        String key = "";
        if(month < 10) 
            key = year + "0" + month;
        else
            key = year + month;
        searchedMList = wholeList.findMonthlyExpense(key, wholeList);
        
        if(searchedMList == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No data to show");
            alert.showAndWait();
        } else {
            ExpenseList list = new ExpenseList();
            list = list.getTotalByCate(searchedMList);
            
            String output = "";
            for(int i = 0; i < list.length(); i++) {
                output += list.get(i).toString()+"\n";
            }
            displayM.setText(output);
            displayM.appendText(String.format("%6s%30.2f", "Total : ",
                    list.getTotal(searchedMList)));
        }
    }

    /**
     * Swapped to Add New mode if the screen is not it.
     * 
     * @param event triggered upon clicking Add New button
     */
    @FXML
    private void addMode(ActionEvent event) {
        if(current.equals("box1") || current.equals("box2")) {
            wrapBox.getChildren().remove(box1);
            wrapBox.getChildren().remove(box2);
            wrapBox.setCenter(box0);
            current = "box0";
            wrapBox.setStyle("-fx-background-color: whitesmoke");
        }
    }
    
    /**
     * Swapped to Daily View mode if the screen is not it.
     * 
     * @param event triggered upon clicking Daily View button
     */
    @FXML
    private void viewDMode(ActionEvent event) {      
        if(current.equals("box0") || current.equals("box2")) {
            wrapBox.getChildren().remove(box0);
            wrapBox.getChildren().remove(box2);
            wrapBox.setCenter(box1);
            current = "box1";
            wrapBox.setStyle("-fx-background-color: #ffeff6");
        }
    }

    /**
     * Swapped to Monthly View mode if the screen is not it.
     * 
     * @param event triggered upon clicking Monthly View button
     */
    @FXML
    private void viewMMode(ActionEvent event) {
        if(current.equals("box0") || current.equals("box1")) {
            wrapBox.getChildren().remove(box0);
            wrapBox.getChildren().remove(box1);
            wrapBox.setCenter(box2);
            current = "box2";
            wrapBox.setStyle("-fx-background-color: #fffde8");
        }
    }
    
    /**
     * Writes the list of expenses to a file and closes the window 
     * after confirmation.
     * 
     * @param event triggered upon clicking the exit button on any modes
     */
    @FXML
    private void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Are you sure you wish to exit?", ButtonType.YES, 
                    ButtonType.NO);
        alert.setTitle("Exit Program");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
 
        if (result.get() == ButtonType.YES) {
            try {
                fileOut = new PrintWriter("expense.txt");
                for (int i = 0; i < wholeList.length(); i++) {
                    fileOut.println(wholeList.get(i).getDate() + "|" +
                                wholeList.get(i).getId() + "|" +
                                wholeList.get(i).category + "|" +
                                wholeList.get(i).getItem() + "|" +
                                wholeList.get(i).getQty() + "|" +
                                wholeList.get(i).getPrice() + "|" +
                                wholeList.get(i).payment + "|" + 
                                wholeList.get(i).getTotal());
                } 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            fileOut.close();
            System.exit(0);
        }
    }
    
    /**
     * Retrieves the count based on how many expenses exist already for a date.
     * If there is none, returns 0. Otherwise, increment by 1.
     * 
     * @param date the key to search the whole list
     * @return the count
     */
    private int findCount(String date) {
        ExpenseList byDayList = wholeList.findDailyExpense(date, wholeList);
        int index = 0; 
        if (byDayList.length() < 1) {
            return 0;
        } else {
            for (int i = 0; i < byDayList.length(); i++) {
                if (byDayList.get(index).getIntId() 
                        < byDayList.get(i).getIntId()) {
                    index = i;
                }
            }
            return byDayList.get(index).getIntId() + 1 ;
        }
    }
    
    /**
     * Sets id on GUI based on the date picked by the user and the count
     * 
     * @param datePicker the date the user picked
     */
    private void setId(DatePicker datePicker) {
        String newDate = datePicker.getValue().format(DateTimeFormatter
                            .ofPattern("yyMMdd"));
        count = findCount(newDate);
        idField.setText(initId(newDate, count));     
    }

    /**
     * Initializes the program 
     * 
     * @param url the address of FXML
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            file = new File("expense.txt");
            if(file.exists()) {
                Scanner in = new Scanner(file);          
                in.useDelimiter(System.getProperty("line.separator"));               
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    String[] fields = line.split("\\|");  
                    Expense record = new Expense();
                    record.setDate(fields[0]);
                    record.setId(fields[0], 
                            Integer.parseInt(fields[1].substring(6,8)));
                    record.setCategory(fields[2]);
                    record.setItem(fields[3]);
                    record.setQty(Integer.parseInt(fields[4]));
                    record.setPrice(Double.parseDouble(fields[5]));
                    record.setPayment(fields[6]);
                    record.setTotal(Integer.parseInt(fields[4]), 
                            Double.parseDouble(fields[5]));  
                    wholeList.add(record);
                }
                in.close();  
            }   
        } catch (FileNotFoundException ex) {
               ex.printStackTrace();
        } 

        yearPicker.getItems().addAll(year);
        monthPicker.getItems().addAll(Month.values());
        String date = new SimpleDateFormat("yyMMdd").format(new Date());
        datePicker.setValue(LocalDate.now());
        idField.setText(initId(date, count));
        
        datePicker.valueProperty().addListener((observable, oldValue, newValue)
                -> { setId(datePicker); });            
        
        cateField.getItems().addAll(Category.values());
        typeField.getItems().addAll(Payment.values());
        
        qtyField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable obs) {
                if(!qtyField.getText().isEmpty())
                    ex.setQty(Integer.parseInt(qtyField.getText()));
            }
        });
        
        priceField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable obs) {
                if(!priceField.getText().isEmpty())
                    ex.setPrice(Double.parseDouble(priceField.getText()));
            }
        });
        
        NumberBinding totalPrice = ex.qtyProperty().
                multiply(ex.priceProperty());
        totalField.textProperty().bind(totalPrice.asString());
        
        qtyField1.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable obs) {
                if(!qtyField1.getText().isEmpty())
                    ex.setQty(Integer.parseInt(qtyField1.getText()));
            }
        });
        
        priceField1.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable obs) {
                if(!priceField1.getText().isEmpty())
                    ex.setPrice(Double.parseDouble(priceField1.getText()));
            }
        });
        
        NumberBinding totalPrice1 = ex.qtyProperty().
                multiply(ex.priceProperty());
        totalField1.textProperty().bind(totalPrice1.asString());      
    }     
}
