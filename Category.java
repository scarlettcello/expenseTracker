package expensetracker.fx;

/**
 * A list of categories for expenses
 * 
 * @author Hyoeun
 */
public enum Category {
    BEAUTY("Beauty"),
    CLOTHES("Clothes"),
    COMMUNICATION("Communication"),
    DEPOSIT("Deposit"),
    DINEOUT("Dine-out"),
    EDUCATION("Education"),
    GIFT("Gift"),
    GROCERY("Grocery"),
    HOUSING("Housing"),
    LEISURE("Leisure"),
    MEDICAL("Medical"),
    MISC("Misc."),
    TRANSPORTATION("Transportation"),
    TAX("Tax");

    private final String name;

    /**
     * Retrieves the name of the category
     * 
     * @return the name of the category
     */
    public String getName(){
        return name;
    }
    
    /**
     * Constructs a category with the name 
     * 
     * @param name the name of category
     */
    private Category(String name) {
        this.name = name;
    }
}

