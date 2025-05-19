package com.example.dotfrontend.response;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChangeBatchProperties {
    long batchId;
    long riderId;
    String location;
}
