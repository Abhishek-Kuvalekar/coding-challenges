package org.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.example.config.nodes.EncryptionConfig;
import org.example.constant.Constants;
import org.example.interfaces.IPrivacyVaultService;
import org.example.model.enums.Action;
import org.example.model.enums.Status;
import org.example.model.request.DetokenizeRequest;
import org.example.model.request.TokenizeRequest;
import org.example.model.response.DetokenizeResponse;
import org.example.model.response.PingResponse;
import org.example.model.response.PrivacyVaultResponse;
import org.example.model.response.TokenizeResponse;
import org.reactivestreams.Publisher;
import org.slf4j.MDC;

import javax.inject.Inject;

@Controller("/privacy-vault")
@Slf4j
public class PrivacyVaultController {
    @Inject
    IPrivacyVaultService privacyVaultService;

    @Inject
    EncryptionConfig config;


    @Get("/ping")
    public Publisher<MutableHttpResponse<PingResponse>> ping() {
        log.info("Config: {}", config);
        return Flowable.fromCallable(() -> HttpResponse.ok(
                PingResponse.builder()
                        .result(
                            PrivacyVaultResponse.builder()
                                    .status(Status.SUCCESS)
                                    .action(Action.PONG)
                                    .build()
                        )
                        .build()
        )).subscribeOn(Schedulers.io()).doFinally(MDC::clear);
    }

    @Post("/tokenize")
    public Publisher<MutableHttpResponse<TokenizeResponse>> tokenize(@Body TokenizeRequest request) {
        return Flowable.fromCallable(() -> {
            MDC.put(Constants.MDC_REQUEST_ID, String.format("%s: %s", Constants.MDC_REQUEST_ID, request.getRequestId()));

            log.info("Initiating tokenization");
            TokenizeResponse response = privacyVaultService.tokenize(request);
            log.info("Completed tokenization. Status: {}", response.getResult().getStatus());

            return HttpResponse.ok(response);
        }).subscribeOn(Schedulers.io()).doFinally(MDC::clear);
    }

    @Post("/detokenize")
    public Publisher<MutableHttpResponse<DetokenizeResponse>> detokenize(@Body DetokenizeRequest request) {
        return Flowable.fromCallable(() -> {
            MDC.put(Constants.MDC_REQUEST_ID, String.format("%s: %s", Constants.MDC_REQUEST_ID, request.getRequestId()));

            return HttpResponse.ok(privacyVaultService.detokenize(request));
        }).subscribeOn(Schedulers.io()).doFinally(MDC::clear);
    }
}
