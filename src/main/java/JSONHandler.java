
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import Utils.Constants;
import Utils.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHandler {

    private String featureName   = ""; // Project Description / Project Name
    private String scenarioName  = ""; // Test Case Description
    private String status        = ""; // result of test case
    private String executionTime = ""; // date and time of the test execution
    private String index         = ""; // index of the test


    public JSONObject testObject;
    public JSONArray testList;
    public FileWriter file;

    public JSONHandler() {
        testObject = new JSONObject();
        testList = new JSONArray();
    }

    public void openJsonFile(String fileName) {
        try {
            file = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public void readJSON(String fileName, String extension)
    {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(Constants.inDirectoryReports + fileName + "-" + Utils.dateNowToString() + extension))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray testList = (JSONArray) obj;

            //Iterate over test array
            for (Object o : testList) {
                parseTestObject((JSONObject) o);
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void parseTestObject(JSONObject test)
    {
        //Get Feature name
        featureName = (String) test.get("name");

        //Get test object within list
        JSONArray jsonArray = (JSONArray) test.get("elements");

        //Get
        for (Object value : jsonArray) {
            JSONArray stepsArray = new JSONArray();

            if (((JSONObject) value).get("type").equals("scenario")) {
                scenarioName = ((JSONObject) value).get("name").toString();
                executionTime = ((JSONObject) value).get("start_timestamp").toString();
                index = ((JSONObject) value).get("id").toString();

                stepsArray = (JSONArray) ((JSONObject) value).get("steps");

                status = getTestCaseResult(stepsArray);

                fillJSON();
            }
        }
    }

    public void fillJSON() {
        JSONObject testDetails = new JSONObject();
        // Fields
        testDetails.put("index", index);
        testDetails.put("featureName", featureName);
        testDetails.put("scenarioName", scenarioName);
        testDetails.put("date", executionTime);
        testDetails.put("status", status);

        //Add tests to list
        testList.add(testDetails);


    }

    //Write JSON file
    public void writeJSON() {
        try {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(testList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTestCaseResult(JSONArray stepsArray) {
        boolean state = true;
        String result;

        for (Object o : stepsArray) {
            JSONObject obj = (JSONObject) ((JSONObject) o).get("result");
            state &= (obj.get("status").equals("passed"));
        }

        result = state ? "passed" : "failed";

        return result;
    }

}
