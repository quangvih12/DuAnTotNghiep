package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.CommentRequest;
import com.example.demo.core.khachHang.service.KHCommentService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/khach-hang/comment")
public class CommentController {

    @Autowired
    KHCommentService khCommentService;

    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CommentRequest request, @RequestParam String token) {
        return ResponseEntity.ok(khCommentService.addComment(request,token));
    }

    @GetMapping()
    public ResponseEntity<?> getList (@RequestParam(value = "token", required = false) String token,@RequestParam("idsp") Integer idsp ) {

        Integer iduser;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        iduser = user.getId();
        return ResponseEntity.ok(khCommentService.getListComment(iduser,idsp));
    }

    @DeleteMapping("/{idcomment}")
    public ResponseEntity<?> deleteGHCT(@PathVariable(value = "idcomment") Integer idcomment) {

        return ResponseEntity.ok(khCommentService.deleteComment(idcomment));
    }

}
