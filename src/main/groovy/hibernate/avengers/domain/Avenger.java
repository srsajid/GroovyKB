package hibernate.avengers.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * The domain class representing each member of the avengers
 * 
 * @author Dinuka.Arseculeratne
 * 
 */
@Entity
@Table(name = "Avengers")
public class Avenger implements Serializable {

	/**
	 * The primary key of the Avenger table
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "avenger_id")
	private Long avengerId;

	/**
	 * The name of the avenger member
	 */
	@Column(name = "avenger_name")
	private String avengerName;

	/**
	 * A flag which holds whether the avenger's powers are awesome
	 */
	@Type(type = "yes_no")
	@Column(name = "is_awesome")
	private boolean isAwesome;

	/**
	 * The list of enemies the avenger has
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "AVENGERS_AND_VILLAINS", joinColumns = { @JoinColumn(name = "avenger_id") }, inverseJoinColumns = { @JoinColumn(name = "villain_id") })
	private List<Villain> enemyList = new ArrayList<Villain>();

	public Long getAvengerId() {
		return avengerId;
	}

	public void setAvengerId(Long avengerId) {
		this.avengerId = avengerId;
	}

	public String getAvengerName() {
		return avengerName;
	}

	public void setAvengerName(String avengerName) {
		this.avengerName = avengerName;
	}

	public boolean isAwesome() {
		return isAwesome;
	}

	public void setAwesome(boolean isAwesome) {
		this.isAwesome = isAwesome;
	}

	public List<Villain> getEnemyList() {
		return enemyList;
	}

	public void addEnemy(Villain enemy) {
		enemyList.add(enemy);
	}

	@Override
	public String toString() {
		return "Avenger [avengerId=" + avengerId + ", avengerName="
				+ avengerName + ", isAwesome=" + isAwesome + ", enemyList="
				+ enemyList + "]";
	}

	
}
