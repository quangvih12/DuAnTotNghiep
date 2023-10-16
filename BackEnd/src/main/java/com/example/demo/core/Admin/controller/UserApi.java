package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.core.Admin.service.AdUserService;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserAPI {

    @Autowired
    AdUserService adUserService;

    @GetMapping("/getAllUser")
    public List<AdminUserResponse> getAllUser(){
        return  adUserService.getAllUser();
    }

    @GetMapping("/getAllUserByRole")
    public List<AdminUserResponse> getAllUserByRole(@RequestParam("role") String role){
      return  adUserService.getAllUserByRole(role);
    }

    @GetMapping("{id}/dia-chi")
    public ResponseEntity<?> getDiaChi(@PathVariable Integer id){
        return  ResponseEntity.ok(adUserService.getUserByDiaChi(id));
    }

    @GetMapping("{id}/hoa-don")
    public ResponseEntity<?> getHodon(@PathVariable Integer id){
        return  ResponseEntity.ok(adUserService.getHoaDonByIdUser(id));
    }

    @PostMapping()
    public ResponseEntity<?>add(@RequestBody AdminUserRequest request){
        return ResponseEntity.ok(adUserService.add(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<?>update(@RequestBody AdminUserRequest request,@PathVariable Integer id){
        return ResponseEntity.ok(adUserService.update(request,id));
    }

    @PutMapping("{id}/delete")
    public ResponseEntity<?>delete(@PathVariable Integer id){
        return ResponseEntity.ok(adUserService.delete(id));
    }

    @PutMapping("{id}/vo-hieu-hoa")
    public ResponseEntity<?>voHieuHoaUser(@PathVariable Integer id){
        return ResponseEntity.ok(adUserService.VoHieuHoaUser(id));
    }


}
