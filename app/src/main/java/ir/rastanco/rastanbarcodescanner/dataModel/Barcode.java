package ir.rastanco.rastanbarcodescanner.dataModel;

import java.io.Serializable;

/**
 * Created by ShaisteS on 12/10/2015.
 * This class is packaging barcode data.
 */
public class Barcode implements Serializable {

    private String content;
    private int format;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }
}
