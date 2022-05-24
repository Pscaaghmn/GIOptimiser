import java.util.Arrays;

public class Artifact extends Equipment{
    //Flower: 0, Plume: 1, Sands: 2, Goblet: 3, Circlet: 4
    //Attribute indexes: ATK(0) ATK%(1) DEF(2) DEF%(3) HP(4) HP%(5) CR(6) CD(7) EM(8) ER(9) HEAL(10) PHYS(11) PYRO(12) ELEC(13) CRYO(14) HYDR(15) ANEM(16) GEO(17) DEND(18)

    private final String set;
    private final int piece;

    private int[] secondaryAttributes;
    private double[] secondaryValues;

    private static final double[] primaryBase = new double[]     {47,    7.0,    0,     8.7,    717,    7.0,   4.7,  9.3,   28,   7.8,  5.4,    8.7,    7.0,    7.0,    7.0,    7.0,    7.0,    7.0,   7.0};
    private static final double[] primaryIncrement = new double[]{13.2,  1.98,   0,     2.48,   203.15, 1.98,  1.32, 2.645, 7.95, 2.2,  1.525,  2.48,   1.98,   1.98,   1.98,   1.98,   1.98,   1.98,  1.98};

    public Artifact(String set, int piece, String primaryAttributeString, String[] secondaryAttributesString, double[] secondaryValues, int level) {
        //Constructor using specific parameters

        //Setting the foundations (i.e. what the attributes actually are)
        this.set = set;
        this.piece = piece;
        setPrimaryAttribute(Equipment.strAttToInt(primaryAttributeString));
        secondaryAttributes = Equipment.strAttToInt(secondaryAttributesString);

        setLevel(level);

        //Assigning the values to the attributes
        setPrimaryValue(primaryBase[getPrimaryAttribute()] + (primaryIncrement[getPrimaryAttribute()] * (level - 1)));
        this.secondaryValues = secondaryValues;
    }

    public Artifact(String set, int piece, int primaryAttributeInt, int[] secondaryAttributesInt, double[] secondaryValues, int level) {
        //Constructor using specific parameters, with int attributes already

        //Setting the foundations (i.e. what the attributes actually are)
        this.set = set;
        this.piece = piece;
        setPrimaryAttribute(primaryAttributeInt);
        secondaryAttributes = secondaryAttributesInt;

        setLevel(level);

        //Assigning the values to the attributes
        setPrimaryValue(primaryBase[getPrimaryAttribute()] + (primaryIncrement[getPrimaryAttribute()] * (level - 1)));
        this.secondaryValues = secondaryValues;
    }

    public Artifact(String attRecord, String valRecord) {
        //Constructor using record string
        int[] attFieldWidths = new int[]{30, 2, 2, 2, 2, 2, 2, 2};
        int[] valFieldWidths = new int[]{4, 4, 4, 4, 4};

        String[] attFields = Database.recordToArray(attRecord, attFieldWidths);
        double[] valFields = Arrays.stream(Database.recordToArray(valRecord, valFieldWidths)).mapToDouble(Double::parseDouble).toArray();

        //Setting the foundations (i.e. what the attributes actually are)
        set = attFields[0].trim();
        piece = Integer.parseInt(attFields[1].trim());
        setPrimaryAttribute(Integer.parseInt(attFields[2].trim()));
        secondaryAttributes = new int[4];
        for (int i = 0; i < 4; i++) {
            secondaryAttributes[i] = Integer.parseInt(attFields[3 + i].trim());
        }
        setLevel(Integer.parseInt(attFields[7].trim()));

        //Assigning the values to the attributes
        setPrimaryValue(valFields[0]);
        secondaryValues = Arrays.copyOfRange(valFields, 1, 4);
    }

    public String toString(Boolean isAttribute){
        //Converting Artifact data to database format
        int[] attFieldWidths = new int[]{30, 2, 2, 2, 2, 2, 2, 2};
        String[] connectedAttData = new String[]
                {set,
                String.valueOf(piece),
                String.valueOf(getPrimaryAttribute()),
                String.valueOf(secondaryAttributes[0]),
                String.valueOf(secondaryAttributes[1]),
                String.valueOf(secondaryAttributes[2]),
                String.valueOf(secondaryAttributes[3]),
                String.valueOf(getLevel())};

        int[] valFieldWidths = new int[]{4, 4, 4, 4, 4};
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
        System.out.println(set);
        System.out.println(new String[]{"Flower of Life", "Plume of Death", "Sands of Eon", "Goblet of Eonothem", "Circlet of Logos"}[piece]);

        super.display();
        for (int i = 0; i < secondaryValues.length; i++) {
            if (secondaryAttributes[i] != -1){
                System.out.println("\u001B[35m" + Equipment.intAttToStr(secondaryAttributes[i]) + ": " + secondaryValues[i] + "\u001B[0m");
            }
        }
    }
}



