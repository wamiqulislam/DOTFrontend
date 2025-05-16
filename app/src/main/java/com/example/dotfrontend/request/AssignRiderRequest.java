package com.example.dotfrontend.request;

public class AssignRiderRequest {
    private Long batchId;
    private Long riderId;

    public AssignRiderRequest(Long batchId, Long riderId) {
        this.batchId = batchId;
        this.riderId = riderId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getRiderId() {
        return riderId;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }
}
