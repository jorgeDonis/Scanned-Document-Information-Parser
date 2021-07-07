package thales;

public class Main {
    public static void main(String[] args) {
        ScanReader scanReader = new ConsoleScanReader();
        ScanRegistry scanRegistry = new ScanRegistry();
        while (scanReader.nextScanAvailable()) {
            Scan scan = scanReader.readSingleScan();
            try {
                scanRegistry.addScan(scan);
            } catch (ScanInsertionException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
