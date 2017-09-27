package expensetracker.fx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The Expense class represents a single expense entry for one item.
 * Each expense has id, date, item, quantity, price, payment type, and total.
 * 
 * @author Hyoeun Lee
 */
public class Expense {
    
    public Category category;
    public Payment payment;
    public String date;
    public String id;
    public String item;
    public Double total;
    public final IntegerProperty qty = 
                new SimpleIntegerProperty(this, "quantity");
    public final DoubleProperty price = 
                new SimpleDoubleProperty(this, "price");
    
    /**
     * Constructs an empty Expense object.
     */
    public Expense() {}
    
    /**
     * Constructs an Expense with a category and total value.
     * 
     * @param cate the category of this Expense
     * @param total the total value of this Expense
     */
    public Expense(Category cate, double total) {
        category = cate;
        this.total = total;
    }
    
    /**
     * Constructs an Expense with a date, category, item, quantity, price, 
     * payment type and count.
     * 
     * @param date the date of this Expense happened
     * @param cate the category of this Expense
     * @param item the name of this Expense
     * @param qty the quantity of this Expense
     * @param price the price of the item of this Expense
     * @param payType the payment type of this Expense
     * @param count the number-order of the Expense of the date
     */
    public Expense (String date, Category cate, String item, int qty, 
                    double price, Payment payType, int count) {
        setDate(date);
        setId(date, count);
        category = cate;
        setItem(item);
        setQty(qty);
        setPrice(price);
        payment = payType; 
        setTotal(qty, price);
    }
   
    /**
     * Places a String value of date to this Expense.
     * 
     * @param date the date of this Expense happened
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    /**
     * Retrieves the date of this Expense.
     * 
     * @return the date of the Expense
     */
    public String getDate() {
        return date;
    }
    
    /**
     * Places a String value of id to this Expense.
     * 
     * @param id the id of this Expense
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Places a String value of date to this Expense using date and count.
     * 
     * @param date the date of this Expense happened
     * @param count the number-order of this Expense of the date
     */
    public void setId(String date, int count) {
        if(count < 10) {
            this.id = date + "0" + count;
        }else {
            this.id = date + count;
        }
    }
    
    /**
     * Retrieves the id of this Expense.
     * 
     * @return the id of this Expense
     */
    public String getId() {
        return id;
    }

    /**
     * Places a category value to this Expense using category enums
     * 
     * @param category a category enum
     */
    public void setCategory (String category) {
        for(Category c : Category.values()) {
            if(c.toString().equals(category)) {
                this.category = c;
            }
        }
    }
    
    /**
     * Places a String value of item to this Expense.
     * 
     * @param item the item of this Expense happened
     * @throws IllegalArgumentException if it is null or does not exist
     */
    public void setItem(String item) {
        if(item != null || !item.isEmpty()) {
            this.item = item;
        }else {
            throw new IllegalArgumentException("Please enter an item");
        }
    }
    
    /**
     * Retrieves the item of this Expense.
     * 
     * @return the item of this Expense
     */
    public String getItem() {
        return item;
    }
    
    /**
     * Places an integer value of quantity to this Expense.
     * 
     * @param qty the quantity of the item of this Expense
     * @throws IllegalArgumentException if it is less than 0
     */
    public final void setQty(int qty) {
        if(qty > 0) {
            this.qty.set(qty);
        }else {
            throw new IllegalArgumentException("The Quantity must be greater "
                    + "than 0");
        }
    }
    
    /**
     * Retrieves the quantity of this Expense.
     * 
     * @return the quantity of this Expense
     */   
    public final int getQty() {
        return qty.get();
    }
       
    /**
     * Retrieves the quantity property of this Expense.
     * 
     * @return the quantity property of this Expense
     */
    public final IntegerProperty qtyProperty() {
        return qty;
    }
    
    /**
     * Places a double value of price to this Expense.
     * 
     * @param price the price of the item of this Expense
     * @throws IllegalArgumentException if it is less than 0
     */
    public final void setPrice(double price) {
        if(price > 0.0) {
            this.price.set(price);
        }else {
            throw new IllegalArgumentException("The price must be greater "
                    + "than 0");
        }
    }
    
    /**
     * Retrieves the price of this Expense.
     * 
     * @return the price of this Expense
     */   
    public final double getPrice() {
        return price.get();
    }
    
    /**
     * Retrieves the price property of this Expense.
     * 
     * @return the price property of this Expense
     */   
    public final DoubleProperty priceProperty() {
        return price;
    }
    
    /**
     * Places a category value to this Expense using category enums
     * 
     * @param payment a payment enum 
     */
    public void setPayment (String payment) {
        for(Payment p : Payment.values()) {
            if(p.toString().equals(payment)) {
                this.payment = p;
            }
        }
    }

    /**
     * Places a double value of total to this Expense using quantity and price
     * 
     * @param qty the quantity of the item of this Expense
     * @param price the price of the item of this Expense
     */
    public void setTotal(int qty, double price) {
        this.total = price * qty;
    }
    
    /**
     * Retrieves the total value of this Expense.
     * 
     * @return the total of this Expense
     */
    public double getTotal() {
        return total;
    }
    
    /**
     * Retrieves the integer value of id of this Expense.
     * 
     * @return the id of this Expense as an integer
     */
    public int getIntId() {
        int intId = Integer.parseInt(id.substring(6,8));
        return intId;
    }
    
    /**
     * Returns a formatted String representing this Expense object.
     * 
     * @return this Expense as a String
     */
    @Override
    public String toString() {
        return String.format("%-24.24s %.2f", category, total);
    }
}
