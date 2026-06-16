package com.devops.pipelineservice.model;

import java.time.LocalDateTime;

public class PipelineRunStage {
    private String name;
    private String command;
    private StageStatus status = StageStatus.PENDING;
    private String logs;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public StageStatus getStatus() {
        return status;
    }

    public String getLogs() {
        return logs;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setStatus(StageStatus status) {
        this.status = status;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
}
