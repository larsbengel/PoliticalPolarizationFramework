package main.java.semantics;


/**
 * class for semantics in weighted argumentation systems
 */
public enum WeightedSemantics {
    SMA ("sum most-attacking", "SMA"),
    BEST1("select best 1", "BEST1"),
    BEST2("select best 2", "BEST2"),
    BEST3("select best 3", "BEST3"),
    BEST4("select best 4", "BEST4");

    public static final WeightedSemantics SUM_MOST_ATTACKING = SMA;
    public static final WeightedSemantics SELECT_BEST_1 = BEST1;
    public static final WeightedSemantics SELECT_BEST_2 = BEST2;
    public static final WeightedSemantics SELECT_BEST_3 = BEST3;
    public static final WeightedSemantics SELECT_BEST_4 = BEST4;

    /** The description of the semantics. */
    private String description;
    /** The abbreviation of the semantics. */
    private String abbreviation;

    /**
     * Creates a new semantics.
     * @param description some description
     * @param abbreviation an abbreviation
     */
    WeightedSemantics(String description, String abbreviation){
        this.description = description;
        this.abbreviation = abbreviation;
    }

    /**
     * Returns the description of the semantics.
     * @return the description of the semantics.
     */
    public String description(){
        return this.description;
    }

    /**
     * Returns the abbreviation of the semantics.
     * @return the abbreviation of the semantics.
     */
    public String abbreviation(){
        return this.abbreviation;
    }
}
