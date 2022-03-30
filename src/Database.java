import java.util.ArrayList;

public class Database {

    private final String filename;
    private final int lineLength;
    private int recordCount;

    private ArrayList<Integer> deletedRows;

    public Database(String filename, int lineLength) {
        this.filename = filename;
        this.lineLength = lineLength;
        deletedRows = new ArrayList<>();
        recordCount = getRecordCount();
    }

    public void appendRecord(String data) {
        //Adds a record to the end of the database
        if (data.length() > lineLength) {
            System.out.println("Could not append data. String too long. Data length " + data.length() + " is longer than " + lineLength);
        } else {
            FileHandler.fileWrite(filename,-1, String.format("%1$-" + lineLength + "s", data)+ "\r\n");
            recordCount++;
        }

    }

    public void deleteRecord(int rowNumber) {
        //Deletes the record from the database using a delete-flag
        deletedRows.add(rowNumber);
        FileHandler.fileWrite(filename, ((rowNumber + 1) * (lineLength + 2)) - 9, "<DEL!#>");

        recordCount--;
    }

    public String getRecord(int rowNumber) {
        //Gets a record from the database, passing through deleted records
        int deletedCount = 0;
        int i = 0;
        while (i < deletedRows.size()){
           //If the following if-statement had been integrated into the while loop, it could cause a runtime error if there were no deleted records (hence deletedRows.get(0) would crash)
           if (deletedRows.get(i) <= rowNumber) {
               //Increasing the line number to look at for each deleted record
               deletedCount++;
           } else {
               return FileHandler.fileRead(filename, (rowNumber + deletedCount) * (lineLength +1), 0);
           }
            i++;
        }
        return FileHandler.fileRead(filename, (rowNumber + deletedCount) * (lineLength +1), 0);
    }

    public int getRecordCount() {
        return FileHandler.getFileLength(filename)/(lineLength+1) - deletedRows.size();
    }

    public boolean recordExists(String data) {
        int i = 0;
        while (i < recordCount){
            if (getRecord(i).trim().equals(data)){
                return true;
            }
            i++;
        }
        return false;
    }

    public static String[] recordToArray(String record, int[] fieldWidths){
        //Converts a record string from a database file into array elements
        String[] fields = new String[fieldWidths.length];
        int beginIndex = 0;
        int endIndex;

        for (int i = 0; i < fieldWidths.length; i++) {
            endIndex = beginIndex + fieldWidths[i];
            fields[i] = record.substring(beginIndex, endIndex);
            beginIndex = endIndex;
        }

        return fields;
    }

    public static String pad(String data, int totalLen){
        //Pad field to correct length
        if (data.length() > totalLen) {
            System.out.println("String could not be padded (too long). '" + data + "' exceeds length of " + totalLen);
            return "";
        } else {
            return String.format("%1$-" + totalLen + "s", data);
        }
    }

    public static String pad(int data, int totalLen){
        //Accepting int data (then converting it)
        return pad(String.valueOf(data), totalLen);
    }
}