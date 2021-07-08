package thales;

import thales.Exception.ScanException;

public class Main {
    public static void main(String[] args) {
        try {
            ScanReader scanReader = new ConsoleScanReader();
            ScanChecker scanChecker = new ScanChecker();
            while (scanReader.nextScanAvailable()) {
                Scan scan = scanReader.readSingleScan();
                scanChecker.checkScan(scan);
            scanReader.close();
            }
        } catch (ScanException e) {
            System.err.println(e.getMessage());
        }
    }
}
