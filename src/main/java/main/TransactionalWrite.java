package main;

import com.github.msemys.esjc.EventData;
import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.ExpectedVersion;
import com.github.msemys.esjc.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.EventStoreWrapper;

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
            t.write(EventData.newBuilder().type("trans1").jsonData("{ a : 1 }").build());
            t.write(EventData.newBuilder().type("trans1").jsonData("{ b : 2 }").build());
            t.write(asList(
                    EventData.newBuilder().type("trans1").jsonData("{ c : 3 }").build(),
                    EventData.newBuilder().type("trans1").jsonData("{ d : 4 }").build())
            );

            if (1==2) {
            throw new NullPointerException("Null by Touraj");
            }

            t.commit();

        } catch (Exception e) {
            e.printStackTrace();
            t.rollback();
        }

        System.out.println("[Touraj] :: Transactional Processing Done ... ");

    }

}
