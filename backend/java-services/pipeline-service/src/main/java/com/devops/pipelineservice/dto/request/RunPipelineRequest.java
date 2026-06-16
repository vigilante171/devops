package com.devops.pipelineservice.dto.request;

public class RunPipelineRequest {
    private String branch;
    private String commitSha;

    public String getBranch() {
        return branch;
    }

    public String getCommitSha() {
        return commitSha;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCommitSha(String commitSha) {
        this.commitSha = commitSha;
    }
}
