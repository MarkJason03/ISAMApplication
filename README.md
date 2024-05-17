
---

# Integrated Servicing and Asset Management (ISAM) System
Proposed conceptual application as part of my dissertation to complete my studies

## Rationale of the project

Help desk and asset management are two crucial components of every organizational success. However, these aspects are often overlooked within rural institutions due to various factors such as insufficient funding, geological topology hindering
network advancement thus solely relying on manual pen-paper based system. Thesee contribute to reasons why  rural institutions often lack the technical infrastructure to provide better management within their organisation. 
Thus, the project proposes a solution called ISAM - an integrated servicing and asset management system.

The proposed system uses a Model-View-Controller (MVC) design pattern aided by a data access object to handle business logic away from the model class. 
The front-end framework that will be used to implement the prototype is the JavaFX framework for the graphical user interface, and SQLite as a relational back-end database to handle transactions and store information. 
Together, they will be used to deliver a system aimed at solving the current challenges of the paper-based systems used within tertiary institutions within developing nations.

The Integrated Servicing and Asset Management (ISAM) System is designed to streamline operations and enhance the efficiency of rural tertiary institutions through integrated asset and service management. 
This system combines asset tracking, maintenance scheduling, and servicing requests into one comprehensive platform to improve operational workflows and reduce downtime.

## Core Features
- **Asset Management**: Track and manage all institutional assets in one interface, including procurement details, maintenance schedules, and usage logs.
- **Service Request Handling**: Allows Admin and end user to create, update, and track service request through their account providing transparency within the service.
- **User Management**: Allows Admin to register, lock, disable accounts and reset user details when necessary.
- **Supplier Management**: Allow Admin to register chosen suppliers for the organisation through contractual registrations.
- **Procurement System**: Detect supplier contract to ensure that assets can be only procured through approved active suppliers.
- **User-friendly Interface**: Designed with JavaFX, providing a rich and responsive desktop user experience.

## Technology Used
- Java 17 or later
- SQLite
- JavaFX

## Usage
Once installed, you can start the ISAM System via the executable jar or through your IDE of choice:
- **Login**: Start by logging into the system using your credentials.
- **Dashboard**: Access the dashboard for a quick overview of assets and pending service requests.
- **Manage Assets**: Add, update, or remove assets using the asset management module.
- **Handle Requests**: Create new service requests or update the status of existing ones through the service request module.


## Future Enhancements
- **Web Integration**: Planning to re-develop the desktop application as a web-based application.
- **Advanced Reporting Features**: Implement advanced analytics for asset utilization and maintenance optimization.
