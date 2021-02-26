package main.java.semantics;

public enum WeightedSemantics {
    SMA ("sum most-attacking", "SMA"),
    BEST1("select best 1", "BEST1"),
    BEST2("select best 2", "BEST2"),
    BEST3("select best 3", "BEST3"),
    BEST4("select best 4", "BEST4"),
    diverse ("diverse semantics", "div");

    public static final WeightedSemantics SUM_MOST_ATTACKING = SMA;

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
