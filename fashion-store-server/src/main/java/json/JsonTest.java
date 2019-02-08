package json;//package json;
//
//import org.json.simple.JSONObject;
//import org.json.simple.JSONObject;
//import server.Customer;
//import server.Shop;
//
//import java.io.StringWriter;
//import java.util.List;
//
//public class JsonTest {
//
//    public static JSONObject createFirstManagerToJson (String details) {
//        JSONObject detailsObj = new JSONObject();
//        detailsObj.put("details",details);
//        return detailsObj;
//    }
//
//    public static String CreateFirstManagerFromJson(JSONObject detailsJson) {
////        StringWriter out = new StringWriter();
//        String details = detailsJson.get("details").toString();
//        return details;
//    }
//
//    public static JSONObject logInDetailsToJson(String userName,String password) {
//        JSONObject logInObj = new JSONObject();
//        logInObj.put("userName",userName);
//        logInObj.put("password",password);
//        return logInObj;
//    }
//
//    public static String [] logInDetailsFromJson(JSONObject logInDetails) {
////        StringWriter out = new StringWriter();
//        String [] details = new String[2];
//        details[0] = logInDetails.get("userName").toString();
//        details[1] = logInDetails.get("password").toString();
//
//        return details;
//    }
//
//    public static JSONObject createNewEmployeeToJson(String typeEmployee,String details) {
//        JSONObject newEmployeeObj = new JSONObject();
//        newEmployeeObj.put("type",typeEmployee);
//        newEmployeeObj.put("details",details);
//        return newEmployeeObj;
//    }
//
//    public static String[] createNewEmployeeFromJson(JSONObject newEmployeeDetails) {
////        StringWriter out = new StringWriter();
//        String [] details = new String[2];
//        details[0] = newEmployeeDetails.get("type").toString();
//        details[1] = newEmployeeDetails.get("details").toString();
//        return details;
//    }
//
//    public static JSONObject createNewCustomerToJson (String details) {
//        JSONObject newCustomerObj = new JSONObject();
//        newCustomerObj.put("details",details);
//        return newCustomerObj;
//    }
//
//    public static String createNewCustomerFromJson(JSONObject detailsNewCustomer) {
////        StringWriter out = new StringWriter();
//        String detailsCustomer = detailsNewCustomer.get("details").toString();
//        return detailsCustomer;
//    }
//
//
//}
//
