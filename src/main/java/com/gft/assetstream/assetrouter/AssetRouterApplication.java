package com.gft.assetstream.assetrouter;

import com.gft.assetstream.assetrouter.business.domain.Asset;
import com.gft.assetstream.assetrouter.business.domain.Route;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@SpringBootApplication
@Slf4j
public class AssetRouterApplication {

    @Autowired
    private StreamBridge streamBridge;

    public static void main(String[] args) {
        SpringApplication.run(AssetRouterApplication.class, args);
    }

    private boolean isNthBitSet(int number, int bitNumber) {
        return (number & (1 << bitNumber)) > 0;
    }

    @Bean
    public Consumer<Message<Asset>> routeAsset() {
        return new Consumer<Message<Asset>>() {
            @Override
            public void accept(Message<Asset> message) {
                log.info("Routing message {}", message);

                MessageHeaders headers = message.getHeaders();
                int routeHint = headers.get("x-route-hint", Integer.class);
                log.info("route-hint {} bit zero {} bit one {}", routeHint, isNthBitSet(routeHint, 0), isNthBitSet(routeHint, 1));

                for (Route r: Route.values()) {
                    if (isNthBitSet(routeHint, r.getHint())) {
                        streamBridge.send(r.getBindingName(), message);
                    }
                }
            }
        };
    }
    
}
