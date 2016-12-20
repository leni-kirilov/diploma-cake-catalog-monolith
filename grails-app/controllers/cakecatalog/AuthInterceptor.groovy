package cakecatalog


class AuthInterceptor {

  AuthInterceptor() {
    match(controller: 'cake')
  }

  boolean before() {
    if (!session['loggedUser']) {
      flash.message = "No logged user found"
      redirect url: '/'
      return false
    }
    true
  }

  boolean after() { true }

  void afterView() {
    // no-op
  }
}
