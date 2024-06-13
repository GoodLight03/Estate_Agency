package com.java.web.estateagency.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.java.web.estateagency.entity.ERole;
import com.java.web.estateagency.entity.Role;
import com.java.web.estateagency.service.RoleService;
import com.java.web.estateagency.service.UserService;
@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	// @Autowired
	// private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// vai trof
		if (!roleService.findByNameRole(ERole.ROLE_ADMIN).isPresent()) {
			roleService.save(new Role(ERole.ROLE_ADMIN));
		}

		if (!roleService.findByNameRole(ERole.ROLE_CUSTOMER).isPresent() ) {
			roleService.save(new Role(ERole.ROLE_CUSTOMER));
		}
		
		if (!roleService.findByNameRole(ERole.ROLE_AGENT).isPresent()) {
			roleService.save(new Role(ERole.ROLE_AGENT));
		}

		//Admin account
		if (!userService.getUserByUsername("Admin").isPresent()) {
			userService.saveAD();
		}

		// // Member account
		// if (userRepository.findByEmail("member@gmail.com") == null) {
		// 	NguoiDung member = new NguoiDung();
		// 	member.setEmail("member@gmail.com");
		// 	member.setPassword(passwordEncoder.encode("123456"));
		// 	HashSet<VaiTro> roles = new HashSet<>();
		// 	roles.add(roleRepository.findByTenVaiTro("ROLE_MEMBER"));
		// 	member.setVaiTro(roles);
		// 	userRepository.save(member);
		// }
		
		// // Shipper account
		// if (userRepository.findByEmail("shipper@gmail.com") == null) {
		// 	NguoiDung member = new NguoiDung();
		// 	member.setEmail("shipper@gmail.com");
		// 	member.setPassword(passwordEncoder.encode("123456"));
		// 	HashSet<VaiTro> roles = new HashSet<>();
		// 	roles.add(roleRepository.findByTenVaiTro("ROLE_SHIPPER"));
		// 	member.setVaiTro(roles);
		// 	userRepository.save(member);
		// }
	}
}
