//package lt.ku.quizz;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//
//import org.assertj.core.api.Assert;
//import org.hibernate.metamodel.spi.MetamodelImplementor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import lt.ku.quizz.controllers.homeController;
//import lt.ku.quizz.entities.Language;
//import lt.ku.quizz.entities.User;
//import lt.ku.quizz.repositories.UserRepository;
//
//
//@SpringBootTest(classes = Quizz2Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class QuizzControllerIntegrationTest {
//	
//	@LocalServerPort
//	private int port;
//	
//	@Autowired
//	homeController hController;
//	
//	@Autowired 
//	MockMvc mockMvc; 
//	
//	@Test
//	public void contextLoads() throws Exception {
//		assertThat(hController).isNotNull();
//	}
//	
//	@Test
//	public void shouldReturnHomeMessage() throws Exception{
//		this.mockMvc.perform(get("/quizz/")).andExpect(status().isOk());
//		//.andExpect(content().string(containsString("Ar esi pasiruošęs spręti įvairiausius klausimynus visomis temomis?")));
//}
//	
//	  @Test
//	  public void testAddLanguage() throws Exception{
//		  this.mockMvc.perform(get("/quizz/")).andDo(print()).andReturn();
//
////		Language language = new Language("Lietuvių");
////		ResponseEntity<String> responseEntity = this.restTemplate
////				.postForEntity("http://localhost:" + port + "/language/new/", language, String.class);
////	   
////	    assertEquals(201, responseEntity.getStatusCodeValue());
//	  }
//	
//	  @Test
//	  public void testAddUser() throws Exception{
//		  //User user = new User("karolina", "slaptazodis", "Karolina", "Ripinskytė", "k.ripinskyte@gmail.com", "user");
//		  this.mockMvc.perform(get("/user/new/")).andDo(print()).andReturn();
////		  ResponseEntity<String> responseEntity = this.restTemplate
////					.postForEntity("http://localhost:" + port + "/user/new/", user, String.class);
////		   
////		    assertEquals(HttpStatus.TEMPORARY_REDIRECT, responseEntity.getStatusCodeValue());
//	  }
//	  
//	  
//	  @Configuration
//	  @EnableJpaRepositories(basePackages = "com.anything.repository")
//	  static class TestConfiguration {
//
//	      @Bean
//	      public EntityManagerFactory entityManagerFactory() {
//	          return mock(EntityManagerFactory.class);
//	      }
//
//	      @Bean
//	      public EntityManager entityManager() {
//	          EntityManager entityManagerMock = mock(EntityManager.class);
//	          //when(entityManagerMock.getMetamodel()).thenReturn(mock(Metamodel.class));
//	          when(entityManagerMock.getMetamodel()).thenReturn(mock(MetamodelImplementor.class));
//	          return entityManagerMock;
//	      }
//
//	      @Bean
//	      public PlatformTransactionManager transactionManager() {
//	          return mock(JpaTransactionManager.class);
//	      }
//
//	  }
//
////	  @Autowired
////	  private UserRepository userRepository;
////
////	  @Autowired
////	  private EntityManager entityManager;
//
////	  @Test
////	  public void shouldSaveUser() {
////		  User user = new User("karolina", "slaptazodis", "Karolina", "Ripinskytė", "k.ripinskyte@gmail.com", "user");
////	      userRepository.save(new User());
////	      assertThat(userRepository.findByUsername(user.getUsername())).isNotNull();
////	      //verify(entityManager.createNamedQuery(anyString()).executeUpdate());
////	  }
//}
