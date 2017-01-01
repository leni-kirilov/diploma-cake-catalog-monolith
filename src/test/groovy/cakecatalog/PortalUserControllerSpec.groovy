package cakecatalog

import grails.test.mixin.*
import spock.lang.*

@TestFor(PortalUserController)
@Mock(PortalUser)
class PortalUserControllerSpec extends Specification {

    static int id = 0

    def populateValidParams(params) {
        assert params != null

        params["id"] = id++
        params["name"] = 'someValidName'
        params["email"] = 'someValidName@bla.com'
        params["password"] = 'top-secr3t'
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.portalUser!= null
    }

    @Ignore
    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def portalUser = new PortalUser()
            portalUser.validate()
            controller.save(portalUser)

        then:"The create view is rendered again with the correct model"
            model.portalUser!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            portalUser = new PortalUser(params)

            controller.save(portalUser)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/portalUser/show/1'
            controller.flash.message != null
            PortalUser.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A redirect to main page is expected"
            response.redirectedUrl == null

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def portalUser = new PortalUser(params)
            session['loggedUser'] = portalUser
            controller.show(portalUser)

        then:"A model is populated containing the domain instance"
            model.portalUser == portalUser
    }
}
