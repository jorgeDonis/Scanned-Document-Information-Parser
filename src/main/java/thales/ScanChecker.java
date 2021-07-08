package thales;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import thales.Exception.ScanCheckException;

public class ScanChecker {

    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMdd");

    private LocalDate checkDateValidity(String date) throws ScanCheckException {
        try {
            return LocalDate.parse(date, dateFormatter);
        } catch (DateTimeException e) {
            throw new ScanCheckException(String.format("Wrong date %s. %s", date, e.getMessage()));
        }
    }

    private static long LAST_READ_ID = 0;

    private void checkScanID (long id) throws ScanCheckException {
        if (id != (LAST_READ_ID + 1))
            throw new ScanCheckException(String.format("Expected %d as scan id.", LAST_READ_ID + 1));
        LAST_READ_ID = id;
    }

    private static HashSet<Integer> scannedPeople = new HashSet<>();

    private void checkPersonNotAlreadyScanned(String firstName, String lastName, String nationality, String birthDate) 
                                              throws ScanCheckException {
        String key = firstName + lastName + nationality + birthDate;
        Integer hashedKey = key.hashCode();
        if (!scannedPeople.add(hashedKey))
            throw new ScanCheckException(String.format("Person %s, %s, (%s) from %s was already present in the DB.",
                lastName, firstName, birthDate, nationality));
    }

    private static HashSet<Integer> scannedDocuments = new HashSet<>();

    private void checkDocumentNotAlreadyScanned(String docNumber, String country, String docType) throws ScanCheckException {
        String key = docNumber + country + docType;
        Integer hashedKey = key.hashCode();
        if (!scannedDocuments.add(hashedKey))
            throw new ScanCheckException(String.format("Document with id %s of type %s from %s was already present in the DB.",
                    docNumber, docType, country));
    }

    private void checkExpiryDate(LocalDate expiryDate) throws ScanCheckException {
        if (expiryDate.compareTo(LocalDate.now()) < 0)
            throw new ScanCheckException("Document has expired.");
    }

    private static final String[] validICAOsArray = { "ESP", "FRA", "POR","AND","MOR" };

    private static HashSet<String> validICAOsSet;

    private static final String[] validDocsArray = { "P", "ID", "DL" };
    
    private static HashSet<String> validDocsSet;

    private void checkICAO(String country) throws ScanCheckException {
        if (!validICAOsSet.contains(country))
            throw new ScanCheckException(String.format("%s is not a valid ICAO country", country));
    }

    private void checkValidDocumentType(String docType) throws ScanCheckException {
        if (!validDocsSet.contains(docType))
            throw new ScanCheckException(String.format("%s is not a valid document type. Try P, ID or DL", docType));
    }

    public void checkScan(Scan scan) throws ScanCheckException {
        try {
            checkScanID(scan.id);
            checkValidDocumentType(scan.docType);
            checkICAO(scan.issuingCountry);
            checkICAO(scan.nationality);
            LocalDate expirtyDate = checkDateValidity(scan.expiryDate);
            checkDateValidity(scan.birthDate);
            checkPersonNotAlreadyScanned(scan.firstName, scan.lastName, scan.nationality, scan.birthDate);
            checkDocumentNotAlreadyScanned(scan.docNumber, scan.issuingCountry, scan.docType);
            checkExpiryDate(expirtyDate);
        } catch (ScanCheckException e) {
            throw new ScanCheckException(String.format("%d, %s", scan.id, e.getMessage()));
        }
    }

    private void initValidICAOsSet() {
        validICAOsSet = new HashSet<>(validICAOsArray.length);
        for (String ICAO : validICAOsArray)
            validICAOsSet.add(ICAO);
    }

    private void initValidDocsSet() {
        validDocsSet = new HashSet<>(validDocsArray.length);
        for (String docType : validDocsArray)
            validDocsSet.add(docType);
    }

    public ScanChecker() {
        initValidICAOsSet();
        initValidDocsSet();
    }
}
