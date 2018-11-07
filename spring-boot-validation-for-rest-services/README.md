What is Validation?
You expect a certain format of request for your RESTful Service. You except the elements of your request to have certain data types, certain domain constraints.

What if you get a request not meeting this constraints?

Think. What should you do?

Can I just return a generic message Something went wrong.. Is that good enough?

One of the core design principles for RESTful services is

Think about the consumer

So, what should you do when something in the request is not valid.

You should return a proper error response

Clear message indicating what went wrong? Which field has an error and what are the accepted values? What the consumer can do to fix the error?
Proper Response Status Bad Request.
Do not include sensitive information in the response.
Recommended response status for validation error is -> 400 - BAD REQUEST
Bean Validation API provides a number of such annotations. Most of these are self explanatory.

DecimalMax
DecimalMin
Digits
Email
Future
FutureOrPresent
Max
Min
Negative
NegativeOrZero
NotBlank
NotEmpty
NotNull
Null
Past
PastOrPresent
Pattern
Positive
PositiveOrZero
Enabling Validation on the Resource
Simple. Add @Valid in addition to @RequestBody.