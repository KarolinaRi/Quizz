package lt.ku.quizz.entities;

import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 64, unique = true)
	@Length(min=3, max=64, message="Naudotojo vardas turi būti sudarytas iš 3 - 64 simbolių")
	private String username;
	
	@Column(nullable = false, length = 255)
	@NotNull
	@Length(min=8, max=255, message="Slaptažodis turi būti sudarytas iš bent 8 simbolių")
	private String password;
	
	@Column(nullable = false, length = 20)
	@NotNull
	@Length(min=3, max=20, message="Vardas turi buti sudarytas is 3 - 20 simbolių")
	private String name;
	
	@Column(nullable = false, length = 25)
	@NotNull
	@Length(min=3, max=25, message="Pavardė turi būti sudaryta is 3 - 25 simbolių")
	private String surname;
	
	@Email(message="El. pašto adresas turi būti tvarkingas")
	private String email;
	
	@Column(nullable = false)
	private String role = "user";
	
	public User() {
		super();
	}


	public User(
			@Length(min = 3, max = 64, message = "Naudotojo vardas turi būti sudarytas iš 3 - 64 simbolių") String username,
			@NotNull @Length(min = 8, max = 255, message = "Slaptažodis turi būti sudarytas iš bent 8 simbolių") String password,
			@NotNull @Length(min = 3, max = 20, message = "Vardas turi buti sudarytas is 3 - 20 simbolių") String name,
			@NotNull @Length(min = 3, max = 25, message = "Pavardė turi būti sudaryta is 3 - 25 simbolių") String surname,
			@Email(message = "El. pašto adresas turi būti tvarkingas") String email, String role) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		HashSet<GrantedAuthority> auth=new HashSet<>();
		auth.add(new SimpleGrantedAuthority(this.role));
		return auth;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
