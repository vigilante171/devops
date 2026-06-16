package com.devops.pipelineservice.model;

public class PipelineStep {
    private String name;
    private String command;
    private int orderIndex;

    public PipelineStep() {}

    public PipelineStep(String name, String command, int orderIndex) {
        this.name = name;
        this.command = command;
        this.orderIndex = orderIndex;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}
