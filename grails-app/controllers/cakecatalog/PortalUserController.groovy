package cakecatalog

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

@Transactional(readOnly = true)
class PortalUserController {

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
      respond portalUser.errors, view: 'create'
      return
    }

    portalUser.save flush: true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.created.message', args: [message(code: 'portalUser.label', default: 'PortalUser'), portalUser.id])
        redirect portalUser
      }
      '*' { respond portalUser, [status: CREATED] }
    }
  }

  def edit(PortalUser portalUser) {
    if (session['loggedUser']?.id == portalUser?.id) {
      respond portalUser
    } else {
      flash.message = "Cannot view this page as you don't own it"
      redirect url: '/'
    }
  }

  @Transactional
  def update(PortalUser portalUser) {
    if (portalUser == null) {
      transactionStatus.setRollbackOnly()
      notFound()
      return
    }

    if (portalUser.hasErrors()) {
      transactionStatus.setRollbackOnly()
      respond portalUser.errors, view: 'edit'
      return
    }

    portalUser.save flush: true

    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.updated.message', args: [message(code: 'portalUser.label', default: 'PortalUser'), portalUser.id])
        redirect portalUser
      }
      '*' { respond portalUser, [status: OK] }
    }
  }

  protected void notFound() {
    request.withFormat {
      form multipartForm {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'portalUser.label', default: 'PortalUser'), params.id])
        redirect action: "index", method: "GET"
      }
      '*' { render status: NOT_FOUND }
    }
  }
}
