<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="form"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Pracownicy</title>
</head>
<body>
<div>
    <h1>Edytuj dane pracownika</h1>

    <form:form method="post" action="editsave" modelAttribute="pracownikModel">
        <form:hidden path="id" value="${pracownikModel.getId()}"/>

        <table >
            <tr>
                <td>Nazwisko : </td>
                <td> <form:input path="nazwisko" value="${pracownikModel.getNazwisko()}"/> </td>
            </tr>
            <tr>
                <td>Pensja :</td>
                <td> <form:input path="pensja" value="${pracownikModel.getPensja()}"/> </td>
            </tr>
            <tr>
                <td>Firma :</td>
                <td> <form:input path="firma" value="${pracownikModel.getFirma()}"/> </td>
            </tr>
            <tr>
                <td> </td>
                <td> <input type="submit" value="Zapisz" /> </td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
