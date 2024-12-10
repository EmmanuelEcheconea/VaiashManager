package com.vaiashmanager.db;

import com.vaiashmanager.db.entity.PermissionEntity;
import com.vaiashmanager.db.entity.RoleEntity;
import com.vaiashmanager.db.entity.RoleEnum;
import com.vaiashmanager.db.entity.UserEntity;
import com.vaiashmanager.db.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableDiscoveryClient

public class DbApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbApplication.class, args);
	}


}
