package com.devops.pipelineservice.dto.request;

import com.devops.pipelineservice.model.PipelineStatus;

public class UpdatePipelineStatusRequest {
    private PipelineStatus status;

    public PipelineStatus getStatus() {
        return status;
    }

    public void setStatus(PipelineStatus status) {
        this.status = status;
    }
}
