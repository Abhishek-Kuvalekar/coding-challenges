package org.example.interfaces;

import org.example.model.request.DetokenizeRequest;
import org.example.model.request.TokenizeRequest;
import org.example.model.response.DetokenizeResponse;
import org.example.model.response.TokenizeResponse;

public interface IPrivacyVaultService {
    TokenizeResponse tokenize(TokenizeRequest request);
    DetokenizeResponse detokenize(DetokenizeRequest request);
}
