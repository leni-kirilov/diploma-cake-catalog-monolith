package cakecatalog

import com.cakecatalog.srv.auth.client.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LoginController {

  AuthClient authClient

  def login() {
    if (session['loggedUser']) {
      redirect(
        action: 'index',
        controller: 'cake',
      )
      return
    }

    if (!params.email || !params.password) {
      flash.message = "Email or password are empty"
      redirect uri: '/'
      return
    }

    if (!authClient.login(new AuthClient.LoginRequest(params.email, params.password))) {
      flash.message = "Wrong username or password"
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
