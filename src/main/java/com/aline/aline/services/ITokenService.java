package com.aline.aline.services;

import org.springframework.stereotype.Service;

@Service
public interface ITokenService {
    boolean isTokenValid(String token);
}
