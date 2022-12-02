import Data.DataBaseHandler;
import Data.User;

public class LoginSubSystem {
    private DataBaseHandler dataBaseHandler;
    private Validator validator;
    public LoginSubSystem(DataBaseHandler dataBaseHandler){
        this.dataBaseHandler = dataBaseHandler;
        validator = new Validator();
    }

    public User signIn(String email, String password) throws Exception{
        validator.isEmailValid(email);
        validator.isPasswordValid(password);
        return dataBaseHandler.signIn(email, password);
    }

    public User signUp(String email, String username, String password) throws Exception{
        validator.isEmailValid(email);
        validator.isPasswordValid(password);
        return dataBaseHandler.signUp(email, username, password);
    }

}
