package com.securevault.api.dto;


// Using a 'record' automatically gives you accessors like password() and email()
public record LoginRequest(String email, String password) {
}
