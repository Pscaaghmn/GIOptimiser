import java.util.Arrays;

public class Equipment {
    private int level;
    private int primaryAttribute;
    private double primaryValue;
    private static final String[] attAbbreviations = new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP", "HP%", "CR", "CD", "EM", "ER", "HEA", "PHY", "PYR", "ELE", "CRY", "HYD", "ANE", "GEO", "DEN"};
    private static final String[] attributes = new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge", "Healing Bonus", "Physical DMG Bonus", "Pyro DMG Bonus", "Electro DMG Bonus", "Cryo DMG Bonus", "Hydro DMG Bonus", "Anemo DMG Bonus", "Geo DMG Bonus", "Dendro DMG Bonus"};

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPrimaryAttribute() {
        return primaryAttribute;
    }

    public void setPrimaryAttribute(int primaryAttribute) {
        this.primaryAttribute = primaryAttribute;
    }

    public double getPrimaryValue() {
        return primaryValue;
    }

    public void setPrimaryValue(double primaryValue) {
        this.primaryValue = primaryValue;
    }

    public String toString(int[] attFieldWidths, String[] connectedAttData, int[] valFieldWidths, String[] connectedValData, Boolean isAttribute){
        //Converts artifact data into a record format
        StringBuilder finalRecord = new StringBuilder();

        //Turning all data of equipment into a line that is to be written into the file
        if(isAttribute) {
            for (int i = 0; i < connectedAttData.length; i++) {
                finalRecord.append(Database.pad((connectedAttData)[i], (attFieldWidths)[i]));
            }
        }else{
            for (int i = 0; i < connectedValData.length; i++) {
                if (connectedValData[i].length() > 4) {
                    finalRecord.append(Database.pad(String.valueOf(Math.round(Float.parseFloat((connectedValData)[i]))), (valFieldWidths)[i]));
                }else{
                    finalRecord.append(Database.pad((connectedValData)[i], (valFieldWidths)[i]));
                }
            }
        }

        return finalRecord.toString();
    }

    public static int strAttToInt(String attribute){
        //Single string
        int indexInAttributesArray = Arrays.asList(attributes).indexOf(attribute);
        if (indexInAttributesArray == -1){
            int i = 0;
            while (i < attAbbreviations.length){
                //Returns the index of the entered attribute
                if(attribute.trim().toUpperCase().contains(attAbbreviations[i]) && attribute.contains("%") == attAbbreviations[i].contains("%")){
                    return i;
                }
                i++;
            }
        }
        return indexInAttributesArray;
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
        return (attribute == -1 ? "" : attributes[attribute]);
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