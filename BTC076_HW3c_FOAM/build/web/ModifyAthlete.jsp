<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : ModifyAthlete
    Created on : Nov 3, 2016, 9:51:01 PM
    Author     : ben
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/css?family=Coiny|Raleway" rel="stylesheet">
        <title>${param.action} Athlete</title>
        <style>
            body{
                width: 800px;
                margin-right: auto;
                margin-left: auto;
                background-color: cadetblue;
                font-family: 'Raleway', sans-serif;
            }
            h1{
                font-family: 'Coiny', cursive; 
            }
            table { 
                border: black 3px solid;             
                border-collapse: collapse; 
                width: 100%;
            }
            th  {
                border: black 1px solid; 
                border-collapse: collapse;
                background-color: darkkhaki;
                font-family: 'Coiny', cursive;
            }

            td  {
                border: black 1px solid;   
                border-collapse: collapse;

            }
            .odd {
                background-color: lightgray;
            }
            .even {
                background-color: white;
            }
            .buttons {
                text-align: right;
                
            }
            .modify{
                width: 55%;
                background-color: white;
                
            }
            .title{
                background-color: darkkhaki;
                font-family: 'Coiny', cursive;
            }
            #error{
                color: orangered;
            }
        </style>
    </head>
    <body>
        <h1>${param.action} Athlete:</h1>
        <h4 id="error">${param.errMsg}</h4>
        <form action="modify" method="post" >
            <table class="modify">
                <tbody>
                    <tr>
                        <td class="title">National ID</td>
                        <td>
                            <c:if test="${!param.action.equalsIgnoreCase('Add')}">
                                <input type="text" name="nationalID" value="${param.nationalID}" readonly />
                            </c:if>
                            <c:if test="${param.action.equalsIgnoreCase('Add')}">
                                <input type="text" name="nationalID" value="${param.nationalID}" />
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">First Name</td>
                        <td>
                            <c:if test="${param.action.equalsIgnoreCase('Delete')}">
                                <input type="text" name="firstName" value="${param.firstName}" readonly/>
                            </c:if>
                            <c:if test="${!param.action.equalsIgnoreCase('Delete')}">
                                <input type="text" name="firstName" value="${param.firstName}" />
                            </c:if>

                        </td>

                    </tr>
                    <tr>
                        <td class="title">Last Name</td>
                        <td>
                            <c:if test="${!param.action.equalsIgnoreCase('Delete')}">
                                <input type="text" name="lastName" value="${param.lastName}" />
                            </c:if>
                            <c:if test="${param.action.equalsIgnoreCase('Delete')}">
                                <input type="text" name="lastName" value="${param.lastName}" readonly />
                            </c:if>
                    </tr>
                    <tr>
                        <td class="title">Date of Birth</td>
                        <td>
                            <c:if test="${!param.action.equalsIgnoreCase('Delete')}">
                                <input type="test" name="dateOfBirth" value="${param.dateOfBirth}" /> (yyyy-mm-dd)
                            </c:if>
                            <c:if test="${param.action.equalsIgnoreCase('Delete')}">
                                <input type="test" name="dateOfBirth" value="${param.dateOfBirth}" readonly /> (yyyy-mm-dd)
                            </c:if>
                        </td>

                    </tr>
                    <tr class="buttons">
                        <td class="title"></td>
                        <td>
                            <input type="submit" value=${param.action} name="action" /> 
                            <input type="submit" value="Cancel" name="action" />
                        </td>

                    </tr>
                </tbody>
            </table>
        </form>
    </body>
</html>
