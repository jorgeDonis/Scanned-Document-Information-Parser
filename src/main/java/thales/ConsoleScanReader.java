package thales;

import java.util.ArrayList;

public class ConsoleScanReader extends ScanReader {

    @Override
    public boolean nextScanAvailable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ArrayList<Scan> readScanArray() {
        throw new RuntimeException("It's not possible to read several records from console at once");
    }

    @Override
    public Scan readSingleScan() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
