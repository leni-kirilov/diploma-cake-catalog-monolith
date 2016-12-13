package cakecatalog

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CakeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cake.list(params), model:[cakeCount: Cake.count()]
    }

    def show(Cake cake) {
        respond cake
    }

    def create() {
        respond new Cake(params)
    }

    @Transactional
    def save(Cake cake) {
        if (cake == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (cake.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cake.errors, view:'create'
            return
        }

        cake.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cake.label', default: 'Cake'), cake.id])
                redirect cake
            }
            '*' { respond cake, [status: CREATED] }
        }
    }

    def edit(Cake cake) {
        respond cake
    }

    @Transactional
    def update(Cake cake) {
        if (cake == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (cake.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cake.errors, view:'edit'
            return
        }

        cake.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'cake.label', default: 'Cake'), cake.id])
                redirect cake
            }
            '*'{ respond cake, [status: OK] }
        }
    }

    @Transactional
    def delete(Cake cake) {

        if (cake == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        cake.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cake.label', default: 'Cake'), cake.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cake.label', default: 'Cake'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
