package thales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Test;

import thales.Exception.ScanCheckException;
import thales.Exception.ScanParsingException;

public class ScanCheckerTest {
    

    static LinkedList<String> expectedErrors = new LinkedList<>();

    static void initExpectedErrors() {
        expectedErrors.add("2, Person PEREZ, JAVIER, (020403) from ESP was already present in the DB.");
        expectedErrors.add("3, HRV is not a valid ICAO country");
        expectedErrors.add("4, USA is not a valid ICAO country");
        expectedErrors.add("5, ITA is not a valid ICAO country");
    }

    private static void assertListEquals(LinkedList<String> expected, LinkedList<String> actual) {
        if (expected.size() != actual.size())
            fail("Size mismatch during list assertion");
        Iterator<String> expectedIter = expected.iterator();
        Iterator<String> actualIterator = actual.iterator();
        while (expectedIter.hasNext()) {
            String expectedString = expectedIter.next().toString();
            String actualString = actualIterator.next().toString();
            assertEquals(expectedString, actualString);
        }
    }

    @Test
    public void testErrorInput() {
        try {
            initExpectedErrors();
            System.setIn(new FileInputStream("resources/test/bad_inputs_test.txt"));
            ConsoleScanReader consoleReader = new ConsoleScanReader();
            ScanChecker checker = new ScanChecker();
            LinkedList<String> errors = new LinkedList<>();
            while (consoleReader.nextScanAvailable()) {
                try {
                    Scan scan = consoleReader.readSingleScan();
                    checker.checkScan(scan);
                } catch (ScanCheckException e) {
                    errors.add(e.getMessage());
                } catch (ScanParsingException e) {
                    fail();
                }
            }
            consoleReader.close();
            assertListEquals(expectedErrors, errors);
        } catch (Exception e) {
            fail();
        }
    }
    
}
