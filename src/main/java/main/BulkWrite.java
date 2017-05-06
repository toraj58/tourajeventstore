package main;

import com.github.msemys.esjc.EventData;
import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.ExpectedVersion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.EventStoreWrapper;
import domain.Credit;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by toraj on 05/06/2017.*/


public class BulkWrite {

    public static void main(String[] args) {
        System.out.println("======================= Bulk Write =============================");

        int bulkAmount = 10;

        EventStore eventstore = EventStoreWrapper.getEventStore();

        Gson gson = new GsonBuilder().create();

        List<Credit> creditList = new LinkedList<>();

        long start = System.currentTimeMillis();

        for (int i = 0; i <bulkAmount ; i++) {

            Credit c = new Credit();
            c.setId(i);
            c.setMoney(i*1000);
            c.setBankName("bank"+i);
            c.setCardName("Master"+i);

            creditList.add(c);

        }

        for (Credit credit : creditList) {

            eventstore.appendToStream("creditStream2", ExpectedVersion.ANY, asList(

                    EventData.newBuilder()
                            .type("credit")
                            .jsonData(gson.toJson(credit))
                            .build())
            ).thenAccept(r -> {System.out.println(r.logPosition); System.out.println("Data recived");});

        }


        long end = System.currentTimeMillis();

        System.out.println("delay is :" + (end - start));
    }

}
