/*---------------------------------------------------------
file: Employee.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Employee class to hold all relevant info and methods
---------------------------------------------------------*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee {

  // class fields
  private final StringBuilder name;
  private String username;
  private String email;
  private final String password;

  /**
   * fetches user's name
   * @return user's name
   */

  public StringBuilder getName() {
    return name;
  }

  /**
   * fetches user's username
   * @return user's username
   */

  public String getUsername() {
    return username;
  }

  /**
   * fetches user's password
   * @return user's pw
   */

  public String getPassword() {
    return password;
  }

  /**
   * fetches user's email
   * @return user's email
   */

  public String getEmail() {
    return email;
  }

  /**
   * sets user's username
   */

  private void setUsername(String name) {
    this.username =
        name.toLowerCase().charAt(0) + name.toLowerCase().substring(1 + name.indexOf(" "));
  }

  /**
   * sets user's email
   */

  private void setEmail(String name) {
    this.email = name.toLowerCase().substring(0, name.indexOf(" ")) + "." + name.toLowerCase()
        .substring(1 + name.indexOf(" ")) + "@oracleacademy.Test";
  }

  /**
   * Employee Constructor
   *
   * @param name     The employee's name
   * @param password The employee's pw
   */

  public Employee(String name, String password) {

    if (checkName(name)) {
      this.name = new StringBuilder().append(name);
      setEmail(name);
      setUsername(name);
    } else {
      this.name = new StringBuilder().append(name);
      this.username = "default";
      this.email = "user@oracleacademy.Test";
    }

    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * checks if employee's name contains space
   *
   * @param name The employee's name
   */

  private boolean checkName(String name) {
    boolean flag = false;

    if (name.contains(" ")) {
      flag = true;
    }
    return flag;
  }

  /**
   * checks if employee's pw is valid
   *
   * @param password The employee's pw
   * @return  the methods determination
   */

  public boolean isValidPassword(String password) {
    boolean flag = false;
    boolean hasUppercase = !password.equals(password.toLowerCase());
    boolean hasLowercase = !password.equals(password.toUpperCase());
    Pattern my_pattern = Pattern.compile("[^a-z 0-9]", Pattern.CASE_INSENSITIVE);
    Matcher my_match = my_pattern.matcher(password);
    boolean check = my_match.find();

    if (hasUppercase && hasLowercase && check) {
      flag = true;
    }
    return flag;
  }

  /**
   * generates a string of employee's details
   */

  @Override
  public String toString() {
    return "Employee Details \n" + "Name : " + name + "\n" + "Username : " + username + "\n"
        + "Email : " + email + "\n" + "Initial Password : " + password + "\n\n";
  }


}





