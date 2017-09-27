package expensetracker.fx;

import java.util.ArrayList;

/**
 * This ExpenseList is to model a list of Expenses 
 * 
 * @author Hyoeun
 */

public class ExpenseList {
    
    /** The Expenses for this ExpenseList. */
    private ArrayList<Expense> expenseList = new ArrayList();
    
    /**
     * Constructs an empty ExpenseList object with an empty ArrayList
     */
    public ExpenseList() {
        
    }
    
    /**
     * Accepts an integer index and returns the Expense object at that index.
     */
    public Expense get(int index) {
        return expenseList.get(index);
    }
  
    /**
     * Accepts an Expense object to add to an expenseList.
     * 
     * @param expense is the expense object which will be added to the list
     */
    public void add(Expense expense) {
       expenseList.add(expense);
    }
    
    /**
     * Updates an Expense object at a specific position of an expenseList.
     * 
     * @param index is the index of the expenseList which will be updated
     * @param expense is the expense object to be placed
     */
    public void set(int index, Expense expense) {
        expenseList.set(index, expense);
    }
    
    /**
     * Removes an Expense object at a specific position of an expenseList.
     * 
     * @param index 
     */
    public void remove(int index) {
        expenseList.remove(index);
    }
       
    /**
     * Retrieves the size of an expenseList.
     * 
     * @return the size of an expenseList
     */
    public int length() {
        return expenseList.size();
    }

    /**
     * Receives an id and returns the index of the expense in the expenseList.
     * If no match, returns -1.
     * 
     * @param id to search
     * @return the index number
     */
    public int findExpense(String id) {
        for(int i=0; i < expenseList.size(); i++) {
            if(id.equals(expenseList.get(i).getId())){
                return i;
            }
        }return -1;
    }
    /**
     * Receives a date and an expenseList to find the expenses for the date, 
     * saves those expenses to a list and return it
     * 
     * @param date the key to search
     * @param list the expenseList to be searched
     * @return a list of expenses for a date
     */
    public ExpenseList findDailyExpense(String date, ExpenseList list) {
        ExpenseList dailyList = new ExpenseList();
        for (int i = 0; i < list.length(); i++){
            if (date.equals(list.get(i).getDate())){
                dailyList.add(list.get(i));
            }
        }return dailyList;
    }
    
    /**
     * Receives a date and an expenseList to find the expenses for the month, 
     * saves those expenses to a list and return it
     * 
     * @param date the key to search
     * @param list the expenseList to be searched
     * @return a list of expenses for a month
     */
    public ExpenseList findMonthlyExpense(String date, ExpenseList list) {
        ExpenseList monthlyList = new ExpenseList();
        for (int i = 0; i < list.length(); i++){
            if (date.equals(list.get(i).getDate().substring(0,4))){
                monthlyList.add(list.get(i));
            }
        }return monthlyList;
    }
    
    /**
     * Gets total for each category if it exits in the monthly expense list
     * 
     * @param list the list to be searched
     * @return monthly total of the list by each category
     */
    public ExpenseList getTotalByCate(ExpenseList list) {
        ExpenseList total = new ExpenseList();
        double cateTotal = 0.0;
        for (Category c: Category.values()) {
            for (int i = 0; i < list.length(); i++) {
                if (list.get(i).category.getName().equals(c.getName())){
                    cateTotal += list.get(i).getPrice();          
                }              
            } 
            Expense mExByCate = new Expense(c, cateTotal);
            total.add(mExByCate);
            cateTotal = 0.0;
        }
        return total;
    }
    
    /**
     * Gets the accumulated total of all the expenses in an expenseList.
     * 
     * @param list the list whose total to be accumulated
     * @return accumulated total value of an expenseList
     */
    public double getTotal(ExpenseList list) {
        double total = 0.0;
        for (int i = 0; i < list.length(); i++) {
            total += list.get(i).getTotal();
        }
        return total;
    }
}
