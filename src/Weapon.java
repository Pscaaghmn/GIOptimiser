public class Weapon extends Equipment{
    private int refinementRank;
    private int baseATK;
    private static final int[] fieldWidths = new int[]{40, 1, 1, 3, 2, 2, 4}; //53 total;

    public Weapon(String name, int weaponType, int refinementRank, int baseATK, String attribute, double value, int level) {
        super(name,weaponType,Equipment.strAttToInt(attribute),value,level);
        this.refinementRank = refinementRank;
        this.baseATK = baseATK;
    }

    public Weapon(String name, int weaponType) {
        super(name,weaponType,0,0,0);
        refinementRank = 1;
        baseATK = 0;
    }

    public Weapon(String record){
        String[] fields = Database.recordToArray(record, fieldWidths);

        setName(fields[0].trim());
        setType(Integer.parseInt(fields[1].trim()));
        refinementRank = Integer.parseInt(fields[2].trim());
        baseATK = Integer.parseInt(fields[3].trim());
        setPrimaryAttribute(Integer.parseInt(fields[4].trim()));
        setLevel(Integer.parseInt(fields[5].trim()));
        setPrimaryValue(Double.parseDouble(fields[6].trim()));
    }

    public String toString(){
        int[] fieldWidths = new int[]{40, 1, 1, 3, 2, 2, 4}; //53 total
        String[] connectedWeaponData = new String[]
                {getName(),
                String.valueOf(getType()),
                String.valueOf(refinementRank),
                String.valueOf(baseATK),
                String.valueOf(getPrimaryAttribute()),
                String.valueOf(getLevel()),
                String.valueOf(getPrimaryValue())};

        return super.toString(fieldWidths, connectedWeaponData, new int[]{}, new String[]{}, true);
    }

    public int getRefinementRank() {
        return refinementRank;
    }

    public void setRefinementRank(int refinementRank) {
        this.refinementRank = refinementRank;
    }

    public int getBaseATK() {
        return baseATK;
    }

    public void setBaseATK(int baseATK) {
        this.baseATK = baseATK;
    }

    public static String[] getTypes(){
        return new String[]{"Sword","Claymore","Polearm","Bow","Catalyst"};
    }

    public static String[] getAttributes(){
        return new String[]{"ATK%", "DEF%", "HP%", "Crit Rate", "Crit Damage",
                "Elemental Mastery", "Energy Recharge", "Physical DMG Bonus"};
    }

    public static int[] getAttributeFieldWidths(){
        return fieldWidths;
    }
}
