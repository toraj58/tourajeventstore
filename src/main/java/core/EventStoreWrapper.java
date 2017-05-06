package core;

import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.EventStoreBuilder;
import config.EventStoreConfig;

/**
 * Created by toraj on 05/06/2017.
 */
public class EventStoreWrapper {

    public static EventStore getEventStore()
    {

        EventStore eventstore = EventStoreBuilder.newBuilder()
                .singleNodeAddress(EventStoreConfig.getIp(), EventStoreConfig.getPort())
                .userCredentials(EventStoreConfig.getUser(), EventStoreConfig.getPassword())
                .build();

        return eventstore;
    }

}
