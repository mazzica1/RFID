package nt.esraakhaled.com.rfid.Controllers.Interfaces;

/**
 * Created by OSX on 8/20/17.
 */

public interface UHFReaderDelegate {
    public void tagEPCRead(String epc);
    public void tagEPCDataRead(String data);
}
