package cakecatalog

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

@Transactional(readOnly = true)
class CakeController extends BaseController{

  static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

  //TODO 4) show OwnerName -> show owner but without edit

  def index(Integer max) {
    PortalUser loggedUser = session['loggedUser']
    if(!loggedUser){
      redirect uri: '/' //TODO no such email/password; 404
      return
    }
    List<Cake> cakes = getVisibleCakes(session['loggedUser'].id as Integer)
    respond cakes, model: [cakeCount: cakes?.size()]
  }

  //TODO move to service?
  private List<Cake> getVisibleCakes(Long ownerId = null) {
    return Cake.createCriteria().listDistinct {
      or {
        if (ownerId) {
          eq('ownerId', ownerId)
        }
        eq('isPublic', true)
      }
    } as List<Cake>
  }

  def show(Cake cake) {
    respond cake
  }

  def create() {
    respond new Cake(params)
  }

  @Transactional
  def save(Cake cake) {
    cake.ownerId = session['loggedUser'].id
    if (cake == null) {
      transactionStatus.setRollbackOnly()
      notFound()
      return
    }

    if (cake.hasErrors()) {
      transactionStatus.setRollbackOnly()
      respond cake.errors, view: 'create'
      return
    }

    cake.save flush: true

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
    cake.ownerId = session['loggedUser'].id
    if (cake == null) {
      transactionStatus.setRollbackOnly()
      notFound()
      return
    }

    if (cake.hasErrors()) {
      transactionStatus.setRollbackOnly()
      respond cake.errors, view: 'edit'
      return
    }

    cake.save flush: true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.updated.message', args: [message(code: 'cake.label', default: 'Cake'), cake.id])
        redirect cake
      }
      '*' { respond cake, [status: OK] }
    }
  }

  @Transactional
  def delete(Cake cake) {

    if (cake == null) {
      transactionStatus.setRollbackOnly()
      notFound()
      return
    }

    cake.delete flush: true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'cake.label', default: 'Cake'), cake.id])
        redirect action: "index", method: "GET"
      }
      '*' { render status: NO_CONTENT }
    }
  }

  protected void notFound() {
    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'cake.label', default: 'Cake'), params.id])
        redirect action: "index", method: "GET"
      }
      '*' { render status: NOT_FOUND }
    }
  }
}
