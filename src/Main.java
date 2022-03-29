public class Main {

    public static void main(String[] args) {

        Database artifactAttributes = new Database("C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_att.txt", 38);
        Database artifactValues = new Database("C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_val.txt",16);

        Artifact testArtifact = new Artifact("Husk of Opulent Dreams", 4, "DEF%", new String[]{"ATK%", "CD", "CR", ""}, new double[]{4.7, 6.2, 3.5, 0}, 0);
        artifactAttributes.appendRecord(testArtifact.toString(true));
        artifactValues.appendRecord(testArtifact.toString(false));

        testArtifact.displayArtifact();

        Artifact duplicate = new Artifact(artifactAttributes.getRecord(0), artifactValues.getRecord(0));

        duplicate.displayArtifact();
    }
}
