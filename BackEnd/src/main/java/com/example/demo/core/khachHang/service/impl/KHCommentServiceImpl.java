package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.CommentRequest;
import com.example.demo.core.khachHang.model.response.CommentResponse;
import com.example.demo.core.khachHang.repository.KHCommentRepository;
import com.example.demo.core.khachHang.service.KHCommentService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.Comment;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KHCommentServiceImpl implements KHCommentService {

    @Autowired
    KHCommentRepository khCommentRepo;

    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;


    @Override
    public Comment addComment(CommentRequest request, String token) {

        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }

        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);

        Comment comment = new Comment();
        comment.setNoiDung(request.getNoiDung());
        comment.setUser(user);
        comment.setSanPham(SanPham.builder().id(request.getSanPham()).build());


        return khCommentRepo.save(comment);
    }

    @Override
    public List<Comment> getListComment(Integer iduser, Integer idsp) {
        return khCommentRepo.getListComment(iduser, idsp);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteComment(Integer id) {
        try {
           Comment comment = khCommentRepo.findById(id).get();
            khCommentRepo.deleteById(comment.getId());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
