import java.util.Arrays;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        String set;
        int piece;
        String primaryAttribute;
        String[] secondaryAttributes;
        double[] secondaryValues;
        int level;

        //   "\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Artifact Inventory\\artifact_att.txt"
        //  "C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_att.txt"
        Database artifactAttributes = new Database("C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_att.txt", 44);

        //  "\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Artifact Inventory\\artifact_val.txt"
        //  "C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_val.txt"
        Database artifactValues = new Database("C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_val.txt",20);

        //  "\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Equipment Names\\artifacts.txt"
        //
        Database artifactNames = new Database("\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Equipment Names\\artifacts.txt", 30);

        //  "\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Equipment Names\\weapons\\.txt"
        //
        Database swordNames = new Database("\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Equipment Names\\weapons\\swords.txt", 40);
        Database claymoreNames = new Database("\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Equipment Names\\weapons\\claymores.txt", 40);
        Database polearmNames = new Database("\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Equipment Names\\weapons\\polearms.txt", 40);
        Database bowNames = new Database("\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Equipment Names\\weapons\\bows.txt", 40);
        Database catalystNames = new Database("\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Equipment Names\\weapons\\catalysts.txt", 40);

        //Storing an arbitrary artifact
        Artifact testArtifact = new Artifact("Husk of Opulent Dreams", 3, "DEF%", new String[]{"ATK%", "CD", "CR", ""}, new double[]{4.7, 6.2, 3.5, 0}, 1);
        artifactAttributes.appendRecord(testArtifact.toString(true));
        artifactValues.appendRecord(testArtifact.toString(false));

        String[] equipment = ("The Stringless\n" +
                "Alley Hunter\n" +
                "Blackcliff Warbow\n" +
                "Compound Bow\n" +
                "Favonius Warbow\n" +
                "Prototype Crescent\n" +
                "Royal Bow\n" +
                "Rust\n" +
                "Sacrificial Bow\n" +
                "The Viridescent Hunt\n" +
                "Windblume Ode\n" +
                "Mitternachts Waltz\n" +
                "Hamayumi\n" +
                "Predator\n" +
                "Mouun's Moon\n" +
                "Skyward Harp\n" +
                "Amos' Bow\n" +
                "Elegy for the End\n" +
                "Thundering Pulse\n" +
                "Polar Star").split("\n");
        Arrays.sort(equipment);

        for (String item:
                equipment) {
           bowNames.appendRecord(Database.pad(item, 40));
        }

/*
        System.out.print("Enter set: ");
        set = userInput.nextLine();

        System.out.print("Enter piece: ");
        piece = userInput.nextInt();

        System.out.print("Enter primary attribute: ");
        primaryAttribute = userInput.nextLine();

        System.out.print("Enter secondary attributes: ");
        secondaryAttributes = userInput.nextLine().split(",");

        System.out.print("Enter secondary values: ");
        secondaryValues = Arrays.stream(userInput.nextLine().split(",")).mapToDouble(Double::parseDouble).toArray();

        System.out.print("Enter level: ");
        level = userInput.nextInt();

        Artifact userArtifact = new Artifact("Husk of Opulent Dreams", 3, "DEF%", new String[]{"ATK%", "CD", "CR", ""}, new double[]{4.7, 6.2, 3.5, 0}, 1);
        artifactAttributes.appendRecord(testArtifact.toString(true));
        artifactValues.appendRecord(testArtifact.toString(false));


        //Retrieving the file data
        Artifact readArtifact = new Artifact(artifactAttributes.getRecord(0), artifactValues.getRecord(0));
        readArtifact.display();

 */
    }
}
