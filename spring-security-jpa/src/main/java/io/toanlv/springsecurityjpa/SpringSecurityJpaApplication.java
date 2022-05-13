package io.toanlv.springsecurityjpa;

import io.toanlv.springsecurityjpa.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SpringSecurityJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJpaApplication.class, args);
	}

}
/*
#cai dat docker mysql va chay
        # cau lenh chay docker:
// docker run -d --rm: chay xong xoa luon
// -p 3309:3306 : anh xa cong 3309 (PC) toi cong 3306 cua container
// --volume mysql-spring-boot-mvc-volume:/var/lib/mysql : Noi luu tru database sau khi tat se khong bi mat
docker run -d --rm --name mysql-spring-boot-mvc \
-e MYSQL_ROOT_PASSWORD=toanlv123 \
-e MYSQL_USER=toanlv \
-e MYSQL_PASSWORD=123456  \
-e MYSQL_DATABASE=test_database \
-p 3309:3306 \
--volume mysql-spring-boot-mvc-volume:/var/lib/mysql \
mysql:latest

// conect to sql:
mysql -h localhost -P 3306 --protocol=tcp -u toanlv -p123456
*/