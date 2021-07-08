package thales;

import thales.Exception.ScanException;

public class Main {
    public static void main(String[] args) {
        ScanReader scanReader = new ConsoleScanReader();
        ScanChecker scanChecker = new ScanChecker();
        while (scanReader.nextScanAvailable()) {
            try {
                Scan scan = scanReader.readSingleScan();
                scanChecker.checkScan(scan);
            } catch (ScanException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
