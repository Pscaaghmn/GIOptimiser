import java.util.Arrays;

public class Artifact {
    //Flower: 1, Plume: 2, Sands: 3, Goblet: 4, Circlet: 5
    //Attribute abbreviations: ATK(0) ATK%(1) DEF(2) DEF%(3) HP(4) HP%(5) CR(6) CD(7) EM(8) ER(9) HEAL(10) PHYS(11) PYRO(12) ELEC(13) CRYO(14) HYDR(15) ANEM(16) GEO(17) DEND(18)

    private final String set;
    private final int piece;

    private final int primaryAttribute;
    private double primaryValue;

    private int[] secondaryAttributes;
    private double[] secondaryValues;

    private int level;

    private static final String[] attAbbreviations = new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP",   "HP%", "CR", "CD",  "EM", "ER", "HEAL", "PHYS", "PYRO", "ELEC", "CRYO", "HYDR", "ANEM", "GEO", "DEND"};
    private static final double[] primaryBase = new double[]     { 47,    7.0,    0,     8.7,    717,    7.0,   4.7,  9.3,   28,   7.8,  5.4,    8.7,    7.0,    7.0,    7.0,    7.0,    7.0,    7.0,   7.0};
    private static final double[] primaryIncrement = new double[]{ 13.2,  1.98,   0,     2.48,   203.15, 1.98,  1.32, 2.645, 7.95, 2.2,  1.525,  2.48,   1.98,   1.98,   1.98,   1.98,   1.98,   1.98,  1.98};

    public Artifact(String set, int piece, String primaryAttributeString, String[] secondaryAttributesString, double[] secondaryValues, int level) {
        //Constructor using specific parameters

        //Setting the foundations (i.e. what the attributes actually are)
        this.set = set;
        this.piece = piece;
        this.primaryAttribute = Arrays.asList(attAbbreviations).indexOf(primaryAttributeString);
        secondaryAttributes = new int[4];

        for (int i = 0; i < secondaryAttributes.length; i++) {
            secondaryAttributes[i] = Arrays.asList(attAbbreviations).indexOf(secondaryAttributesString[i]);
        }

        this.level = level;

        //Assigning the values to the attributes
        primaryValue = primaryBase[0] + (primaryIncrement[0] * (level - 1));
        this.secondaryValues = secondaryValues;
    }

    public Artifact(String attRecord, String valRecord) {
        //Constructor using record string
        int[] attFieldWidths = new int[]{30, 1, 1, 1, 1, 1, 1, 2};
        int[] valFieldWidths = new int[]{4, 4, 4, 4};

        String[] attFields = Database.recordToArray(attRecord, attFieldWidths);
        double[] valFields = Arrays.stream(Database.recordToArray(valRecord, valFieldWidths)).mapToDouble(Double::parseDouble).toArray();

        //Setting the foundations (i.e. what the attributes actually are)
        set = attFields[0].trim();
        piece = Integer.parseInt(attFields[1]);
        primaryAttribute = Arrays.asList(attAbbreviations).indexOf(attFields[2]);
        secondaryAttributes = Artifact.stringAttributeToInt(Arrays.copyOfRange(attFields, 3, 6));
        level = Integer.parseInt(attFields[7].trim());

        //Assigning the values to the attributes
        primaryValue = valFields[0];
        secondaryValues = Arrays.copyOfRange(valFields, 1, 4);
    }

    public String toString(Boolean isAttribute){
        //Converts artifact data into a record format
        StringBuilder finalRecord = new StringBuilder();

        if (isAttribute) {
            //If using the artifact_att file

            finalRecord.append(Database.pad(set, 30));
            finalRecord.append(Database.pad(String.valueOf(piece), 1));

            finalRecord.append(Database.pad(primaryAttribute, 4));
            for (int i = 0; i < 4; i++) {
                finalRecord.append(Database.pad(secondaryAttributes[i], 4));
            }

            finalRecord.append(Database.pad(String.valueOf(level), 2));
        } else {
            //If using the artifact_val file

            finalRecord.append(Database.pad(String.valueOf(primaryValue), 4));
            for (int i = 0; i < 4; i++) {
                finalRecord.append(Database.pad(String.valueOf(secondaryValues[i]), 4));
            }
        }

        return finalRecord.toString();
    }

    public static int[] stringAttributeToInt(String[] attributes){
        //Convert strings like ATK into a number value, which is easier to manipulate (e.g. finding the correct base stat to use, since it's just an index value)
        int[] conversion = new int[attributes.length];
        for (int i = 0; i < attributes.length; i++) {
            conversion[i] = Arrays.asList(attAbbreviations).indexOf(attributes[i]);
        }
        return conversion;
    }

    public void displayArtifact(){
        System.out.println(set);
        System.out.println(new String[]{"Flower of Life", "Plume of Death", "Sands of Eon", "Goblet of Eonothem", "Circlet of Logos"}[piece]);

        System.out.println(primaryAttribute + ": " + primaryValue);

        for (int i = 0; i < secondaryValues.length; i++) {
            System.out.println(secondaryAttributes[i] + ": " + secondaryValues[i]);
        }

        System.out.println("Level " + level);
    }
}



