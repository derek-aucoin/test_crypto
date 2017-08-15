package census.generator;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Map;

import org.json.JSONArray;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Generator {
  public static void main(String[] args) throws Exception {
    pull();
    // URL resource = Resources.getResource("coinbaseUSD.csv");
    // test(resource);

    // for (int i = 0; i < 10000; i++){
    // int j = getSolicitation();
    // System.out.println(j);
    // }
  }

  public static void pull() {
    try {
      HttpResponse<JsonNode> jsonResponse = Unirest
          .get("https://api.gdax.com/products/BTC-USD/trades")
          .header("accept", "application/json").asJson();
//      System.out.println(jsonResponse.getStatus());
//      System.out.println(jsonResponse.getStatusText());
//      System.out.println(jsonResponse.getHeaders());
      JSONArray trades = jsonResponse.getBody().getArray();
      trades.length();
      Trade t = new Trade(trades.getJSONObject(0));
      System.out.println(t.getSize());
      System.out.println(trades.get(0));
      System.out.println(trades.get(1));
      System.out.println(trades.get(99));
    } catch (UnirestException e) {
      System.out.println("unirest call failed");
      e.printStackTrace();
    }
  }

  public static void test(URL existing) throws Exception {
    File file = new File(existing.toURI());

    ICsvMapReader mapReader = null;
    // ICsvMapWriter mapWriter = null;
    try {
      CsvPreference prefs = CsvPreference.STANDARD_PREFERENCE;
      mapReader = new CsvMapReader(new FileReader(file), prefs);
      // mapWriter = new CsvMapWriter(new FileWriter("output.csv"), prefs);

      // header used to read the original file
      final String[] readHeader = mapReader.getHeader(true);

      // header used to write the new file
      // (same as 'readHeader', but with additional column)
      // final String[] writeHeader = new String[readHeader.length + 6];
      // System.arraycopy(readHeader, 0, writeHeader, 6, readHeader.length);
      // final String hprogram = "Program";
      // final String hcycle = "Cycle";
      // final String hunit = "ReportingUnit";
      // final String hsolicit = "Solicitation";
      // final String hattempt = "Attempt";
      // final String hrender = "Rendering";
      // writeHeader[0] = hprogram;
      // writeHeader[1] = hcycle;
      // writeHeader[2] = hunit;
      // writeHeader[3] = hsolicit;
      // writeHeader[4] = hattempt;
      // writeHeader[5] = hrender;

      // mapWriter.writeHeader(writeHeader);

      Map<String, String> row;
      int i = 0;
      while ((row = mapReader.read(readHeader)) != null) {
        if (i++ < 20) {
          System.out.println(row);
        } else {
          break;
        }
        // int solicitation = getSolicitation();
        // int attempt = getAttempt();
        // int render = getRender();
        // add your column with desired value
        // row.put(hprogram, "AHS");
        // row.put(hcycle, "2013");
        // row.put(hunit, String.valueOf(i++));
        // row.put(hsolicit, String.valueOf(solicitation));
        // row.put(hattempt, String.valueOf(attempt));
        // row.put(hrender, String.valueOf(render));
        // mapWriter.write(row, writeHeader);
      }

    } finally {
      if (mapReader != null) {
        mapReader.close();
      }
      // if (mapWriter != null) {
      // mapWriter.close();
      // }
    }

  }

}
