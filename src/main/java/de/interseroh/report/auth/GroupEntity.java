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
@Table(name = "GROUP")
public class GroupEntity extends AbstractPersistable<Long> implements Group {

	private static final long serialVersionUID = 3878644102882809215L;

	@Column(name = "GROUP_NAME")
	private String name;

	@OneToMany(mappedBy = "group", targetEntity = MembershipEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Membership> memberships;

	@Override
	public Collection<Membership> getMemberships() {
		return memberships;
	}

	@Override
	public void addMembership(Membership membership) {
		memberships.add(membership);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
