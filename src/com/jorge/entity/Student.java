package com.jorge.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// MANY SIDE entity

@Entity
//@Table(name="student") // If we comment @Table annotation, Hibernate will create a table with the name of this calss => "Student" (CASE SENSITIVE!!!)
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="enrollment_id", nullable=false)
	private String enrollmentId;
	
	//@Column(name="name") // If we comment @Column annotation, Hibernate will create this column with the name of this attribute => "name" (CASE SENSITIVE!!!)
	private String name;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE}) // Many students to one guide. 
																  // CascadeType.PERSIST: Everything you change in student row is save in its linked guide row automatically
																  // CascadeType.REMOVE: If you delete a student row, it will delete its linked guide row automatically
	@JoinColumn(name="guide_id") // guide_id is the name of the field in student table, linked to id field in guide table
	private Guide guide;
	
	public Student() {}
	
	public Student(String enrollmentId, String name, Guide guide) {
		this.enrollmentId = enrollmentId;
		this.name = name;
		this.guide = guide;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Guide getGuide() {
		return guide;
	}

	public void setGuide(Guide guide) {
		this.guide = guide;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", enrollmentId=" + enrollmentId + ", name=" + name + ", guide=" + guide + "]";
	}
	
}
