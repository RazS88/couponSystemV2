# Java
# Technologies: Spring Boot, Spring JPA, Spring Web RESTful api, Hibernate, MySql, Apache Tomcat.

****************************************************************

                          This is a small description: how to use this system  

1. You need to define connection to your data base.
In the file - application.property, you need to set userName, password , Schema . 
2. For getting admin controller you just need to send POST query with: login - "admin" , password - 1234.
3. After login every type of User will receive token,  that needs to be sent with every query, 
but for the fast tests  :(we prepared for you this functional)- After login you need to copy token
Add token to your first query that must to be: 
(for Admin :getAllCustomers() , for Company:getAllCompanyCoupons() , for Customer: getAllCustomerCoupons()) after this action
this token would be saved like a field(It is only for fast tests)
4. Now you are owner of perfect coupon System Enjoy it.
