package com.hinmu.lims;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author tomsun28
 * @date
 */
@MapperScan("com.hinmu.lims.dao")
@EnableTransactionManagement
@SpringBootApplication
public class BootshiroApplication{

	public static void main(String[] args) {
		SpringApplication.run(BootshiroApplication.class, args);
	}


}
