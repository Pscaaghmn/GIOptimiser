public class Main {

    public static void main(String[] args) {

        //"\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Artifact Inventory\\artifact_att.txt"
        //"C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_att.txt"
        Database artifactAttributes = new Database("C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_att.txt", 44);

        //"\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Artifact Inventory\\artifact_val.txt"
        //"C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_val.txt"
        Database artifactValues = new Database("C:\\Users\\Sam\\Documents\\Genshin Optimiser\\Artifact Inventory\\artifact_val.txt",20);

        //Storing an arbitrary artifact
        Artifact testArtifact = new Artifact("Husk of Opulent Dreams", 3, "DEF%", new String[]{"ATK%", "CD", "CR", ""}, new double[]{4.7, 6.2, 3.5, 0}, 1);
        artifactAttributes.appendRecord(testArtifact.toString(true));
        artifactValues.appendRecord(testArtifact.toString(false));

        //Retrieving the file data
        Artifact readArtifact = new Artifact(artifactAttributes.getRecord(0), artifactValues.getRecord(0));
        readArtifact.display();
    }
}
