package cakecatalog

import grails.transaction.Transactional

@Transactional(readOnly = true)
class LoginController {

  static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

  def login() {
    if (session['loggedUser']) {
      redirect(
        action: 'index',
        controller: 'cake',
      )
      return
    }

    if (!params.email /*|| !params.password*/) {
      redirect uri: '/' //TODO no such email/password; 404
      return
    }

    PortalUser loggedUser = PortalUser.findByEmail(params.email)
    session['loggedUser'] = loggedUser

    redirect(
      action: 'index',
      controller: 'cake',
    )
  }

  def logout() {
    session.removeAttribute('loggedUser')
    redirect uri: '/'
  }
}
