package thales;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import thales.Exception.ScanConsoleException;
import thales.Exception.ScanParsingException;

public class ConsoleScanReader extends ScanReader {

    class ExpectedScanField {
        private String regex;
        private Pattern pattern;
        private String fieldName;
        private String description;

        public ExpectedScanField(String regex, String fieldName, String description) {
            this.regex = regex;
            this.fieldName = fieldName;
            this.description = description;
            pattern = Pattern.compile(this.regex);
        }

        public void checkField(String comparedField) throws ScanParsingException {
            Matcher matcher = pattern.matcher(comparedField);
            if (!matcher.find())
                throw new ScanParsingException(
                    String.format("Error while parsing the %s field. Expected %s. Got %s", fieldName, description, comparedField)
                );
        }
    }

    private final ExpectedScanField[] expectedFields = {
        new ExpectedScanField("\\b\\d+\\b",       "Scan line number", "Unsigned integer"),
        new ExpectedScanField("\\b[A-Z]+\\b",     "Document type",    "P (Passport), ID or DL (Driver's License)"),
        new ExpectedScanField("\\b[A-Z]{3}\\b",   "Issuing country",  "3 letter ICAO country"),
        new ExpectedScanField("\\b[a-zA-Z]+\\b",  "Last name",        "Alphanumeric string"),
        new ExpectedScanField("\\b[a-zA-Z]+\\b",  "First name",       "Alphanumeric string"),
        new ExpectedScanField("\\b\\d+\\b",       "Document number",  "Unsigned integer"),
        new ExpectedScanField("\\b[A-Z]{3}\\b",   "Nationality",      "3 letter ICAO country"),
        new ExpectedScanField("\\b\\d{6}\\b",     "Date of birth",    "YYMMDD formatted date"),
        new ExpectedScanField("\\b\\d{6}\\b",     "Date of expiry",   "YYMMDD formatted date"),
    };

    private int noLines;
    private int noReadLines;

    private Scanner scanner;

    @Override
    public void close() {
        scanner.close();
    }

    public ConsoleScanReader() throws ScanConsoleException {
        noReadLines = 0;
        scanner = new Scanner(System.in);
        System.out.print("Total number of records: ");
        try {
            noLines = scanner.nextInt();
            if (noLines <= 0)
                throw new ScanConsoleException("Number of total records has to be positive");
        } catch (InputMismatchException e) {
            throw new ScanConsoleException("Number of total records has to be numeric");
        }
    }

    @Override
    public boolean nextScanAvailable() {
        return (noReadLines < noLines);
    }

    @Override
    public Scan[] readScanArray() {
        throw new RuntimeException("It's not possible to read several records from console at once");
    }

    private Scan parseFields(String[] fields) throws ScanParsingException {
        Scan scan = new Scan();
        if (fields.length != expectedFields.length)
            throw new ScanParsingException(String.format("Expected %d arguments, got %d",
                expectedFields.length, fields.length));
        try {
            scan.id = Long.parseLong(fields[0]);
        } catch (NumberFormatException e) {
            throw new ScanParsingException(String.format("Error parsing number %s \n %s", fields[0], e.getMessage()));
        }
        scan.docType = fields[1];
        scan.issuingCountry = fields[2];
        scan.lastName = fields[3];
        scan.firstName = fields[4];
        scan.docNumber = fields[5];
        scan.nationality = fields[6];
        scan.birthDate = fields[7];
        scan.expiryDate = fields[8];
        return scan;
    }

    private Scan parseLine(String line) throws ScanParsingException {
        if (line.endsWith(","))
            throw new ScanParsingException("Incorrectly placed commas");
        String[] readFields = line.split(",");
        try {
            for (int i = 0; i < readFields.length; ++i) {
                if (i == expectedFields.length)
                    throw new ScanParsingException(
                            String.format("Too many scan fields, expected %d", expectedFields.length));
                expectedFields[i].checkField(readFields[i]);
            }
            return parseFields(readFields);
        } catch (ScanParsingException e) {
            throw e;
        }
    }

    @Override
    public Scan readSingleScan() throws ScanParsingException {
        String line = scanner.next();
        try {
            Scan scan = parseLine(line);
            ++noReadLines;
            return scan;
        } catch (ScanParsingException e) {
            throw e;
        }
    }
}
