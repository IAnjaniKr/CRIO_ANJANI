package qtriptest;

import java.io.File;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportSingleton {

    private static ExtentReports extent;
    private static ExtentTest test;

    private ReportSingleton() {
        // Get the current working directory
        String currentWorkingDir = System.getProperty("user.dir");

        // Initialize ExtentReports
        /*****
         System.getProperty("user.dir") is a command in Java 
         that retrieves the current working directory of the Java application.
         In terms of a Gradle project, 
         this method returns the full path to the directory where the build.gradle file is located, 
         which is usually the “app” directory- in case of this project: “/home/crio-user/workspace/bytes/me_extent_reports/app” 
         */
        extent = new ExtentReports(currentWorkingDir + "/test-output/extent-report.html", true);

        // Load configuration from XML
        ///home/crio-user/workspace/anjanikmr51-ME_QTRIP_QA_V2/app/extent_customization_configs.xml
        
        extent.loadConfig(new File(currentWorkingDir+"extent_customization_configs.xml"));
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            new ReportSingleton(); // Initialize the singleton if it's null
        }
        return extent;
    }

    public static ExtentTest createTest(String testName, String testDescription) {
        test = extent.startTest(testName, testDescription);
        return test;
    }

    public static void log(LogStatus status, String message) {
        test.log(status, message);
    }

    public static void flushReport() {
        extent.flush();
    }
}