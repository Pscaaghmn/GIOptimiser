import java.awt.*;

public class Main {

    public static void main(String[] args) {
        String filePath = (false?"D:\\Java Projects\\GIOptimiser\\":"C:\\Users\\16huynh_s\\IdeaProjects\\GIOptimiser\\src\\");

        MainFrame mf = new MainFrame(filePath);
        mf.setBounds(0,0, 1500, 800);
        mf.setPreferredSize(new Dimension(1500,800));
        mf.setResizable(true);
        mf.setVisible(true);

/* ------------------------------------------------------------------------------------------------------------------------------------------
        //  "\\\\bex-file-01\\studenthome$\\16\\16Huynh_S\\Genshin Optimiser\\Artifact Inventory\\artifact_att.txt"
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

        //Storing an arbitrary artifact:
        Artifact testArtifact = new Artifact("Husk of Opulent Dreams", 3, "DEF%", new String[]{"ATK%", "CD", "CR", ""}, new double[]{4.7, 6.2, 3.5, 0}, 1);
        artifactAttributes.appendRecord(testArtifact.toString(true));
        artifactValues.appendRecord(testArtifact.toString(false));

 */

    }
}
