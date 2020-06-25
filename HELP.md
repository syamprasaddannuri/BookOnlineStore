Book store service Design
   
    1.Objective
        1.1: Goals and Non-goals
    2. Assumptions
    3. Design and Architectural view
        3.1: High level architecture
        3.2: Technologies
    4.Database design
        4.1:Data model
    5.Server design
        5.1: API’s
        5.2: Class diagram
    6. Performance,Scale and reliability
    7. Monitoring and alerting
---------------------------------------------------------------------------------------------------------------------------
Objective:
    
    To offer users the ability to maintain an online book store and enable the users to buy books from online store.
        
    Goals: 
        - Admin can add books to the store.
        - Admin can remove book from store
        - Admin can mark the book not available.
        - Admin can add inventory to the book.
        - Admin can add the price of the book.
        - Users can search books in the store by giving the text.
            - System should be capable of returning a search result if the text matches ISBN, Title and Author name.
        - Users should be able to search media posts about the book by providing the ISBN.
        - System should provide all the media posts which match partially with the book title.
        - Users can buy a book from a store.
    Non-goals:
        - Managing user data.
        - Authentication of users.
        - Authorization of user actions
Assumptions:

    - Media search api is highly available
    - Price of book changes based only date.

Design and Architectural view:

    High level design:
   ![alt text](HLD.png)

    Components:
    
    Book Store service:
        It exposes a set of apis to implement the use cases listed as part of goals.
    Media Service:
        This is an external service which exposes media api to search the media coverage of the books.
    Elastic load balancer:
        Balances the load across multiple copies of the book store.
    Book store:
        Database to store the book related data.

Technologies:
    Tools:
    
