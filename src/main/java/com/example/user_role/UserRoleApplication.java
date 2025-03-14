package com.example.user_role;

import com.example.user_role.entities.Role;
import com.example.user_role.entities.User;
import com.example.user_role.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class UserRoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRoleApplication.class, args);
	}
	@Bean
	CommandLineRunner start(UserService userService){
		return args -> {
			User user1 = new User();
			user1.setUsername("Yassine");
			user1.setPassword("123456");

			userService.addNewUser(user1);


			User user2 = new User();
			user2.setUsername("Yahya");
			user2.setPassword("00112233");

			userService.addNewUser(user2);

			Stream.of("STUDENT", "USER", "ADMIN").forEach(role -> {
				Role role1 = new Role();
				role1.setRoleName(role);
				userService.addNewRole(role1);
			});



			userService.addRoleToUser("Yassine", "ADMIN");
			userService.addRoleToUser("Yassine", "USER");
			userService.addRoleToUser("Yahya", "USER");
			userService.addRoleToUser("Yahya", "STUDENT");


			try {
				User user = userService.authenticate("Yassine", "123456");
				System.out.println(user.getUserId());
				System.out.println(user.getUsername());
				System.out.println("Roles => ");
				user.getRoles().forEach(r->{
					System.out.println(r.toString());
				});
			}catch (Exception e){
				e.printStackTrace();
			}
		};
	}

}
