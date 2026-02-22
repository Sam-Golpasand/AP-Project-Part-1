Assigment 1 from Group 16

This assignment has been made to fufill the requirements with minimal extras added. 
We made several helper functions to help reduce duplicated code such as the validateNotNull which (as the name suggest) ensure the argument isn't null. 

The more interesting function is Ensure capacity since we extend the array in multiple methods. First we run a check if the array is full. If so we enlarge it by 10.
You could also double the array instead of extending it by 10 but i am not sure how much of a performance increase that will grant. Depends on the amount of items are expected to be added.

For the exception handling (and displaying) we reused our arraylist implementation. We could also have used the in built java one but since we worked so hard on it we figured we could just use that.

NOTE: The comparator has been copied from the slides since there are different ways to compare the Person class but to be consistent with the assignment we decided to use what was provided.

No extra test have been added.
