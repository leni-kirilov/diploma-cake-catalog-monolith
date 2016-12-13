package cakecatalog

class PortalUser {

  long id
  String name
  String email
  String password

  static constraints = {
  }

  static mapping = {
    id generator: "identity"
  }
}
