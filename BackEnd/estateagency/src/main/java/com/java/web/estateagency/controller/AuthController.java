package com.java.web.estateagency.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.web.estateagency.config.vnpay.VNPayService;
import com.java.web.estateagency.model.request.CreateUserRequest;
import com.java.web.estateagency.model.request.LoginRequest;
import com.java.web.estateagency.model.response.MessageResponse;
import com.java.web.estateagency.model.response.UserInfoResponse;
import com.java.web.estateagency.security.jwt.JwtUtils;
import com.java.web.estateagency.security.service.UserDetailsImpl;
import com.java.web.estateagency.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class AuthController {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private UserService userService;

    

        @PostMapping("/login")
        @Operation(summary = "Đăng nhập")
        public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
                Authentication authentication = authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

                List<String> roles = userDetails.getAuthorities().stream()
                                .map(item -> item.getAuthority())
                                .collect(Collectors.toList());

                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new UserInfoResponse(userDetails.getId(),
                                                userDetails.getUsername(),
                                                userDetails.getEmail(),
                                                roles));
                // return ResponseEntity.ok(jwtCookie);
        }

        // @PostMapping("/register")
        // @Operation(summary="Đăng ký")
        // public ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest
        // request){
        // //log.info(request.toString());
        // userService.register(request);

        // return ResponseEntity.ok(new MessageResponse("User registered
        // successfully!"));
        // }

        @PostMapping("/register")
        @Operation(summary = "Đăng ký")
        public ResponseEntity<?> register(@RequestParam("username") String username,
                        @RequestParam("password") String password, @RequestParam("email") String email,
                        @RequestParam("role") String[] role, @RequestParam("img") MultipartFile img) {

                log.info(role[0] + "Hekk");
                Set<String> sec = new HashSet<>();
                sec.add(role[0]);
                CreateUserRequest request = new CreateUserRequest();
                request.setUsername(username);
                request.setEmail(email);
                request.setRole(sec);
                request.setPassword(password);
                log.info(request.toString());
                request.setImg(img);

                userService.register(request);

                return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }

        @PostMapping("/logout")
        @Operation(summary = "Đăng xuất")
        public ResponseEntity<?> logoutUser() {
                ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                                .body(new MessageResponse("You've been logout!"));
        }

        

}
