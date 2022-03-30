import java.util.Arrays;

public class Equipment {
    protected int level;
    protected int primaryAttribute;
    protected double primaryValue;
    private static final String[] attAbbreviations = new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP",   "HP%", "CR", "CD",  "EM", "ER", "HEAL", "PHYS", "PYRO", "ELEC", "CRYO", "HYDR", "ANEM", "GEO", "DEND"};

    public String toString(Boolean isAttribute){
        //Should not happen (the equipment types have their own things to return
        return "";
    }

    public static int strAttToInt(String attribute){
        //Single string
        return Arrays.asList(attAbbreviations).indexOf(attribute);
    }

    public static int[] strAttToInt(String[] attributes){
        //Convert string attributes like ATK into a number value, which is easier to manipulate (e.g. finding the correct base stat to use, since it is just an index value)
        int[] conversion = new int[attributes.length];
        for (int i = 0; i < attributes.length; i++) {
            conversion[i] = strAttToInt(attributes[i]);
        }
        return conversion;
    }

    public static String intAttToStr(int attribute){
        return (attribute == -1 ? "" : attAbbreviations[attribute]);
    }

    public static String[] intAttToStr(int[] attributes){
        //Convert string attributes like ATK into a number value, which is easier to manipulate (e.g. finding the correct base stat to use, since it is just an index value)
        String[] conversion = new String[attributes.length];
        for (int i = 0; i < attributes.length; i++) {
            conversion[i] = intAttToStr(attributes[i]);
        }
        return conversion;
    }

    public void display(){
        System.out.println("Level " + level);
        System.out.println("\u001B[33m" + intAttToStr(primaryAttribute) + ": " + primaryValue + "\u001B[0m");
    }

}
