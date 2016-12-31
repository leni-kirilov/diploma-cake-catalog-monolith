package cakecatalog

import com.cakecatalog.srv.auth.client.AuthClient
import com.cakecatalog.srv.auth.client.model.PortalUserResponse
import grails.transaction.Transactional
import grails.validation.ValidationErrors

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

@Transactional(readOnly = true)
class PortalUserController {

  AuthClient authClient

  def show(PortalUser portalUser) {
    respond portalUser
  }

  def create() {
    respond new PortalUser(params)
  }

  @Transactional
  def save(PortalUser portalUser) {
    if (portalUser == null) {
      notFound()
      return
    }

    PortalUserResponse createdPortalUser
    try {
      createdPortalUser = authClient.createPortalUser(portalUser.name, portalUser.email, portalUser.password)
      portalUser.id = createdPortalUser.id

      request.withFormat {
        form multipartForm {
          flash.message = message(code: 'default.created.message', args: [message(code: 'portalUser.label', default: 'PortalUser'), portalUser.id])
          redirect portalUser
        }
        '*' { respond portalUser, [status: CREATED] }
      }

    } catch (Exception e) {
      respond new ValidationErrors(createdPortalUser), view: 'create'
      return
    }
  }

  def edit(PortalUser portalUser) {
    if (session['loggedUser']?.id == portalUser?.id) {
      respond portalUser
    } else {
      flash.message = "Cannot view this page as you don't own it"
      redirect(
        action: 'index',
        controller: 'cake',
      )
    }
  }

  @Transactional
  def update(PortalUser portalUser) {
    if (portalUser == null) {
      notFound()
      return
    }

    authClient.updatePortalUser(portalUser.id, portalUser.name, portalUser.password)
    portalUser.refresh()

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
