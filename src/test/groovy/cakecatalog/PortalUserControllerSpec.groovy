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

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.portalUserList
            model.portalUserCount == 0
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

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def portalUser = new PortalUser(params)
            controller.show(portalUser)

        then:"A model is populated containing the domain instance"
            model.portalUser == portalUser
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def portalUser = new PortalUser(params)
            controller.edit(portalUser)

        then:"A model is populated containing the domain instance"
            model.portalUser == portalUser
    }

    @Ignore
    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/portalUser/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def portalUser = new PortalUser()
            portalUser.validate()
            controller.update(portalUser)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.portalUser == portalUser

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            portalUser = new PortalUser(params).save(flush: true)
            controller.update(portalUser)

        then:"A redirect is issued to the show action"
            portalUser != null
            response.redirectedUrl == "/portalUser/show/$portalUser.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/portalUser/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def portalUser = new PortalUser(params).save(flush: true)

        then:"It exists"
            PortalUser.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(portalUser)

        then:"The instance is deleted"
            PortalUser.count() == 0
            response.redirectedUrl == '/portalUser/index'
            flash.message != null
    }
}
