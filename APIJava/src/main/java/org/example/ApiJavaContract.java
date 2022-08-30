/*
 * SPDX-License-Identifier: Apache-2.0
 */
package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.List;

@Contract(name = "ApiJavaContract",
    info = @Info(title = "ApiJava contract",
                description = "My Smart Contract",
                version = "0.0.1",
                license =
                        @License(name = "Apache-2.0",
                                url = ""),
                                contact =  @Contact(email = "APIJava@example.com",
                                                name = "APIJava",
                                                url = "http://APIJava.me")))
@Default
public class ApiJavaContract implements ContractInterface {
    public  ApiJavaContract() {

    }
    @Transaction()
    public boolean apiJavaExists(Context ctx, String sampleApiCtxId) {
        byte[] buffer = ctx.getStub().getState(sampleApiCtxId);
        return (buffer != null && buffer.length > 0);
    }

    @Transaction()
    public void create(Context ctx, String sampleApiCtxId, String value) {
        boolean exists = apiJavaExists(ctx,sampleApiCtxId);
        if (exists) {
            throw new RuntimeException("The asset "+sampleApiCtxId+" already exists");
        }
        ctx.getStub().putState(sampleApiCtxId, value.getBytes(UTF_8));
    }

    @Transaction()
    public String retrieve(Context ctx, String sampleApiCtxId) {
        boolean exists = apiJavaExists(ctx,sampleApiCtxId);
        if (!exists) {
            throw new RuntimeException("The asset "+sampleApiCtxId+" does not exist");
        }

        return new String(ctx.getStub().getState(sampleApiCtxId),UTF_8);
    }

    @Transaction()
    public void update(Context ctx, String sampleApiCtxId, String newValue) {
        boolean exists = apiJavaExists(ctx,sampleApiCtxId);
        if (!exists) {
            throw new RuntimeException("The asset "+sampleApiCtxId+" does not exist");
        }
        ctx.getStub().putState(sampleApiCtxId, newValue.getBytes(UTF_8));
    }

    @Transaction()
    public void delete(Context ctx, String sampleApiCtxId) {
        boolean exists = apiJavaExists(ctx,sampleApiCtxId);
        if (!exists) {
            throw new RuntimeException("The asset "+sampleApiCtxId+" does not exist");
        }
        ctx.getStub().delState(sampleApiCtxId);
    }

    @Transaction()
    public String getStateByRange(Context ctx, String start, String end) {
      String result = "";  
      try {
        QueryResultsIterator<KeyValue> iterator = (QueryResultsIterator<KeyValue>)ctx.getStub().getStateByRange(start, end);
        for (KeyValue iterable_element : iterator) {
            String key = iterable_element.getKey();
            String value = new String(iterable_element.getValue());
            result += key + "|" + value + "-";
            System.out.println(result);
        }            
      }
      catch (Exception e) {
        return e.getMessage();
      }
      return result;       
    }

    @Transaction()
    public String getHistoryByKey(Context ctx, String key) {
      String result = "";  
      try {
        QueryResultsIterator<KeyModification> iterator = (QueryResultsIterator<KeyModification>)ctx.getStub().getHistoryForKey(key);
        for (KeyModification iterable_element : iterator) {
            String times = iterable_element.getTimestamp().toString();
            String value = new String(iterable_element.getValue());
            result += times + "|" + value + "-";
            System.out.println(result);
        }            
      }
      catch (Exception e) {
        return e.getMessage();
      }
      return result;       
    }

    @Transaction()
    public String getChannelID(Context ctx) {  
       String channel = ctx.getStub().getChannelId(); 
       return channel;        
    }

    @Transaction()
    public String putPrivateData(Context ctx, String collection, String key, String value) {
        ctx.getStub().putPrivateData (collection, key, value);
        return "{'ok'}";
    
    }

    @Transaction()
    public String getPrivateData(Context ctx, String collection, String keyString) {
        return new String(ctx.getStub().getPrivateData(collection, keyString));
    }

    @Transaction()
    public void deletePrivateData(Context ctx, String collection, String keyString) {
        ctx.getStub().delPrivateData(collection, keyString);
    }

    @Transaction()
    public String getCreator(Context ctx) {
        return new String(ctx.getStub().getCreator());
    }

    @Transaction()
    public String getArgs(Context ctx) {
      List<byte[]> args = ctx.getStub().getArgs();
      String resp = "";
      for (byte[] elemento : args) {
         resp += "|" + new String(elemento) + "|";
      }
      return resp;
    }

    @Transaction()
    public String getQueryResultString(Context ctx, String query) {
      String result = "";  
      try {
        QueryResultsIterator<KeyValue> iterator = ctx.getStub().getQueryResult(query);
        System.out.println(iterator.toString()); 
        for (KeyValue iterable_element : iterator) {
            String key = iterable_element.getKey().toString();
            String value = iterable_element.getStringValue();
            result += key + "|" + value + "-";
        }            
      }
      catch (Exception e) {
        return "error:"  + e.getMessage();
      }
      return result;       
    }

    @Transaction()
    public void setEvent(Context ctx, String eventName, String payload) {
        ctx.getStub().setEvent(eventName, payload.getBytes());
    }

}
