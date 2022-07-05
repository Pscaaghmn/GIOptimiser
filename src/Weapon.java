public class Weapon extends Equipment{
    private String name;
    private int weaponType;
    private int refinementRank;
    private int baseATK;

    public Weapon(String name, int weaponType, int refinementRank, int baseATK, String attribute, double value, int level) {
        this.name = name;
        this.weaponType = weaponType;
        this.refinementRank = refinementRank;
        this.baseATK = baseATK;

        setPrimaryAttribute(Equipment.strAttToInt(attribute));
        setLevel(level);
        setPrimaryValue(value);
    }

    public Weapon(String name, int weaponType) {
        this.name = name;
        this.weaponType = weaponType;

        //Preset
        refinementRank = 1;
        baseATK = 0;
        setPrimaryAttribute(Equipment.strAttToInt("ATK%"));
        setLevel(0);
        setPrimaryValue(0);
    }

    public Weapon(String record){
        int[] fieldWidths = new int[]{40, 1, 1, 3, 2, 2, 4}; //53 total

        String[] fields = Database.recordToArray(record, fieldWidths);

        name = fields[0].trim();
        weaponType = Integer.parseInt(fields[1].trim());
        refinementRank = Integer.parseInt(fields[2].trim());
        baseATK = Integer.parseInt(fields[3].trim());
        setPrimaryAttribute(Integer.parseInt(fields[4].trim()));
        setLevel(Integer.parseInt(fields[5].trim()));
        setPrimaryValue(Integer.parseInt(fields[6].trim()));
    }

    public String toString(){
        int[] fieldWidths = new int[]{40, 1, 1, 3, 2, 2, 4}; //53 total
        String[] connectedWeaponData = new String[]
                {name,
                String.valueOf(weaponType),
                String.valueOf(refinementRank),
                String.valueOf(baseATK),
                String.valueOf(getPrimaryAttribute()),
                String.valueOf(getLevel()),
                String.valueOf(getPrimaryValue())};

        return super.toString(fieldWidths, connectedWeaponData, new int[]{}, new String[]{}, true);
    }
}
