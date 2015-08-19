package de.interseroh.report.auth;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "USER")
public class UserEntity extends AbstractPersistable<Long> implements User {

	private static final long serialVersionUID = -8469721636610148266L;

	@Column(name = "USER_EMAIL")
	private String email;

	@OneToMany(mappedBy = "user", targetEntity = MembershipEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Membership> memberships;

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Collection<Membership> getMemberships() {
		return memberships;
	}

	@Override
	public void addMembership(Membership membership) {
		memberships.add(membership);
	}

}
