package piglab.service.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

    /**
     * A class to test interactions with the MySQL database using a REST service.
     * Annotation @Controller makes this class a Spring controller = request handler coming from DispatcherServlet
     * @author OTH
     */
    @Controller // Yes, this is a MVC controller
    @SuppressWarnings("unused") //do not display Method Unused warning in case of MVC Controller
    public class UserController
    {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        //DAO declaration. set by Spring Dependency injection in Constructor
        private UserDao userDao=null;

        /**
        * Constructor - Spring DI : only one constructor may be annotated Autowired
        */
        @Autowired // let Spring find the interface implementation
        public UserController(UserDao dao)
        {
            //make sure dao is not null or throw Exception
            Assert.notNull(dao, "userdao cannot be null");
            this.userDao=dao;
            logger.debug("constructor OK");
        }

        /**
         * /create  --> Create a new user and save it in JPA repository
         * @param user user object mapped by Spring from json
         * @return A HTTP response. status CREATED if OK - status 400 BAD REQUEST if Exception occurs
         */
        @RequestMapping(method = RequestMethod.POST, value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<User> createUser(@RequestBody User user) throws Exception
        {
            HttpHeaders headers = new HttpHeaders();
            try
            {
                User u2 = userDao.save(user);
                logger.debug("user created : "+u2.getId());
                return new ResponseEntity<User>(u2, HttpStatus.CREATED);
            }
            catch (Exception e)
            {
                Exception e2 = new Exception(user.getEmail()+"--------"+e.toString());
                throw e2;
            }
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(Exception.class)
        public ResponseEntity conflict(Exception e)
        {
            logger.error(e.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.set("mymessage",e.toString());
            headers.set("user","toto");
            return new ResponseEntity<Void>(headers, HttpStatus.BAD_REQUEST);
        }






        /**
         * /delete  --> Delete the user having the passed id.
         *
         * @param id The id of the user to delete
         * @return A string describing if the user is succesfully deleted or not.
         */
        @RequestMapping("/delete")
        @ResponseBody
        String delete(long id) {
            try {
                User user = new User(id);
                userDao.delete(user);
            }
            catch (Exception ex) {
                return "Error deleting the user: " + ex.toString();
            }
            return "User succesfully deleted!";
        }

        /**
         * /get-by-email  --> Return the id for the user having the passed email.
         *
         * @param email The email to search in the database.
         * @return The user id or a message error if the user is not found.
         */
        @RequestMapping("/get-by-email")
        @ResponseBody
        public String getByEmail(String email) {
            String userId;
            try {
                User user = userDao.findByEmail(email);
                userId = String.valueOf(user.getId());
            }
            catch (Exception ex) {
                return "User not found";
            }
            return "The user id is: " + userId;
        }

        /**
         * /update  --> Update the email and the name for the user in the database
         * having the passed id.
         *
         * @param id The id for the user to update.
         * @param email The new email.
         * @param name The new name.
         * @return A string describing if the user is succesfully updated or not.
         */
        @RequestMapping("/update")
        @ResponseBody
        public String updateUser(long id, String email, String name) {
            try {
                User user = userDao.findOne(id);
                user.setEmail(email);
                user.setLastname(name);
                userDao.save(user);
            }
            catch (Exception ex) {
                return "Error updating the user: " + ex.toString();
            }
            return "User succesfully updated!";
        }



    } // class UserController