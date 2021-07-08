package thales;

public class Scan {
    //Represents the order in which the scans are input
    public long id;

    //Unique document identifier
    public String docNumber;

    //Passport (P), ID card (ID) or driver license (DL)
    public String docType;

    //ICAO three-letter country (ESP, FRA, POR, AND or MOR)
    public String issuingCountry, nationality;

    public String firstName, lastName;

    //Dates in format YYMMDD
    public String birthDate, expiryDate;
}
