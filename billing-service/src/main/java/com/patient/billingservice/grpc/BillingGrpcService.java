package com.patient.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import com.patient.billingservice.dto.BillingAccountResponseDTO;
import com.patient.billingservice.service.BillingService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private final BillingService billingService;

    public BillingGrpcService(BillingService billingService) {
        this.billingService = billingService;
    }

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseStreamObserver) {
        try {
            BillingAccountResponseDTO account = billingService.createBillingAccount(
                    billingRequest.getPatientId(),
                    billingRequest.getPatientName(),
                    billingRequest.getEmail()
            );

            BillingResponse response = BillingResponse.newBuilder()
                    .setAccountId(account.getId())
                    .setStatus(account.getStatus())
                    .build();

            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        } catch (Exception exception) {
            responseStreamObserver.onError(
                    Status.INTERNAL.withDescription(exception.getMessage()).asRuntimeException()
            );
        }
    }
}
