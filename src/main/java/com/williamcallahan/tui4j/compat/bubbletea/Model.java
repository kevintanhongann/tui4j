package com.williamcallahan.tui4j.compat.bubbletea;

/**
 * Defines the Model contract for init, update, and view.
 * tui4j: src/main/java/com/williamcallahan/tui4j/compat/bubbletea/Model.java
 */
public interface Model {

    Command init();
    UpdateResult<? extends Model> update(Message msg);
    String view();
}