Server Design

    Class diagram:
   ![alt text](ClassDiagram.png)
    
    API’s:
        
        Add Book : 
            - This service is used to add the book.
            - What does this service do ?
                - Check whether the book is already present or not.
                - If it is not present then create the book		       
                    - POST method :			 	
                    -  Request URL : <Bookstore DNS>/api/book				
                    -  Request contains following information					
                        - ISBN of book					
                        - Title of book					
                        - Author of book					
                        - Description of book           
                -  Response contains following information          
                        - ISBN of a book         
                        - Title of a book
                        - author of a book            
                        - description about a book
        
        Delete Book : 
            - This service is used to delete the book
            - What does this service do ?
                    - Check whether the book is already present or not.
    - If it is present then deletes it.
    - DELETE method :
    - Request URL : <Bookstore DNS>/api/book/remove 
    - Request contains following information
    - ISBN of book
    - Response contains following information
    - ISBN of a book
    - Title of a book
    - author of a book
    - description about a book
    -	Search Book :
    - This service is used to search the books based on given text.
    - What does this service do ?
        - This service searches all the books which are having partial   or full match with the given text compared to Title / Author / ISBN
    - GET method : 
        - Request URL : <Bookstore DNS>/api/book
        - Request contains following information 
            - search key (it is text which is in string format)
        - List of Responses contains following information
            - ISBN of a book
    - Title of a book
    - author of a book
    - description about a book
        -	Add User : 
                - This service is used to add the user.
                - What does this service do ?
                    - Checks whether the user is already present or not.
                    - If user is not present then it adds user
                    - POST method : 
                        - Request URL : <Bookstore DNS>/api/user
                        - Request contains following information
                            - Id
    - name
                            - age
                            - mobile number
                            - email id
                        - Response contains following information
                            - Id
                            - name
                            - age
                            - mobile number
                            - email id
        - 	Delete User :
                - This service is used to delete the user.
                - What does this service do ?
                    - Checks whether the user is present or not.
                    - If a user is present then it deletes the user.
                    - DELETE method :
                        - Request URL : <Bookstore DNS>/api/user/remove
                        - Request contains following information
                            - Id of user to be deleted
                        - Response contains following information
                            - Id
                            - name
                            - age
                            - mobile number
                            - email id
        - 	Add Inventory :
                - This service is used to increment the inventory for a book.
                - What does this service do ?
                    - Checks whether the book inventory is present or not.
                    - If it is present then increments the count of books available
                    - If not then book inventory is created with one count.
                    - POST method :
    Request URL :
     <Bookstore DNS>/api/inventory/addInventiry
                        - Request contains following information 
                            - ISBN of a book 
                        - Response contains the following information
                            - Id
    - ISBN of a book
                            - count of book 
                            - status of a book
        - 	Decrement Inventory : 
                - This service is used to decrement the inventory count of a book.
                - What does this service do ?
                    - Checks whether the book inventory is present or not.
                    - If it is present then decrements the count of books available
                    - If not then report an error.
                    - POST method : 
                        - Request URL : 
    <Bookstore DNS>/api/inventory/deleteInventory
                        - Request contains following information 
                            - ISBN of a book
                        - Response contains the following information
                            - Id
    - ISBN of a book
                            - count of book 
                            - status of a book
        - 	Get Inventory :
                - This service is used to get the inventory details of the book.
                - What does this service do ?
                    - Checks whether the book inventory is present or not
                    - If it is present then it returns inventory details of book 
                    - If not then it reports an error 
                - GET method :
                    - Request URL : <Bookstore DNS>/api/inventory/getInventory
                    - Request contains following information
                            - ISBN of a book
                    - Response contains following information
                            - Id
    - ISBN of a book
                            - count of book 
                            - status of a book
        -	Delete Inventory :
                - This service is used to make book un available in inventory
                - What does this service do ?
                    - Checks whether the book inventory is present or not
                    - If it is present then it changes status of book available to  
                                                     Unavailable
                    - If not then it reports an error.
                    - DELETE method
                        - Request URL : 
    <Bookstore DNS>/api/inventory/deleteInventory
    - Request contains following information
            - ISBN of a book
    - Response contains following information
            - Id
    - ISBN of a book
                                - count of book 
                                - status of a book
        -	Book Ordering :
                - This service is used to order the book from store
                - What does this service do ?
                    - Checks whether the book inventory is present or not
    - If it is available then it places the order of book
    - If not it reports an error 
    - GET method :
        - Request URL : <Bookstore DNS>/api/ordering
        - Request contains following information
                - ISBN of a book
        - Response contains following information
                - ISBN 
                - Title
                - Author
                - Description
                - Order status
        -	Update Price :
                - This service is used to update the price of a book
                - What does this service do ?
                    - It updates the price of a book
                    - POST method :
                        - Request URL : <Bookstore DNS>/price/api
                        - Request contains the following information
                                - ISBN
                                - Price
                                - Date
                        - Response contains the following information
                                - ISBN
                                - Price
                                - Date
        -	Get Price :
                - This service is used to get the price details of a book
                - What does this service do ?
                    - Checks whether book is available or not
                    - If it is available then it returns a price information of a book
                    - If not it reports an error
                - GET method :
                        - Request URL : <Bookstore DNS>/price/api
                        - Request contains the following information
                                - ISBN
                        - Response contains the following information
                                - ISBN
                                - Price
                                - Date
    
                        
    
    
    
    
     
    
    
    BookModel : 
    It maintains details of the book. This model contains following information in each document
        -  ISBN of book (primary key)
        -  Title of book
        -  Id of author 
        -  description of book
           -     BookInvetoryModel : 
    -  It maintains the status of a book. This model contains following
       Information in each document
    Id of inventory
    ISBN of book
    Number of books available
    Status of book (information about book is available or not)
          -     Price Model :
            - It maintains price information for books. This model contains following
                              Information in each document.
    ISBN of book
    Price of a book
    Date in which price is updated
    -    User Model :
    - It maintains the user information. This model contains following 
      Information in each document.
    Id of user (This id is used as author id in book model)
    Name of user
    Age of user
    Mobile number of user
    Email Id of user 	
    
            API’s for User :
    
                -  addUser API : 
                    - This api is used to add add user information to the user 
                database. 
                    - It takes UserRequest as input which contains “id, name, age, 
                Mobile number, email id “ and converts it as User using 
                Converter and finally user will be added to database.
                
    
    
    
    -  deleteUser API :
                    - This api is used to delete the user information from the
                Database.
                    - It takes the id of the user as input to detect user document in 
                the database. After detecting user entry will be deleted.
            
    
    
    API’s for Book Inventory :
                
    
    - addInventory API : 
    
    - This api is used to increment the count of available books
    The ISBN of the book is taken as input to find the document count to be incremented. 
                
                - decrementInventory API : 
    
                    - This api is used to decrement the count of available books. 
                The ISBN of the book is taken as input to find the document 
    count to be decremented.
    
    - getInventory API : 
    
    - This api is used to get the entire inventory information of a particular book. Based on ISBN of a given book it checks whether inventory is available or not. If it is available then the entire inventory document of a given isbn is returned.
    
    - deleteInventory API :
    
        - This api is used to change the status of a book that is not available in inventory. Based on ISBN of a given book it checks whether inventory is available or not. If it is available then the status of that particular book in inventory is marked as unavailable. 
    
            API for pricing : 
                
                - updatePrice API : 
                    -  This api is used to update the price of a book. It accepts 
    PriceRequestDto as input which contains information about isbn of the book price to be updated, updated price and date. 
    
    
    
    - getPriceForBook API : 
    
    - This api is used to get the price information of a requested book. It accepts ISBN of a book as input to provide the price information about that particular book.
    
    - API for Media : 
        - get Posts By ISBN API : 
            - This api is used to display the posts which came in the media based on the title and description of media posts. For the given ISBN of a book get the title of the book first. Search the title in media posts. Return all the posts which are having partial match or full match of a given title.
    
    
    
        
                
    
    
                    
    
    
                                   
    
            
