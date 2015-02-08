package lin.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="test_entity")
public class TestEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Column(name="test_name")
	private String name;
	
	@Column(name="test_value")
	private String value;
	
	@Column(name="test_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date date;
	
	private TestEntity data;

	public TestEntity(){
		System.out.println("ok.");
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public TestEntity getData() {
		return data;
	}
	public void setData(TestEntity data) {
		this.data = data;
	}
	
}
