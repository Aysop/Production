import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee {

  StringBuilder name = new StringBuilder();

  String username;

  String password;

  String email;

  public Employee(String name, String password) {

    if (checkName(name)) {
      this.name.append(name);
      setEmail(name);
      setUsername(name);
    } else {
      this.name.append(name);
      this.username = "default";
      this.email = "user@oracleacademy.Test";
    }

    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }

  }

  private void setUsername(String name) {
    this.username =
        name.toLowerCase().charAt(0) + name.toLowerCase().substring(1 + name.indexOf(" "));
  }


  private boolean checkName(String name) {
    boolean flag = false;

    if (name.contains(" ")) {
      flag = true;
    }
    return flag;
  }


  private void setEmail(String name) {
    this.email = name.toLowerCase().substring(0, name.indexOf(" ")) + "." + name.toLowerCase()
        .substring(1 + name.indexOf(" ")) + "@oracleacademy.Test";
  }

  private boolean isValidPassword(String password) {
    boolean flag = false;
    boolean hasUppercase = !password.equals(password.toLowerCase());
    boolean hasLowercase = !password.equals(password.toUpperCase());
    Pattern my_pattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
    Matcher my_match = my_pattern.matcher(password);
    boolean check = my_match.find();

    if (hasUppercase && hasLowercase && check) {
      flag = true;
    }
    return flag;
  }


  @Override
  public String toString() {
    return "Employee Details \n" + "Name : " + name + "\n" + "Username : " + username + "\n"
        + "Email : " + email + "\n" + "Initial Password : " + password;
  }

}



