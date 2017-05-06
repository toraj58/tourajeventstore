package main;

import com.github.msemys.esjc.EventData;
import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.ExpectedVersion;
import com.github.msemys.esjc.Position;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.EventStoreConfig;
import core.EventStoreWrapper;
import domain.Order;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static java.util.Arrays.asList;

/**
 * Created by toraj on 05/06/2017.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("[Touraj] :: Begin Event Store");

        System.out.println("test" + EventStoreConfig.getIp());

        Order order = new Order();
        order.setId(1000);
        order.setAmount(1200);
        order.setOrderName("digi mobile777");
        order.setOrderType("yalda666");

        Gson gson = new GsonBuilder().create();

        EventStore eventstore = EventStoreWrapper.getEventStore();

//        [touraj] :: Follwoing is the Async Model
   /*     eventstore.appendToStream("foo", ExpectedVersion.ANY, Arrays.asList(
                     EventData.newBuilder()
                        .eventId(UUID.randomUUID())
                        .type("StringType")
                        .data("touraj String")
                        .build(),
                EventData.newBuilder()
                        .type("jsonType")
                        .jsonData("{ touraj : 'testTouraj' }")
                        .build())
        ).thenAccept(r -> System.out.println(r.logPosition));*/

//   [Touraj] :: Following is the Syncronzied Model ...Remove Get to make it Synchronized

        try {
            eventstore.appendToStream("tourajStream", ExpectedVersion.ANY, asList(
                    EventData.newBuilder()
                            .type("byteType")
                            .data(new byte[]{1, 2, 3, 4, 5})
                            .metadata(new byte[]{6, 7, 8, 9, 0})
                            .build(),
                    EventData.newBuilder()
                            .eventId(UUID.randomUUID())
                            .type("stringType")
                            .data("touraj was here")
                            .build(),
                    EventData.newBuilder()
                            .type("jsonType")
                            .jsonData(gson.toJson(order))
                            .build())
            ).thenAccept(r -> {System.out.println(r.logPosition); System.out.println("Data recived");}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("After Write");

//        [Touraj] :: Reading Wrtie Events :

        eventstore.readEvent("foo", 1, false).thenAccept(e ->
                System.out.format("id: '%s'; type: '%s'; data: '%s'",
                        e.event.originalEvent().eventId,
                        e.event.originalEvent().eventType,
                        e.event.originalEvent().data));

        System.out.println("=============================================================");
        System.out.println("=============================================================");

        eventstore.readAllEventsBackward(Position.END, 10, false).thenAccept(e ->
                e.events.forEach(i -> System.out.format("@%s  id: '%s'; type: '%s'; data: '%s'\n",
                        i.originalPosition,
                        i.originalEvent().eventId,
                        i.originalEvent().eventType,
                        new String(i.originalEvent().data))));

    }

}
