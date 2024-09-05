package com.java.web.estateagency.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.java.web.estateagency.model.response.ErrorResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.java.web.estateagency.entity.User;
import com.java.web.estateagency.model.request.CreateUserRequest;
import com.java.web.estateagency.model.request.LoginRequest;
import com.java.web.estateagency.model.response.MessageResponse;
import com.java.web.estateagency.model.response.UserInfoResponse;
// import com.java.web.estateagency.security.jwt.JwtUtils;
// import com.java.web.estateagency.security.service.UserDetailsImpl;
import com.java.web.estateagency.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(
        name = "Authentication in Estateagency",
        description = "Login, Register Acount REST APIs in Estateagency"
)
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class AuthController {

        @Autowired
        private UserService userService;

        @Operation(
                summary = "Register Account REST API",
                description = "REST API to create new Account inside Estateagency"
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "201",
                        description = "HTTP Status CREATED"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDto.class)
                        )
                )
        }
        )
        @PostMapping("/register")
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


        @Operation(
                summary = "Login Account REST API",
                description = "REST API to Get Account on Estateagency"
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "HTTP Status OK"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDto.class)
                        )
                )
        }
        )
        @RequestMapping("/login")
        public User getUserDetailsAfterLogin(Authentication authentication) {
                //log.info(authentication.getName());
                Optional<User> customers = userService.getUserByUsername(authentication.getName());
                if (customers.isPresent()) {
                        return customers.get();
                } else {
                        return null;
                }

        }

        // @PostMapping("/logout")
        // @Operation(summary = "Đăng xuất")
        // public ResponseEntity<?> logoutUser() {
        //         ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        //         return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        //                         .body(new MessageResponse("You've been logout!"));
        // }

}
