
---

# Integrated Servicing and Asset Management (ISAM) System
A Proposed conceptual application as part of my dissertation to complete my studies.

## Rationale of the project

Help desk and asset management are two crucial components of every organizational success. However, these aspects are often overlooked within rural institutions due to various factors such as insufficient funding, geological topology hindering
network advancement thus solely relying on manual pen-paper based system. These contribute to reasons why  rural institutions often lack the technical infrastructure to provide better management within their organisation. 
Thus, the project proposes a solution called ISAM - an integrated servicing and asset management system.

The proposed system uses a Model-View-Controller (MVC) design pattern aided by a data access object to handle business logic away from the model class. 
The front-end framework that will be used to implement the prototype is the JavaFX framework for the graphical user interface, and SQLite as a relational back-end database to handle transactions and store information. 
Together, they will be used to deliver a system aimed at solving the current challenges of the paper-based systems used within tertiary institutions within developing nations.

The Integrated Servicing and Asset Management (ISAM) System is designed to streamline operations and enhance the efficiency of rural tertiary institutions through integrated asset and service management. 
This system combines asset tracking, maintenance scheduling, and servicing requests into one comprehensive platform to improve operational workflows and reduce downtime.

## User Requirements
- **Asset Management**: Track and manage all institutional assets in one interface, including procurement details, maintenance schedules, and usage logs.
- **Service Request Handling**: Allows Admin and end user to create, update, and track service request through their account providing transparency within the service.
- **User Management**: Allows Admin to register, lock, disable accounts and reset user details when necessary.
- **Supplier Management**: Allow Admin to register chosen suppliers for the organisation through contractual registrations.
- **Procurement System**: Detect supplier contract to ensure that assets can be only procured through approved active suppliers.
- **User-friendly Interface**: Designed with JavaFX, providing a rich and responsive desktop user experience.
- **Gmail API**: Uses Gmail API to send real-time update to mimic real-world usages.

## Technology Used
- Java 17 or later
- SQLite
- JavaFX
- Gmail API
- Argon2

## How to run
-  Run **App** class to initiate the application.
### Admin Demo Account
- Username: **admin**
- Password: **admin**

![admin](https://github.com/MarkJason03/ISAMApplication/assets/81525475/a5b40b75-c67e-4c5e-86fb-a91d79f648c2)


  
### User Demo Account
- Username: **user** 
- Password: **user**


![client](https://github.com/MarkJason03/ISAMApplication/assets/81525475/c7838ea5-05f5-40ca-b75a-02d4cff0cd75)



## Features
### Admin Features
- **Dashboard**: Access the dashboard for a quick overview of procurement requests, overdue assets, and pending service requests.
- **Monthly Dashboard**: Access the dashboard for a quick overview of user usage chart, asset conditions, ticket volume, monthly spending, department budget.
- **Manage Asset Entry**: Add, update, or remove assets using the asset management module.
- **Manage Asset Allocation**: Allows admin to allocate and track registered assets to end users, providing clear history of the allocated assset.
- **Manage User Accounts**: Allow admins to set user roles, dictate account expiry dates, reset passwords, add and update other user details.
- **Manage Suppliers**: Add, update, or supplier contracts.
- **Manage Procurement**: Add, Deny procurement requests.
- **Handle Requests**: Create new service requests or update the status of existing ones through the service request module.


### Client Features
- **Dashboard**: Clear Indication For access the dashboard for a quick overview of currently assigned assets and pending service requests.
- **Handle Requests**: Create new service requests or update the status of existing ones through the service request module.


### Shared Features
- **Role-based login**: Different views are provided to the current logged in user depending on their role.
- **Profile Editing**: Allows end-users to edit their own profile details.
- **Handle Requests**: Create new service requests or update the status of existing ones through the service request module.






## Future Enhancements
- **Web Integration**: Planning to re-develop the desktop application as a web-based application.
- **Advanced Reporting Features**: Implement advanced analytics for asset utilization and maintenance optimization.
