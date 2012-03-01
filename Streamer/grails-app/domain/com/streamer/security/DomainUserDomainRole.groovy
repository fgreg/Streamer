package com.streamer.security

import org.apache.commons.lang.builder.HashCodeBuilder

class DomainUserDomainRole implements Serializable {

	DomainUser domainUser
	DomainRole domainRole

	boolean equals(other) {
		if (!(other instanceof DomainUserDomainRole)) {
			return false
		}

		other.domainUser?.id == domainUser?.id &&
			other.domainRole?.id == domainRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (domainUser) builder.append(domainUser.id)
		if (domainRole) builder.append(domainRole.id)
		builder.toHashCode()
	}

	static DomainUserDomainRole get(long domainUserId, long domainRoleId) {
		find 'from DomainUserDomainRole where domainUser.id=:domainUserId and domainRole.id=:domainRoleId',
			[domainUserId: domainUserId, domainRoleId: domainRoleId]
	}

	static DomainUserDomainRole create(DomainUser domainUser, DomainRole domainRole, boolean flush = false) {
		new DomainUserDomainRole(domainUser: domainUser, domainRole: domainRole).save(flush: flush, insert: true)
	}

	static boolean remove(DomainUser domainUser, DomainRole domainRole, boolean flush = false) {
		DomainUserDomainRole instance = DomainUserDomainRole.findByDomainUserAndDomainRole(domainUser, domainRole)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(DomainUser domainUser) {
		executeUpdate 'DELETE FROM DomainUserDomainRole WHERE domainUser=:domainUser', [domainUser: domainUser]
	}

	static void removeAll(DomainRole domainRole) {
		executeUpdate 'DELETE FROM DomainUserDomainRole WHERE domainRole=:domainRole', [domainRole: domainRole]
	}

	static mapping = {
		id composite: ['domainRole', 'domainUser']
		version false
	}
}
