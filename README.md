# Retro
A multimedia inventory management application written in Java.


## Table of Contents
- [Part One (Version One)](#part-one-version-one)
  - [Assignment](#assignment)
  - [System Design Document](#system-design-document)
- [Part Two (Version Two)](#part-two-version-two)
- [Part Three (Version Three)](#part-three-version-three)


## Part One (Version One)
##### Assignment
Create an inventory system for a multimedia store such as Borders.  The system will manage an inventory comprised of CDs, DVDs, and books. The system will allow the user to enter new items into the system, search for and edit existing items, and delete items (CRUD operations).  The input and output can be accomplished by using System objects.   The user interface code will be modularized as a View (or Delegate) component, such that it can easily be swapped out for a different View component.  The initial view will be a text-based console view.  It is recommended that you use the Scanner class to manage keyboard input. The underlying inventory representation will be modularized as a Model component such that it can easily be swapped out for a different Model. The inventory will be persisitent, and it will be maintained using the Properties class.  

Your application should make use of *inheritance*, *polymorphism*, at least one *interface* definition, and at least one *enumeration* (Enum type).

##### System Design Document
*Key abstractions of inventory application, and the relationships between them*

###### Overview
The media inventory application contains the following classes and enumerables: 
- Maintainable 
- InventoryItem
- MediaType
- Book
- CD 
- Dvd
- DemoView
- DemoController
- DemoModel
- DemoInventoryMain

###### Maintainable
`Maintainable` is an interface that contains methods to get (or load), add, search, edit, and delete strings representing items in a database.

###### InventoryItem
Abstract class `InventoryItem` implements interface `Maintainable` methods `addItem`, `editItem`, and `deleteItem`.  It includes helper methods for manipulating strings, which are useful in implementing method editItem.  Specifically, it contains methods to get and set the number of fields in a string while using a “\” as a delimiter.  It also has methods to get and set the beginning and ending index points of a field within a string, so that the field may be updated using `StringBuilder`.  Finally it has method `getFirstField`, which returns a string representing the first field of a media item entry, so that it can be stored as the key for a `Properties` value.  

`InventoryItem`’s constructor has three parameters: a properties object to store persistent inventory, a string to represent the name of the input/output file, and a string to represent the file header. `InventoryItem` also includes methods to load, list and save `Properties` objects.  

`InventoryItem` does not implement `Maintainable` interface method `searchItem`, and therefore it is declared abstract.  `InventoryItem` has concrete subclasses `Book`, `Cd` and `DVD`.  Each subclass implements its own version of the interface `searchItem` method, to allow message output to vary by media item. 

###### MediaType
Enum `MediaType` declares a constructor that takes string parameters representing the name of the media and the fields of the media.  It declares private instance fields of both for Book, Cd and Dvd, as well as providing accessor methods.  The model will rely on the accessor methods to communicate such information to the view. 

###### DemoView – DemoController – DemoModel
`DemoView` uses `Scanner` to process user input.  Method `setCategory` in `DemoView` calls `setModelCategory` on the controller, which in turn sets the Category in Model.  Model’s `setCategory` method sets the index number for the array of `Maintainable` objects book, CD, and Dvd according to the user’s selection; it then notifies model with a string of the current media type.  

In response, `DemoView` calls `model.getItem`s method loads the properties for the item the user selected; model.getCategory and model.getFields pass to View the name of the category and its fields, so that the view can update its user prompts.  

`DemoView` has overloaded method `setOperation`, which passes to model, via controller, the string user entered, a number corresponding to the selected operation (Create, Search, Edit, Delete).  Where the user selects to edit an item, a second string corresponding to the updated field entry, as well as a number corresponding to the field to edit, are also taken as arguments.  

Once the model receives the operation number from controller, it notifies `DemoView` with an integer argument.  In response, `DemoView` calls method `performTransaction()` on model, which executes method `addItem`, `searchItem`, `editItem`, or `deleteItem` on the current `Maintainable` interface object.  

###### DemoInventoryMain
`DemoInventoryMain` contains the main method for the inventory system.  It wires the `DemoView`, `DemoController`, and `DemoModel` together by ensuring the controller has a reference to the model, the view has a reference to controller and a `setModel` method, and the model has a `setView` method.  The view method `start()` initializes the application.  


## Part Two (Version Two)
Replace the "View" component of your application wth a new view.  The new view will make use of Java Swing library classes or SWT. Design a GUI that supports the functionality of the previous "View" component.  The new "View" component will interface with the "Model" component in the same manner as the previous "View" component.


## Part Three (Version Three)
Replace the "Model" component of your application with a new model.  The new model will make use of JDBC to connect to an underlying relational database.  Design a model that supports the functionality of the previous "Model" component.  The new "Model" will interface with the "View" component in the same manner as the previous "View" component.  In addition, incorporate exception handling for SQLExceptions.

##### Screenshots
*Add inventory*

![v3_add_item]
(/docs/static/v3_add_item.png)

*Edit inventory*

![v3_edit_item]
(/docs/static/v3_edit_item.png)

*Search inventory*

![v3_search_item]
(/docs/static/v3_search_item.png)

*Remove inventory*

![v3_delete_item]
(/docs/static/v3_delete_item.png)