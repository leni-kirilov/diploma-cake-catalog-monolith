<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cake.label', default: 'Cake')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-cake" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link controller="login" action="logout"><g:message code="Logout"/></g:link></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link class="edit" action="edit" controller="portalUser" resource="${session['loggedUser']}">Edit profile</g:link></li>
            </ul>
        </div>
        <div id="show-cake" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="cake" />
            <g:form resource="${this.cake}" method="DELETE">
                <fieldset class="buttons">
                    <g:if test="${session['loggedUser'].id == this.cake.ownerId }">
                        <g:link class="edit" action="edit" resource="${this.cake}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </g:if>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
