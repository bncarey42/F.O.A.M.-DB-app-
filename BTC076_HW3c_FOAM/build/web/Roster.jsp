<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 
    Document   : Roster.jsp
    Created on : Sep 20, 2016, 9:23:23 PM
    Author     : rfoy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/css?family=Coiny|Raleway" rel="stylesheet">
        <title>FOAM Athlete Roster</title>
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
            
        </style>
    </head>
    <body>
        <h1>Roster:</h1>
        <h4>${param.errMsg}</h4>
        <h4>${param.msg}</h4>
        <form action="ModifyAthlete.jsp" method="post">
            <table>
                <thead>
                <th>National ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Date of Birth</th>
                <th>Age</th>
                <th colspan="2"></th>

                </thead>
                <c:forEach var="athlete" items="${athletes}" varStatus = "athStatus">
                    <tr class="${athStatus.count % 2 == 0 ? 'even' : 'odd'}">
                        <td>${athlete.nationalID}</td>
                        <td>${athlete.firstName}</td>
                        <td>${athlete.lastName}</td>
                        <td>${athlete.dateOfBirth}</td>
                        <td>${athlete.age}</td>
                        <td>
                            <a href="ModifyAthlete.jsp?action=Edit&nationalID=${athlete.nationalID}&firstName=${athlete.firstName}&lastName=${athlete.lastName}&dateOfBirth=${athlete.dateOfBirth}">Edit</a>
                        </td>
                        <td>
                            <a href=ModifyAthlete.jsp?action=Delete&nationalID=${athlete.nationalID}&firstName=${athlete.firstName}&lastName=${athlete.lastName}&dateOfBirth=${athlete.dateOfBirth}">Delete</a>
                        </td>
                    </tr>
                </c:forEach> 

                <tr>
                    <td colspan="7">

                        <input type="submit" value="Add" name="action"/>

                    </td>
                </tr> 
            </table>
        </form>
    </body>
</html>
