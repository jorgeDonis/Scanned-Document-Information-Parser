package thales;

import java.util.LinkedList;
import java.util.List;

import thales.Exception.ScanCheckException;
import thales.Exception.ScanParsingException;

public class Main {
    public static void main(String[] args) {
        try {
            ScanReader scanReader = new ConsoleScanReader();
            ScanChecker scanChecker = new ScanChecker();
            List<String> scanErrors = new LinkedList<>();
            while (scanReader.nextScanAvailable()) {
                try {
                    Scan scan = scanReader.readSingleScan();
                    scanChecker.checkScan(scan);
                } catch (ScanParsingException e) {
                    System.err.println(e.getMessage());
                } catch (ScanCheckException e) {
                    scanErrors.add(e.getMessage());
                }
            }
            System.err.println("### ERRORS ###");
            for (String error : scanErrors)
                System.err.println(error);
            scanReader.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
