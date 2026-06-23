package com.Dummy.Dummy.enums;

import java.util.Set;

public enum Role {
	USER(Set.of(Permissions.READ)),
	ADMIN(Set.of(Permissions.WRITE, Permissions.READ, Permissions.UPDATE, Permissions.DELETE));

	public Set<Permissions> permissions;

	Role(Set<Permissions> permissions) {
		// TODO Auto-generated constructor stub
		this.permissions = permissions;
	}

	public Set<Permissions> getPermissions() {
		return permissions;
	}
}
