package cakecatalog

class PortalUser {

  long id
  String name
  String email
  String password

  static constraints = {
    email blank: false
    password blank: false, password: true
  }

  static mapping = {
    id generator: "identity"
  }
}
