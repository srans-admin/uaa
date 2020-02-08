package com.srans.uaa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/** 
 *  $2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu = password
	4	12	$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu	2344	admin	SYSTEMADMIN
	5	12	$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu	2344	user	USER
 
 * @author ram
 *
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    @Column
    //@JsonIgnore
    private String password;
    @Column
    private long salary;
    @Column
    private int age; 
    @Column
    private String role;
    
    
    public User( ) {
		super();
		this.username = "";
		this.password = "";
		this.salary = 1;
		this.age = 1;
		this.role = "USER";
	}
    
    
    public User( String username, String password, long salary, int age, String role) {
		super();
		this.username = username;
		this.password = password;
		this.salary = salary;
		this.age = age;
		this.role = role;
	}
    

	public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", salary=");
		builder.append(salary);
		builder.append(", age=");
		builder.append(age);
		builder.append(", role=");
		builder.append(role);
		builder.append("]");
		return builder.toString();
	}
 
	
	
	
}
