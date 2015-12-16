package ir.rastanco.rastanbarcodescanner.dataModel;

/**
 * Created by ShaisteS on 12/12/2015.
 * This class is packaging File information that save in user mobile.
 */
public class FileInfo {
    private String fileName;
    private String fileType;
    private String dateModified;

    public FileInfo(String name,String type,String dateM){
        fileName=name;
        fileType=type;
        dateModified=dateM;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}
