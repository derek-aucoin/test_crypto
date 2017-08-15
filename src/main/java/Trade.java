package census.generator;

import java.math.BigDecimal;

import org.json.JSONObject;

public class Trade {
  private BigDecimal size;
  private BigDecimal price;
  private String time;
  
  public Trade(JSONObject trade){
    size = trade.getBigDecimal("size");
//    size = trade.getLong("size");
    price = trade.getBigDecimal("price");
    time = trade.getString("time");
  }
  
  public BigDecimal getSize(){
    return size;
  }
}
