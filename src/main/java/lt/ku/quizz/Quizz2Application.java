package lt.ku.quizz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class Quizz2Application {

	public static void main(String[] args) {
		SpringApplication.run(Quizz2Application.class, args);
	}
	
	 @RequestMapping("/")
	    String index() {
	        return "home";
	    }

}
