package cakecatalog

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PortalUserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def show(PortalUser portalUser) {
        respond portalUser
    }

    def create() {
        respond new PortalUser(params)
    }

    @Transactional
    def save(PortalUser portalUser) {
        if (portalUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (portalUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond portalUser.errors, view:'create'
            return
        }

        portalUser.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'portalUser.label', default: 'PortalUser'), portalUser.id])
                redirect portalUser
            }
            '*' { respond portalUser, [status: CREATED] }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'portalUser.label', default: 'PortalUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
