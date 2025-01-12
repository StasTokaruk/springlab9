<!DOCTYPE html>
<html>
<head>
    <title>Hotels</title>
</head>
<body>
<h1>Available Hotels</h1>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Location</th>
        <th>Price Per Night</th>
        <th>Action</th>
    </tr>
    <c:forEach var="hotel" items="${hotels}">
        <tr>
            <td>${hotel.name}</td>
            <td>${hotel.location}</td>
            <td>${hotel.pricePerNight}</td>
            <td>
                <form action="book" method="post">
                    <input type="hidden" name="hotelId" value="${hotel.id}" />
                    <label>Name: <input type="text" name="customerName" required></label><br>
                    <label>Check-in: <input type="date" name="checkInDate" required></label><br>
                    <label>Check-out: <input type="date" name="checkOutDate" required></label><br>
                    <button type="submit">Book</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
