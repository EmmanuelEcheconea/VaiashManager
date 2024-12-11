package com.vaiashmanager.ms_sendermail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsSendermailApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSendermailApplication.class, args);
	}

}
