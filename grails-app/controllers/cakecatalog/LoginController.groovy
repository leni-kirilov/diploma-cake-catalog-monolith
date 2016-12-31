package cakecatalog

import com.cakecatalog.srv.auth.client.*
import com.cakecatalog.srv.auth.client.model.LoginRequest
import com.cakecatalog.srv.auth.client.model.PortalUserResponse
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

    PortalUserResponse loggedUser = authClient.login(new LoginRequest(params.email, params.password))
    if (!loggedUser) {
      flash.message = "Wrong username or password"
      redirect uri: '/'
      return
    }

    session['loggedUser'] = PortalUser.get(loggedUser.id)

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
