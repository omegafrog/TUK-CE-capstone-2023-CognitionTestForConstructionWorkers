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

import java.util.List;
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

    @GetMapping("/admin/:id")
    public ResponseEntity<Map<String, Object>> getAdmin(
            @PathVariable Long id
    ){
        AdminDTO adminDTO = adminService.findById(id);
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("admin", adminDTO);
        return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get common admin success", result), HttpStatus.OK);
    }

    @GetMapping("/admins")
    public ResponseEntity<Map<String, Object>> getAdmins(){
        List<AdminDTO> all = adminService.findAll();
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("admins", all);
        return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get common admin list success", result), HttpStatus.OK);
    }

    @PostMapping("/admin/:id")
    public ResponseEntity<Map<String, Object>> updateAdmin(
            @PathVariable Long id,
            @RequestParam AdminForm adminForm
    ){
        AdminDTO adminDTO = AdminDTO.builder()
                .name(adminForm.getName())
                .position(adminForm.getPosition())
                .role(adminForm.getRole())
                .username(adminForm.getUsername())
                .password(adminForm.getPassword())
                .build();
        AdminDTO updated = adminService.updateAdmin(id, adminDTO);
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("admin", adminDTO);
        return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Update common admin success", result), HttpStatus.OK);
    }

    @DeleteMapping("/admin/:id")
    public ResponseEntity<Map<String ,Object>> deleteAdmin(
            @PathVariable Long id
    ){
        adminService.deleteAdmin(id);
        return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Delte common admin success", null), HttpStatus.OK);
    }
}
