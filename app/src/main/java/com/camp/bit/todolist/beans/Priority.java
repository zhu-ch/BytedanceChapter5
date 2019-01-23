package com.camp.bit.todolist.beans;

public enum  Priority {
    COMMON(0), URGENT(1);

    public final int intValue;

    Priority(int intValue) {
        this.intValue = intValue;
    }

    public static Priority from(int intValue) {
        for (Priority priority : Priority.values()) {
            if (priority.intValue == intValue) {
                return priority;
            }
        }
        return COMMON; // default
    }
}
