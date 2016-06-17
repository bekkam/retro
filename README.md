#### Deliverables:
##### Part One (Version One)

Create an inventory system for a multimedia stor such as Borders.  The system will manage an inventory comprised of CDs, DVDs, and books. The system will allow the user to enter new items into the system, search for and edit existing items, and delete items (CRUD operations).  The input and output can be accomplished by using System objects.   The user interface code will be modularized as a View (or Delegate) component, such that it can easily be swapped out for a different View component.  The initial view will be a text-based console view.  It is recommended that you use the Scanner class to manage keyboard input. The underlying inventory representation will be modularized as a Model component such that it can easily be swapped out for a different Model. The inventory will be persisitent, and it will be maintained using the Properties class.  

Your application should make use of inheritance, polymorphism, at least one interface definition, and at least one enumeration (Enum type).

##### Part Two (Version  Two)
Replace the "View" component of your application wth a new view.  The new view will make use of Java Swing library classes or SWT. Design a GUI that supports the functionality of the previous "View" component.  The new "View" component will interface with the "Model" component in the same manner as the previous "View" component.


##### Part Three (Version Three)
Replace the "Model" component of your application with a new model.  The new model will make use of JDBC to connect to an underlying relational database.  Design a model that supports the functionality of the previous "Model" component.  The new "Model" will interface with the "View" component in the same manner as the previous "View" component.  In addition, incorporate exception handling for SQLExceptions.