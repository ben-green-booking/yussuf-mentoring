## Part 2 - Dependency injection basics

### Description

We need to update some of the functionality in our controller to accomodate a new shop which wants to connect to our database. They idea of what makes a pet a pet is slightly different from ours.
However, the existing endpoints we've created are still in use by existing pet shops, so we need to create a V2 of the controller which allows the new shop to connect and use our services.

### Objectives
This will be a good opportunity for us to have a look at constructor based dependency management.
* Constructor based dependency management
* Solidifying the idea that your public interface doesn't necessarily represent your private domain