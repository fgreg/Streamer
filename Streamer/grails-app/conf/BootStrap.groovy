import com.streamer.security.*

class BootStrap {

    def init = { servletContext ->
		def adminRole = new DomainRole(authority: 'ROLE_ADMIN').save(flush: true)
		def userRole = new DomainRole(authority: 'ROLE_USER').save(flush: true)
		
		def testUser = new DomainUser(username: 'me', enabled: true, password: 'password')
		testUser.save(flush: true)
		
		DomainUserDomainRole.create testUser, adminRole, true
		
		assert DomainUser.count() == 1
		assert DomainRole.count() == 2
		assert DomainUserDomainRole.count() == 1
    }
    def destroy = {
    }
}
