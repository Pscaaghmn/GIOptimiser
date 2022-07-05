import java.util.ArrayList;

public class Database {

    private final String filename;
    private final int lineLength;
    private int recordCount;

    private final ArrayList<Integer> deletedRows;

    public Database(String filename, int lineLength) {
        this.filename = filename;
        this.lineLength = lineLength;
        deletedRows = new ArrayList<>();
        recordCount = -1;
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
        if (getRecordCount()==0){
            System.out.println("Nothing to delete");
        }

        int row = rowWithOffset(rowNumber);

        int i = 0;

        while (i < deletedRows.size()){
            if (row < deletedRows.get(i)) {
                break;
            }
            i++;
        }

        deletedRows.add(i, row);
        FileHandler.fileWrite(filename, ((row+1) * (lineLength + 2)) - 9, "<DEL!#>" + "\r\n");

        recordCount--;
    }

    public String getRecord(int rowNumber) {
        //Gets a record from the database, passing through deleted records
        return FileHandler.fileRead(filename, rowWithOffset(rowNumber) * (lineLength + 2), 0);
    }

    public String getRecord(int rowNumber, boolean overrideOffset) {
        //Gets a record from the database, passing through deleted records
        return FileHandler.fileRead(filename, (overrideOffset ? rowNumber : rowWithOffset(rowNumber)) * (lineLength + 2), 0);
    }

    public int getRecordCount() {
        if(recordCount == -1) {

            int rawLines = FileHandler.getFileLength(filename) / (lineLength + 1);
            int records = 0;
            for (int i = 0; i < rawLines; i++) {

                if (getRecord(i, true).startsWith("<DEL!#>", lineLength - 7)) {
                    deletedRows.add(i);
                } else {
                    records++;
                }
            }

            return records;

        }else{
            return (FileHandler.getFileLength(filename) / (lineLength + 1)) - deletedRows.size();
        }
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

    private int rowWithOffset(int rowNumber){
        //Offsetting original row number because of deleted record debris
        int row = rowNumber;

        int i = -1;
        while (i < row) {
            i++;
            if (deletedRows.contains(i)){
                row++;
            }
        }

        return row;
    }
}