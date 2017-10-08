package hibernate.avengers.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * This class represents the Villain forces against the avengers
 * 
 * @author Dinuka.Arseculeratne
 * 
 */
@Entity
@Table(name = "Villains")
public class Villain implements Serializable {

	/**
	 * The primary key of the Enemy table
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "villain_id")
	private Long villaiId;

	/**
	 * The name of the enemy
	 */
	@Column(name = "villain_name")
	private String villainName;

	/**
	 * A flag which checks whether the villain is super awesome
	 */
	@Type(type = "yes_no")
	@Column(name = "is_awesome")
	private boolean isAwesome;

	public Long getVillaidId() {
		return villaiId;
	}

	public void setVillaidId(Long villaidId) {
		this.villaiId = villaidId;
	}

	public String getVillainName() {
		return villainName;
	}

	public void setVillainName(String villainName) {
		this.villainName = villainName;
	}

	public boolean isAwesome() {
		return isAwesome;
	}

	public void setAwesome(boolean isAwesome) {
		this.isAwesome = isAwesome;
	}

	@Override
	public String toString() {
		return "Villain [villaiId=" + villaiId + ", villainName=" + villainName
				+ ", isAwesome=" + isAwesome + "]";
	}

}
