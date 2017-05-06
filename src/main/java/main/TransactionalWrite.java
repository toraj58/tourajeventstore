package main;

import com.github.msemys.esjc.EventData;
import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.ExpectedVersion;
import com.github.msemys.esjc.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.EventStoreWrapper;
import exceptions.OrderException;

import static java.util.Arrays.asList;

/**
 * Created by toraj on 05/06/2017.
 */
public class TransactionalWrite {


    public static void main(String[] args) {

        System.out.println("[Touraj] :: Begin Transactional Test");

        Gson gson = new GsonBuilder().create();

        EventStore eventstore = EventStoreWrapper.getEventStore();

        Transaction t = null;

        try  {
            t = eventstore.startTransaction("transactStream4", ExpectedVersion.ANY).get();
            t.write(EventData.newBuilder().type("trans1").jsonData("{ data1 : 'here1' }").build());
            t.write(EventData.newBuilder().type("trans1").jsonData("{ data2 : 'here2' }").build());
            t.write(asList(
                    EventData.newBuilder().type("trans1").jsonData("{ data3 : 'here3' }").build(),
                    EventData.newBuilder().type("trans1").jsonData("{ data4 : 'here4' }").build())
            );

            if (1==2) {
            throw new OrderException("Order Exception Occured");
            }

            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            t.rollback();
        }

        System.out.println("[Touraj] :: Transactional Processing Done ... ");

    }

}
