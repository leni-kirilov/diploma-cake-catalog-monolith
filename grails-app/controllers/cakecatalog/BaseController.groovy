package cakecatalog

import grails.transaction.Transactional

@Transactional(readOnly = true)
class BaseController {

  static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

  def beforeInterceptor = [action: this.&auth]

  def auth() {
    if (!session['loggedUser']) {
      redirect uri: '/'
      return false
    }
  }
}
