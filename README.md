# Java-OOP-GUI
I worked with a group to design, implement, and test an object oriented bus ticket program in Java with an interactive GUI.
## System Requirements
We started with designing the overall structure of our program. We drafted a list of users and use cases, and incorporated these into a use case diagram. Datailed use case descriptions for each bubble in the diagram were also included in the [final report](<Design Documents/Final_Report.pdf>).

<img src="Design Documents/use_case_diagram.JPG" width=400>

## Domain Class Model
With the use case diagram complete, we then determined the classes that our program would need. We created a [domain class model](<Design Documents/domain_class_model.JPG>) to depict the interactions between classes. OOP principles such as inheritance were also used to improve the overall design.

## UML Diagrams
Using the domian class model, our next step was to create UML diagrams for each class in our project. These diagrams showed the visibility, attributes, and methods for all of our classes. An example UML diagram for the Passenger class is shown below, with all diagrams included in the [final report](<Design Documents/Final_Report.pdf>).

<img src="Design Documents/uml_passenger.JPG" width=400>

## State Diagram
Finally, we created a [state diagram](<Design Documents/state_diagram.JPG>) for our system to show each step of the process as a user buys a bus ticket. This helped us organize the functions that would occur at each stage of our program.

## Implementation and Testing
After the design was complete, it was time to begin coding. To improve the readability of our code, each class was stored in a package. The overall code structure is shown below:
* Hardware
  - [Ticket.java](Code/hardware/Ticket.java)
  - [Bus.java](Code/harware/Bus.java)
* Software
  - [Route.java](Code/software/Route.java)
  - [TicketingDemo.java](Code/software/TicketingDemo.java)
  - [TicketingGUI.java](Code/software/TicketingGUI.java)
  - [TicketingSite.java](Code/software/TicketingSite.java)
* People
  - [Agent.java](Code/hardware/Agent.java)
  - [Passenger.java](Code/hardware/Passenger.java)
  - [User.java](Code/hardware/User.java)

A GUI with the below main screen was also created, allowing for users to easily find and book bus tickets.

<img src="Design Documents/GUI.JPG" width=400>
