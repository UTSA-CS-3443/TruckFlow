/** @Author: Daniel Barboza
 *  This is the User class in this class it has the attributes username,password,permissions,profilePictureFilePath attributes. Within this class it extends the person class and has two paramartized constructors followed
 *  by getters and setters. And lastly its follwed by a method that loads Users from the data file path and matches up the username and passowrd the corresponds matche that finds in the file.
 */
package edu.utsa.cs3443.truckflow.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class User {

    private String username;
    private String password;


    /** @Author: Daniel Barboza
     * This is a paramertized constructor of the User Class its job is to initalizes the attributes of the class and stores the attributes as userName, password, and permissions since it extends/ inherits person class the super keyword
     * helps with passing the attributes of that class in the constructor. The super keyword passes the attributes first name lastName email and phone number.
     */
    public User(String userName, String password, String s, String username, String string) {
        this.username = userName;
        this.password = password;
    }
    /** @Author: Daniel Barboza
     * This is a paramertized constructor of the User Class its job is to initalizes the attributes of the class and stores the attributes as userName, password, and permissions, and on this constructor
     * it adds one more attribute that it initalizes whuich is profilePictureFilePath. since The userClass extends/inherits person class the super keyword is used on its attrubbutes like firstName,lastName, email, phoneNumber
     * helps with passing the attributes of that class in the constructor.
     */

    /**
     * This is the userName getter it gets the username that is stored in the attribute and returns it back.
     * @return this getter method reutnrs the username that was stored inside of it
     */
    public String getUsername() {
        return username;
    }

    /**
     * This is the setUserName method this method sets the new/ updates the information onto the attribute.
     * @Param in this method it passes userName as a string.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This is the get password method it gets what is inside the attribute of password.
     * @return the method returns password when it is called upon.
     */
    public String getPassword() {
        return password;
    }

    /**
     * This is the set password method it sets the new information onto the attribute.
     * @Param: it passes pasword as a string.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This is the get permissons getter method it gets what is inside this attribute permissions.
     * @param:the method returns the permissons when it is called upon it.
     */


    /**
     * This is the set permissions method it sets new information onto the permissons attributes.
     * @ param method passed permissons as a integer
     */

    /**
     * This is the getNewUserObject getter method in this method it creates a new User each time that its called on each time as mentioned it will create a new user
     * @param:the method passes username as a string
     * @return  the method returns a User object it creates a new User each time when called on
     */
    public static User getNewUserObject(String username) {
        return new User("", "", "", username, "");
    }


    /**
     * This is is a method that loadsUserFile from the data file it loads the user from file and first checks if the userName is empty or null after that is has checked the username it then checks
     * the password is empty and if not it returns null. After that it has checked that its not null a try catch starts working and it then reads the file from the file data users.csv in the try catch
     * it reads the file and in the while loop it checks if the username and passowrd matches up and lastly it returns a user object within the method.
     * @return after that it has checked the file the method returns null if nothing was found and if not return a User object that is returned inside the try catch and while loop
     * @ param the metho passes username and password as a string
     * @throws:RuntimeException if an error occurs while reading the file this is thrown by the catch block
     */
    // static method that loads data from users.csv file
    public static User loadUserFile(String username, String password) {
      // check if username is empyu or null to not return null
        if(username.isEmpty()||username == null ){
            return null;
        }
        // check if password is empty or null
        else if(password.isEmpty() || password==null){
            return  null;
        }
        try(BufferedReader br = new BufferedReader(new FileReader("Data/users.csv"))){
            String line;
            while((line = br.readLine())!=null){
                if(line.endsWith(username+","+password)){
                    return new User("", "", "", username, "");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

































}
