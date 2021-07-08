package thales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;

import org.junit.Test;

import thales.Exception.ScanParsingException;

public class ConsoleReaderTest 
{
    @Test
    public void testValidInputs() {
        try {
            System.setIn(new FileInputStream("resources/test/valid_inputs_test.txt"));
            ConsoleScanReader consoleReader = new ConsoleScanReader();
            while (consoleReader.nextScanAvailable()) {
                consoleReader.readSingleScan();
            }
            consoleReader.close();
        }  catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testWrongCountry() {
        try {
            System.setIn(new FileInputStream("resources/test/test_parse_country.txt"));
            ConsoleScanReader consoleReader = new ConsoleScanReader();
            consoleReader.readSingleScan();
            fail();
            consoleReader.close();
        } catch (ScanParsingException e) {
            assertEquals("Error while parsing the Issuing country field. Expected 3 letter ICAO country. Got ESPS", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * More tests regarding each field should be implemented.
     */
}
