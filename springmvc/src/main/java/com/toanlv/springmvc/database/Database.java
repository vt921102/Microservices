package com.toanlv.springmvc.database;

import com.toanlv.springmvc.model.Product;
import com.toanlv.springmvc.repositories.ProductRepository;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Database {


    public static final Logger logger = (Logger) LoggerFactory.getLogger(Database.class);

    @Bean
    CommandLineRunner initDatabase (ProductRepository productRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
               /* Product productA = new Product("IPhone 11",2021,"/iphone11",12.5);
                Product productB = new Product("IPhone 13",2022,"/iphone13",12.5);
                productRepository.save(productA);
                productRepository.save(productB);*/
            }
        };
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


docker exec -it mysql-spring-boot-mvc
// conect to sql:
mysql -h localhost -P 3306 --protocol=tcp -u toanlv -p123456
*/