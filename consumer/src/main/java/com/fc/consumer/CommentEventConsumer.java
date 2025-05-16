package com.fc.consumer;

import com.fc.event.CommentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static com.fc.event.CommentEventType.ADD;
import static com.fc.event.CommentEventType.REMOVE;

@Slf4j
@Component
public class CommentEventConsumer {

    private final CommentAddTask commentAddTask;

    private final CommentRemoveTask commentRemoveTask;

    public CommentEventConsumer(CommentAddTask commentAddTask, CommentRemoveTask commentRemoveTask) {
        this.commentAddTask = commentAddTask;
        this.commentRemoveTask = commentRemoveTask;
    }

    @Bean("comment") // event yaml 에 정의되어야함
    public Consumer<CommentEvent> comment() {
        return event -> {
            if (event.getType() == ADD) {
                commentAddTask.processEvent(event);
            } else if (event.getType() == REMOVE) {
                commentRemoveTask.processEvent(event);
            }
        };
    }
}
