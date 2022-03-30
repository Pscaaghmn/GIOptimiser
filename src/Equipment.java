import java.util.Arrays;

public class Equipment {
    protected int level;
    protected int primaryAttribute;
    protected double primaryValue;
    private static final String[] attAbbreviations = new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP",   "HP%", "CR", "CD",  "EM", "ER", "HEAL", "PHYS", "PYRO", "ELEC", "CRYO", "HYDR", "ANEM", "GEO", "DEND"};

    public String toString(Boolean isAttribute){
        return "";
    }

    public static int[] strAttToInt(String[] attributes){
        //Convert string attributes like ATK into a number value, which is easier to manipulate (e.g. finding the correct base stat to use, since it is just an index value)
        int[] conversion = new int[attributes.length];
        for (int i = 0; i < attributes.length; i++) {
            conversion[i] = Artifact.strAttToInt(attributes[i]);
        }
        return conversion;
    }

    public static int strAttToInt(String attribute){
        //Single string
        return Arrays.asList(attAbbreviations).indexOf(attribute);
    }

    public void display(){
        System.out.println(primaryAttribute + ": " + primaryValue);

        System.out.println("Level " + level);
    }

}
