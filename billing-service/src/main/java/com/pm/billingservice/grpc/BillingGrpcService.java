package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<billing.BillingResponse> responseObeserver) {
        log.info("createBillingAccount request received{}", billingRequest.toString());

        // Business logic - ex: save to db, perform calculation etc.

        BillingResponse billingResponse = BillingResponse.newBuilder().setAccountId("12345").setStatus("ACTIVE").build();

        responseObeserver.onNext(billingResponse);
        responseObeserver.onCompleted();

    }

}
