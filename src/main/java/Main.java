import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//public class Main {
//    public static void main(String[] args) {
//
//       // divide(4,0);
//       // System.out.println( compareDogs("Rex", "Apollo"));
//
//    }
//
//    public static int divide(int a, int b) {
//        int c = -1;
//
//        try {
//            c = a / b;
//        }
//        catch (Exception e) {
//            System.err.print("Exception ");
//        }
//        finally {
//            System.err.println("Finally ");
//        }
//
//        return c;
//    }
//
//    public static String compareDogs(String firstDogName, String secondDogName) {
//        String firstDog = new String(firstDogName);
//        String secondDog = new String(secondDogName);
//
//        return String.format("%s - %s - %s", firstDog.equals(secondDog), firstDog.equals("Rex"), "pet" == "pet");
//    }
//}

class Solution {
    /*
     * Complete the function below.
     *https://jsonmock.hackerrank.com/api/countries/search?name=
     */

    static int getCountries(String s, int p) throws Exception {

        String out =
                new Scanner(new URL("https://jsonmock.hackerrank.com/api/countries/search?name=" + s)
                        .openStream()
                        , "UTF-8").useDelimiter("\\A").next();


        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("javascript");
        String script = "Java.asJSONCompatible(" + out + ")";
        Map result = (Map) engine.eval(script);
        System.out.print(result);
        System.out.print(out);

        System.out.println(result.size());

        List data = (List)result.get("data");
        int totalPagers = (Integer)result.get("total_pages");

        int count = 0;

        for (int i=1 ; i<=totalPagers; i++){

            String outPerPage =
                    new Scanner(new URL("https://jsonmock.hackerrank.com/api/countries/search?name=" + s +"&page=" + i)
                            .openStream()
                            , "UTF-8").useDelimiter("\\A").next();

            String scriptPerPage = "Java.asJSONCompatible(" + outPerPage + ")";
            Map resultPerPage = (Map) engine.eval(scriptPerPage);
            System.out.print(resultPerPage);
            System.out.print(outPerPage);

            System.out.println(resultPerPage.size());

            List dataPerPage = (List)resultPerPage.get("data");

            for(Object country : dataPerPage){
                int population = (Integer)((Map)country).get("population");
                if(population>p){
                    count++;
                }
            }
        }

        System.out.println(count);
        return count;
    }


    public static void main(String[] args) throws Exception {
        getCountries("in",1000000);
    }


}
