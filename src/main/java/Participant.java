import java.util.Date;
import java.util.Timer;

public class Participant {
    public String name;
    public String firstConnect;
    public String lastDisconnect;
    public String parTime;
    public String UPN;
    public String email;
    public String role;
    public Participant(){

    }

    public Participant(String name, String firstConnect, String lastDisconnect, String parTime, String UPN, String email, String role) {
        this.name = name;
        this.firstConnect = firstConnect;
        this.lastDisconnect = lastDisconnect;
        this.parTime = parTime;
        this.UPN = UPN.isBlank()?"N/A":UPN;
        this.email = email.isBlank()?"N/A":email;
        this.role = role;
    }

    public void showParticipant(){
        System.out.print(name+"         ");
        System.out.print(firstConnect+"         ");
        System.out.print(lastDisconnect+"       ");
        System.out.print(parTime+"         ");
        System.out.print(UPN+"          ");
        System.out.print(email+"        ");
        System.out.print(role+"         "+"\n");
    }
}
