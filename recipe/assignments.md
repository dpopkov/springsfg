# Display List of Recipes

* Use 'Perfect Guacamole' and 'Spice Grilled Chicken Tacos' from www.simplyrecipes.com for source data
* Add any needed Unit of Measures to data.sql
* Use Bootstrap class to create recipes on startup
* Create Service to return recipe list to controller
* Pass list to Thymeleaf view to display on index page


# Write Unit Test for Index Controller

* Create JUnit test for Index Controller
* Use Mockito Mock for RecipeService and Model
* Verify proper string is returned
* Verify interactions with mocks

# Display Remaining Recipe Properties

* Add hint for recipe type
* Add iterator for categories
* Add iterator for ingredients
* Complete remaining properties


# Configure MySQL

* Create a local MySQL instance for use in this course
    * MySQL can be installed natively or in a Docker container
* Setup two databases. One for dev, one for prod.
    * Call databases sfg_dev and sfg_prod
* Create two accounts: sft_dev_user, sfg_prod_user
* Both should have DML, and no DDL access.