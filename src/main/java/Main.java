import Utils.Constants;
import Utils.Utils;

public class Main {
    public static void main(final String[] args) {
        JSONHandler jsonHandler = new JSONHandler();
        FileHandler fileHandler = new FileHandler();
        Sender sender = new Sender();
        String fileName = "";
        String extensionFileName = Constants.jsonExtension;
        String logExtensionFileName = Constants.logExtension;

        if(args[0].equalsIgnoreCase("android") )
            fileName = Constants.androidReportName;
        else if (args[0].equalsIgnoreCase("ios"))
            fileName = Constants.iosReportName;
        else {
            System.out.println(args[0] + " does not exist!");
            System.exit(1);
        }

        // Create new JSON output file
        jsonHandler.openJsonFile(Constants.elkReportPath + fileName + "-" + Utils.dateNowToString() + logExtensionFileName);

        // Copy the JSON file from Automation folder to reports one
        fileHandler.copyFile(Constants.cucumberReportPath + fileName + extensionFileName,
                Constants.elkReportPath + fileName + extensionFileName);

        // Fill the report JSON file
        jsonHandler.readJSON(fileName, extensionFileName);

        // Write the report JSON file
        jsonHandler.writeJSON();

        // Append line breaker
        fileHandler.appendNewLine(Constants.elkReportPath + fileName + "-" + Utils.dateNowToString() + logExtensionFileName);

        // delete the old JSON file from reports folder
        fileHandler.deleteFile(fileName + extensionFileName);

        // send the JSON file to LOGS_TESTES on CGD server
        sender.sendFileByFTP();

    }
}
