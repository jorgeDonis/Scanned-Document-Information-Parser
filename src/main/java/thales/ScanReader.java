package thales;

import java.util.ArrayList;

/**
 * This class is presented as in interface in order to allow easy integration
 * of new kinds of readers. For example, in the future, a FileScanReader could
 * be implemented.
 */
public abstract class ScanReader {
    /**
     * Performs the reading of a single record.
     * Performs any kind of initialization, if necessary.
     * @return Single scan if reading was succesful.
     */
    public abstract Scan readSingleScan();

    public abstract boolean nextScanAvailable();

    /**
     * Performs the reading of several records in one go. This method is added for
     * efficiency purposes (it is more efficient to read a text file all at once
     * rather than line by line). Performs any kind of initialization, if necessary.
     * 
     * Could be used by the (not yet implemented) FileScanReader
     * 
     * @return Array of scans
     */
    public abstract ArrayList<Scan> readScanArray();
}
