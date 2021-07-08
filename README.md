# Scanned-Document-Information-Parser
This project serves as a test for the Thales selection process

## Installation & usage

The only needed software is Java 8 and Maven. You can get both from a Debian-like distribution with `apt`

```
apt install openjdk-8-jdk openjdk-8-jre maven
```

Then clone the repository
```
git clone https://github.com/jorgeDonis/Scanned-Document-Information-Parser.git
```
And build the project (tests will also be run).
```
cd Scanned-Document-Information-Parser
mvn install
```
Finally, you can execute the main file with
```
java -jar target/scanned_document_information_parser-1.0-SNAPSHOT.jar
```

## Considerations

* The creation of a `ScanReader` abstract class only serves as an example. Such class would not exist in production code. Abstraction should only be
present when there is a certainty that several classes will inherit from the parent class. In this case, I found it plausible that said classes would exist. For example, a future `FileScanReader` which reads scans from a file would inherit from `ScanReader`.

* The check of preexisting documents in (in memory) database is done with a hash table. In Java, this is the `HashSet<Integer>` class. In this case, I chose to use an Integer as key, here's why. If I'd used `HashSet<String>` and stored the combined String, the memory usage would be much greater. Instead, I combine the String of all the necessary fields and then calculate a 32-bit hash from there (Java `hashCode()` method). This way, memory usage is reduced considerably (imagine large Strings and large ammount of scans).

* It would've been ideal to initialize the `HashSet` container initial size before calling succecive `add()` operations (to avoid copies due to container enlargement). However, this would've created a high coupling between `ScanChecker` and `ScanReader`, so I opted out of that idea. Had I not implemented the abstraction, I would've initialized the `HashSet` with the number of lines to be read.



