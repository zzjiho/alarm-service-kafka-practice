package com.fc.consumer;

import com.fc.event.LikeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static com.fc.event.LikeEventType.ADD;
import static com.fc.event.LikeEventType.REMOVE;

@Component
@Slf4j
public class LikeEventConsumer {

//    private final LikeAddTask likeAddTask;
//
//    private final LikeRemoveTask likeRemoveTask;
//
//    public LikeEventConsumer(LikeAddTask likeAddTask, LikeRemoveTask likeRemoveTask) {
//        this.likeAddTask = likeAddTask;
//        this.likeRemoveTask = likeRemoveTask;
//    }
//
//    @Bean("like")
//    public Consumer<LikeEvent> like() {
//        return event -> {
//            if (event.getType() == ADD) {
//                likeAddTask.processEvent(event);
//            } else if (event.getType() == REMOVE) {
//                likeRemoveTask.processEvent(event);
//            }
//        };
//    }

    @Bean("like")
    public Consumer<LikeEvent> like() {
        return event -> {
            log.info(event.toString());
        };
    }
}
