package thales;

public class Scan {
    //Represents the order in which the scans are input
    private long id;

    //Unique document identifier
    private long docNumber;

    //Passport (P), ID card (ID) or driver license (DL)
    private String docType;

    //ICAO three-letter country (ESP, FRA, POR, AND or MOR)
    private String issuingCountry, nationality;

    private String firstName, lastName;

    //Dates in format YYMMDD
    private String birthDate, expiryDate;
}
