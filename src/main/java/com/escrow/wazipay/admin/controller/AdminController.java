package com.escrow.wazipay.admin.controller;

import com.escrow.wazipay.response.Response;
import org.springframework.http.ResponseEntity;

public interface AdminController {
    ResponseEntity<Response> setAdmin(Integer userId);
}
