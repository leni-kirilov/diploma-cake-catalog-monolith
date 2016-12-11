package cakecatalog

import grails.test.mixin.*
import spock.lang.*

@TestFor(CakeController)
@Mock(Cake)
class CakeControllerSpec extends Specification {

    static int id = 1

    def populateValidParams(params) {
        assert params != null

        params["id"] = id++
        params["name"] = 'Chocolate cake'
        params["description"] = 'This cake consists of many choco flakes'
        params["isPublic"] = false
        params['ownerId'] = 12
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.cakeList
            model.cakeCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.cake!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def cake = new Cake()
            cake.validate()
            controller.save(cake)

        then:"The create view is rendered again with the correct model"
            model.cake!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            cake = new Cake(params)

            controller.save(cake)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/cake/show/1'
            controller.flash.message != null
            Cake.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def cake = new Cake(params)
            controller.show(cake)

        then:"A model is populated containing the domain instance"
            model.cake == cake
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def cake = new Cake(params)
            controller.edit(cake)

        then:"A model is populated containing the domain instance"
            model.cake == cake
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/cake/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def cake = new Cake()
            cake.validate()
            controller.update(cake)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.cake == cake

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            cake = new Cake(params).save(flush: true)
            controller.update(cake)

        then:"A redirect is issued to the show action"
            cake != null
            response.redirectedUrl == "/cake/show/$cake.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/cake/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def cake = new Cake(params).save(flush: true)

        then:"It exists"
            Cake.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(cake)

        then:"The instance is deleted"
            Cake.count() == 0
            response.redirectedUrl == '/cake/index'
            flash.message != null
    }
}
