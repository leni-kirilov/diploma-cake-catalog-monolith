package cakecatalog


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(AuthInterceptor)
class AuthInterceptorSpec extends Specification {

  void "Test auth interceptor matching"() {
    when: "A request matches the interceptor"
    withRequest(controller: "cake")

    then: "The interceptor does match"
    interceptor.doesMatch()
  }
}
