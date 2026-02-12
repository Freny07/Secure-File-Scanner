Features of the Secure File Scanner:
> Scans user-specified directories while safely ignoring system and hidden files.

> Detects Personally Identifiable Information (PII) such as emails and sensitive data using regex patterns.

> Identifies malicious code patterns including SQL injection and XSS indicators.

> Classifies detected issues into risk levels (High, Medium, Low).

> Displays real-time scan statistics for total, high-risk, and medium-risk findings.

> Allows sorting of detected files based on file size in ascending or descending order.

> Provides an option to delete infected or suspicious files directly from the interface.

> Supports exporting a detailed audit log of scan results for reporting and analysis.


To view this file:

> Fork this repository.

> Clone the forked repository to your selected folder.

> Open terminal in Intellij IDEA and make sure you are in the project root file a.k.a. scanner.

> Make sure to have springboot related packages installed globablly.

> Then run: .\mvnw.cmd spring-boot:run

> Open it on http://localhost:8081/ in your web browser.
