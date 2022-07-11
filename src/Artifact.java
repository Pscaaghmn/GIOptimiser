import java.util.Arrays;

public class Artifact extends Equipment{
    private int[] secondaryAttributes;
    private double[] secondaryValues;

    private static final double[] primaryBase = new double[]     {47,    7.0,    0,     8.7,    717,    7.0,   4.7,  9.3,   28,   7.8,  5.4,    8.7,    7.0,    7.0,    7.0,    7.0,    7.0,    7.0,   7.0};
    private static final double[] primaryIncrement = new double[]{13.2,  1.98,   0,     2.48,   203.15, 1.98,  1.32, 2.645, 7.95, 2.2,  1.525,  2.48,   1.98,   1.98,   1.98,   1.98,   1.98,   1.98,  1.98};
    private static final int[] attFieldWidths = new int[]{30, 1, 2, 1, 1, 1, 1, 2};
    private static final int[] valFieldWidths = new int[]{4, 4, 4, 4, 4};

    public Artifact(String set, int piece, String primaryAttributeString, String[] secondaryAttributesString, double[] secondaryValues, int level) {
        super(set,
                piece,
                Equipment.strAttToInt(primaryAttributeString),
                primaryBase[Equipment.strAttToInt(primaryAttributeString)] + (primaryIncrement[Equipment.strAttToInt(primaryAttributeString)] * (level)),
                level);
        secondaryAttributes = Equipment.strAttToInt(secondaryAttributesString);
        this.secondaryValues = secondaryValues;
    }

    public Artifact(String set, int piece) {
        super(set, piece,0,0,0);
        secondaryAttributes = new int[]{0,1,2,3};
        this.secondaryValues = new double[]{0,0,0,0};
    }

    public Artifact(String attRecord, String valRecord) {
        //Constructor using record string
        String[] attFields = Database.recordToArray(attRecord, attFieldWidths);
        double[] valFields = Arrays.stream(Database.recordToArray(valRecord, valFieldWidths)).mapToDouble(Double::parseDouble).toArray();

        //Setting the foundations (i.e. what the attributes actually are)
        setName(attFields[0].trim());
        setType(Integer.parseInt(attFields[1].trim()));
        setPrimaryAttribute(Integer.parseInt(attFields[2].trim()));
        secondaryAttributes = new int[4];
        for (int i = 0; i < 4; i++) {
            secondaryAttributes[i] = Integer.parseInt(attFields[3 + i].trim());
        }
        setLevel(Integer.parseInt(attFields[7].trim()));

        //Assigning the values to the attributes
        setPrimaryValue(valFields[0]);
        secondaryValues = Arrays.copyOfRange(valFields, 1, 5);
    }

    public String toString(Boolean isAttribute){
        String[] connectedAttData = new String[]
                {getName(),
                String.valueOf(getType()),
                String.valueOf(getPrimaryAttribute()),
                String.valueOf(secondaryAttributes[0]),
                String.valueOf(secondaryAttributes[1]),
                String.valueOf(secondaryAttributes[2]),
                String.valueOf(secondaryAttributes[3]),
                String.valueOf(getLevel())};

        String[] connectedValData = new String[]
                {String.valueOf(getPrimaryValue()),
                String.valueOf(secondaryValues[0]),
                String.valueOf(secondaryValues[1]),
                String.valueOf(secondaryValues[2]),
                String.valueOf(secondaryValues[3])};

        return super.toString(attFieldWidths, connectedAttData, valFieldWidths, connectedValData, isAttribute);
    }

    @Override
    public void display(){
        System.out.println(getName());
        System.out.println(new String[]{"Flower of Life", "Plume of Death", "Sands of Eon", "Goblet of Eonothem", "Circlet of Logos"}[getType()]);

        super.display();
        for (int i = 0; i < secondaryValues.length; i++) {
            if (secondaryAttributes[i] != -1){
                System.out.println("\u001B[35m" + Equipment.intAttToStr(secondaryAttributes[i]) + ": " + secondaryValues[i] + "\u001B[0m");
            }
        }
    }

    public static String[] getTypes(){
        return new String[]{"Flower","Plume","Sands","Goblet","Circlet"};
    }


    public static int[] getAttributeFieldWidths(){
        return attFieldWidths;
    }

    public int[] getSecondaryAttributes() {
        return secondaryAttributes;
    }

    public double[] getSecondaryValues() {
        return secondaryValues;
    }

    public static int[] getValFieldWidths(){
        return valFieldWidths;
    }

    public void setSecondaryAttributes(int[] secondaryAttributes) {
        this.secondaryAttributes = secondaryAttributes;
    }

    public void setSecondaryValues(double[] secondaryValues) {
        this.secondaryValues = secondaryValues;
    }

    public static double[] getPrimaryIncrement(){
        return primaryIncrement;
    }
    public static double[] getPrimaryBase(){
        return primaryBase;
    }
}



