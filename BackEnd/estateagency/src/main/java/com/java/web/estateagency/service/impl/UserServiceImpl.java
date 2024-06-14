package com.java.web.estateagency.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.ERole;
import com.java.web.estateagency.entity.Role;
import com.java.web.estateagency.entity.User;
import com.java.web.estateagency.exception.NotFoundException;
import com.java.web.estateagency.model.request.ChangePasswordRequest;
import com.java.web.estateagency.model.request.CreateUserRequest;
import com.java.web.estateagency.model.request.UpdateProfileRequest;
import com.java.web.estateagency.repository.RoleRepository;
import com.java.web.estateagency.repository.UserRepository;
import com.java.web.estateagency.service.UserService;
import com.java.web.estateagency.utils.ImageUpload;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private ImageUpload imageUpload;

  @Override
  public void register(CreateUserRequest request) {
    try {
      // TODO Auto-generated method stub
      User user = new User();
      user.setUsername(request.getUsername());
      user.setEmail(request.getEmail());
      user.setPassword(encoder.encode(request.getPassword()));
      Set<String> strRoles = request.getRole();
      Set<Role> roles = new HashSet<>();

      if (strRoles == null) {
        Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
      } else {
        strRoles.forEach(role -> {
          switch (role) {
            case "admin":
              Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
              roles.add(adminRole);

              break;
            case "agent":
              Role modRole = roleRepository.findByName(ERole.ROLE_AGENT)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
              roles.add(modRole);

              break;
            default:
              Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
              roles.add(userRole);
          }
        });
      }
      user.setRoles(roles);

      if (request.getImg() == null) {
        user.setImg(null);
      } else {
        imageUpload.uploadFile(request.getImg());
        user.setImg(Base64.getEncoder().encodeToString(request.getImg().getBytes()));
      }

      userRepository.save(user);
    } catch (Exception e) {
      e.printStackTrace();

    }

  }

  @Override
  public Optional<User> getUserByUsername(String username) {
    // TODO Auto-generated method stub
    Optional<User> user = userRepository.findByUsername(username);
    return user;
  }

  @Override
  public User updateUser(UpdateProfileRequest request) {
    // TODO Auto-generated method stub
    User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new NotFoundException("Not Found User"));
    user.setFullname(request.getFullname());

    user.setEmail(request.getEmail());

    user.setState(request.getState());
    user.setAddress(request.getAddress());
    user.setPhone(request.getPhone());
    userRepository.save(user);
    return user;
  }

  @Override
  public void changePassword(ChangePasswordRequest request) {
    // TODO Auto-generated method stub
    // User user =
    // userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new
    // NotFoundException("Not Found User"));

    // if(encoder.encode(request.getOldPassword()) != user.getPassword()){
    // throw new BadRequestException("Old Passrword Not Same");
    // }
    // user.setPassword(encoder.encode(request.getNewPassword()));

    // userRepository.save(user);

  }

  @Override
  public List<User> getListAgent() {
    List<User> users = userRepository.findAll();
    List<User> agent = users.stream()
        .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_AGENT)))
        .collect(Collectors.toList());
    return agent;
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User getUsserId(Long id) {
    return userRepository.findById(id).get();
  }

  @Override
  public void saveAD() {
    User user = new User();
    user.setUsername("Admin");
    user.setFullname("Admin");
    user.setPassword(encoder.encode("123456"));
    user.setEmail("Admin");
    user.setState("Admin");
    user.setAddress("Admin@gmail.com");
    user.setPhone("Admin");
    Set<Role> roles = new HashSet<>();
    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(adminRole);
    user.setRoles(roles);
    userRepository.save(user);
  }

}
