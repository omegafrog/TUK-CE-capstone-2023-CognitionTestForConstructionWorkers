package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.AdminForm;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/super")
public class SuperAdminController {

    private final AdminService adminService;

    @PostMapping("/admin")
    public ResponseEntity<Map<String, Object>> addAdmin(
            @RequestParam AdminForm adminForm
            ){
        AdminDTO adminDTO = adminService.addAdmin(adminForm);
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("admin", adminDTO);
        return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Add common admin success", result), HttpStatus.OK);
    }


}
