package cakecatalog

import com.cakecatalog.srv.auth.client.AuthClient
import com.cakecatalog.srv.auth.client.model.PortalUserResponse
import grails.validation.ValidationErrors

import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

class PortalUserController {

  AuthClient authClient

  def show(PortalUser portalUser) {
    if (session['loggedUser']?.id != portalUser?.id) {
      flash.message = "Cannot view this page as you don't own it."
      redirect(
        action: 'index',
        controller: 'cake',
      )
      return
    }
    respond portalUser
  }

  def create() {
    respond new PortalUser()
  }

  def save(PortalUser portalUser) {
    if (portalUser == null) {
      log.info "No user $portalUser found"
      notFound()
      return
    }

    PortalUserResponse createdPortalUser
    try {
      createdPortalUser = authClient.createPortalUser(portalUser.name, portalUser.email, portalUser.password)
      portalUser.id = createdPortalUser.id

      flash.message = "New user created"
      redirect(
        action: 'index',
        controller: 'cake',
      )

    } catch (Exception e) {
      log.info e
      respond new ValidationErrors(createdPortalUser), view: 'create'
      return
    }
  }

  def edit(Long id) {
    log.info "Editting user with id: [$id]..."

    if (session['loggedUser']?.id == id) {
      respond convertToPortalUser(session['loggedUser'] as PortalUserResponse)
    } else {
      flash.message = "Cannot view this page as you don't own it. Your logged user ${session['loggedUser']} vs required: $id"
      redirect(
        action: 'index',
        controller: 'cake',
      )
    }
  }

  private static PortalUser convertToPortalUser(PortalUserResponse apiUser){
    new PortalUser(name: apiUser.name, id: apiUser.id, email: apiUser.email)
  }

  def update(PortalUser portalUser) {
    if (portalUser == null) {
      notFound()
      return
    }

    PortalUserResponse updatedPortalUser = authClient.updatePortalUser(portalUser.id, portalUser.name, portalUser.password)
    session['loggedUser'] = updatedPortalUser

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
