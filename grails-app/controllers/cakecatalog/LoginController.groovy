package cakecatalog

import grails.transaction.Transactional

@Transactional(readOnly = true)
class LoginController {

  def login() {
    if (session['loggedUser']) {
      redirect(
        action: 'index',
        controller: 'cake',
      )
      return
    }

    if (!params.email /*TODO || !params.password*/) {
      flash.message = "Email or password are empty"
      redirect uri: '/'
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
    flash.message = "You have logged out"
    session.removeAttribute('loggedUser')
    redirect uri: '/'
  }
}
